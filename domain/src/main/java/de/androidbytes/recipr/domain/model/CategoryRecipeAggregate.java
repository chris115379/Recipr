package de.androidbytes.recipr.domain.model;

import java.util.List;

/**
 * Created by Christoph on 23.05.2016.
 */
public class CategoryRecipeAggregate {

    private Category category;
    private List<Recipe> recipes;

    public CategoryRecipeAggregate(Category category, List<Recipe> recipes) {
        this.category = category;
        this.recipes = recipes;
    }

    public Category getCategory() {
        return category;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
