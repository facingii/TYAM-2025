package mx.uv.fiee.iinf.tyam.passingreceivingparameters;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import mx.uv.fiee.iinf.tyam.passingreceivingparameters.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater ());
        setContentView (binding.getRoot ());

        binding.btnSend.setOnClickListener (v -> {
           var intent = new Intent(this, ReceiveActivity.class);
           intent.putExtra("name", "Grissel Trinidad");
           intent.putExtra("age", 27);
           intent.putExtra("address", "Av. 5 de Mayo");
           intent.putExtra("phone", "5512345678");
           intent.putExtra("email", "grissel.trinidad@mailinator.com");
           startActivity(intent);
        });

        binding.btnReceive.setOnClickListener (v -> {
            var intent = new Intent(this, ResultActivity.class);
            resultLauncher.launch(intent);
        });
    }

    private final ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if ( result.getResultCode() == RESULT_OK) {
                    var resultIntent = result.getData ();
                    var val = resultIntent.getStringExtra ("value");
                    if (val != null) {
                        Snackbar.make(binding.getRoot(), val, Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Snackbar.make(binding.getRoot(), "Canceled", Snackbar.LENGTH_LONG).show();
                }
            });

}
