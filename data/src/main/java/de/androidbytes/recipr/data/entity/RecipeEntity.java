package de.androidbytes.recipr.data.entity;

import de.androidbytes.recipr.data.provider.recipe.RecipeContentValues;
import de.androidbytes.recipr.data.provider.recipe.RecipeCursor;
import de.androidbytes.recipr.domain.model.Recipe;
import lombok.Getter;


@Getter
public class RecipeEntity {

    private long id;
    private String name;
    private boolean favorite;
    private String imageUrl;
    private long userId;
    private long categoryId;

    public RecipeEntity(long id, String name, boolean favorite, String imageUrl, long userId, long categoryId) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public RecipeEntity(RecipeCursor cursor) {
        this(
                cursor.getId(),
                cursor.getName(),
                cursor.isFavorite(),
                cursor.getImageUrl(),
                cursor.getUserId(),
                cursor.getCategoryId()
        );
    }

    public RecipeEntity(Recipe recipe, long userId, long categoryId) {
        this(
                recipe.getId(),
                recipe.getName(),
                recipe.isFavorite(),
                recipe.getImageUrl(),
                userId,
                categoryId
        );
    }

    public RecipeContentValues getContentValues() {
        RecipeContentValues recipeContentValues = new RecipeContentValues();
        recipeContentValues.putName(name);
        recipeContentValues.putFavorite(favorite);
        recipeContentValues.putImageUrl(imageUrl);
        recipeContentValues.putUserId(userId);
        recipeContentValues.putCategoryId(categoryId);
        return recipeContentValues;
    }
}
