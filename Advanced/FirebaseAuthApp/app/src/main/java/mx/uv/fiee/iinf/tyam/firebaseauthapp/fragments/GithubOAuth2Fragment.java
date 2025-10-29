package mx.uv.fiee.iinf.tyam.firebaseauthapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;

import java.io.IOException;

import mx.uv.fiee.iinf.tyam.firebaseauthapp.BuildConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GithubOAuth2Fragment extends Fragment {
    public static final String CLIENT_ID = BuildConfig.GITHUB_CLIENT_ID;
    public static final String CLIENT_SECRET = BuildConfig.GITHUB_CLIENT_SECRET;
    public static final String OAUTH_URL_CALLBACK = "https://baseproject-4208c.firebaseapp.com/__/auth/handler";
    public static final String OAUTH_SERVICE_CODE_URL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID;
    public static final String OAUTH_URL_ACCESS_TOKEN = "https://github.com/login/oauth/access_token";
    public static final String QUERY_CODE_KEY_PARAMETER = "code";
    public static final String TAG = "MarJul";

    public interface OnAuthenticationComplete {
        void authenticationComplete (String token);
    }

    private OnAuthenticationComplete listener;
    private WebView webView;

    public void setOnAuthenticationCompleteListener (OnAuthenticationComplete l) {
        this.listener = l;
    }

    @SuppressLint ("SetJavaScriptEnabled")
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity activity = getActivity ();
        if (activity == null) return null;

        webView = new WebView (activity.getBaseContext ());
        webView.setWebChromeClient (new WebChromeClient ());

        WebViewClient webViewClient = new WebViewClient () {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, WebResourceRequest request) {
                var uri = request.getUrl();
                Log.i (TAG, "Redirecting..." + uri.toString());
                if (uri.toString().startsWith (OAUTH_URL_CALLBACK)) {
                    if (uri.getQueryParameter (QUERY_CODE_KEY_PARAMETER) != null) {
                        String code = uri.getQueryParameter (QUERY_CODE_KEY_PARAMETER);
                        Log.i (TAG, "Code: " + code);
                        getAuthenticateToken (code);
                        return true;
                    } else if (uri.getQueryParameter ("error") != null) {
                        String message = uri.getQueryParameter ("error");
                        if (message != null) Log.e (TAG, message);
                    }
                }
                return super.shouldOverrideUrlLoading (view, request);
            }
        };

        webView.setWebViewClient (webViewClient);
        webView.getSettings ().setJavaScriptEnabled (true);

        return webView;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);

        Log.i (TAG, OAUTH_SERVICE_CODE_URL);
        webView.loadUrl (OAUTH_SERVICE_CODE_URL);
    }

    public void getAuthenticateToken (String requestCode) {
        new HttpRequestHelper(this.listener).getAccessToken(requestCode);
    }
}

class HttpRequestHelper {

    private final GithubOAuth2Fragment.OnAuthenticationComplete listener;
    private final OkHttpClient client;
    public HttpRequestHelper (GithubOAuth2Fragment.OnAuthenticationComplete listener)
    {
        client = new OkHttpClient ();
        this.listener = listener;
    }

    public void getAccessToken (String requestCode) {
        RequestBody formBody = new FormBody.Builder()
                .add("client_id", GithubOAuth2Fragment.CLIENT_ID)
                .add("client_secret", GithubOAuth2Fragment.CLIENT_SECRET)
                .add("code", requestCode)
                .build();

        Request request = new Request.Builder()
                .url(GithubOAuth2Fragment.OAUTH_URL_ACCESS_TOKEN)
                .header("Accept", "application/json")
                .post(formBody)
                .build();

        client.newCall (request).enqueue (new Callback () {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e (GithubOAuth2Fragment.TAG, "IOError", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful ()) {
                    Log.e (GithubOAuth2Fragment.TAG, "Error: " + response.code ());
                    if (response.body() != null) {
                        Log.e(GithubOAuth2Fragment.TAG, "Error Body: " + response.body().string());
                    }
                    return;
                }

                if (response.body() != null) {
                    String content = response.body().string();
                    Log.i (GithubOAuth2Fragment.TAG, content);

                    AccessTokenRequestResponse token = new Gson().fromJson (content, AccessTokenRequestResponse.class);

                    if (token != null && token.access_token != null) {
                        listener.authenticationComplete (token.access_token);
                    } else {
                        Log.e(GithubOAuth2Fragment.TAG, "Failed to parse access token from response.");
                    }
                }
            }
        });
    }
}

class AccessTokenRequestResponse {
    String access_token;
    String scope;
    String token_type;
}
