package de.androidbytes.recipr.presentation.single.detail.di;

import dagger.Module;
import dagger.Provides;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.ChangeFavorStateOfRecipe;
import de.androidbytes.recipr.domain.interactor.GetRecipeDetails;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.Ingredient;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.model.Step;
import de.androidbytes.recipr.domain.repository.RecipeDetailRepository;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.single.core.model.IngredientViewModel;
import de.androidbytes.recipr.presentation.single.detail.model.RecipeDetails;
import de.androidbytes.recipr.presentation.single.core.model.StepViewModel;
import de.androidbytes.recipr.presentation.single.detail.model.mapper.IngredientToIngredientViewModelMapper;
import de.androidbytes.recipr.presentation.single.detail.model.mapper.RecipeToRecipeDetailsMapper;
import de.androidbytes.recipr.presentation.single.detail.model.mapper.StepToStepViewModelMapper;
import de.androidbytes.recipr.presentation.single.detail.view.RecipeDetailsViewDataProcessor;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;

import javax.inject.Named;

@Module
public class RecipeDetailModule {

    private long recipeId;
    long userId;

    public RecipeDetailModule(long userId, long recipeId) {
        this.userId = userId;
        this.recipeId = recipeId;
    }

    @Provides
    @PerActivity
    @Named("recipeDetails")
    UseCase provideGetRecipeDetailsUseCase(RecipeDetailRepository recipeDetailRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new GetRecipeDetails(userId, recipeId, recipeDetailRepository, executionThread, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("changeFavorState")
    UseCase provideChangeFavorStateOfRecipeUseCase(RecipeDetailRepository recipeDetailRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new ChangeFavorStateOfRecipe(userId, recipeId, recipeDetailRepository, executionThread, postExecutionThread);
    }





    @Provides
    @PerActivity
    DataMapper<Ingredient, IngredientViewModel> provideIngredientToIngredientViewModelMapper() {
        return new IngredientToIngredientViewModelMapper();
    }

    @Provides
    @PerActivity
    DataMapper<Step, StepViewModel> provideStepToStepViewModelMapper() {
        return new StepToStepViewModelMapper();
    }

    @Provides
    @PerActivity
    DataMapper<Recipe, RecipeDetails> provideRecipeToRecipeDetailsMapper(
            DataMapper<Ingredient, IngredientViewModel> ingredientMapper,
            DataMapper<Step, StepViewModel> stepMapper) {
        return new RecipeToRecipeDetailsMapper(ingredientMapper, stepMapper);
    }

    @Provides
    @PerActivity
    ViewDataProcessor<Recipe, RecipeDetails> provideViewPreprocessor(DataMapper<Recipe, RecipeDetails> dataMapper) {
        return new RecipeDetailsViewDataProcessor(dataMapper);
    }

}
