package mx.uv.fiee.iinf.tyam.firebaseauthapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import mx.uv.fiee.iinf.tyam.firebaseauthapp.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private static String TAG = "MarJul";
    private FirebaseAuth auth;
    private FragmentRegisterBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate (inflater, container, false);
        return binding.getRoot ();
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        auth = FirebaseAuth.getInstance ();

        binding.btnRegister.setOnClickListener (v -> {
            registerUser (
                    binding.etEmailRegister.getText().toString (),
                    binding.etPasswordlRegister.getText().toString ()
            );
        });
    }

    private void registerUser (String email, String password) {
        auth.createUserWithEmailAndPassword (email, password)
            .addOnCompleteListener (task -> {
                if (task.isSuccessful ()) {
                    Toast.makeText (getContext (), "Register completed!", Toast.LENGTH_LONG).show ();
                } else {
                    if (task.getException () != null) {
                        Log.e(TAG, task.getException().getMessage());
                    }

                    Toast.makeText (getContext (), "Register failed!", Toast.LENGTH_LONG).show ();
                }
            });
    }
}