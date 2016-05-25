package de.androidbytes.recipr.presentation.single.add.model.mapper;

import de.androidbytes.recipr.domain.model.Ingredient;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.single.core.model.IngredientViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IngredientViewModelToIngredientMapper implements DataMapper<IngredientViewModel, Ingredient> {

    @Inject
    public IngredientViewModelToIngredientMapper() {
    }

    @Override
    public Ingredient transform(IngredientViewModel model) {
        return new Ingredient(model.getId(), model.getName(), model.getQuantity(), model.getUnit());
    }

    @Override
    public List<Ingredient> transform(Collection<IngredientViewModel> modelCollection) {
        List<Ingredient> ingredients = new ArrayList<>(modelCollection.size());
        for (IngredientViewModel ingredientViewModel : modelCollection) {
            ingredients.add(transform(ingredientViewModel));
        }
        return ingredients;
    }
}
