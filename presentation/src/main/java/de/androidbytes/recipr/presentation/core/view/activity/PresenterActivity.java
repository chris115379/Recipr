package de.androidbytes.recipr.presentation.core.view.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import nz.bradcampbell.compartment.HasPresenter;
import nz.bradcampbell.compartment.Presenter;

public abstract class PresenterActivity<C extends HasPresenter<P>, P extends Presenter> extends ComponentActivity<C> {

    private boolean isDestroyedBySystem;
    private P presenter;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = getPresenter();
        presenter.onCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        isDestroyedBySystem = false;

        try {
            presenter.bindView(this);
        } catch (ClassCastException e1) {
            throw new RuntimeException("Your activity needs to implement the " +
                    "view interface expected by " + presenter.getClass().getSimpleName() + ".", e1);
        }
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        isDestroyedBySystem = true;
        presenter.onSaveInstanceState(outState);
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
        presenter.unbindView();
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        if (!isDestroyedBySystem) {
            presenter.onDestroy();
        }
    }

    public P getPresenter() {
        return getComponent().getPresenter();
    }
}
