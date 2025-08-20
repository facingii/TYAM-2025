package com.outlook.gonzasosa.activitylifecyclekts

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val tag = "PKAT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "OnCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
        
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "OnPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "OnRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "OnDestroy")
    }

}