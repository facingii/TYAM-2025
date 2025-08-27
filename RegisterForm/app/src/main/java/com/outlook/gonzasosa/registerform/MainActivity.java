package com.outlook.gonzasosa.registerform;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);

        EditText edtFirstName = findViewById(R.id.edtName);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view ->
        {
            String firstName = edtFirstName.getText().toString();
            Toast.makeText(this, firstName, Toast.LENGTH_SHORT).show();
        } );

    }

}
