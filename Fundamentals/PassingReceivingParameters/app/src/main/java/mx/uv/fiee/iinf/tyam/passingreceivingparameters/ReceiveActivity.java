package mx.uv.fiee.iinf.tyam.passingreceivingparameters;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.uv.fiee.iinf.tyam.passingreceivingparameters.databinding.ActivityReceiveBinding;

public class ReceiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = ActivityReceiveBinding.inflate(getLayoutInflater ());
        setContentView(binding.getRoot());

        var intent = getIntent ();
        if (intent != null) {
            var name = intent.getStringExtra("name");
            var age = intent.getIntExtra("age", 0);
            var address = intent.getStringExtra("address");
            var phone = intent.getStringExtra("phone");
            var email = intent.getStringExtra("email");

            binding.tvName.setText(name);
            binding.tvAge.setText(String.valueOf(age));
            binding.tvAddress.setText(address);
            binding.tvPhone.setText(phone);
            binding.tvEmail.setText(email);
        }
    }
}
