package de.androidbytes.recipr.domain.interactor;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import de.androidbytes.recipr.domain.model.CategoryRecipeAggregate;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;
import rx.Observable;

import javax.inject.Inject;
import java.util.List;

public class GetAllCategoriesWithSelectedRecipes extends BaseUseCase<List<CategoryRecipeAggregate>> {

    private RecipeOverviewRepository recipeOverviewRepository;
    private int recipesPerCategoryCount;
    private long userId;

    @Inject
    public GetAllCategoriesWithSelectedRecipes(long userId, int recipesPerCategoryCount, RecipeOverviewRepository recipeOverviewRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userId = userId;
        this.recipesPerCategoryCount = recipesPerCategoryCount;
        this.recipeOverviewRepository = recipeOverviewRepository;
    }

    @Override
    protected Observable<List<CategoryRecipeAggregate>> buildUseCaseObservable() {
        return recipeOverviewRepository.findAllCategories(userId, recipesPerCategoryCount);
    }
}
