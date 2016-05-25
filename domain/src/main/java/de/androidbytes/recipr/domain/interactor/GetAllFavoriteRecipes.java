package de.androidbytes.recipr.domain.interactor;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;
import rx.Observable;

import javax.inject.Inject;
import java.util.List;

public class GetAllFavoriteRecipes extends BaseUseCase<List<Recipe>> {

    private RecipeOverviewRepository recipeOverviewRepository;
    private long userId;

    @Inject
    public GetAllFavoriteRecipes(long userId, RecipeOverviewRepository recipeOverviewRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userId = userId;
        this.recipeOverviewRepository = recipeOverviewRepository;
    }

    @Override
    protected Observable<List<Recipe>> buildUseCaseObservable() {
        return recipeOverviewRepository.findAllFavorites(userId);
    }
}
