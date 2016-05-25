package de.androidbytes.recipr.domain.interactor;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.domain.repository.UserRepository;
import rx.Observable;

import javax.inject.Inject;

public class GetLoggedInUser extends BaseUseCase<User> {

    private UserRepository userRepository;

    @Inject
    public GetLoggedInUser(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Observable<User> buildUseCaseObservable() {
        return userRepository.getLoggedInUser();
    }
}
