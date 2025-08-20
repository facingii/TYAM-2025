package com.outlook.gonzasosa.activitylifecycle;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "PKAT";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Log.d (TAG, "onCreate");
    }

    @Override
    protected void onStart () {
        super.onStart ();
        Log.d (TAG, "onStart");
    }

    @Override
    protected void onResume () {
        super.onResume ();
        Log.d (TAG, "onResume");
    }

    @Override
    protected void onPause () {
        super.onPause ();
        Log.d (TAG, "onPause");
    }

    @Override
    protected void onStop () {
        super.onStop ();
        Log.d (TAG, "onStop");
    }

    @Override
    protected void onRestart () {
        super.onRestart ();
        Log.d (TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
