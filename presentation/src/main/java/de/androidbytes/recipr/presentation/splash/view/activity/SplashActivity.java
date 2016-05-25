package de.androidbytes.recipr.presentation.splash.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.ReciprApplication;
import de.androidbytes.recipr.presentation.core.view.activity.PresenterActivity;
import de.androidbytes.recipr.presentation.splash.di.DaggerSplashComponent;
import de.androidbytes.recipr.presentation.splash.di.SplashComponent;
import de.androidbytes.recipr.presentation.splash.di.SplashModule;
import de.androidbytes.recipr.presentation.splash.presenter.SplashPresenter;
import de.androidbytes.recipr.presentation.splash.view.SplashView;

import javax.inject.Inject;

/**
 * Splash Activity to start the appropriate StartActivity
 */
public class SplashActivity extends PresenterActivity<SplashComponent, SplashPresenter> implements SplashView {

    @Inject
    SplashPresenter presenter;

    @Inject
    Tracker tracker;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjection();
        presenter.loadLoggedInUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.initialize();
        tracker.setScreenName(SplashActivity.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void setUpInjection() {
        getComponent().inject(this);
    }

    @Override
    protected SplashComponent onCreateNonConfigurationComponent() {
        return DaggerSplashComponent.builder()
                .applicationComponent(((ReciprApplication) getApplication()).getApplicationComponent())
                .splashModule(new SplashModule())
                .build();
    }

    @Override
    public void registerUser(User user) {
        ((ReciprApplication) getApplication()).setLoggedInUser(user);
    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
