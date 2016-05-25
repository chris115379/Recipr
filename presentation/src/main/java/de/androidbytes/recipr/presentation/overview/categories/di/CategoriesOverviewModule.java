package de.androidbytes.recipr.presentation.overview.categories.di;

import dagger.Module;
import dagger.Provides;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.GetAllCategoriesWithSelectedRecipes;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.CategoryRecipeAggregate;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.overview.categories.model.CategoryContainer;
import de.androidbytes.recipr.presentation.overview.categories.model.mapper.RecipeToRecipeViewModelMapper;
import de.androidbytes.recipr.presentation.overview.categories.view.CategoriesOverviewViewDataProcessor;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;

import java.util.List;

@Module
public class CategoriesOverviewModule {

    int maxColumnCount;
    private long userId;

    public CategoriesOverviewModule(long userId, int maxColumnCount) {
        this.userId = userId;
        this.maxColumnCount = maxColumnCount;
    }

    @Provides
    @PerActivity
    UseCase provideGetCategoriesOverviewUseCase(
            RecipeOverviewRepository recipeOverviewRepository,
            ExecutionThread executionThread,
            PostExecutionThread postExecutionThread
    ) {
        return new GetAllCategoriesWithSelectedRecipes(userId, maxColumnCount, recipeOverviewRepository, executionThread, postExecutionThread);
    }




    @Provides
    @PerActivity
    DataMapper<Recipe, RecipeViewModel> provideRecipeToCategoryRecipeMapper() {
        return new RecipeToRecipeViewModelMapper();
    }

    @Provides
    @PerActivity
    ViewDataProcessor<List<CategoryRecipeAggregate>, List<CategoryContainer>> provideViewPreprocessor(DataMapper<Recipe, RecipeViewModel> dataMapper) {
        return new CategoriesOverviewViewDataProcessor(dataMapper);
    }

}
