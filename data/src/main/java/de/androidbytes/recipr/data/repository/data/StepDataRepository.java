package de.androidbytes.recipr.data.repository.data;

import android.content.ContentResolver;
import android.net.Uri;
import de.androidbytes.recipr.data.entity.StepEntity;
import de.androidbytes.recipr.data.exception.DatabaseException;
import de.androidbytes.recipr.data.provider.step.StepColumns;
import de.androidbytes.recipr.data.provider.step.StepContentValues;
import de.androidbytes.recipr.data.provider.step.StepCursor;
import de.androidbytes.recipr.data.provider.step.StepSelection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class StepDataRepository {

    private ContentResolver contentResolver;

    @Inject
    public StepDataRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public StepEntity createStep(StepEntity stepEntity) {

        StepContentValues contentValues = stepEntity.getContentValues();
        Uri createdStepUri = contentResolver.insert(StepColumns.CONTENT_URI, contentValues.values());
        if (createdStepUri != null) {
            StepCursor stepCursor = new StepCursor(contentResolver.query(createdStepUri, null, null, null, null));
            if (stepCursor.moveToFirst())
                return new StepEntity(stepCursor);
        }

        throw new DatabaseException("Could not create step with number '" + stepEntity.getNumber() + "'");

    }

    public List<StepEntity> findStepsOfRecipe(long recipeId) {
        StepSelection stepSelection = new StepSelection();
        stepSelection.recipeId(recipeId);
        StepCursor stepCursor = stepSelection.query(contentResolver);

        List<StepEntity> stepEntities = new ArrayList<>(stepCursor.getCount());
        while (stepCursor.moveToNext()) {
            stepEntities.add(new StepEntity(stepCursor));
        }

        return stepEntities;
    }
}
