package de.androidbytes.recipr.presentation.overview.core.di;

import dagger.Component;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.overview.core.view.activity.OverviewActivity;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface OverviewComponent {
    void inject(OverviewActivity overviewActivity);
}