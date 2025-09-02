package mx.uv.fiee.iinf.tyam.fundamentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.uv.fiee.iinf.tyam.fundamentals.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate (getLayoutInflater ());
        setContentView(binding.getRoot ());

        binding.btnActivity2.setOnClickListener(v -> {
            //Intent intent = new Intent (this, SecondActivity.class);

            var phoneNumber = Uri.parse("tel:2941054453");
            var intent = new Intent (Intent.ACTION_CALL, phoneNumber);
            startActivity(intent);
        });
    }
}
