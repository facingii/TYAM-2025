package mx.uv.fiee.iinf.tyam.broadcastreceiversapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBattery;
    private BatteryLevelReceiver batteryLevelReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivBattery = findViewById(R.id.batteryLevel);
        batteryLevelReceiver = new BatteryLevelReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryLevelReceiver);
    }

    class BatteryLevelReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float)scale;

            if (batteryPct <= 20) {
                ivBattery.setImageResource(R.drawable.battery_level_0);
            } else if (batteryPct <= 40) {
                ivBattery.setImageResource(R.drawable.battery_level_1);
            } else if (batteryPct <= 60) {
                ivBattery.setImageResource(R.drawable.battery_level_2);
            } else if (batteryPct <= 80) {
                ivBattery.setImageResource(R.drawable.battery_level_3);
            } else {
                ivBattery.setImageResource(R.drawable.battery_level_4);
            }
        }
    }
}
