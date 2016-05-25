package de.androidbytes.recipr.presentation.overview.categories.model;

import android.support.annotation.NonNull;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryContainer implements Comparable<CategoryContainer> {

    private final String categoryName;
    private final List<RecipeViewModel> categorizedRecipes = new ArrayList<>();

    public CategoryContainer(String categoryName, List<RecipeViewModel> categorizedRecipes) {
        this.categoryName = categoryName;
        this.categorizedRecipes.clear();
        this.categorizedRecipes.addAll(categorizedRecipes);
    }

    @Override
    public int compareTo(@NonNull CategoryContainer another) {
        return this.getCategoryName().compareTo(another.getCategoryName());
    }
}
