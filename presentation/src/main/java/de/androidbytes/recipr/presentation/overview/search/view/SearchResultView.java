package de.androidbytes.recipr.presentation.overview.search.view;


import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;

import java.util.List;

/**
 * Created by Christoph on 05.05.2016.
 */
public interface SearchResultView {

    void renderRecipes(List<RecipeViewModel> recipes);
}
