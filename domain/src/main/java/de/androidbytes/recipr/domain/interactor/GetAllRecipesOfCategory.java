package de.androidbytes.recipr.domain.interactor;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;
import rx.Observable;

import javax.inject.Inject;
import java.util.List;

public class GetAllRecipesOfCategory extends BaseUseCase<List<Recipe>> {

    private RecipeOverviewRepository recipeOverviewRepository;
    private String categoryName;
    private long userId;

    @Inject
    public GetAllRecipesOfCategory(long userId, String categoryName, RecipeOverviewRepository recipeOverviewRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userId = userId;
        this.categoryName = categoryName;
        this.recipeOverviewRepository = recipeOverviewRepository;
    }

    @Override
    protected Observable<List<Recipe>> buildUseCaseObservable() {
        return recipeOverviewRepository.getRecipesOfCategory(userId, categoryName);
    }
}
