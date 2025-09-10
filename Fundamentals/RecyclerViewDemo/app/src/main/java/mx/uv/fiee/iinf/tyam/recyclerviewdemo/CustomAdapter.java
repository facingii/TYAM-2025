package mx.uv.fiee.iinf.tyam.recyclerviewdemo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>
{
    private final List<Employee> employeeList;

    public CustomAdapter(List<Employee> data)  {
        employeeList = data;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from (parent.getContext ());
        var view = inflater.inflate (R.layout.list_item, parent, false);

        return new CustomViewHolder (view);
    }

    @Override
    public void onBindViewHolder (@NonNull CustomViewHolder holder, int position) {
        var employee = employeeList.get (position);
        holder.bind (employee);
    }

    @Override
    public int getItemCount () {
        return employeeList.size ();
    }

}
