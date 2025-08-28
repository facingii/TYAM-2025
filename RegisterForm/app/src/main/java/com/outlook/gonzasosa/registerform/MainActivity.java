package com.outlook.gonzasosa.registerform;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.outlook.gonzasosa.registerform.vm.FormViewModel;

public class MainActivity extends AppCompatActivity {
    private final String ACTIVITY_TITLE = "Register Form";
    private final String PREFERENCES_NAME = "RegisterForm";
    private final String FIRST_NAME_KEY = "FirstName";
    private final String LAST_NAME_KEY = "LastName";
    private final String AGE_KEY = "Age";
    private final String PHONE_KEY = "Phone";

    private FormViewModel viewModel;
    private EditText edtFirstName, edtLastName, edtAge, edtPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FormViewModel.class);
        setContentView (R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null)
        {
            setActionBar(toolbar);
            setTitle (ACTIVITY_TITLE);
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
    protected void onResume() {
        super.onResume();

        viewModel.FirstName = edtFirstName.getText().toString();
        viewModel.LastName = edtLastName.getText().toString();
        viewModel.Age = Integer.parseInt(edtAge.getText().toString());
        viewModel.Phone = edtPhone.getText().toString();
    }

    @Override
    protected void onPause() {
        super.onPause();

        edtFirstName.setText(viewModel.FirstName);
        edtLastName.setText(viewModel.LastName);
        edtAge.setText(String.valueOf(viewModel.Age));
        edtPhone.setText(viewModel.Phone);
    }

    @Override
    protected void onStop () {
        super.onStop ();
        savePreferences ();
    }

    private void savePreferences ()
    {
        var sharedPreferences = getSharedPreferences (PREFERENCES_NAME, MODE_PRIVATE);
        var editor = sharedPreferences.edit ();

        editor.putString (FIRST_NAME_KEY, viewModel.FirstName);
        editor.putString (LAST_NAME_KEY, viewModel.LastName);
        editor.putInt    (AGE_KEY, viewModel.Age);
        editor.putString (PHONE_KEY, viewModel.Phone);
        editor.apply ();
    }
}
