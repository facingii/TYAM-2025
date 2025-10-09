package mx.uv.fiee.iinf.tyam.galleryglideapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import mx.uv.fiee.iinf.tyam.galleryglideapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private static final String[] downloadUrls = {
            "https://picsum.photos/id/0/5000/3333",
            "https://picsum.photos/id/1/5000/3333",
            "https://picsum.photos/id/2/5000/3333",
            "https://picsum.photos/id/3/5000/3333",
            "https://picsum.photos/id/4/5000/3333",
            "https://picsum.photos/id/5/5000/3334",
            "https://picsum.photos/id/6/5000/3333",
            "https://picsum.photos/id/7/4728/3168",
            "https://picsum.photos/id/8/5000/3333",
            "https://picsum.photos/id/9/5000/3269",
            "https://picsum.photos/id/10/2500/1667",
            "https://picsum.photos/id/11/2500/1667",
            "https://picsum.photos/id/12/2500/1667",
            "https://picsum.photos/id/13/2500/1667",
            "https://picsum.photos/id/14/2500/1667",
            "https://picsum.photos/id/15/2500/1667",
            "https://picsum.photos/id/16/2500/1667",
            "https://picsum.photos/id/17/2500/1667",
            "https://picsum.photos/id/18/2500/1667",
            "https://picsum.photos/id/19/2500/1667",
            "https://picsum.photos/id/20/3670/2462",
            "https://picsum.photos/id/21/3008/2008",
            "https://picsum.photos/id/22/4434/3729",
            "https://picsum.photos/id/23/3887/4899",
            "https://picsum.photos/id/24/4855/1803",
            "https://picsum.photos/id/25/5000/3333",
            "https://picsum.photos/id/26/4209/2769",
            "https://picsum.photos/id/27/3264/1836",
            "https://picsum.photos/id/28/4928/3264",
            "https://picsum.photos/id/29/4000/2670",
            "https://picsum.photos/id/30/1280/901",
            "https://picsum.photos/id/31/3264/4912",
            "https://picsum.photos/id/32/4032/3024",
            "https://picsum.photos/id/33/5000/3333",
            "https://picsum.photos/id/34/3872/2592",
            "https://picsum.photos/id/35/2758/3622",
            "https://picsum.photos/id/36/4179/2790",
            "https://picsum.photos/id/37/2000/1333",
            "https://picsum.photos/id/38/1280/960",
            "https://picsum.photos/id/39/3456/2304",
            "https://picsum.photos/id/40/4106/2806",
            "https://picsum.photos/id/41/1280/805",
            "https://picsum.photos/id/42/3456/2304",
            "https://picsum.photos/id/43/1280/831",
            "https://picsum.photos/id/44/4272/2848",
            "https://picsum.photos/id/45/4592/2576",
            "https://picsum.photos/id/46/3264/2448",
            "https://picsum.photos/id/47/4272/2848",
            "https://picsum.photos/id/48/5000/3333",
            "https://picsum.photos/id/49/1280/792",
            "https://picsum.photos/id/50/4608/3072",
            "https://picsum.photos/id/51/5000/3333",
            "https://picsum.photos/id/52/1280/853",
            "https://picsum.photos/id/53/1280/1280",
            "https://picsum.photos/id/54/3264/2176",
            "https://picsum.photos/id/55/4608/3072",
            "https://picsum.photos/id/56/2880/1920",
            "https://picsum.photos/id/57/2448/3264",
            "https://picsum.photos/id/58/1280/853",
            "https://picsum.photos/id/59/2464/1632",
            "https://picsum.photos/id/60/1920/1200",
            "https://picsum.photos/id/61/3264/2448",
            "https://picsum.photos/id/62/2000/1333",
            "https://picsum.photos/id/63/5000/2813",
            "https://picsum.photos/id/64/4326/2884",
            "https://picsum.photos/id/65/4912/3264",
            "https://picsum.photos/id/66/3264/2448",
            "https://picsum.photos/id/67/2848/4288",
            "https://picsum.photos/id/68/4608/3072",
            "https://picsum.photos/id/69/4912/3264",
            "https://picsum.photos/id/70/3011/2000",
            "https://picsum.photos/id/71/5000/3333",
            "https://picsum.photos/id/72/3000/2000",
            "https://picsum.photos/id/73/5000/3333",
            "https://picsum.photos/id/74/4288/2848",
            "https://picsum.photos/id/75/1999/2998",
            "https://picsum.photos/id/76/4912/3264",
            "https://picsum.photos/id/77/1631/1102",
            "https://picsum.photos/id/78/1584/2376",
            "https://picsum.photos/id/79/2000/3011",
            "https://picsum.photos/id/80/3888/2592",
            "https://picsum.photos/id/81/5000/3250",
            "https://picsum.photos/id/82/1500/997",
            "https://picsum.photos/id/83/2560/1920",
            "https://picsum.photos/id/84/1280/848",
            "https://picsum.photos/id/85/1280/774",
            "https://picsum.photos/id/87/1280/960",
            "https://picsum.photos/id/88/1280/1707",
            "https://picsum.photos/id/89/4608/2592",
            "https://picsum.photos/id/90/3000/1992",
            "https://picsum.photos/id/91/3504/2336",
            "https://picsum.photos/id/92/3568/2368",
            "https://picsum.photos/id/93/2000/1334",
            "https://picsum.photos/id/94/2133/1200",
            "https://picsum.photos/id/95/2048/2048",
            "https://picsum.photos/id/96/4752/3168",
            "https://picsum.photos/id/98/3264/2176",
            "https://picsum.photos/id/99/4912/3264",
            "https://picsum.photos/id/100/2500/1656",
            "https://picsum.photos/id/101/2621/1747"
    };

    ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        binding.rvPictures.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rvPictures.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.rvPictures.setAdapter(new Gallerydapter());
    }

    // inner class adapter
    class Gallerydapter extends RecyclerView.Adapter<GalleryViewHolder>
    {
        @NonNull
        @Override
        public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            var view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
            String url = downloadUrls [position];
            holder.setPicture(url);
        }

        @Override
        public int getItemCount() {
            return downloadUrls.length;
        }
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivPicture;
        GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPicture = itemView.findViewById(R.id.ivPicture);
        }

        void setPicture (String url)
        {
            Glide.with (getBaseContext())
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .into(ivPicture);

        }
    }


}
