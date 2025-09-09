package mx.uv.fiee.iinf.tyam.fundamentals;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.uv.fiee.iinf.tyam.fundamentals.databinding.ActivityBrowseBinding;

/**
 * Actividad que muestra una página web utilizando un WebView.
 * <p>
 * Puntos clave:
 * <ul>
 *     <li>Utiliza View Binding para interactuar con las vistas.</li>
 *     <li>Carga la URL "https://developer.android.com" en un WebView al crearse.</li>
 * </ul>
 */
public class BrowseActivity extends AppCompatActivity {

    ActivityBrowseBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityBrowseBinding.inflate (getLayoutInflater ());
        setContentView (binding.getRoot ());

        // Configura la Toolbar y el título.
        setSupportActionBar(binding.browseToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(R.string.browse);

        // Carga una URL predeterminada en el WebView.
        binding.browser.loadUrl("https://developer.android.com");
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
            finish ();
        }

        return super.onOptionsItemSelected(item);
    }
}
