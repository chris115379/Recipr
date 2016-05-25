package de.androidbytes.recipr.presentation.single.detail.model.mapper;

import de.androidbytes.recipr.domain.model.Ingredient;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.single.core.model.IngredientViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IngredientToIngredientViewModelMapper implements DataMapper<Ingredient, IngredientViewModel> {

    @Override
    public IngredientViewModel transform(Ingredient model) {
        return new IngredientViewModel(
                model.getId(),
                model.getName(),
                model.getQuantity(),
                model.getUnit()
        );
    }

    @Override
    public List<IngredientViewModel> transform(Collection<Ingredient> modelCollection) {

        List<IngredientViewModel> ingredientViewModels = new ArrayList<>(modelCollection.size());
        for (Ingredient ingredient : modelCollection) {
            ingredientViewModels.add(transform(ingredient));
        }
        return ingredientViewModels;
    }
}
