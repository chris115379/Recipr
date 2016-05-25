package de.androidbytes.recipr.presentation.overview.categories.presenter;

import android.widget.ImageView;
import de.androidbytes.recipr.domain.interactor.core.DefaultSubscriber;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.CategoryRecipeAggregate;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.core.presenter.UseCasePresenter;
import de.androidbytes.recipr.presentation.overview.categories.model.CategoryContainer;
import de.androidbytes.recipr.presentation.overview.categories.view.CategoriesOverviewView;
import de.androidbytes.recipr.presentation.overview.categories.view.fragment.CategoriesOverviewFragment;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;

import javax.inject.Inject;
import java.util.List;

@PerActivity
public class CategoriesOverviewPresenter extends UseCasePresenter<CategoriesOverviewView> {

    private List<CategoryContainer> cachedData;
    private ViewDataProcessor<List<CategoryRecipeAggregate>, List<CategoryContainer>> viewDataProcessor;

    @Inject
    Navigator navigator;

    @Inject
    public CategoriesOverviewPresenter(
            UseCase useCase,
            ViewDataProcessor<List<CategoryRecipeAggregate>, List<CategoryContainer>> viewDataProcessor
    ) {
        super(useCase);
        this.viewDataProcessor = viewDataProcessor;
    }

    @Override
    public void bindView(CategoriesOverviewView view) {
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

    private void prepareResultBeforeRendering(List<CategoryRecipeAggregate> recipes) {
        cachedData = viewDataProcessor.processData(recipes);
        renderResult(cachedData);
    }

    private void renderResult(List<CategoryContainer> categoryContainers) {
        if(categoryContainers.size() == 0) {
            getView().renderEmptyView();
        } else {
            getView().renderCategoriesOverview(categoryContainers);
        }
    }

    public void loadData() {
        executeUseCase(new CategoriesOverviewSubscriber());
    }

    public void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel) {
        navigator.navigateToRecipeDetailsView(((CategoriesOverviewFragment) getView()).getActivity(), rootImageView, recipeViewModel.getRecipeId());
    }
    
    public void onExpandSectionClicked(String categoryName) {
        navigator.navigateToCategoryView(((CategoriesOverviewFragment) getView()).getActivity(), categoryName);
    }
    
    private final class CategoriesOverviewSubscriber extends DefaultSubscriber<List<CategoryRecipeAggregate>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onNext(List<CategoryRecipeAggregate> recipes) {
            prepareResultBeforeRendering(recipes);
        }
    }
}