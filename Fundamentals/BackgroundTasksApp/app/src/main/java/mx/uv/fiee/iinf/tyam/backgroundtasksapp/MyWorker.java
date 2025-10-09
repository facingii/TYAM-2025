package mx.uv.fiee.iinf.tyam.backgroundtasksapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Un ejemplo de Worker para realizar tareas en segundo plano.
 * Este worker maneja las solicitudes de trabajo en un hilo de fondo.
 */
public class MyWorker extends Worker {
    private static final String TAG = "MarJul";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * Maneja el trabajo en un hilo en segundo plano
     *
     * @return
     */
    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Thread: " + Thread.currentThread().getName());
        String urlTarget = getInputData().getString("urlTarget");
        if (urlTarget == null) {
            return Result.failure();
        }

        Log.d(TAG, "Starting Worker");
        try {
            URL url = new URL(urlTarget);
            URLConnection connection = url.openConnection();
            if (connection instanceof HttpURLConnection httpConnection) {
                int responseCode = httpConnection.getResponseCode();
                Log.d(TAG, "Response Code: " + responseCode);
                return Result.success();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in Worker", e);
            return Result.failure();
        }
        Log.d(TAG, "Finishing Worker");
        return Result.success();
    }
}
