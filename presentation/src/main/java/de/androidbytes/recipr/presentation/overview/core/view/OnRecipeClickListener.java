package de.androidbytes.recipr.presentation.overview.core.view;

import android.widget.ImageView;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;

/**
 * Created by Christoph on 09.05.2016.
 */
public interface OnRecipeClickListener {
    void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel);
}
