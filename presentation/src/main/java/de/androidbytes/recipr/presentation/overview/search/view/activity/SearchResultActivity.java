package de.androidbytes.recipr.presentation.overview.search.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.BindInt;
import butterknife.BindView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.ReciprApplication;
import de.androidbytes.recipr.presentation.core.view.activity.PresenterActivity;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.OnRecipeClickListener;
import de.androidbytes.recipr.presentation.overview.search.di.DaggerSearchResultComponent;
import de.androidbytes.recipr.presentation.overview.search.di.SearchResultComponent;
import de.androidbytes.recipr.presentation.overview.search.di.SearchResultModule;
import de.androidbytes.recipr.presentation.overview.search.presenter.SearchResultPresenter;
import de.androidbytes.recipr.presentation.overview.search.view.SearchResultView;
import de.androidbytes.recipr.presentation.overview.search.view.adapter.SearchResultAdapter;

import javax.inject.Inject;
import java.util.List;

public class SearchResultActivity extends PresenterActivity<SearchResultComponent, SearchResultPresenter> implements SearchResultView, OnRecipeClickListener {
    private static final String RECIPE_NAME_EXTRA = "RECIPE_NAME_EXTRA";

    @Inject
    SearchResultPresenter presenter;

    @Inject
    Tracker tracker;

    @Inject
    SearchResultAdapter searchResultAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_category)
    RecyclerView recyclerView;

    @BindInt(R.integer.favoritesOverviewColumnCount)
    int columnCount;
    private String recipeName;

    public static Intent getLaunchIntent(Context context, String searchRecipeName) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(RECIPE_NAME_EXTRA, searchRecipeName);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        recipeName = getIntent().getStringExtra(RECIPE_NAME_EXTRA);

        if(recipeName != null) {
            setTitle(recipeName);
        }

        super.onCreate(savedInstanceState);
        setUpInjection();
        setUpToolbar();
        setUpLayout(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName(SearchResultActivity.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected SearchResultComponent onCreateNonConfigurationComponent() {
        return DaggerSearchResultComponent.builder()
                .applicationComponent(((ReciprApplication) getApplicationContext()).getApplicationComponent())
                .searchResultModule(new SearchResultModule(getLoggedInUserId(), recipeName))
                .build();
    }

    private void setUpInjection() {
        getComponent().inject(this);
    }

    private void setUpToolbar() {
        ActionBar actionBar = setSupportActionBarAndReturn(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setUpLayout(Bundle savedInstanceState) {

        recyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
        recyclerView.setAdapter(searchResultAdapter);

        if (savedInstanceState == null) {
            initializeData();
        }
    }

    private void initializeData() {
        presenter.loadData();
    }

    @Override
    public void renderRecipes(List<RecipeViewModel> recipeViewModels) {
        searchResultAdapter.swapData(recipeViewModels);
        searchResultAdapter.setOnRecipeClickListener(this);
    }

    @Override
    public void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel) {
        presenter.onRecipeClicked(rootImageView, recipeViewModel);
    }
}
