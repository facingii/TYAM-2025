package mx.uv.fiee.iinf.tyam.basicserviceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.uv.fiee.iinf.tyam.basicserviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String INTENT_FILTER = "MarJulMar";
    ActivityMainBinding binding;
    Intent intent;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        var binding = ActivityMainBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

        Log.i("MarJul", "Thread name: "+ Thread.currentThread().getName());

        binding.startService.setOnClickListener (view -> {
            intent = new Intent (getBaseContext (), MyService.class);
            intent.putExtra ("TIME", System.currentTimeMillis ());

            startService (intent);
        });

        binding.stopService.setOnClickListener (view -> {
            if (intent != null) stopService (intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver (receiver, new IntentFilter(INTENT_FILTER), Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (receiver != null) {
            unregisterReceiver (receiver);
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver () {
        @Override
        public void onReceive (Context context, Intent intent) {
            Log.d ("MARJUL", "Receiving...");
            if (intent == null) return;

            var timeFinish = intent.hasExtra("FINISH") ? intent.getLongExtra("FINISH", 0L) : 0L;
            Toast.makeText (getBaseContext (), "Finish time is " + timeFinish, Toast.LENGTH_LONG).show();
        }
    };
}