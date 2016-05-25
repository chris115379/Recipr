package de.androidbytes.recipr.presentation.overview.category.di;

import dagger.Component;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.overview.category.presenter.CategoryPresenter;
import de.androidbytes.recipr.presentation.overview.category.view.activity.CategoryActivity;
import nz.bradcampbell.compartment.HasPresenter;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = CategoryModule.class)
public interface CategoryComponent extends HasPresenter<CategoryPresenter> {
    void inject(CategoryActivity categoryActivity);
}
