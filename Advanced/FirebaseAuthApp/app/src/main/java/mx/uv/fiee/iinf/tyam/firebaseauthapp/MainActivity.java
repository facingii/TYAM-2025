package mx.uv.fiee.iinf.tyam.firebaseauthapp;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executors;

import mx.uv.fiee.iinf.tyam.firebaseauthapp.fragments.GithubOAuth2Fragment;
import mx.uv.fiee.iinf.tyam.firebaseauthapp.fragments.LoginFragment;
import mx.uv.fiee.iinf.tyam.firebaseauthapp.fragments.RegisterFragment;

public class MainActivity extends AppCompatActivity {
    private static final String GOOGLE_ID_CLIENT = BuildConfig.GOOGLE_ID_CLIENT;
    private static final String TAG = "MarJul";
    private FirebaseAuth auth;
    private CredentialManager credentialManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView (R.layout.activity_main);

        auth = FirebaseAuth.getInstance ();
        credentialManager = CredentialManager.create(this);

        ActionBar actionBar = getSupportActionBar ();
        if (actionBar != null) {
            actionBar.setTitle ("Auth Options");
        }

        getSupportFragmentManager ()
                .beginTransaction ()
                .add (R.id.rootContainer, new LoginFragment())
                .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.main, menu);
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {

        int id = item.getItemId ();
        if (id == R.id.mnuRegister) {
            showRegisterView ();
        } else if (id == R.id.mnuGoogleSignin) {
            showGoogleSignInView ();
        } else if (id == R.id.mnuGithubSignin) {
            showGithubSignInView ();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showRegisterView () {
        getSupportFragmentManager ()
                .beginTransaction ()
                .addToBackStack (null)
                .replace (R.id.rootContainer, new RegisterFragment())
                .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();
    }

    private void showGithubSignInView () {
        GithubOAuth2Fragment github = new GithubOAuth2Fragment ();
        github.setOnAuthenticationCompleteListener (this::firebaseAuthWithGithubProvider);

        getSupportFragmentManager ()
                .beginTransaction ()
                .add (R.id.rootContainer, github)
                .setTransition (FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit ();
    }

    private void firebaseAuthWithGithubProvider (String token) {
        AuthCredential credential = GithubAuthProvider.getCredential (token);

        auth.signInWithCredential (credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful ()) {
                        Toast.makeText (getBaseContext (), "Github SignIn Successful!", Toast.LENGTH_LONG).show ();
                    } else {
                        Toast.makeText (getBaseContext (),
                                "SignIn with Google services failed with exception " +
                                        (task.getException () != null ? task.getException().getMessage () : "None"),
                                Toast.LENGTH_LONG).show ();
                    }
                });
    }

    private void showGoogleSignInView () {
//        var googleIdOption = new GetGoogleIdOption.Builder()
//                .setFilterByAuthorizedAccounts(true)
//                .setServerClientId(GOOGLE_ID_CLIENT)
//                .setAutoSelectEnabled(false)
//                .build();

        var googleIdOption = new GetSignInWithGoogleOption.Builder(GOOGLE_ID_CLIENT)
                .build();

        var request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        credentialManager.getCredentialAsync(
                getBaseContext(),
                request,
                new CancellationSignal(),
                Executors.newSingleThreadExecutor(),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse getCredentialResponse) {
                        handleSignIn(getCredentialResponse.getCredential());
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Log.e(TAG, "Couldn't retrieve user's credentials: " + e.getLocalizedMessage());
                    }
                }
        );
    }

    private void handleSignIn(Credential credential) {
        if (credential instanceof CustomCredential customCredential
                && credential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {

            Bundle credentialData = customCredential.getData();
            GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credentialData);

            firebaseAuthWithGoogle(googleIdTokenCredential.getIdToken());
        } else {
            Log.w(TAG, "Credential is not of type Google ID!");
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(getBaseContext(), user.getDisplayName(), Toast.LENGTH_LONG).show();
                    } else {
                        // If sign in fails, display a message to the user
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }
                });
    }
}

