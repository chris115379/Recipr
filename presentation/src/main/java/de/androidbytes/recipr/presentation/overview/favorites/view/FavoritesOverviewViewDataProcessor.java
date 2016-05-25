package de.androidbytes.recipr.presentation.overview.favorites.view;

import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;

import javax.inject.Inject;
import java.util.List;

@PerActivity
public class FavoritesOverviewViewDataProcessor implements ViewDataProcessor<List<Recipe>, List<RecipeViewModel>> {

    private DataMapper<Recipe, RecipeViewModel> dataMapper;

    @Inject
    public FavoritesOverviewViewDataProcessor(DataMapper<Recipe, RecipeViewModel> dataMapper) {
        this.dataMapper = dataMapper;
    }

    @Override
    public List<RecipeViewModel> processData(List<Recipe> recipes) {
        //noinspection UnnecessaryLocalVariable --> Used for clarification
        List<RecipeViewModel> recipeViewModels = dataMapper.transform(recipes);
        return recipeViewModels;
    }
}
