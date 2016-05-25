package de.androidbytes.recipr.presentation.core.view.fragment;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.presentation.ReciprApplication;
import nz.bradcampbell.compartment.HasPresenter;
import nz.bradcampbell.compartment.Presenter;
import nz.bradcampbell.compartment.PresenterControllerFragment;

/**
 * Created by Christoph on 03.05.2016.
 */
public abstract class BaseFragment<C extends HasPresenter<P>, P extends Presenter> extends PresenterControllerFragment<C, P> {

    private Unbinder unbinder;

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected User getLoggedInUser() {
        return ((ReciprApplication) getContext().getApplicationContext()).getLoggedInUser();
    }

    protected long getLoggedInUserId() {
        return getLoggedInUser().getId();
    }

}
