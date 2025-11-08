package mx.uv.fiee.iinf.tyam.fbrealtimedatatabaseapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import mx.uv.fiee.iinf.tyam.fbrealtimedatatabaseapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MarJul";
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        auth.signInAnonymously().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MainActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                GetData();
            } else {
                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu once when the options menu is created. Avoid inflating in onPrepareOptionsMenu
        // because that method is called repeatedly and will append menu items multiple times.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == R.id.mnuAdd) {
            AddSong ();
            return true;
        }

        return super.onOptionsItemSelected (item);
    }

    private void GetData() {
        DatabaseReference databaseReference = database.getReference("/songs/artist");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item: snapshot.getChildren ()) {
                    Log.d (TAG, item.getKey () + " - " +  item.getValue ().toString ());

//                    var title = (Map<String, Object>) item.getValue();
//                    for (Map.Entry<String, Object> entry : title.entrySet()) {
//                        Log.d (TAG, "Title: " + entry.getKey ());
//                        Log.d (TAG, "Value: " + entry.getValue ());
//                    }

//                    for (DataSnapshot songItem: item.getChildren ()) {
//                        String title = songItem.getValue (String.class);
//                        Log.d (TAG, "Song: " + title);
//                    }

                    for (DataSnapshot albumItem: item.getChildren ()) {
                        for (DataSnapshot trackItem: albumItem.getChildren ()) {
                            Track track = trackItem.getValue (Track.class);
                            if (track != null) {
                                Log.d (TAG, "Title: " + track.Title);
                                Log.d (TAG, "Genre: " + track.Genre);
                                Log.d (TAG, "Duration: " + track.Duration);
                            }
                        }
                    }
                    Log.d(TAG, "---------------------");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void AddSong() {
        var database = FirebaseDatabase.getInstance();
        var album = "Album " + ((int)(Math.random() * 5) + 1);
        var artist = "Artist " + ((int)(Math.random() * 5) + 1);
        var path = String.format ("/songs/artist/%s/%s", artist, album);
        DatabaseReference databaseReference = database.getReference(path);

        Track newTrack = new Track ();
        newTrack.Title = "New Song " + UUID.randomUUID ();
        newTrack.Genre = "Genre X";
        newTrack.Duration = 210;

        String key = databaseReference.push().getKey();
        if (key != null) {
            databaseReference.child(key).setValue(newTrack);
        }
    }
}

class Track {
    @PropertyName("title")
    public String Title;
    @PropertyName("genre")
    public String Genre;
    @PropertyName("duration")
    public int Duration;
}