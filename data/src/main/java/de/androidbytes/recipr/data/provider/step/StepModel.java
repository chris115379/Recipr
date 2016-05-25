package de.androidbytes.recipr.data.provider.step;


import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.BaseModel;

/**
 * A recipe is an Entity used to combine related ingredients and steps for preparation.
 */
public interface StepModel extends BaseModel {

    /**
     * Get the {@code recipe_id} value.
     */
    long getRecipeId();

    /**
     * Title of the StepEntity.
     * Can be {@code null}.
     */
    @Nullable
    int getNumber();

    /**
     * Instruction of the preparation step.
     * Can be {@code null}.
     */
    @Nullable
    String getInstruction();
}
