package mx.uv.fiee.iinf.tyam.fundamentals;

import android.os.Bundle;

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

        // Carga una URL predeterminada en el WebView.
        binding.browser.loadUrl("https://developer.android.com");
    }

}
