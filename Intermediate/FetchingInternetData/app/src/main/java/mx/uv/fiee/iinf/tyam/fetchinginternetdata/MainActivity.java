package mx.uv.fiee.iinf.tyam.fetchinginternetdata;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import mx.uv.fiee.iinf.tyam.fetchinginternetdata.databinding.ActivityMainBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private OkHttpClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = new OkHttpClient();

        binding.btnFetchText.setOnClickListener(v -> fetchText());
        binding.btnFetchJson.setOnClickListener(v -> fetchJson());
        binding.btnFetchImage.setOnClickListener(v -> fetchImage());
        binding.btnPostData.setOnClickListener(v -> postData());
    }

    private void fetchText ()
    {
        var textUri = "https://f1e6088aeead4104b5100cecf8ed94ba.api.mockbin.io/";
        Request request = new Request.Builder()
                .url(textUri)
                .build();
//
//        try (var response = client.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                var responseBody = response.body();
//                if (responseBody != null) {
//                    var textResponse = responseBody.string();
//                    binding.tvResult.setText(textResponse);
//                }
//            } else {
//                binding.tvResult.setText("Error: " + response.code());
//            }
//        } catch (Exception e) {
//            binding.tvResult.setText("Exception: " + e.getMessage());
//        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure (@NonNull Call call, @NonNull IOException e) {
                runOnUiThread( () -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread (() -> {
                    try {
                        binding.tvResult.setText(response.body().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    private void fetchJson ()
    {
        var jsonUri = "https://19d184f8601c4cfba42b6084ee0edd27.api.mockbin.io/";
        Request request = new Request.Builder()
                .url(jsonUri)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure (@NonNull Call call, @NonNull IOException e) {
                runOnUiThread( () -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show() );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(() -> {
                    KardexItem item;
                    try {
                        String json = response.body().string();
                        binding.tvResult.setText(json);
                        Gson gson = new GsonBuilder().create();
                        item = gson.fromJson(json, KardexItem.class);
                        Snackbar.make(binding.getRoot(), "Name: " + item.getName(), Snackbar.LENGTH_LONG).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    private void postData ()
    {
        var data = new KardexItem();
        data.setName ("Martha");
        data.setLastName ("Yam");
        data.setAge (20);
        data.setAddress ("Av. Monterrey #232, Col. Centro, C.P. 91000, Xalapa, Veracruz, México");

        Gson gson = new GsonBuilder().create ();
        var json = gson.toJson(data);

        var postUri = "https://48a3bda808054b66957e1889f3c86d4b.api.mockbin.io/";
        Request request = new Request.Builder()
                .url(postUri)
                .post(RequestBody.create(json, MediaType.parse("application/json")))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(() -> {
                    try {
                        binding.tvResult.setText (response.body().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    private void fetchImage ()
    {
        var imageUri = "https://fastly.picsum.photos/id/11/2500/1667.jpg?hmac=xxjFJtAPgshYkysU_aqx2sZir-kIOjNR9vx0te7GycQ";
        Request request = new Request.Builder()
                .url(imageUri)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure (@NonNull Call call, @NonNull IOException e) {
                runOnUiThread( () -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream ());
               if (bitmap != null) {
                   runOnUiThread (() -> {
                           var scaledBitmap = scale(bitmap, binding.tvResult.getWidth(), binding.tvResult.getHeight());
                           ImageSpan imageSpan = new ImageSpan (getBaseContext(), scaledBitmap);
                           SpannableStringBuilder builder = new SpannableStringBuilder();
                           builder.append ("This is an image downloaded from the internet: \n\n");
                           builder.append (" ", imageSpan, 0);

                           binding.tvResult.setText(builder);
                   });
               }
            }
        });
    }

    private Bitmap scale (Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if (ratioMax > ratioBitmap) {
            finalWidth = (int) ((float)maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) ((float)maxWidth / ratioBitmap);
        }
        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true);
    }
}

class KardexItem
{
    private String name;
    private String lastName;
    private int age;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}