package de.androidbytes.recipr.presentation.core.presenter;

import android.support.annotation.CallSuper;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import nz.bradcampbell.compartment.BasePresenter;
import rx.Subscriber;

public class UseCasePresenter<V> extends BasePresenter<V> {

    private final UseCase useCase;

    public UseCasePresenter(UseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        useCase.unsubscribe();
    }

    protected void executeUseCase(Subscriber subscriber) {
        useCase.execute(subscriber);
    }

    protected UseCase getUseCase() {
        return useCase;
    }
}
