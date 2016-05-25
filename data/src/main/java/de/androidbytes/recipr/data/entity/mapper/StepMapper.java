package de.androidbytes.recipr.data.entity.mapper;

import de.androidbytes.recipr.data.entity.StepEntity;
import de.androidbytes.recipr.domain.model.Step;

import javax.inject.Inject;

public class StepMapper {

    @Inject public StepMapper() {}

    public Step map(StepEntity stepEntity) {
        return new Step(
                stepEntity.getId(),
                stepEntity.getNumber(),
                stepEntity.getInstruction()
        );
    }
}
