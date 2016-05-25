package de.androidbytes.recipr.presentation.overview.category.view;


import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;

import java.util.List;

/**
 * Created by Christoph on 05.05.2016.
 */
public interface CategoryView {

    void renderCategoryRecipes(List<RecipeViewModel> recipes);

    void renderTitle(String categoryName);
}
