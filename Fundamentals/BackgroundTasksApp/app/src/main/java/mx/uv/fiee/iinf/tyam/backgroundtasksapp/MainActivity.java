package mx.uv.fiee.iinf.tyam.backgroundtasksapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import mx.uv.fiee.iinf.tyam.backgroundtasksapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra(MyJobIntentService.EXTRA_RESULT, -1);
            String message = result != -1 ?
                "Service completed with response code: " + result :
                "Service failed";
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnJobIntentService.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyJobIntentService.class);
            intent.putExtra("urlTarget", "https://www.uv.mx");
            MyJobIntentService.enqueueWork(this, intent);
        });

        binding.btnWorker.setOnClickListener(v -> {
            Data inputData = new Data.Builder()
                .putString("urlTarget", "https://www.uv.mx")
                .build();

            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(inputData)
                .build();

            WorkManager.getInstance(this).enqueue(workRequest);

            // Observe the work status
            WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null) {
                        if (workInfo.getState().isFinished()) {
                            String message = workInfo.getState() == WorkInfo.State.SUCCEEDED ?
                                "Worker completed successfully" :
                                "Worker failed";
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,
            new IntentFilter(MyJobIntentService.ACTION_RESULT),
            Context.RECEIVER_EXPORTED
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
