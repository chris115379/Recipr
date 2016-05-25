package de.androidbytes.recipr.domain.interactor;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.domain.repository.UserRepository;
import rx.Observable;

import javax.inject.Inject;

public class LoginUser extends BaseUseCase<User> {

    private UserRepository userRepository;
    private User user = null;

    @Inject
    public LoginUser(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userRepository = userRepository;
    }

    public LoginUser loginUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return userRepository.loginUser(user);
    }
}
