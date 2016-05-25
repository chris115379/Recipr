package de.androidbytes.recipr.presentation.overview.categories.view;

import de.androidbytes.recipr.domain.model.Category;
import de.androidbytes.recipr.domain.model.CategoryRecipeAggregate;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.overview.categories.model.CategoryContainer;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.ViewDataProcessor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@PerActivity
public class CategoriesOverviewViewDataProcessor implements ViewDataProcessor<List<CategoryRecipeAggregate>, List<CategoryContainer>> {

    private DataMapper<Recipe, RecipeViewModel> dataMapper;

    @Inject
    public CategoriesOverviewViewDataProcessor(DataMapper<Recipe, RecipeViewModel> dataMapper) {
        this.dataMapper = dataMapper;
    }

    @Override
    public List<CategoryContainer> processData(List<CategoryRecipeAggregate> categoryRecipeAggregates) {

        List<CategoryContainer> categoryContainers = new ArrayList<>(categoryRecipeAggregates.size());
        for (CategoryRecipeAggregate categoryRecipeAggregate : categoryRecipeAggregates) {
            Category category = categoryRecipeAggregate.getCategory();
            List<Recipe> recipes = categoryRecipeAggregate.getRecipes();
            List<RecipeViewModel> recipeViewModels = dataMapper.transform(recipes);
            categoryContainers.add(new CategoryContainer(category.getName(), recipeViewModels));
        }

        return categoryContainers;
    }
}
