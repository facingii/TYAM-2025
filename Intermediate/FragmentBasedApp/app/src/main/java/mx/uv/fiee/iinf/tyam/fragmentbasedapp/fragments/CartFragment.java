package mx.uv.fiee.iinf.tyam.fragmentbasedapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashSet;

import mx.uv.fiee.iinf.tyam.fragmentbasedapp.databinding.CartFragmentBinding;

public class CartFragment extends Fragment {
    private final String SHARED_PREFERENCES_NAME = "CARTSHOP";
    private final String SHARED_PREFERENCES_KEY = "ITEMS";
    private CartFragmentBinding binding;

    public static CartFragment getInstance (Bundle params) {
        var fragment = new CartFragment ();
        fragment.setArguments (params);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CartFragmentBinding.inflate (inflater, container, false);
        return binding.getRoot ();
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var bundle = getArguments ();
        if (bundle == null) return;

        var item = bundle.getString ("ITEMSELECTED", "");
        if (item.isEmpty()) return;

        if (getActivity () == null) return;

        var sharedPrefs = getActivity().getSharedPreferences (SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        var storedItems = sharedPrefs.getStringSet (SHARED_PREFERENCES_KEY, new HashSet<>());
        var newItems = new HashSet<String>(storedItems);
        if (!storedItems.contains (item)) {
            newItems.add(item);
        }

        var editor = sharedPrefs.edit ();
        editor.putStringSet ("ITEMS", newItems);
        editor.apply ();

        var adapter = new ArrayAdapter<>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, newItems.toArray ());

        binding.lstItems.setAdapter (adapter);
    }
}
