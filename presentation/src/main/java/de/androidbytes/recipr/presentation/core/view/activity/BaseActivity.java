package de.androidbytes.recipr.presentation.core.view.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.ButterKnife;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.presentation.ReciprApplication;
import nz.bradcampbell.compartment.ComponentCacheActivity;

public abstract class BaseActivity extends ComponentCacheActivity {

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected ActionBar setSupportActionBarAndReturn(@NonNull Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        return getSupportActionBar();
    }

    protected abstract int getLayoutResourceId();

    protected View getRootView() {
        return findViewById(android.R.id.content);
    }



    public User getLoggedInUser() {
        return ((ReciprApplication) getApplication()).getLoggedInUser();
    }

    protected long getLoggedInUserId() {
        return getLoggedInUser().getId();
    }
}
