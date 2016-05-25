package de.androidbytes.recipr.domain.interactor;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.SaveRecipeRepository;
import rx.Observable;

import javax.inject.Inject;

public class SaveRecipe extends BaseUseCase<Recipe> {

    private SaveRecipeRepository saveRecipeRepository;
    private Recipe recipe;
    private long userId;

    @Inject
    public SaveRecipe(long userId, SaveRecipeRepository saveRecipeRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userId = userId;
        this.saveRecipeRepository = saveRecipeRepository;
    }

    public SaveRecipe recipeToSave(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    @Override
    protected Observable<Recipe> buildUseCaseObservable() {
        return saveRecipeRepository.saveRecipe(userId, recipe);
    }
}
