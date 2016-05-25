package de.androidbytes.recipr.presentation.single.add.presenter;

import de.androidbytes.recipr.domain.interactor.SaveRecipe;
import de.androidbytes.recipr.domain.interactor.core.DefaultSubscriber;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.model.*;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.presenter.MultiUseCasePresenter;
import de.androidbytes.recipr.presentation.single.add.model.mapper.IngredientViewModelToIngredientMapper;
import de.androidbytes.recipr.presentation.single.add.model.mapper.StepViewModelToStepMapper;
import de.androidbytes.recipr.presentation.single.add.view.AddRecipeView;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@PerActivity
public class AddRecipePresenter extends MultiUseCasePresenter<AddRecipeView> {

    private List<String> cachedCategoryTitles = new ArrayList<>();

    @Inject
    IngredientViewModelToIngredientMapper ingredientMapper;

    @Inject
    StepViewModelToStepMapper stepMapper;

    @Inject
    public AddRecipePresenter(
            @Named("availableCategories") final UseCase availableCategoriesUseCase,
            @Named("saveRecipe") final UseCase saveRecipeUseCase) {
        super(new HashMap<String, UseCase>() {{
            put("availableCategories", availableCategoriesUseCase);
            put("saveRecipeAction", saveRecipeUseCase);
        }});
    }

    public void loadData() {
        executeUseCase("availableCategories", new CategoriesSubscriber());
    }

    private void prepareCategoryResultBeforeRendering(List<Category> categories) {
        cachedCategoryTitles.clear();
        for (Category category : categories) {
            cachedCategoryTitles.add(category.getName());
        }
        setDataForAutoCompletion(cachedCategoryTitles);
    }

    private void setDataForAutoCompletion(List<String> cachedCategoryTitles) {
        getView().changeCategoryAutocompletionData(cachedCategoryTitles);
    }

    public void saveRecipeAction(User loggedInUser) {
        List<Ingredient> ingredients = ingredientMapper.transform(getView().getIngredients());
        List<Step> steps = stepMapper.transform(getView().getSteps());
        String recipeName = getView().getRecipeName();
        String categoryName = getView().getCategoryName();
        String imageUrl = getView().getImageUrl();

        if (validateInput(recipeName, categoryName, ingredients, steps, imageUrl)) {
            Recipe recipe = new Recipe(recipeName, ingredients, steps, false, imageUrl, loggedInUser, new Category(loggedInUser, categoryName));
            SaveRecipe saveRecipe = (SaveRecipe) getUseCase("saveRecipeAction");
            saveRecipe.recipeToSave(recipe);
            getView().destroy();
            executeUseCase("saveRecipeAction", new DefaultSubscriber());
        }
    }

    private boolean validateInput(String recipeName, String categoryName, List<Ingredient> ingredients, List<Step> steps, String imageUrl) {

        boolean recipeNameIsValid = isRecipeNameValid(recipeName);
        showRecipeNameError(!recipeNameIsValid);

        boolean categoryNameIsValid = isCategoryNameValid(categoryName);
        showCategoryNameError(!categoryNameIsValid);

        boolean ingredientsAreValid = areIngredientsValid(ingredients);
        showIngredientsError(!ingredientsAreValid);

        boolean stepsAreValid = areStepsValid(steps);
        showStepsError(!stepsAreValid);

        return recipeNameIsValid && categoryNameIsValid && ingredientsAreValid && stepsAreValid;
    }

    private boolean isRecipeNameValid(String recipeName) {
        return recipeName != null && !recipeName.isEmpty();
    }

    private void showRecipeNameError(boolean b) {
        getView().showRecipeNameError(b);
    }

    private boolean isCategoryNameValid(String categoryName) {
        return categoryName != null && !categoryName.isEmpty();
    }

    private void showCategoryNameError(boolean b) {
        getView().showCategoryError(b);
    }

    private boolean areIngredientsValid(List<Ingredient> ingredients) {
        return ingredients != null && ingredients.size() > 0;
    }

    private void showIngredientsError(boolean b) {
        getView().showIngredientsError(b);

    }

    private boolean areStepsValid(List<Step> steps) {
        return steps != null && steps.size() > 0;
    }
    
    private void showStepsError(boolean b) {
        getView().showStepsError(b);
    }

    public boolean shouldClose() {

        List<Ingredient> ingredients = ingredientMapper.transform(getView().getIngredients());
        List<Step> steps = stepMapper.transform(getView().getSteps());
        String recipeName = getView().getRecipeName();
        String imageUrl = getView().getImageUrl();

        boolean shouldClose = !(isRecipeNameValid(recipeName) && (areIngredientsValid(ingredients) || areStepsValid(steps) || (imageUrl != null && !imageUrl.isEmpty())));

        if (!shouldClose) {
            getView().renderConfirmCloseDialog();
        }
        return shouldClose;
    }
    
    private final class CategoriesSubscriber extends DefaultSubscriber<List<Category>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onNext(List<Category> categories) {
            prepareCategoryResultBeforeRendering(categories);
        }
    }


}
