package mx.uv.fiee.iinf.tyam.backgroundtasksapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Un ejemplo de JobIntentService para realizar tareas en segundo plano.
 * Este servicio maneja las solicitudes de trabajo en un hilo de fondo.
 */
public class MyJobIntentService extends JobIntentService {
    private static final int JOB_ID = 890412;
    private static final String TAG = "MarJul";
    public static final String ACTION_RESULT = "mx.uv.fiee.iinf.tyam.backgroundtasksapp.ACTION_RESULT";
    public static final String EXTRA_RESULT = "result";

    /**
     * Encola el trabajo para ser manejado por este servicio.
     *
     * @param context
     * @param intent
     */
    public static void enqueueWork (Context context, Intent intent) {
        enqueueWork (context, MyJobIntentService.class, JOB_ID, intent);
    }

    /**
     * Maneja el trabajo en un hilo en segundo plano
     *
     *
     * @param intent The intent describing the work to now be processed.
     */
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        var urlTarget = intent.getStringExtra("urlTarget");

        Log.d(TAG, "Thread: " + Thread.currentThread().getName());
        Log.d(TAG, "Starting JobIntentService");
        try {
            URL url = new URL(urlTarget);
            URLConnection connection = url.openConnection ();
            if (connection instanceof HttpURLConnection httpConnection) {
                int responseCode = httpConnection.getResponseCode();
                Log.d(TAG, "Response Code: " + responseCode);

                // Send broadcast with result
                Intent resultIntent = new Intent(ACTION_RESULT);
                resultIntent.putExtra(EXTRA_RESULT, responseCode);
                sendBroadcast(resultIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error in JobIntentService", e);
            // Send broadcast with error
            Intent resultIntent = new Intent(ACTION_RESULT);
            resultIntent.putExtra(EXTRA_RESULT, -1);
            sendBroadcast(resultIntent);
        }
        Log.d(TAG, "Finishing JobIntentService");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Destroying JobIntentService");
    }
}
