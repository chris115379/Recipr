package de.androidbytes.recipr.presentation.login.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import butterknife.OnClick;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.ReciprApplication;
import de.androidbytes.recipr.presentation.core.view.activity.PresenterActivity;
import de.androidbytes.recipr.presentation.login.di.DaggerLoginComponent;
import de.androidbytes.recipr.presentation.login.di.LoginComponent;
import de.androidbytes.recipr.presentation.login.di.LoginModule;
import de.androidbytes.recipr.presentation.login.presenter.LoginPresenter;
import de.androidbytes.recipr.presentation.login.view.LoginView;

import javax.inject.Inject;


public class LoginActivity extends PresenterActivity<LoginComponent, LoginPresenter> implements LoginView {

    private static final int SIGN_IN_WITH_GOOGLE = 0x0007;

    @Inject
    LoginPresenter presenter;

    @Inject
    Tracker tracker;

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected LoginComponent onCreateNonConfigurationComponent() {
        return DaggerLoginComponent.builder()
                .applicationComponent(((ReciprApplication) getApplicationContext()).getApplicationComponent())
                .loginModule(new LoginModule())
                .build();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjection();
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName(LoginActivity.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void setUpInjection() {
        getComponent().inject(this);
    }

    @Override
    public void renderGoogleSignIn() {
        Intent signInIntent = getPresenter().getSignInWithGoogleIntent();
        startActivityForResult(signInIntent, SIGN_IN_WITH_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_WITH_GOOGLE) {
            getPresenter().handleSignInResult(data);
        }
    }

    @Override
    public void renderError(String message) {
        View rootView = getRootView();
        if (rootView != null)
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void registerUser(User user) {
        ((ReciprApplication) getApplication()).setLoggedInUser(user);
    }

    @Override
    public void destroy() {
        finish();
    }

    @OnClick(R.id.btn_google_sign_in)
    public void onGoogleSignInButtonClicked() {
        presenter.signInWithGoogleButtonClicked();
    }
}
