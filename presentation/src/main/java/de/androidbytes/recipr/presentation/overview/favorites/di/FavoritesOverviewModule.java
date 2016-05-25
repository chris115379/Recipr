package de.androidbytes.recipr.presentation.overview.favorites.di;

import dagger.Module;
import dagger.Provides;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.GetAllFavoriteRecipes;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.overview.categories.model.mapper.RecipeToRecipeViewModelMapper;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;
import de.androidbytes.recipr.presentation.overview.favorites.view.FavoritesOverviewViewDataProcessor;

import java.util.List;

@Module
public class FavoritesOverviewModule {

    long userId;

    public FavoritesOverviewModule(long userId) {
        this.userId = userId;
    }

    @Provides
    @PerActivity
    UseCase provideGetCategoriesOverviewUseCase(
            RecipeOverviewRepository recipeOverviewRepository,
            ExecutionThread executionThread,
            PostExecutionThread postExecutionThread
    ) {
        return new GetAllFavoriteRecipes(userId, recipeOverviewRepository, executionThread, postExecutionThread);
    }




    @Provides
    @PerActivity
    DataMapper<Recipe, RecipeViewModel> provideRecipeToCategoryRecipeMapper() {
        return new RecipeToRecipeViewModelMapper();
    }

    @Provides
    @PerActivity
    ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> provideViewPreprocessor(DataMapper<Recipe, RecipeViewModel> dataMapper) {
        return new FavoritesOverviewViewDataProcessor(dataMapper);
    }

}
