package mx.uv.fiee.iinf.tyam.sqlitedemo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import mx.uv.fiee.iinf.tyam.sqlitedemo.databinding.ActivityDetailsBinding;
import mx.uv.fiee.iinf.tyam.sqlitedemo.db.AppDatabase;
import mx.uv.fiee.iinf.tyam.sqlitedemo.entities.UserProfile;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    private AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        db = Room.databaseBuilder (this, AppDatabase.class, "userProfile").build ();

        setContentView (binding.getRoot ());
        setSupportActionBar (binding.toolbarDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled (true);
        getSupportActionBar().setDisplayShowHomeEnabled (true);

        CompletableFuture.supplyAsync (this::loadProfileDetails)
                .thenAccept (opt -> {
                    opt.ifPresent (profile -> setProfile (profile));
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private Optional<UserProfile> loadProfileDetails ()
    {
        return db.userProfileDao ()
                .getAllProfiles ()
                .stream ()
                .sorted ((u1, u2) -> u1.compareTo (u2))
                .findFirst ();
    }

    private void setProfile (UserProfile profile)
    {
        binding.tvName.setText (profile.firstName);
        binding.tvLastName.setText (profile.lastName);
        binding.tvAge.setText (String.valueOf (profile.age));
        binding.tvPhone.setText (profile.phone);
    }
}
