package de.androidbytes.recipr.presentation.overview.favorites.di;

import dagger.Component;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.overview.favorites.presenter.FavoritesOverviewPresenter;
import de.androidbytes.recipr.presentation.overview.favorites.view.fragment.FavoritesOverviewFragment;
import nz.bradcampbell.compartment.HasPresenter;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FavoritesOverviewModule.class)
public interface FavoritesOverviewComponent extends HasPresenter<FavoritesOverviewPresenter> {
    void inject(FavoritesOverviewFragment favoritesOverviewFragment);
}
