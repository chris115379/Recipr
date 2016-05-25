package de.androidbytes.recipr.presentation.overview.search.data;

import android.content.Context;
import android.os.AsyncTask;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.domain.repository.RecipeOverviewRepository;

import javax.inject.Inject;
import java.util.List;

public class SearchRecipesTask extends AsyncTask<String, Void, List<Recipe>> {

    private Context context;
    private RecipeOverviewRepository recipeOverviewRepository;
    private final long userId;
    private String searchRecipes;

    @Inject
    public SearchRecipesTask(Context context, RecipeOverviewRepository recipeOverviewRepository, long userId, String searchRecipes) {
        this.context = context.getApplicationContext();
        this.recipeOverviewRepository = recipeOverviewRepository;
        this.userId = userId;
        this.searchRecipes = searchRecipes;
    }

    private OnSearchRecipesTaskFinishedListener searchRecipesTaskFinishedListener;

    public interface OnSearchRecipesTaskFinishedListener {
        void returnSearchQueryResult(List<Recipe> recipeResult);
    }

    public SearchRecipesTask registerSearchResultListener(OnSearchRecipesTaskFinishedListener searchRecipesTaskFinishedListener) {
        this.searchRecipesTaskFinishedListener = searchRecipesTaskFinishedListener;
        return this;
    }

    public SearchRecipesTask unregisterSearchResultListener() {
        searchRecipesTaskFinishedListener = null;
        return this;
    }


    @Override
    protected List<Recipe> doInBackground(String... params) {

        if(userId != 0 && searchRecipes != null) {
            return recipeOverviewRepository.findRecipesOfUserByName(userId, searchRecipes);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        if(searchRecipesTaskFinishedListener != null) {
            if(recipes != null && recipes.size() > 0) {
                searchRecipesTaskFinishedListener.returnSearchQueryResult(recipes);
            }
        }
    }
}
