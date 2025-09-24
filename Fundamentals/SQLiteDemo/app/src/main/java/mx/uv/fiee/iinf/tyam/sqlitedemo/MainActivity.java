package mx.uv.fiee.iinf.tyam.sqlitedemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import mx.uv.fiee.iinf.tyam.sqlitedemo.databinding.ActivityMainBinding;
import mx.uv.fiee.iinf.tyam.sqlitedemo.db.AppDatabase;
import mx.uv.fiee.iinf.tyam.sqlitedemo.entities.UserProfile;
import mx.uv.fiee.iinf.tyam.sqlitedemo.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MM";
    ActivityMainBinding binding;
    AppDatabase db;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        binding = ActivityMainBinding.inflate (getLayoutInflater ());
        db = Room
                .databaseBuilder (this, AppDatabase.class, "userProfile")
                .addMigrations(Utils.UpgradeV2)
                .build ();

        setContentView (binding.getRoot ());
        setSupportActionBar (binding.toolbarMain);

        binding.profilePic.setOnClickListener (v -> {
            openGallery ();
        });

        binding.btnSave.setOnClickListener(v ->
        {
            if (!validateForm ()) {
                return;
            }

            var userProfile = new UserProfile ();
            userProfile.firstName = binding.edtName.getText ().toString ();
            userProfile.lastName = binding.edtLastName.getText ().toString ();
            userProfile.age = Integer.parseInt (binding.edtAge.getText ().toString ());
            userProfile.phone = binding.edtPhone.getText ().toString ();

            CompletableFuture.supplyAsync (() -> saveForm (userProfile))
                    .thenAccept (opt -> {
                        var result = opt.orElse (false);
                        if (result) {
                            Snackbar.make (binding.getRoot (), "User saved", Snackbar.LENGTH_SHORT).show ();
                            savePicture (getContentResolver (), binding.profilePic.getDrawable());
                            clearFields ();
                        } else {
                            Snackbar.make (binding.getRoot (), "Error saving user", Snackbar.LENGTH_SHORT).show ();
                        }
                    });
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

    private Optional<Boolean> saveForm (UserProfile userProfile) {
        try {
            db.userProfileDao ().insertProfile (userProfile);
        }
        catch (Exception e) {
            Log.e (TAG, e.getMessage ());
            return Optional.empty ();
        }

        return Optional.of(true);
    }

    private void clearFields () {
        binding.edtName.setText ("");
        binding.edtLastName.setText ("");
        binding.edtAge.setText ("");
        binding.edtPhone.setText ("");
    }

    private void openGallery () {
        var intent = new Intent ();
        intent.setAction (Intent.ACTION_GET_CONTENT);
        intent.setType ("image/*");
        galleryActivityResult.launch(intent);
    }

    /**
     * Activity Result Launcher for Gallery
     *
     */
    ActivityResultLauncher<Intent> galleryActivityResult = registerForActivityResult (new ActivityResultContracts.StartActivityForResult (), result -> {
        if (result.getResultCode () == RESULT_OK) {
            var uri = result.getData ().getData ();
            InputStream inputStream = null;

            try {
                inputStream = getContentResolver ().openInputStream (uri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            var bitmap = BitmapFactory.decodeStream (inputStream);
            binding.profilePic.setImageBitmap (bitmap);
        }
    });

    private boolean savePicture (ContentResolver contentResolver, Drawable drawable) {
        if (!(drawable instanceof BitmapDrawable)) {
            return false;
        }
        var bitmap = ((BitmapDrawable) drawable).getBitmap();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, UUID.randomUUID().toString() + ".jpg");

        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        if (imageUri == null) {
            return false;
        }

        try (OutputStream outputStream = contentResolver.openOutputStream(imageUri)) {
            if (outputStream == null) {
                return false;
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

