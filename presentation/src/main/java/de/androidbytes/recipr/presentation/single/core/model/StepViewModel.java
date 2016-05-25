package de.androidbytes.recipr.presentation.single.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepViewModel {

    private long id;
    private int number;
    private String instruction;

    public StepViewModel(long id, int number, String instruction) {
        this.id = id;
        this.number = number;
        this.instruction = instruction;
    }
}
