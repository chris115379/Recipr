package de.androidbytes.recipr.presentation.login.di;

import dagger.Component;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.login.presenter.LoginPresenter;
import de.androidbytes.recipr.presentation.login.view.activity.LoginActivity;
import nz.bradcampbell.compartment.HasPresenter;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = LoginModule.class)
public interface LoginComponent extends HasPresenter<LoginPresenter> {
    void inject(LoginActivity loginActivity);
}
