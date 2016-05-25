package de.androidbytes.recipr.presentation.single.add.di;


import dagger.Component;
import de.androidbytes.recipr.presentation.single.add.presenter.AddRecipePresenter;
import de.androidbytes.recipr.presentation.single.add.view.activity.AddRecipeActivity;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import nz.bradcampbell.compartment.HasPresenter;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = AddRecipeModule.class)
public interface AddRecipeComponent extends HasPresenter<AddRecipePresenter> {
    void inject(AddRecipeActivity addRecipeActivity);
}
