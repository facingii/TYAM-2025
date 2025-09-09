package mx.uv.fiee.iinf.tyam.activitiestransitionkts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mx.uv.fiee.iinf.tyam.activitiestransitionkts.databinding.ActivityCallBinding
import androidx.core.net.toUri

class CallActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCall.setOnClickListener {

        }

        binding.btnBrowse.setOnClickListener {
            val webpage = "https://www.android.com".toUri ()
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity (intent)
        }

        binding.btnEmail.setOnClickListener {
            val emailIntent = Intent (Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("john.mclean@examplepetstore.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Email Body")
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))

            startActivity(emailIntent)
        }
    }

}