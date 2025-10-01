package mx.uv.fiee.iinf.tyam.foregroundserviceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import mx.uv.fiee.iinf.tyam.foregroundserviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String FILTER_TAG = "GETTINGDATAFROMSERVICE";
    private static final String TAG = "MarJul";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityMainBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

        Intent intent = getIntent ();
        if (intent != null && intent.getExtras () != null)
        {
            String data = intent.hasExtra (FILTER_TAG) ? intent.getStringExtra (FILTER_TAG) : "";
            Log.d (TAG, data);
        }

        binding.startService.setOnClickListener (view -> {
            if (checkForPermissions ()) {
                launchService ();
            }
        });
    }

    private boolean checkForPermissions ()
    {
        int permission = checkSelfPermission (Manifest.permission.POST_NOTIFICATIONS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermission (Manifest.permission.POST_NOTIFICATIONS);
            return false;
        }

        permission = checkSelfPermission (Manifest.permission.FOREGROUND_SERVICE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.FOREGROUND_SERVICE);
            return false;
        }

        return true;
    }

    private void requestPermission (String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
        {
            return;
        }

        requestPermissionLauncher.launch(permission);
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult (new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Snackbar.make (binding.getRoot (), "Permission granted", Snackbar.LENGTH_SHORT).show ();
                }
            });
    private void launchService () {
        Intent intent = new Intent(getBaseContext (), MyForegroundService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService (intent);
        } else {
            startService (intent);
        }
    }
}