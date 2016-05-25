package de.androidbytes.recipr.data.entity;

import de.androidbytes.recipr.data.provider.step.StepContentValues;
import de.androidbytes.recipr.data.provider.step.StepCursor;
import de.androidbytes.recipr.domain.model.Step;
import lombok.Getter;

@Getter
public class StepEntity {

    private long id;
    private int number;
    private String instruction;
    private long recipeId;

    public StepEntity(long id, int number, String instruction, long recipeId) {
        this.id = id;
        this.number = number;
        this.instruction = instruction;
        this.recipeId = recipeId;
    }

    public StepEntity(StepCursor cursor) {
        this(
                cursor.getId(),
                cursor.getNumber(),
                cursor.getInstruction(),
                cursor.getRecipeId()
        );
    }

    public StepEntity(Step step, long recipeId) {
        this(
                step.getId(),
                step.getNumber(),
                step.getInstruction(),
                recipeId
        );
    }

    public StepContentValues getContentValues() {
        StepContentValues stepContentValues = new StepContentValues();
        stepContentValues.putNumber(number);
        stepContentValues.putInstruction(instruction);
        stepContentValues.putRecipeId(recipeId);
        return stepContentValues;
    }
}
