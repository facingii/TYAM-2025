package mx.uv.fiee.iinf.tyam.activitiestransitionkts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mx.uv.fiee.iinf.tyam.activitiestransitionkts.databinding.ActivityBrowseBinding

class BrowseActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val binding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.browser.loadUrl("https:dev.android.com")
    }

}