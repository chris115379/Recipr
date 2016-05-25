package de.androidbytes.recipr.presentation.overview.categories.di;

import dagger.Component;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.overview.categories.presenter.CategoriesOverviewPresenter;
import de.androidbytes.recipr.presentation.overview.categories.view.fragment.CategoriesOverviewFragment;
import nz.bradcampbell.compartment.HasPresenter;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = CategoriesOverviewModule.class)
public interface CategoriesOverviewComponent extends HasPresenter<CategoriesOverviewPresenter> {
    void inject(CategoriesOverviewFragment categoriesOverviewFragment);
}
