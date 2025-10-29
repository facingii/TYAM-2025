package mx.uv.fiee.iinf.tyam.firebaseauthapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mx.uv.fiee.iinf.tyam.firebaseauthapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FirebaseAuth auth;
    private FragmentLoginBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate (inflater, container, false);
        return binding.getRoot ();
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance ();

        binding.btnLogin.setOnClickListener (v -> {
            login (
                    binding.etEmailLogin.getText ().toString (),
                    binding.etPasswordlLogin.getText().toString ()
            );
        });
    }

    private void login (String email, String password) {
        auth.signInWithEmailAndPassword (email, password)
                .addOnCompleteListener (task -> {
                    if (task.isSuccessful ()) {
                        FirebaseUser user = auth.getCurrentUser ();
                        String name = "";

                        if (user != null) {
                            name = user.getDisplayName ();
                        }

                        Toast.makeText (getActivity (), "Usuario " + name, Toast.LENGTH_LONG).show ();
                    } else {
                        Toast.makeText (getActivity (), "Usuario y/o contraseña no reconocida!", Toast.LENGTH_LONG).show ();
                    }
                });
    }
}