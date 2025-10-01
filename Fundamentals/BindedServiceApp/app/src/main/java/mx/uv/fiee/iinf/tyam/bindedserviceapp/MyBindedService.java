package mx.uv.fiee.iinf.tyam.bindedserviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyBindedService extends Service {
    private static final String TAG = "MarJul";

    private IBinder myBinder = new MyBinder();
    private List<String> planets;

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        Log.i (TAG, "Binding activity...");
        addResultValues ();
        return myBinder;
    }

    public String getItem () {
        Random random = new Random ();
        String foo = planets.get (random.nextInt (10));
        return foo;
    }

    private void addResultValues () {
        planets = Arrays.asList ("Sun", "Mercury", "Venus", "Earth", "Mars", "Jupyter", "Saturnus", "Uranus", "Neptunus", "Pluto");
    }

    // proxy pattern
    class MyBinder extends Binder { //iBinder
        MyBindedService getService () {
            return MyBindedService.this;
        }
    }

}