package de.androidbytes.recipr.presentation.splash.presenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import de.androidbytes.recipr.domain.interactor.core.DefaultSubscriber;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.core.presenter.UseCasePresenter;
import de.androidbytes.recipr.presentation.core.util.GoogleAccountManager;
import de.androidbytes.recipr.presentation.splash.view.SplashView;

import javax.inject.Inject;
import javax.inject.Named;

@PerActivity
public class SplashPresenter extends UseCasePresenter<SplashView> {

    @Inject
    Navigator navigator;

    @Inject
    GoogleAccountManager googleAccountManager;

    @Inject
    public SplashPresenter(@Named("loggedInUser") final UseCase getLoggedInUserUseCase) {
        super(getLoggedInUserUseCase);
    }

    public void loadLoggedInUser() {
        executeUseCase(new UserAccountSubscriber());
    }

    public void initialize() {
        googleAccountManager.initialize(getView().getContext());
    }

    private final class UserAccountSubscriber extends DefaultSubscriber<User> {

        User user = null;

        @Override
        public void onCompleted() {

            Context context = ((AppCompatActivity) getView());

            if (user != null) {
                getView().registerUser(user);
                getView().destroy();
                navigator.navigateToOverview(context);
            } else {
                getView().destroy();
                navigator.navigateToLogin(context);
            }
        }

        @Override
        public void onNext(User user) {
            this.user = user;
        }
    }


}
