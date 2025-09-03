package mx.uv.fiee.iinf.tyam.fundamentals;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.uv.fiee.iinf.tyam.fundamentals.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivitySecondBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

        binding.btnTH.setOnClickListener (v -> {
            Intent intent = new Intent (SecondActivity.this, ThirdActivity.class);
            startActivity (intent);
        });
    }

}
