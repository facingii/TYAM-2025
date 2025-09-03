package mx.uv.fiee.iinf.tyam.fundamentals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import mx.uv.fiee.iinf.tyam.fundamentals.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    /**
     * Abre la actividad de llamadas telefónicas.
     */
    private void openCallActivity ()
    {
        Intent intent = new Intent(MainActivity.this, CallActivity.class);
        startActivity(intent);
    }

    /**
     * Abre la actividad de navegación web.
     */
    private void openBrowseActivity ()
    {
        Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot ());

        setSupportActionBar(binding.toolbar);
        setTitle (R.string.app_title);
    }

    /**
     * Crea el menú de opciones.
     * @param menu El menú de opciones a crear
     *
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.main, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Maneja la selección de un elemento del menú.
     * @param item El elemento del menú seleccionado
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == R.id.mnuPhoneCall) {
            if (!CheckCallPermission ()) {
                RequestPhoneCallPermission ();
                return false;
            }

            openCallActivity ();
            return true;
        }

        if (item.getItemId () == R.id.mnuBrowse) {
            openBrowseActivity ();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Verifica si la aplicación tiene el permiso para realizar llamadas telefónicas.
     * @return {@code true} si el permiso está concedido, {@code false} en caso contrario.
     */
    private boolean CheckCallPermission ()
    {
        var permission = ContextCompat
                .checkSelfPermission (this, Manifest.permission.CALL_PHONE);

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Solicita el permiso para realizar llamadas telefónicas al usuario.
     * Si el usuario ya ha denegado el permiso anteriormente y no seleccionó "No volver a preguntar",
     * esta función no hace nada, asumiendo que se mostrará una justificación en la UI.
     * En caso contrario, lanza la solicitud de permiso.
     */
    private void RequestPhoneCallPermission ()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale (this,
                Manifest.permission.CALL_PHONE)) {
            // Se podría mostrar una explicación al usuario aquí si es necesario.
            return;
        }

        requestPermissionLauncher.launch (Manifest.permission.CALL_PHONE);
    }

    /**
     * Contrato para solicitar un permiso.
     * Cuando el usuario responde a la solicitud, si el permiso es concedido (isGranted = true),
     * se procede a abrir la {@link CallActivity}.
     */
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult (new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openCallActivity ();
                }
            });

}
