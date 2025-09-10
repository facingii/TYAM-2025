package mx.uv.fiee.iinf.tyam.recyclerviewdemo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CustomViewHolder extends RecyclerView.ViewHolder
{
    TextView tvTitle;
    TextView tvDesc;
    ImageView picture;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById (R.id.tvTitle);
        tvDesc = itemView.findViewById (R.id.tvDesc);
        picture = itemView.findViewById (R.id.picture);
    }

    public void bind (Employee employee) {
        tvTitle.setText (employee.title ());
        tvDesc.setText (employee.description ());
        picture.setImageDrawable (employee.picture ());
    }
}

