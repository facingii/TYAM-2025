package mx.uv.fiee.iinf.tyam.basicserviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    public static final String TAG = "MarJul";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d (TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate () {
        super.onCreate();
        Log.d (TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d (TAG, "onStartCommand");

        long startTime = intent.getLongExtra ("TIME", 0);
        doDownload (startTime);

        return START_STICKY;
        //return super.onStartCommand (intent, flags, startId);
    }

    // long process
    void doDownload (long time) {
        Log.d ("TAG", "On download method...");
        try {
            Thread.sleep(5000); // stopping thread
            Log.i(TAG, "Thread name " + Thread.currentThread().getName());
        } catch (Exception ex) { ex.printStackTrace (); }

        Intent intent = new Intent ("MarJulMar"); //package result
        intent.putExtra ("FINISH", System.currentTimeMillis ());
        sendBroadcast (intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d (TAG, "onDestroy");
    }
}