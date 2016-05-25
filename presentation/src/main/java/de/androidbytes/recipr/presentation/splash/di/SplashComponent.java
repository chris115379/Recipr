package de.androidbytes.recipr.presentation.splash.di;

import dagger.Component;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.splash.view.activity.SplashActivity;
import de.androidbytes.recipr.presentation.splash.presenter.SplashPresenter;
import nz.bradcampbell.compartment.HasPresenter;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SplashModule.class)
public interface SplashComponent extends HasPresenter<SplashPresenter> {
    void inject(SplashActivity splashActivity);
}
