package de.androidbytes.recipr.presentation.single.detail.model.mapper;

import de.androidbytes.recipr.domain.model.Step;
import de.androidbytes.recipr.presentation.core.model.DataMapper;
import de.androidbytes.recipr.presentation.single.core.model.StepViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StepToStepViewModelMapper implements DataMapper<Step, StepViewModel> {

    @Override
    public StepViewModel transform(Step model) {
        return new StepViewModel(
                model.getId(),
                model.getNumber(),
                model.getInstruction()
        );
    }

    @Override
    public List<StepViewModel> transform(Collection<Step> modelCollection) {

        List<StepViewModel> stepViewModels = new ArrayList<>(modelCollection.size());
        for (Step step : modelCollection) {
            stepViewModels.add(transform(step));
        }
        return stepViewModels;
    }
}
