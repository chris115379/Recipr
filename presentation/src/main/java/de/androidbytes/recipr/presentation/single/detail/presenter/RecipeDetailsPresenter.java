package de.androidbytes.recipr.presentation.single.detail.presenter;

import de.androidbytes.recipr.domain.interactor.ChangeFavorStateOfRecipe;
import de.androidbytes.recipr.domain.interactor.core.DefaultSubscriber;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.core.presenter.MultiUseCasePresenter;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;
import de.androidbytes.recipr.presentation.single.detail.model.RecipeDetails;
import de.androidbytes.recipr.presentation.single.detail.view.RecipeDetailsView;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;

@PerActivity
public class RecipeDetailsPresenter extends MultiUseCasePresenter<RecipeDetailsView> {

    private RecipeDetails cachedData;
    private ViewDataProcessor<Recipe, RecipeDetails> viewDataProcessor;

    @Inject
    Navigator navigator;


    @Inject
    public RecipeDetailsPresenter(
            @Named("recipeDetails") final UseCase recipeDetailsUseCase,
            @Named("changeFavorState") final UseCase changeFavorStateUseCase,
            ViewDataProcessor<Recipe, RecipeDetails> viewDataProcessor
    ) {
        super(new HashMap<String, UseCase>(){{
            put("recipeDetails", recipeDetailsUseCase);
            put("changeFavorState", changeFavorStateUseCase);
        }});
        this.viewDataProcessor = viewDataProcessor;
    }

    @Override
    public void bindView(RecipeDetailsView view) {
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

    private void prepareResultBeforeRendering(Recipe recipe) {
        cachedData = viewDataProcessor.processData(recipe);
        renderResult(cachedData);
    }

    private void renderResult(RecipeDetails recipeDetails) {
        getView().renderRecipeDetails(recipeDetails);
    }

    public void loadData() {
        executeUseCase("recipeDetails", new RecipeDetailsSubscriber());
    }

    public void changeFavorStateOfRecipe() {
        boolean favorState = !cachedData.isFavorite();
        ((ChangeFavorStateOfRecipe) getUseCase("changeFavorState")).setFavorState(favorState);
        executeUseCase("changeFavorState", new RecipeDetailsSubscriber());
    }

    private final class RecipeDetailsSubscriber extends DefaultSubscriber<Recipe> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onNext(Recipe recipe) {
            prepareResultBeforeRendering(recipe);
        }
    }
}