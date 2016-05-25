package de.androidbytes.recipr.presentation.single.add.model.mapper;

import de.androidbytes.recipr.domain.model.Step;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.single.core.model.StepViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StepViewModelToStepMapper implements DataMapper<StepViewModel, Step> {

    @Inject
    public StepViewModelToStepMapper() {
    }

    @Override
    public Step transform(StepViewModel model) {
        return new Step(model.getId(), model.getNumber(), model.getInstruction());
    }

    @Override
    public List<Step> transform(Collection<StepViewModel> modelCollection) {
        List<Step> steps = new ArrayList<>(modelCollection.size());
        for (StepViewModel stepViewModel : modelCollection) {
            steps.add(transform(stepViewModel));
        }
        return steps;
    }
}
