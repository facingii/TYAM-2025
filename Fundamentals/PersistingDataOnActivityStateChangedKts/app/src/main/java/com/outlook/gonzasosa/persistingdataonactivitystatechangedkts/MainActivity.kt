package com.outlook.gonzasosa.persistingdataonactivitystatechangedkts

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.outlook.gonzasosa.persistingdataonactivitystatechangedkts.vm.FormDataViewModel

class MainActivity : AppCompatActivity()  {
    val TAG: String = "Mrth"
    val EditTextKey: String = "G"
    val CheckBoxKey: String = "P"

    var et: EditText? = null
    var cb: CheckBox? = null

    var vm: FormDataViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        // create and link ViewModel
        vm = ViewModelProvider (this)[FormDataViewModel::class]

        setContentView (createView ())
    }

    fun createView (): View {
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams (
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER_VERTICAL.toFloat ()
        )

        val layout: LinearLayout = LinearLayout (this)
        layout.layoutParams = params
        layout.orientation = LinearLayout.VERTICAL

        val componentParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams (
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        cb = CheckBox (this)
        cb!!.layoutParams = componentParams
        cb!!.text = "Check me!"
        cb!!.textSize = 20.0f

        et = EditText(this)
        et!!.layoutParams = componentParams
        et!!.hint = "Type something..."
        et!!.textSize = 20.0f
        et!!.gravity = Gravity.CENTER

        layout.addView (cb)
        layout.addView (et)

        return layout
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState (outState)
//
//        outState.putString (EditTextKey, et!!.text.toString ())
//        outState.putBoolean (CheckBoxKey, cb!!.isChecked)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//        et!!.setText (savedInstanceState.getString (EditTextKey))
//        cb!!.isChecked = savedInstanceState.getBoolean (CheckBoxKey)
//    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")

        et!!.setText (vm!!.editTextValue)
        cb!!.isChecked = vm!!.checkBoxValue
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")

        vm!!.editTextValue = et!!.text.toString ()
        vm!!.checkBoxValue = cb!!.isChecked
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}