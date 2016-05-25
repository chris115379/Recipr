package de.androidbytes.recipr.presentation.single.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientViewModel {

    private long id;
    private String name;
    private String quantity;
    private String unit;

    public IngredientViewModel(long id, String name, String quantity, String unit) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

}
