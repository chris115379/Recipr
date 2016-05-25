package de.androidbytes.recipr.domain.interactor;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import de.androidbytes.recipr.domain.model.Category;
import de.androidbytes.recipr.domain.repository.CategoryRepository;
import rx.Observable;

import javax.inject.Inject;
import java.util.List;

public class GetAllCategories extends BaseUseCase<List<Category>> {

    private CategoryRepository categoryRepository;
    private long userId;

    @Inject
    public GetAllCategories(long userId, CategoryRepository categoryRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userId = userId;
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected Observable<List<Category>> buildUseCaseObservable() {
        return categoryRepository.findAllCategories(userId);
    }
}
