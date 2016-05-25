package de.androidbytes.recipr.presentation.single.add.view;

import de.androidbytes.recipr.presentation.single.core.model.IngredientViewModel;
import de.androidbytes.recipr.presentation.single.core.model.StepViewModel;

import java.util.List;

/**
 * Created by Christoph on 16.05.2016.
 */
public interface AddRecipeView {
    void changeCategoryAutocompletionData(List<String> cachedCategoryTitles);

    String getRecipeName();

    String getCategoryName();

    List<IngredientViewModel> getIngredients();

    List<StepViewModel> getSteps();

    void destroy();

    String getImageUrl();

    void showStepsError(boolean show);

    void showIngredientsError(boolean show);

    void showCategoryError(boolean show);

    void showRecipeNameError(boolean show);

    void renderConfirmCloseDialog();
}
