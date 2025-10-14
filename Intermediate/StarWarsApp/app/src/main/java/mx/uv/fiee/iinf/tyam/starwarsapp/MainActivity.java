package mx.uv.fiee.iinf.tyam.starwarsapp;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.LinkedList;

import mx.uv.fiee.iinf.tyam.starwarsapp.databinding.ActivityMainBinding;
import mx.uv.fiee.iinf.tyam.starwarsapp.fragments.PeopleFragment;
import mx.uv.fiee.iinf.tyam.starwarsapp.fragments.PlanetsFragment;
import mx.uv.fiee.iinf.tyam.starwarsapp.fragments.VehicleFragment;

public class MainActivity extends AppCompatActivity {
    //private TabLayout tabLayout;
    private int prevTab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        var binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView (binding.getRoot());

        setSupportActionBar (binding.toolbar);

        if (getSupportActionBar () != null) {
            getSupportActionBar ().setTitle (Utils.TITLE);
        }

        MyViewPagerAdapter mvpa = new MyViewPagerAdapter (getSupportFragmentManager (), getLifecycle ());
        mvpa.addFragment (new PlanetsFragment());
        mvpa.addFragment (new PeopleFragment());
        mvpa.addFragment (new VehicleFragment());

        binding.myViewPager.setAdapter (mvpa);


        binding.tabLayout.addOnTabSelectedListener (new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected (TabLayout.Tab tab) {
                prevTab = tab.getPosition ();
            }

            @Override
            public void onTabUnselected (TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected (TabLayout.Tab tab) {
            }
        });

        new TabLayoutMediator(binding.tabLayout, binding.myViewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText (Utils.TAB_PLANETS);
            }

            if (position == 1) {
                tab.setText (Utils.TAB_PEOPLE);
            }

            if (position == 2) {
                tab.setText (Utils.TAB_VEHICLES);
            }

            binding.myViewPager.setCurrentItem (tab.getPosition (), true);
        }).attach ();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (prevTab == 0) {
                    setEnabled(false);
                    ActivityCompat.finishAfterTransition(MainActivity.this);
                } else {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(prevTab - 1));
                }
            }
        });

    }
}

class MyViewPagerAdapter extends FragmentStateAdapter {
    private final LinkedList<Fragment> fragments = new LinkedList<> ();

    public MyViewPagerAdapter (@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super (fragmentManager, lifecycle);
    }

    public void addFragment (Fragment item) {
        fragments.add (item);
    }

    @NonNull
    @Override
    public Fragment createFragment (int position) {
        return fragments.get (position);
    }

    @Override
    public int getItemCount () {
        return fragments.size ();
    }
}
