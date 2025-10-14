package mx.uv.fiee.iinf.tyam.starwarsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mx.uv.fiee.iinf.tyam.starwarsapp.R;
import mx.uv.fiee.iinf.tyam.starwarsapp.Utils;
import mx.uv.fiee.iinf.tyam.starwarsapp.databinding.FragmentsUiBinding;
import mx.uv.fiee.iinf.tyam.starwarsapp.interfaces.SWApiService;
import mx.uv.fiee.iinf.tyam.starwarsapp.models.Planet;
import mx.uv.fiee.iinf.tyam.starwarsapp.models.PlanetHeader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanetsFragment extends Fragment {
    FragmentsUiBinding binding;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentsUiBinding.inflate (inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvElements.addItemDecoration (new DividerItemDecoration (view.getContext (), DividerItemDecoration.VERTICAL));
        binding.rvElements.setItemAnimator (new DefaultItemAnimator ());
        binding.rvElements.setLayoutManager (new LinearLayoutManager (view.getContext ()));

        SWApiService service = Utils.createService ();

        service.getPlanets().enqueue (new Callback<PlanetHeader>() {
            @Override
            public void onResponse(@NonNull Call<PlanetHeader> call, @NonNull Response<PlanetHeader> response) {
                if (response.body () == null) return;

                List<Planet> results = response.body().results;
                binding.rvElements.setAdapter (new PlanetsAdapter (results));
            }

            @Override
            public void onFailure(@NonNull Call<PlanetHeader> call, @NonNull Throwable t) {
                Toast.makeText (view.getContext (), t.getMessage (), Toast.LENGTH_LONG).show ();
            }
        });
    }
}


class PlanetsAdapter extends RecyclerView.Adapter<PlanetsAdapter.PlanetViewHolder> {
    private final List<Planet> data;

    PlanetsAdapter(List<Planet> d) {
        data = d;
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from (parent.getContext ());
        View view = inflater.inflate (R.layout.list_item, parent, false);

        return new PlanetViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull PlanetViewHolder holder, int position) {
        Planet planet = data.get (position);
        holder.setData (planet.name, planet.diameter, planet.population);
    }

    @Override
    public int getItemCount () {
        return data.size ();
    }

    static class PlanetViewHolder extends RecyclerView.ViewHolder {
        TextView tvData1, tvData2, tvData3;

        PlanetViewHolder(View itemView) {
            super (itemView);

            tvData1 = itemView.findViewById (R.id.tvData1);
            tvData2 = itemView.findViewById (R.id.tvData2);
            tvData3 = itemView.findViewById (R.id.tvData3);
        }

        void setData (String data1, String data2, String data3) {
            tvData1.setText (data1);
            tvData2.setText (String.format (Locale.US, "%s: %s","Diameter", data2));
            tvData3.setText (String.format (Locale.US, "%s: %s", "Population", data3));
        }
    }
}