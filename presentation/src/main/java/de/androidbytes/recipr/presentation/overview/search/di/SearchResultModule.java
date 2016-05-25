package de.androidbytes.recipr.presentation.overview.search.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.overview.categories.model.mapper.RecipeToRecipeViewModelMapper;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;
import de.androidbytes.recipr.presentation.overview.favorites.view.FavoritesOverviewViewDataProcessor;
import de.androidbytes.recipr.presentation.overview.search.data.SearchRecipesTask;
import de.androidbytes.recipr.presentation.overview.search.presenter.SearchResultPresenter;

import java.util.List;

@Module
public class SearchResultModule {

    private String searchedRecipeName;
    private long userId;

    public SearchResultModule(long userId, String searchedRecipeName) {
        this.userId = userId;
        this.searchedRecipeName = searchedRecipeName;
    }

    @Provides
    @PerActivity
    DataMapper<Recipe, RecipeViewModel> provideRecipeToRecipeViewModelMapper() {
        return new RecipeToRecipeViewModelMapper();
    }

    @Provides
    @PerActivity
    ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> provideViewPreprocessor(DataMapper<Recipe, RecipeViewModel> dataMapper) {
        return new FavoritesOverviewViewDataProcessor(dataMapper);
    }

    @Provides
    @PerActivity
    SearchRecipesTask provideSearchRecipesTask(Context context, RecipeOverviewRepository recipeOverviewRepository) {
        return new SearchRecipesTask(context, recipeOverviewRepository, userId, searchedRecipeName);
    }

    @Provides
    @PerActivity
    SearchResultPresenter provideSearchResultPresenter(SearchRecipesTask searchRecipesTask, ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> viewDataProcessor, Navigator navigator) {
        return new SearchResultPresenter(searchRecipesTask, viewDataProcessor, navigator);
    }

}
