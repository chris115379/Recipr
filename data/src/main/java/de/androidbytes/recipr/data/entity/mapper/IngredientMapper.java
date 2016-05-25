package de.androidbytes.recipr.data.entity.mapper;

import de.androidbytes.recipr.data.entity.IngredientEntity;
import de.androidbytes.recipr.domain.model.Ingredient;

import javax.inject.Inject;

/**
 * Created by Christoph on 22.05.2016.
 */
public class IngredientMapper {

    @Inject public IngredientMapper () {}

    public Ingredient map (IngredientEntity ingredientEntity) {
        return new Ingredient(
                ingredientEntity.getId(),
                ingredientEntity.getName(),
                ingredientEntity.getQuantity(),
                ingredientEntity.getUnit()
        );
    }

}
