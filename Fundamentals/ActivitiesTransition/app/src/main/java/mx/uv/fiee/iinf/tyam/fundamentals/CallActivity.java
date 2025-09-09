package mx.uv.fiee.iinf.tyam.fundamentals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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

        // Configura la Toolbar y el título.
        setSupportActionBar(binding.callToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.call_title);

        binding.btnCall.setOnClickListener(v -> {
            var uri = Uri.parse("tel:1234567890");
            var intent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(intent);
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

    /**
     * Maneja la selección de un elemento del navigation drawer.
     * @param item The menu item that was selected.
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
