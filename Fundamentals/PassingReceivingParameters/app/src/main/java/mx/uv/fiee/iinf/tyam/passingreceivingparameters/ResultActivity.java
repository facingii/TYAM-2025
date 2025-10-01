package mx.uv.fiee.iinf.tyam.passingreceivingparameters;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.uv.fiee.iinf.tyam.passingreceivingparameters.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = ActivityResultBinding.inflate(getLayoutInflater ());
        setContentView(binding.getRoot());

        binding.btnOk.setOnClickListener(v -> {
            var intent = new Intent();
            intent.putExtra("value", binding.edtValue.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

        binding.btnCancel.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

}
