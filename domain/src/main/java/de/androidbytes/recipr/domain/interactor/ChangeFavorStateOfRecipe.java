package de.androidbytes.recipr.domain.interactor;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeDetailRepository;
import rx.Observable;

import javax.inject.Inject;

public class ChangeFavorStateOfRecipe extends BaseUseCase<Recipe> {

    private RecipeDetailRepository recipeDetailRepository;
    private long userId;
    private long recipeId;
    private boolean favorState = false;

    @Inject
    public ChangeFavorStateOfRecipe(long userId, long recipeId, RecipeDetailRepository recipeDetailRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userId = userId;
        this.recipeId = recipeId;
        this.recipeDetailRepository = recipeDetailRepository;
    }

    public ChangeFavorStateOfRecipe setFavorState(boolean favorState) {
        this.favorState = favorState;
        return this;
    }

    @Override
    protected Observable<Recipe> buildUseCaseObservable() {
        return recipeDetailRepository.changeFavorStateOfRecipe(userId, recipeId, favorState);
    }
}
