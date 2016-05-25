package de.androidbytes.recipr.presentation.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import de.androidbytes.recipr.domain.interactor.LoginUser;
import de.androidbytes.recipr.domain.interactor.core.DefaultSubscriber;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.core.presenter.UseCasePresenter;
import de.androidbytes.recipr.presentation.core.util.GoogleAccountManager;
import de.androidbytes.recipr.presentation.login.view.LoginView;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class LoginPresenter extends UseCasePresenter<LoginView> implements GoogleApiClient.OnConnectionFailedListener {

    @Inject
    Navigator navigator;

    @Inject
    GoogleAccountManager googleAccountManager;

    @Inject
    public LoginPresenter(@Named("loginUser") final UseCase createUserUseCase) {
        super(createUserUseCase);
    }

    public void initializeSignIn() {
        if (!googleAccountManager.isInitialized()) {
            googleAccountManager.initialize(((AppCompatActivity) getView()));
        }
        googleAccountManager.setConnectionFailedListener(this);
    }

    public Intent getSignInWithGoogleIntent() {
        return googleAccountManager.getSignInIntent();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        getView().renderError(((Context) getView()).getString(R.string.login_connection_error));
    }

    public void signInWithGoogleButtonClicked() {
        initializeSignIn();
        getView().renderGoogleSignIn();
    }

    public void handleSignInResult(Intent data) {

        final User user = googleAccountManager.getSignInResult(data);

        if (user != null) {
            loginUser(user);
        } else {
            getView().renderError(((Context) getView()).getString(R.string.login_signin_error));
        }
    }

    private void loginUser(User account) {
        LoginUser loginUserUseCase = (LoginUser) getUseCase();
        loginUserUseCase.loginUser(new User(account.getId(), account.getGoogleId(), account.getName(), account.getImageUrl(), true));
        executeUseCase(new UserAccountSubscriber());
    }
    
    private final class UserAccountSubscriber extends DefaultSubscriber<User> {

        User user = null;

        @Override
        public void onCompleted() {
            if (user != null) {
                getView().registerUser(user);
                getView().destroy();
                navigator.navigateToOverview(((AppCompatActivity) getView()));
            }
        }

        @Override
        public void onNext(User user) {
            this.user = user;
        }
    }


}
