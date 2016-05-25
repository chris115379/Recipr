package de.androidbytes.recipr.presentation.login.di;

import dagger.Module;
import dagger.Provides;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.LoginUser;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.repository.UserRepository;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;

import javax.inject.Named;

@Module
public class LoginModule {

    @Provides
    @PerActivity
    @Named("loginUser")
    UseCase provideLoginUserUseCase(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new LoginUser(userRepository, executionThread, postExecutionThread);
    }

}
