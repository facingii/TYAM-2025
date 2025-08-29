package com.outlook.gonzasosa.registerformkts

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider

import com.outlook.gonzasosa.registerformkts.databinding.ActivityMainBinding
import com.outlook.gonzasosa.registerformkts.viewmodel.FormViewModel

class MainActivity : AppCompatActivity() {
    private val registerForm: String = "RegisterForm"
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[FormViewModel::class]

        setContentView (binding.root)
        setSupportActionBar(binding.toolbar)

        loadPreferences()

        binding.btnSave.setOnClickListener {
            val firstName = binding.edtName.text.toString()
            Toast.makeText(this, firstName, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        binding.edtName.setText (viewModel.firstName)
        binding.edtLastName.setText (viewModel.lastName)
        binding.edtAge.setText (viewModel.age.toString())
        binding.edtPhone.setText (viewModel.phone)
    }

    override fun onPause() {
        super.onPause()

        val age = if (binding.edtName.text.toString().isEmpty()) 0
        else binding.edtAge.text.toString().toInt()

        viewModel.firstName = binding.edtName.text.toString ()
        viewModel.lastName = binding.edtLastName.text.toString ()
        viewModel.age = age
        viewModel.phone = binding.edtPhone.text.toString ()
    }

    override fun onStop() {
        super.onStop()

        val sharedPreferences = getSharedPreferences(registerForm, MODE_PRIVATE)
        sharedPreferences.edit {
            putString ("firstName", viewModel.firstName)
            putString ("lastName", viewModel.lastName)
            putInt ("age", viewModel.age)
            putString ("phone", viewModel.phone)

            apply()
        }
    }

    fun loadPreferences () {
        val sharedPreferences = getSharedPreferences(registerForm, MODE_PRIVATE)
        val firstName = sharedPreferences.getString("firstName", "")
        val lastName = sharedPreferences.getString("lastName", "")
        val age = sharedPreferences.getInt("age", 0)
        val phone = sharedPreferences.getString("phone", "")

        viewModel.firstName = firstName!!
        viewModel.lastName = lastName!!
        viewModel.age = age!!
        viewModel.phone = phone!!
    }

}