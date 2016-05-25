package de.androidbytes.recipr.presentation.overview.categories.view;


import de.androidbytes.recipr.presentation.overview.categories.model.CategoryContainer;

import java.util.List;

/**
 * Created by Christoph on 05.05.2016.
 */
public interface CategoriesOverviewView {

    void renderCategoriesOverview(List<CategoryContainer> recipes);
    void renderEmptyView();

}
