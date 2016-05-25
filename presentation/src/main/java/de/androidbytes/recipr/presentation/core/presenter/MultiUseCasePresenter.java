package de.androidbytes.recipr.presentation.core.presenter;

import android.support.annotation.CallSuper;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import nz.bradcampbell.compartment.BasePresenter;
import rx.Subscriber;

import java.util.HashMap;

public class MultiUseCasePresenter<V> extends BasePresenter<V> {

    private final HashMap<String, UseCase> useCases;

    public MultiUseCasePresenter(HashMap<String, UseCase> useCases) {
        this.useCases = useCases;
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        for (UseCase useCase : useCases.values()) {
            useCase.unsubscribe();
        }
    }

    protected void executeUseCase(String key, Subscriber subscriber) {
        UseCase useCase = useCases.get(key);
        if (useCase != null) {
            useCase.execute(subscriber);
        }
    }

    protected UseCase getUseCase(String key) {
        return useCases.get(key);
    }
}
