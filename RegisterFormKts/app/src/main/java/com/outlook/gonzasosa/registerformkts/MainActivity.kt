package com.outlook.gonzasosa.registerformkts

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView (R.layout.activity_main)

        val firstName = findViewById<EditText>(R.id.edtName)

        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            val firstName = firstName.text.toString()
            Toast.makeText(this, firstName, Toast.LENGTH_SHORT).show()
        }
    }

}