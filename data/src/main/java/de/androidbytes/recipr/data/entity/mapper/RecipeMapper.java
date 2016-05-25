package de.androidbytes.recipr.data.entity.mapper;

import de.androidbytes.recipr.data.entity.*;
import de.androidbytes.recipr.domain.model.Ingredient;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.model.Step;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;


@Singleton
public class RecipeMapper {

    @Inject
    IngredientMapper ingredientMapper;

    @Inject
    StepMapper stepMapper;

    @Inject
    UserMapper userMapper;

    @Inject
    CategoryMapper categoryMapper;

    @Inject
    public RecipeMapper() {
    }

    public Recipe map(RecipeEntity recipeEntity, List<IngredientEntity> ingredientEntities, List<StepEntity> stepEntities, UserEntity userEntity, CategoryEntity categoryEntity) {
        final List<Ingredient> ingredients = new ArrayList<>(ingredientEntities.size());
        for (IngredientEntity ingredientEntity : ingredientEntities) {
            ingredients.add(ingredientMapper.map(ingredientEntity));
        }

        final List<Step> steps = new ArrayList<>(stepEntities.size());
        for (StepEntity stepEntity : stepEntities) {
            steps.add(stepMapper.map(stepEntity));
        }

        return new Recipe(
                recipeEntity.getId(),
                recipeEntity.getName(),
                ingredients,
                steps,
                recipeEntity.isFavorite(),
                recipeEntity.getImageUrl(),
                userMapper.map(userEntity),
                categoryMapper.map(categoryEntity, userEntity)
        );
    }
}
