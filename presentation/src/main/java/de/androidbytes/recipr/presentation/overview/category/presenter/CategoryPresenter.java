package de.androidbytes.recipr.presentation.overview.category.presenter;

import android.app.Activity;
import android.widget.ImageView;
import de.androidbytes.recipr.domain.interactor.core.DefaultSubscriber;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.core.presenter.UseCasePresenter;
import de.androidbytes.recipr.presentation.overview.category.view.CategoryView;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;

import javax.inject.Inject;
import java.util.List;

@PerActivity
public class CategoryPresenter extends UseCasePresenter<CategoryView> {

    private String categoryName;
    private List<RecipeViewModel> cachedData;

    private ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> viewDataProcessor;

    @Inject
    Navigator navigator;

    @Inject
    public CategoryPresenter(
            UseCase useCase,
            ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> viewDataProcessor
    ) {
        super(useCase);
        this.viewDataProcessor = viewDataProcessor;
    }

    @Override
    public void bindView(CategoryView view) {
        super.bindView(view);
        displayCachedDataIfExist();
    }

    private void displayCachedDataIfExist() {
        if (cachedData != null)
            renderResult(categoryName, cachedData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cachedData = null;
    }

    private void prepareResultBeforeRendering(List<Recipe> recipes) {
        cachedData = viewDataProcessor.processData(recipes);
        if (recipes.size() > 0)
            categoryName = recipes.get(0).getCategory().getName();
        else
            categoryName = "";
        renderResult(categoryName, cachedData);
    }

    private void renderResult(String categoryName, List<RecipeViewModel> viewModels) {
        getView().renderTitle(categoryName);
        getView().renderCategoryRecipes(viewModels);
    }

    public void loadData() {
        executeUseCase(new CategorySubscriber());
    }

    public void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel) {
        navigator.navigateToRecipeDetailsView((Activity) getView(), rootImageView, recipeViewModel.getRecipeId());
    }

    private final class CategorySubscriber extends DefaultSubscriber<List<Recipe>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onNext(List<Recipe> recipes) {
            prepareResultBeforeRendering(recipes);
        }
    }
}
