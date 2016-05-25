package de.androidbytes.recipr.presentation.core.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import de.androidbytes.recipr.domain.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoogleAccountManager implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private boolean initialized = false;
    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener;

    @Inject
    public GoogleAccountManager() {
    }

    public synchronized void initialize(Context context) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        googleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((AppCompatActivity) context, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        initialized = true;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionFailedListener != null) {
            connectionFailedListener.onConnectionFailed(connectionResult);
        }
    }

    public void setConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {
        this.connectionFailedListener = connectionFailedListener;
    }

    public Intent getSignInIntent() {
        return Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    }

    public User getSignInResult(Intent data) {

        GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        User user = null;

        if (googleSignInResult.isSuccess()) {
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            if (account != null) {
                user = new User(-1, account.getId(), account.getDisplayName(), account.getPhotoUrl().toString(), false);
            }
        }

        return user;
    }

    public boolean isInitialized() {
        return initialized;
    }
}
