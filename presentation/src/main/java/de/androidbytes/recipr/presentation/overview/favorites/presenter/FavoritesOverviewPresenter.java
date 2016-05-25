package de.androidbytes.recipr.presentation.overview.favorites.presenter;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import de.androidbytes.recipr.domain.interactor.core.DefaultSubscriber;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.core.presenter.UseCasePresenter;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;
import de.androidbytes.recipr.presentation.overview.favorites.view.FavoritesOverviewView;

import javax.inject.Inject;
import java.util.List;

@PerActivity
public class FavoritesOverviewPresenter extends UseCasePresenter<FavoritesOverviewView> {

    private List<RecipeViewModel> cachedData;
    private ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> viewDataProcessor;

    @Inject
    Navigator navigator;

    @Inject
    public FavoritesOverviewPresenter(
            UseCase useCase,
            ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> viewDataProcessor
    ) {
        super(useCase);
        this.viewDataProcessor = viewDataProcessor;
    }

    @Override
    public void bindView(FavoritesOverviewView view) {
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
    }

    private void prepareResultBeforeRendering(List<Recipe> recipes) {
        cachedData = viewDataProcessor.processData(recipes);
        renderResult(cachedData);
    }

    private void renderResult(List<RecipeViewModel> recipeViewModels) {
        if(recipeViewModels.size() == 0) {
            getView().renderEmptyView();
        } else {
            getView().renderFavoritesOverview(recipeViewModels);
        }
    }

    public void loadData() {
        executeUseCase(new CategoriesOverviewSubscriber());
    }

    public void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel) {
        navigator.navigateToRecipeDetailsView(((Fragment) getView()).getActivity(), rootImageView, recipeViewModel.getRecipeId());
    }
    
    private final class CategoriesOverviewSubscriber extends DefaultSubscriber<List<Recipe>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onNext(List<Recipe> recipes) {
            prepareResultBeforeRendering(recipes);
        }
    }
}