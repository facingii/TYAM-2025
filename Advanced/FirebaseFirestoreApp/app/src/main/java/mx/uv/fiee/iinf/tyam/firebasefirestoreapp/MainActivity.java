package mx.uv.fiee.iinf.tyam.firebasefirestoreapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mx.uv.fiee.iinf.tyam.firebasefirestoreapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MarJul";
    private ActivityMainBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        auth.signInAnonymously()
                .addOnSuccessListener(authResult -> Snackbar.make(binding.getRoot(), "Login successful!", Snackbar.LENGTH_LONG).show())
                .addOnFailureListener(e -> Snackbar.make(binding.getRoot(), "Login failed!", Snackbar.LENGTH_LONG).show());

        var adapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, data);
        binding.lstProducts.setAdapter (adapter);
        binding.btnQuery.setOnClickListener(v -> query());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.signOut();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuAdd)
        {
            addNewDocument ();
            return true;
        }

        if (item.getItemId() == R.id.mnuLoad)
        {
            retrieveProducts ();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addNewDocument ()
    {
//        Map<String, Object> user = new HashMap<>();
//        user.put("id", "products");
//        user.put("first", "Julia");
//        user.put("middle", "Karina");
//        user.put("last", "Viveros");
//        user.put("born", 2006);
//
//        db.collection("users")
//            .add(user)
//            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                }
//            })
//            .addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.w(TAG, "Error adding document", e);
//                }
//            });

//        var product = new Product() {{
//            productId = UUID.randomUUID().toString();
//            description = "bike helmet color red/black with loopable buttons";
//            name = "bike helmet";
//            price = 245.00;
//        }};
//

//        db.collection("products")
//                .document("product-" + UUID.randomUUID())
//                .set(product)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + product.productId);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });

//        db.collection("products")
//                .document("product-70920dea-4758-428d-aed0-64bd2ccf5227")
//                .update ("price", 250.0);

        loadProductsInBatch ();
    }

    private void retrieveProducts ()
    {
        db.collection("products")
                .limit(50)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (var document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            var product = document.toObject(Product.class);
                            Log.d(TAG, product.toString());

                            data.add(product.name);
                        }

                        binding.lstProducts.invalidateViews();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

    private void query () {
        Filter filter = Filter.and (
            Filter.lessThan("price", 200.00),
            Filter.greaterThan("name", binding.edtQuery.getText().toString())
        );

        db.collection("products")
                .where (filter)
                //.whereGreaterThan("name", binding.edtQuery.getText().toString())
                //.whereGreaterThan("price", 250.0)
                .orderBy("name")
                .limit(20)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        data.clear();
                        for (var document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            var product = document.toObject(Product.class);
                            data.add(product.name);
                        }
                        binding.lstProducts.invalidateViews();
                    }
                });
    }

    private void loadProductsInBatch ()
    {
        WriteBatch batch = db.batch();
        String[] productNames = {
            "Bike Helmet", "Mountain Bike", "Road Bike", "Cycling Gloves", "Water Bottle", "Bike Lock", "Bike Light", "Cycling Jersey", "Bike Pump", "Bike Bell",
            "Saddle Bag", "Bike Shoes", "Bike Shorts", "Cycling Cap", "Bike Chain", "Bike Pedals", "Bike Mirror", "Bike Rack", "Bike Stand", "Bike Computer",
            "Bike Basket", "Bike Fender", "Bike Tube", "Bike Tire", "Bike Handlebar", "Bike Grips", "Bike Tape", "Bike Seat", "Bike Cleats", "Bike Lubricant",
            "Bike Cleaner", "Bike Tool Kit", "Bike Patch Kit", "Bike Cable", "Bike Brake", "Bike Rotor", "Bike Derailleur", "Bike Shifter", "Bike Cassette", "Bike Crank",
            "Bike Fork", "Bike Frame", "Bike Headset", "Bike Stem", "Bike Spacer", "Bike Bolt", "Bike Nut", "Bike Washer", "Bike Spring", "Bike Shock",
            "Bike Suspension", "Bike Wheel", "Bike Rim", "Bike Spoke", "Bike Hub", "Bike Axle", "Bike Freewheel", "Bike Cog", "Bike Sprocket", "Bike Guard",
            "Bike Cover", "Bike Mat", "Bike Trainer", "Bike Roller", "Bike Stand", "Bike Mount", "Bike Holder", "Bike Bag", "Bike Backpack", "Bike Pannier",
            "Bike Basket", "Bike Cart", "Bike Trailer", "Bike Child Seat", "Bike Flag", "Bike Horn", "Bike Reflector", "Bike Sticker", "Bike Decal", "Bike Paint",
            "Bike Grease", "Bike Wax", "Bike Polish", "Bike Rag", "Bike Brush", "Bike Sponge", "Bike Bucket", "Bike Stand", "Bike Display", "Bike Shelf",
            "Bike Hook", "Bike Clamp", "Bike Strap", "Bike Rope", "Bike Cord", "Bike Net", "Bike Screen", "Bike Shield", "Bike Protector", "Bike Alarm"
        };
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.productId = "product-" + UUID.randomUUID();
            product.name = productNames[i % productNames.length];
            product.description = "High quality " + product.name.toLowerCase() + " for cycling enthusiasts. Durable and reliable.";
            product.price = 50.0 + (i * 4.5); // Prices from 50.0 to 495.5
            DocumentReference docRef = db.collection("products").document(product.productId);
            batch.set(docRef, product);
        }
        batch.commit()
            .addOnSuccessListener(aVoid -> Log.d(TAG, "Batch upload successful"))
            .addOnFailureListener(e -> Log.w(TAG, "Batch upload failed", e));
    }
}


class Product {
    public String productId;
    public String description;
    public String name;
    public double price;

    @Override
    public String toString ()
    {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}