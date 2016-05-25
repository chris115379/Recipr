package de.androidbytes.recipr.presentation.overview.search.presenter;

import android.app.Activity;
import android.widget.ImageView;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;
import de.androidbytes.recipr.presentation.overview.search.data.SearchRecipesTask;
import de.androidbytes.recipr.presentation.overview.search.view.SearchResultView;
import nz.bradcampbell.compartment.BasePresenter;

import javax.inject.Inject;
import java.util.List;

@PerActivity
public class SearchResultPresenter extends BasePresenter<SearchResultView> implements SearchRecipesTask.OnSearchRecipesTaskFinishedListener {

    private List<RecipeViewModel> cachedData;
    private final ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> viewDataProcessor;
    private final Navigator navigator;

    private SearchRecipesTask searchRecipesTask;

    @Inject
    public SearchResultPresenter(
            SearchRecipesTask searchRecipesTask,
            ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> viewDataProcessor,
            Navigator navigator) {
        this.searchRecipesTask = searchRecipesTask;
        this.viewDataProcessor = viewDataProcessor;
        this.navigator = navigator;
    }

    @Override
    public void bindView(SearchResultView view) {
        super.bindView(view);
        displayCachedDataIfExist();
    }

    private void displayCachedDataIfExist() {
        if (cachedData != null)
            renderResult(cachedData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cachedData = null;
        searchRecipesTask.unregisterSearchResultListener();
    }

    private void prepareResultBeforeRendering(List<Recipe> recipes) {
        cachedData = viewDataProcessor.processData(recipes);
        renderResult(cachedData);
    }

    private void renderResult(List<RecipeViewModel> viewModels) {
        getView().renderRecipes(viewModels);
    }

    public void loadData() {
        searchRecipesTask.registerSearchResultListener(this);
        searchRecipesTask.execute();
    }

    public void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel) {
        navigator.navigateToRecipeDetailsView((Activity) getView(), rootImageView, recipeViewModel.getRecipeId());
    }

    @Override
    public void returnSearchQueryResult(List<Recipe> recipeResult) {
        if (recipeResult != null && recipeResult.size() > 0) {
            prepareResultBeforeRendering(recipeResult);
        }
    }
}
