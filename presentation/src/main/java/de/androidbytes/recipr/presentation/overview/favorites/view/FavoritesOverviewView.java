package de.androidbytes.recipr.presentation.overview.favorites.view;


import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;

import java.util.List;

/**
 * Created by Christoph on 05.05.2016.
 */
public interface FavoritesOverviewView {

    void renderFavoritesOverview(List<RecipeViewModel> recipes);
    void renderEmptyView();

}
