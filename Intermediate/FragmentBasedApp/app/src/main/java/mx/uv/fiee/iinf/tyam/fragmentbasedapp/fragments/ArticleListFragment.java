package mx.uv.fiee.iinf.tyam.fragmentbasedapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mx.uv.fiee.iinf.tyam.fragmentbasedapp.databinding.FragmentArticleListBinding;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.interfaces.ArticleListClickListener;

public class ArticleListFragment extends Fragment {
    private FragmentArticleListBinding binding;
    private ArticleListClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ArticleListClickListener) {
            listener = (ArticleListClickListener) context;
        } else {
            throw new RuntimeException(context + " must implement ArticleListClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentArticleListBinding.inflate (inflater, container, false);
        return binding.getRoot ();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() == null) {
            return;
        }

        var itemList = new String [] {"Article 1", "Article 2", "Article 3", "Article 4", "Article 5"};
        var adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itemList);

        binding.lvArticles.setOnItemClickListener((parent, v, position, id) -> {
            var foo = (String) binding.lvArticles.getAdapter().getItem(position);
            Log.d("MarJul", foo);

            this.listener.articles_onClick(position + 1);
        });

        binding.lvArticles.setAdapter(adapter);
    }
}
