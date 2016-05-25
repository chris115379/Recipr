package de.androidbytes.recipr.presentation.single.detail.view;

import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.single.detail.model.RecipeDetails;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;

import javax.inject.Inject;

@PerActivity
public class RecipeDetailsViewDataProcessor implements ViewDataProcessor<Recipe, RecipeDetails> {

    DataMapper<Recipe, RecipeDetails> dataMapper;

    @Inject
    public RecipeDetailsViewDataProcessor(DataMapper<Recipe, RecipeDetails> dataMapper) {
        this.dataMapper = dataMapper;
    }

    @Override
    public RecipeDetails processData(Recipe recipe) {
        return dataMapper.transform(recipe);
    }
}
