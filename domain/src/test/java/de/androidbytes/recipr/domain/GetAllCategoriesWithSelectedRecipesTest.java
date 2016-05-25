package de.androidbytes.recipr.domain;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.interactor.GetAllCategoriesWithSelectedRecipes;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class GetAllCategoriesWithSelectedRecipesTest {

    @Mock
    private ExecutionThread mockExecutionThread;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Mock
    private RecipeOverviewRepository mockRecipeOverviewRepository;

    @Mock
    private Recipe mockRecipe;

    private GetAllCategoriesWithSelectedRecipes getAllRecipes;
    private TestSubscriber<List<Recipe>> subscriber;

    private List<Recipe> recipes = new ArrayList<>();

    //region Setup logic
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUpExecutionThreads();
        setUpSubscriber();
        setUpTestSubject();
    }

    private void setUpExecutionThreads() {
        setUpExecutionThread();
        setUpPostExecutionThread();
    }

    private void setUpExecutionThread() {
        given(mockExecutionThread.getScheduler()).willReturn(Schedulers.newThread());
    }

    private void setUpPostExecutionThread() {
        given(mockPostExecutionThread.getScheduler()).willReturn(Schedulers.newThread());
    }

    private void setUpSubscriber() {
        subscriber = new TestSubscriber<>();
    }

    private void setUpTestSubject() {
        getAllRecipes = new GetAllCategoriesWithSelectedRecipes(2, mockRecipeOverviewRepository, mockExecutionThread, mockPostExecutionThread);
    }
    //endregion
    
    @Test
    @Ignore
    public void Execute_NoRecipesAvailable_CompletedWithoutOnNextEvent() {
        givenNoRecipesAvailable();
        executeUseCase();
        expectCompletedWithoutOnNextEvent();
    }

    private void givenNoRecipesAvailable() {
        given(mockRecipeOverviewRepository.findAllCategories(userId, 5)).willReturn(Observable.<List<Recipe>>empty());
    }

    private void expectCompletedWithoutOnNextEvent() {
        verifyCorrectMethodsCalled();

        subscriber.awaitTerminalEvent();
        subscriber.assertValueCount(0);
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        subscriber.isUnsubscribed();
    }

    @Test
    public void Execute_RecipesAvailable_ReturnRecipes() {
        givenRecipesAvailable();
        executeUseCase();
        expectCompletedWithReturnedRecipes();
    }

    private void givenRecipesAvailable() {
        recipes.add(mockRecipe);
        given(mockRecipeOverviewRepository.findAllCategories(userId, 5)).willReturn(Observable.just(recipes));
    }

    private void expectCompletedWithReturnedRecipes() {
        verifyCorrectMethodsCalled();

        subscriber.awaitTerminalEvent();
        subscriber.assertValueCount(1);
        subscriber.assertValue(recipes);
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        subscriber.isUnsubscribed();
    }

    @Test
    @Ignore
    public void Execute_ExceptionThrown_PassThroughException() {
        givenExceptionThrown();
        executeUseCase();
        expectExceptionPassThrough();
    }

    private void givenExceptionThrown() {
        given(mockRecipeOverviewRepository.findAllCategories(userId, 5)).willReturn(Observable.<List<Recipe>>error(new NullPointerException("Some Test Exception")));
    }

    private void expectExceptionPassThrough() {
        verifyCorrectMethodsCalled();

        subscriber.awaitTerminalEvent();
        subscriber.assertValueCount(0);
        subscriber.assertError(NullPointerException.class);
        subscriber.assertTerminalEvent();
        subscriber.isUnsubscribed();
    }

    private void executeUseCase() {
        getAllRecipes.execute(subscriber);
    }

    private void verifyCorrectMethodsCalled() {
        verify(mockRecipeOverviewRepository).findAllCategories(userId, 5);
        verifyNoMoreInteractions(mockRecipeOverviewRepository);

        verify(mockExecutionThread).getScheduler();
        verifyNoMoreInteractions(mockExecutionThread);

        verify(mockPostExecutionThread).getScheduler();
        verifyNoMoreInteractions(mockPostExecutionThread);
    }

}
