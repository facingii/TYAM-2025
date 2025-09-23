package mx.uv.fiee.iinf.tyam.sqlitedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import mx.uv.fiee.iinf.tyam.sqlitedemo.databinding.ActivityMainBinding;
import mx.uv.fiee.iinf.tyam.sqlitedemo.db.AppDatabase;
import mx.uv.fiee.iinf.tyam.sqlitedemo.entities.UserProfile;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MM";
    ActivityMainBinding binding;
    AppDatabase db;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        binding = ActivityMainBinding.inflate (getLayoutInflater ());
        db = Room.databaseBuilder (this, AppDatabase.class, "userProfile").build ();

        setContentView (binding.getRoot ());
        setSupportActionBar (binding.toolbarMain);

        binding.btnSave.setOnClickListener(v ->
        {
            if (!validateForm ()) {
                return;
            }

            if (saveForm ()) {
                Snackbar.make (binding.getRoot (), "User saved", Snackbar.LENGTH_SHORT).show ();
                clearFields ();
            } else {
                Snackbar.make (binding.getRoot (), "Error saving user", Snackbar.LENGTH_SHORT).show ();
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        getMenuInflater().inflate (R.menu.menu, menu);
        return super.onPrepareOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {
        if (item.getItemId () == R.id.mnuDetails)
        {
            goToDetails ();
        }

        return super.onOptionsItemSelected (item);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void goToDetails () {
        var intent = new Intent (this, DetailsActivity.class);
        startActivity (intent);
    }

    private boolean validateForm () {
        if (binding.edtName.getText ().toString ().isEmpty ()) {
            binding.edtName.setError ("Name is required");
            return false;
        };
        if (binding.edtLastName.getText ().toString ().isEmpty ()) {
            binding.edtLastName.setError ("Last name is required");
            return false;
        };
        if (binding.edtAge.getText ().toString ().isEmpty ()) {
            binding.edtAge.setError ("Age is required");
            return false;
        };
        if (binding.edtPhone.getText ().toString ().isEmpty ()) {
            binding.edtPhone.setError ("Phone is required");
            return false;
        };
        if (binding.edtAge.getText ().toString ().isEmpty ()) {
            binding.edtAge.setError ("Age is required");
            return false;
        };

        return true;
    }

    private boolean saveForm () {
        var userProfile = new UserProfile ();
        userProfile.firstName = binding.edtName.getText ().toString ();
        userProfile.lastName = binding.edtLastName.getText ().toString ();
        userProfile.age = Integer.parseInt (binding.edtAge.getText ().toString ());
        userProfile.phone = binding.edtPhone.getText ().toString ();

        try {
            new Thread (() -> db.userProfileDao ().insertProfile (userProfile))
                    .start ();
        }
        catch (Exception e) {
            Log.e (TAG, e.getMessage ());
            return false;
        }

        return true;
    }

    private void clearFields () {
        binding.edtName.setText ("");
        binding.edtLastName.setText ("");
        binding.edtAge.setText ("");
        binding.edtPhone.setText ("");
    }
}

