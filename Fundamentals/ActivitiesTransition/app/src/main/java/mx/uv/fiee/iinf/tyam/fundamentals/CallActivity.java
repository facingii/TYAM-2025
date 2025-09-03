package mx.uv.fiee.iinf.tyam.fundamentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.uv.fiee.iinf.tyam.fundamentals.databinding.ActivityCallBinding;

public class CallActivity extends AppCompatActivity {

    ActivityCallBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityCallBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

        binding.btnCall.setOnClickListener(v -> {
        });

        /**
         * Abre una página web en el navegador predeterminado.
         */
        binding.btnBrowse.setOnClickListener(v -> {
            var webpage = Uri.parse("https://www.android.com");
            var intent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(intent);
        });

        /**
         * Abre una aplicación de correo electrónico.
         */
        binding.btnEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jan@example.com"}); // recipients
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));

            //startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            startActivity(emailIntent);
        });
    }

}
