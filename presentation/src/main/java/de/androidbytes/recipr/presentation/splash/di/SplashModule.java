package de.androidbytes.recipr.presentation.splash.di;

import dagger.Module;
import dagger.Provides;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.GetLoggedInUser;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.repository.UserRepository;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;

import javax.inject.Named;

@Module
public class SplashModule {

    @Provides
    @PerActivity
    @Named("loggedInUser")
    UseCase provideGetLoggedInUserUseCase(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new GetLoggedInUser(userRepository, executionThread, postExecutionThread);
    }

}
