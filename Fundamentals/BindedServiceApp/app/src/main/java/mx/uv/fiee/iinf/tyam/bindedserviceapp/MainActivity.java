package mx.uv.fiee.iinf.tyam.bindedserviceapp;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    MyBindedService myService;
    RecyclerView rvPlanets;
    LinkedList<String> planets;
    PlanetsAdapter adapter;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        planets = new LinkedList<> ();

        rvPlanets = findViewById (R.id.rvPlanets);
        rvPlanets.setLayoutManager (new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvPlanets.addItemDecoration (new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        findViewById (R.id.btnTrigger).setOnClickListener (view -> {
            String planet = myService.getItem ();
            planets.add (planet);
            adapter.notifyDataSetChanged ();
        });
    }

    @Override
    protected void onResume () {
        super.onResume ();
        Intent intent = new Intent (getBaseContext (), MyBindedService.class);
        bindService (intent, serviceConnection, BIND_AUTO_CREATE);

        adapter = new PlanetsAdapter ();
        rvPlanets.setAdapter (adapter);
    }

    @Override
    protected void onPause () {
        super.onPause ();
        unbindService (serviceConnection);
        planets.clear ();
    }

    ServiceConnection serviceConnection = new ServiceConnection () {
        @Override
        public void onServiceConnected (ComponentName componentName, IBinder iBinder) {
            MyBindedService.MyBinder binder = (MyBindedService.MyBinder) iBinder;
            myService = binder.getService ();
            Toast.makeText (getBaseContext (), "Service connected!", Toast.LENGTH_LONG).show ();
        }

        @Override
        public void onServiceDisconnected (ComponentName componentName) {
            Log.d ("MarJul", "onServiceDisconnected");
        }
    };


    class PlanetsAdapter extends RecyclerView.Adapter<PlanetsAdapter.PlanetsHolder> {
        @NonNull
        @Override
        public PlanetsHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater) getBaseContext ().getSystemService (LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.list_item, parent, false);

            return new PlanetsHolder (view);
        }

        @Override
        public void onBindViewHolder (@NonNull PlanetsHolder holder, int position) {
            String planet = planets.get (position);
            holder.tvItem.setText (planet);
        }

        @Override
        public int getItemCount() {
            return planets.size ();
        }

        class PlanetsHolder extends RecyclerView.ViewHolder {
            TextView tvItem;

            public PlanetsHolder(@NonNull View itemView) {
                super (itemView);
                tvItem = itemView.findViewById (R.id.tvItem);
            }
        }
    }
}
