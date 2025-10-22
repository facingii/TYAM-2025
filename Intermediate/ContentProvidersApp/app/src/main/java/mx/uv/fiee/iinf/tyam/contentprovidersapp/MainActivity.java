package mx.uv.fiee.iinf.tyam.contentprovidersapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;

import mx.uv.fiee.iinf.tyam.contentprovidersapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MarJul";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        int perm = ContextCompat.checkSelfPermission (getBaseContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (perm != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestReadPermissionLauncher.launch (Manifest.permission.READ_MEDIA_AUDIO);
            } else {
                requestReadPermissionLauncher.launch (Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            return;
        }

        loadAudios ();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater ().inflate (R.menu.main, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == R.id.mnuInsertAudio) {
            try {
                if (!hasWritePermission ()) {
                    requestWritePermissionLauncher.launch (Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    return true;
                }
                insertAudio ();
            } catch (Exception ex) {
                ex.printStackTrace ();
            }
        } else if (item.getItemId () == R.id.mnuDeleteAudio) {
            deleteAudio ();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean hasWritePermission () {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            return true;
        }

        int permission = ContextCompat.checkSelfPermission (getBaseContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    // consultas
    void loadAudios () {
        // información a recuperar
        String[] columns = {MediaStore.Audio.Media._ID, MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Media.DISPLAY_NAME};
        String order = MediaStore.Audio.Media.DEFAULT_SORT_ORDER; // orden
        String selection = null; //MediaStore.Audio.Media.DISPLAY_NAME + " LIKE ?";
        String [] selectionArgs = null;//{ "%Robot%" };

        // SELECT MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Media.ALBUM
        // FROM MediaStore.Audio.Media.EXTERNAL_CONTENT_URI ORDER BY MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        Cursor cursor = getBaseContext ()
                .getContentResolver ()
                .query (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, columns, selection, selectionArgs, order);

        if (cursor == null) return;
        if (cursor.getCount () == 0) {
            cursor.close ();
            Log.i (TAG, "No media audio was found!");
            return;
        }

        var artists = new LinkedList<String>();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            int index = cursor.getColumnIndexOrThrow (MediaStore.Audio.Media.DISPLAY_NAME);
            String artist = cursor.getString (index);

            artists.add (artist);
        }

        cursor.close ();

        var adapter = new ArrayAdapter<String>(
                getBaseContext (),
                android.R.layout.simple_list_item_1,
                artists
        );

        binding.audioList.setAdapter (adapter);
    }

    void insertAudio () throws FileNotFoundException, IOException, IntentSender.SendIntentException {
        String filename = "Go_Robot_3" + UUID.randomUUID() + ".mp3";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.AudioColumns.TITLE, filename);
        values.put(MediaStore.Audio.AudioColumns.DISPLAY_NAME, filename);
        values.put(MediaStore.Audio.AudioColumns.MIME_TYPE, "audio/mpeg");
        values.put(MediaStore.Audio.AudioColumns.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Audio.AudioColumns.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_MUSIC);

        Uri uriObject = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            uriObject = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);

            IntentSender intentSender = MediaStore.createWriteRequest(
                    getContentResolver(),
                    Collections.singletonList(uriObject)).getIntentSender();

            requestIntentSenderLauncher.launch(new IntentSenderRequest.Builder(intentSender).build());
            return;
        }

        insertAudio(filename);
    }
    public void insertAudio (String filename) throws IOException {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.AudioColumns.TITLE, filename);
        values.put(MediaStore.Audio.AudioColumns.DISPLAY_NAME, filename);
        values.put(MediaStore.Audio.AudioColumns.MIME_TYPE, "audio/mpeg");
        values.put(MediaStore.Audio.AudioColumns.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Audio.AudioColumns.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_MUSIC);
        values.put (MediaStore.Audio.Media.IS_PENDING, true); // importante!
        // /mnt/volume/Music/go_robot.mp3
        Uri uriObject = getContentResolver().insert (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
        if (uriObject != null) {
            OutputStream fos = getContentResolver().openOutputStream (uriObject);
            InputStream fis = getResources().openRawResource (R.raw.go_robot);

            int bytesRead;
            int bufferSize = 1024;
            byte [] buffer = new byte [bufferSize];

            while ((bytesRead = fis.read (buffer, 0, bufferSize)) > -1) {
                fos.write (buffer, 0, bytesRead);
            }

            fos.flush ();
            fos.close ();

            values.clear ();
            values.put (MediaStore.Audio.AudioColumns.IS_PENDING, false); //importante
            getContentResolver().update(uriObject, values, null, null);

            Log.i(TAG, "Inserted!");
            Snackbar.make (binding.getRoot(), "Inserted!", Snackbar.LENGTH_SHORT).show();
        } else {
            Log.e (TAG, "Insertion Failed!");
            Snackbar.make (binding.getRoot(), "Insertion Failed!", Snackbar.LENGTH_SHORT).show();
        }
    }

    void deleteAudio () {
        String selection = MediaStore.Audio.Media.DISPLAY_NAME + " LIKE ?";
        String [] selectionArgs = new String [1];
        selectionArgs[0] = "%Go_Robot%.mp3";

        // DELETE FROM MediaStore.Audio.Media.EXTERNAL_CONTENT_URI WHERE MediaStore.Audio.Media.DISPLAY_NAME LIKE 'Go_Robot_3.mp3'
        int rows = getContentResolver().delete (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);

        if (rows > 0) {
            Log.i (TAG, "Deleted!");
            Snackbar.make (binding.getRoot(), "Audio Deleted!", Snackbar.LENGTH_SHORT).show();
        } else {
            Log.i (TAG, "Not found!");
            Snackbar.make (binding.getRoot(), "File Not found!", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Permission request callback
     */
    private final ActivityResultLauncher<String> requestReadPermissionLauncher =
             registerForActivityResult (new androidx.activity.result.contract.ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    loadAudios();
                }
            });

    private final ActivityResultLauncher<String> requestWritePermissionLauncher =
            registerForActivityResult (new androidx.activity.result.contract.ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    try {
                        insertAudio ();
                    } catch (Exception ex) {
                        ex.printStackTrace ();
                    }
                }
            });

    private final ActivityResultLauncher<IntentSenderRequest> requestIntentSenderLauncher =
            registerForActivityResult (new androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult(), result -> {
                if (result.getResultCode () == RESULT_OK) {
                    Log.i (TAG, "Intent Sender OK");
                    try {
                        insertAudio ("Go_Robot_3.mp3");
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                } else {
                    Log.i (TAG, "Intent Sender Canceled");
                }
            });

}
