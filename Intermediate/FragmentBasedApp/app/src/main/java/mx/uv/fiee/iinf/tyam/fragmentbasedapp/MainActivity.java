package mx.uv.fiee.iinf.tyam.fragmentbasedapp;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.CompletableFuture;

import mx.uv.fiee.iinf.tyam.fragmentbasedapp.database.AppDatabase;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.databinding.ActivityMainBinding;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.fragments.ArticleFragment;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.fragments.ArticleListFragment;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.fragments.CartFragment;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.interfaces.ArticleListClickListener;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.interfaces.BuyItButtonListener;
import mx.uv.fiee.iinf.tyam.fragmentbasedapp.models.Reviews;

public class MainActivity extends AppCompatActivity implements BuyItButtonListener, ArticleListClickListener {
    private static final String DB_NAME = "reviews.db";
    private static final String TAG = "MarJul";
    private AppDatabase database;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        var view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Product Reviews");
        }

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DB_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Log.d(TAG, "Database created for the first time");
                        CompletableFuture.runAsync(() -> populateDatabase());
                    }
                })
                .build();

        if (binding.myContainer != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.myContainer, new ArticleListFragment())
                    .addToBackStack("articleList")
                    .commit();
        }

        if (binding.articlesContainer != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.articlesContainer, new ArticleListFragment())
                    .addToBackStack("articleList")
                    .commit();
        }
    }

    private void populateDatabase() {
        var reviews1 = new Reviews();
        reviews1.ArticleId = 1;
        reviews1.Review = getResources().getString(R.string.review_article1);

        var reviews2 = new Reviews();
        reviews2.ArticleId = 2;
        reviews2.Review = getResources().getString(R.string.review_article2);

        var reviews3 = new Reviews();
        reviews3.ArticleId = 3;
        reviews3.Review = getResources().getString(R.string.review_article1);

        var reviews4 = new Reviews();
        reviews4.ArticleId = 4;
        reviews4.Review = getResources().getString(R.string.review_article2);

        var reviews5 = new Reviews();
        reviews5.ArticleId = 5;
        reviews5.Review = getResources().getString(R.string.review_article1);

        database.reviewDAO().insertAll(reviews1, reviews2, reviews3, reviews4, reviews5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuSettings) {
            Toast.makeText(getBaseContext(), "Settings", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void buyit_clicked(int articleId) {
        try {
            int containerId = binding.myContainer != null
                    ? binding.myContainer.getId()
                    : binding.cartContainer.getId();

            if (containerId == -1) {
                Log.w(TAG, "Container not found");
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("ITEMSELECTED", "Article " + articleId);
            var cartFragment = CartFragment.getInstance(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .replace(containerId, cartFragment)
                    .addToBackStack("article" + articleId)
                    .commit();
        }
        catch (NullPointerException ex)
        {
            Log.e (TAG, "Null pointer exception: " + ex.getMessage (), ex);
        }
    }

    @Override
    public void articles_onClick(int articleId) {
        CompletableFuture.supplyAsync(() -> database.reviewDAO().findReviewById(articleId))
            .thenAccept(result -> {
                runOnUiThread(() -> {
                    if (result == null) {
                        Log.d(TAG, "Result is null");
                        return;
                    }

                    var params = new Bundle();
                    params.putString("REVIEW", result.Review);
                    params.putString("TITLE", "Article " + articleId);
                    params.putString("IMAGE", "article" + articleId);
                    params.putInt("ID", articleId);

                    int comtainerId = binding.myContainer != null
                            ? binding.myContainer.getId()
                            : binding.articleReviewContainer.getId();

                    ArticleFragment fragment = ArticleFragment.getInstance (params);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .replace(comtainerId, fragment)
                            .addToBackStack("Article" + articleId)
                            .commit();
                });
            });
    }
}
