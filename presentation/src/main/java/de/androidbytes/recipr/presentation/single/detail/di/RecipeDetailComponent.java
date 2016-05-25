package de.androidbytes.recipr.presentation.single.detail.di;


import dagger.Component;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.single.detail.presenter.RecipeDetailsPresenter;
import de.androidbytes.recipr.presentation.single.detail.view.activity.RecipeDetailsActivity;
import nz.bradcampbell.compartment.HasPresenter;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = RecipeDetailModule.class)
public interface RecipeDetailComponent extends HasPresenter<RecipeDetailsPresenter> {
    void inject(RecipeDetailsActivity recipeDetailsActivity);
}
