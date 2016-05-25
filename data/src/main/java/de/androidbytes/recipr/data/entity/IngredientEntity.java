package de.androidbytes.recipr.data.entity;

import de.androidbytes.recipr.data.provider.ingredient.IngredientContentValues;
import de.androidbytes.recipr.data.provider.ingredient.IngredientCursor;
import de.androidbytes.recipr.domain.model.Ingredient;
import lombok.Getter;

@Getter
public class IngredientEntity {

    private long id;
    private String name;
    private String quantity;
    private String unit;
    private long recipeId;

    public IngredientEntity(long id, String name, String quantity, String unit, long recipeId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.recipeId = recipeId;
    }

    public IngredientEntity(IngredientCursor cursor) {
        this(
                cursor.getId(),
                cursor.getName(),
                cursor.getQuantity(),
                cursor.getUnit(),
                cursor.getRecipeId()
        );
    }

    public IngredientEntity(Ingredient ingredient, long recipeId) {
        this(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                recipeId
        );
    }

    public IngredientContentValues getContentValues() {
        IngredientContentValues ingredientContentValues = new IngredientContentValues();
        ingredientContentValues.putName(name);
        ingredientContentValues.putQuantity(quantity);
        ingredientContentValues.putUnit(unit);
        ingredientContentValues.putRecipeId(recipeId);
        return ingredientContentValues;
    }
}
