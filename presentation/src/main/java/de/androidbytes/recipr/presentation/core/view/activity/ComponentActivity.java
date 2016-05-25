package de.androidbytes.recipr.presentation.core.view.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import nz.bradcampbell.compartment.ComponentCache;
import nz.bradcampbell.compartment.ComponentControllerDelegate;
import nz.bradcampbell.compartment.ComponentFactory;

/**
 * Created by Christoph on 15.05.2016.
 */
public abstract class ComponentActivity<C> extends BaseActivity {

    private ComponentControllerDelegate<C> componentDelegate = new ComponentControllerDelegate<>();

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComponentCache componentCache = getComponentCache();
        componentDelegate.onCreate(componentCache, savedInstanceState, componentFactory);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        componentDelegate.onResume();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        componentDelegate.onSaveInstanceState(outState);
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        componentDelegate.onDestroy();
    }

    /** Override this method to use a custom component cache */
    protected ComponentCache getComponentCache() {
        return (ComponentCache) this;
    }

    public C getComponent() {
        return componentDelegate.getComponent();
    }

    protected abstract C onCreateNonConfigurationComponent();

    private ComponentFactory<C> componentFactory = new ComponentFactory<C>() {
        @NonNull
        @Override
        public C createComponent() {
            return onCreateNonConfigurationComponent();
        }
    };
}
