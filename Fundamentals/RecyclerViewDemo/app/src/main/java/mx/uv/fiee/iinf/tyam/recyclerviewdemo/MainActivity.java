package mx.uv.fiee.iinf.tyam.recyclerviewdemo;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import mx.uv.fiee.iinf.tyam.recyclerviewdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = ActivityMainBinding.inflate(getLayoutInflater ());
        setContentView(binding.getRoot ());

        setSupportActionBar (binding.toolbar);
        binding.toolbar.setTitle (getTitle ());

        var employeeList = Utils.GetEmployeeList (this);
        binding.rv.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false));
        binding.rv.setAdapter (new CustomAdapter (employeeList, position -> {
            var employee = employeeList.get (position);
            Snackbar.make (binding.rv, employee.title (), Snackbar.LENGTH_LONG).show ();
        }));
    }

}



