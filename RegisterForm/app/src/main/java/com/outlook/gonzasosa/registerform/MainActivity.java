package com.outlook.gonzasosa.registerform;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String FIRST_NAME_KEY = "firstName";
    private static final String LAST_NAME_KEY = "lastName";
    private static final String AGE_KEY = "age";
    private static final String PHONE_KEY = "phone";

    private EditText edtFirstName, edtLastName, edtAge, edtPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            setActionBar (toolbar);
            setTitle ("Register Form");
        }

        edtFirstName = findViewById(R.id.edtName);
        edtLastName = findViewById(R.id.edtLastName);
        edtAge = findViewById(R.id.edtAge);
        edtPhone = findViewById(R.id.edtPhone);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view ->
        {
            String firstName = edtFirstName.getText().toString();
            Toast.makeText(this, firstName, Toast.LENGTH_SHORT).show();
        } );
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString (FIRST_NAME_KEY, edtFirstName.getText().toString());
        outState.putString (LAST_NAME_KEY, edtLastName.getText().toString());
        outState.putString (AGE_KEY, edtAge.getText().toString());
        outState.putString (PHONE_KEY, edtPhone.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        edtFirstName.setText(savedInstanceState.getString(FIRST_NAME_KEY));
        edtLastName.setText(savedInstanceState.getString(LAST_NAME_KEY));
        edtAge.setText(savedInstanceState.getString(AGE_KEY));
        edtPhone.setText(savedInstanceState.getString(PHONE_KEY));
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePreferences ();
    }

    private void savePreferences ()
    {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String age = edtAge.getText().toString();
        String phone = edtPhone.getText().toString();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("age", age);
        editor.putString("phone", phone);

        editor.apply();
    }
}
