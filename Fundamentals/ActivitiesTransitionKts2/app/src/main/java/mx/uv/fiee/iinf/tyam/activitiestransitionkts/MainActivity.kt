package mx.uv.fiee.iinf.tyam.activitiestransitionkts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity () {

    fun openBrowseActivity () {
        val intent = Intent(this, BrowseActivity::class.java)
        startActivity (intent)
    }

    fun openOpenCallActivity () {
        val intent = Intent(this, CallActivity::class.java)
        startActivity (intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuPhoneCall -> {
                if (!checkForPermission()) {
                    requestPhoneCallPermission ()
                    return false
                }

                openOpenCallActivity ()
                return true
            }
            R.id.mnuBrowse -> {
                openBrowseActivity ()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun checkForPermission (): Boolean
    {
        val permission = ContextCompat
            .checkSelfPermission(this, Manifest.permission.CALL_PHONE)

        return permission == PackageManager.PERMISSION_GRANTED
    }

    fun requestPhoneCallPermission ()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            return
        }

        requestPermissionLauncher.launch (Manifest.permission.CALL_PHONE)
    }

    val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openOpenCallActivity ()
            }
        }
}