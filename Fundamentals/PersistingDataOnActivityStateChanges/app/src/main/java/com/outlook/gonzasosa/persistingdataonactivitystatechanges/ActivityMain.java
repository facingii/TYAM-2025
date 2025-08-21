package com.outlook.gonzasosa.persistingdataonactivitystatechanges;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.outlook.gonzasosa.persistingdataonactivitystatechanges.vm.FormDataViewModel;

public class ActivityMain extends AppCompatActivity {
    private final String TAG = "Mrth";
    private final String EditTextKey = "G";
    private final String CheckBoxKey = "P";

    // global components to save its instance
    private EditText et;
    private CheckBox cb;

    // only global viewmodel
    //FormDataViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create and link viewmodel
        //vm = new ViewModelProvider(this).get (FormDataViewModel.class);

        setContentView (createView ());
    }


    @NonNull
    private View createView () {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.CENTER_VERTICAL
        );

        LinearLayout layout = new LinearLayout (this);
        layout.setLayoutParams (params);
        layout.setOrientation (LinearLayout.VERTICAL);

        LinearLayout.LayoutParams componentParams = new LinearLayout.LayoutParams (
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        cb = new CheckBox (this);
        cb.setLayoutParams (componentParams);
        cb.setTextSize (20);
        cb.setText ("Check me!");

        et = new EditText (this);
        et.setLayoutParams (componentParams);
        et.setTextSize (20);
        et.setHint ("Type something...");
        et.setGravity (Gravity.CENTER);

        layout.addView (cb);
        layout.addView (et);
        return layout;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString (EditTextKey, et.getText ().toString ());
        outState.putBoolean (CheckBoxKey, cb.isChecked ());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        et.setText (savedInstanceState.getString (EditTextKey));
        cb.setChecked (savedInstanceState.getBoolean (CheckBoxKey));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d (TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d (TAG, "onResume");

        // restoring data from viewmodel
        //et.setText (vm.editTextValue);
        //cb.setChecked (vm.checkBoxValue);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d (TAG, "onPause");

        // saving data to viewmodel
        //vm.editTextValue = et.getText ().toString ();
        //vm.checkBoxValue = cb.isChecked ();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d (TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d (TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d (TAG, "onDestroy");
    }
}
