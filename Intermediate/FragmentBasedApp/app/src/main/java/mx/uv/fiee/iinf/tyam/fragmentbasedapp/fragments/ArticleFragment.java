package mx.uv.fiee.iinf.tyam.fragmentbasedapp.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import mx.uv.fiee.iinf.tyam.fragmentbasedapp.databinding.ArticleFragmentBinding;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.interfaces.BuyItButtonListener;

public class ArticleFragment extends Fragment {
    private final static String TAG = "MarJul";
    private final static String RES = "drawable";
    private ArticleFragmentBinding binding;
    private BuyItButtonListener listener;
    public static ArticleFragment getInstance (Bundle params) {
        ArticleFragment fragment = new ArticleFragment ();
        fragment.setArguments (params);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof BuyItButtonListener)) {
            throw new RuntimeException (context + " must implement BuyItButtonListener");
        } else {
            this.listener = (BuyItButtonListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ArticleFragmentBinding.inflate (inflater);
        return binding.getRoot ();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        var params = getArguments ();
        if (params != null) {
            var title = params.getString ("TITLE", "");
            var review = params.getString ("REVIEW", "");
            var image = params.getString ("IMAGE", "");

            var id = params.getInt ("ID", 0);
            if (id == 0) {
                return;
            }

            int drawableId = android.R.drawable.ic_menu_report_image;;
            if (getActivity() != null && getActivity().getResources() != null) {
                drawableId = getActivity().getResources().getIdentifier(image, RES, getActivity().getPackageName());
            }

            Drawable drawable = null;
            try {
                drawable = ResourcesCompat.getDrawable (getResources(), drawableId, getActivity().getTheme());
            }
            catch (Resources.NotFoundException e)
            {
                Log.e (TAG, "Drawable not found: " + e.getMessage (), e);
                return;
            }

            binding.tvArticle1Review.setText (review);
            binding.ivArticle.setImageBitmap (scale(drawable, 80, 80));
            binding.tvTitle.setText (title);

            binding.btnBuyIt.setOnClickListener(v ->
                this.listener.buyit_clicked (id)
            );
        }
    }

    private Bitmap scale (Drawable drawable, int w, int h)
    {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        return Bitmap.createScaledBitmap(bitmap, w, h, true);
    }

}
