package de.androidbytes.recipr.presentation.single.add.di;

import dagger.Module;
import dagger.Provides;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.GetAllCategories;
import de.androidbytes.recipr.domain.interactor.SaveRecipe;
import de.androidbytes.recipr.domain.interactor.core.UseCase;
import de.androidbytes.recipr.domain.repository.CategoryRepository;
import de.androidbytes.recipr.domain.repository.SaveRecipeRepository;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;

import javax.inject.Named;

@Module
public class AddRecipeModule {

    long userId;

    public AddRecipeModule(long userId) {
        this.userId = userId;
    }

    @Provides
    @PerActivity
    @Named("availableCategories")
    UseCase provideGetAllCategoriesUseCase(CategoryRepository categoryRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new GetAllCategories(userId, categoryRepository, executionThread, postExecutionThread);
    }

    @Provides
    @PerActivity
    @Named("saveRecipe")
    UseCase provideSaveRecipeUseCase(SaveRecipeRepository saveRecipeRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        return new SaveRecipe(userId, saveRecipeRepository, executionThread, postExecutionThread);
    }

}
