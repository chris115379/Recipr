package de.androidbytes.recipr.presentation.overview.category.view.activity;

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
import de.androidbytes.recipr.presentation.overview.category.di.CategoryComponent;
import de.androidbytes.recipr.presentation.overview.category.di.CategoryModule;
import de.androidbytes.recipr.presentation.overview.category.di.DaggerCategoryComponent;
import de.androidbytes.recipr.presentation.overview.category.presenter.CategoryPresenter;
import de.androidbytes.recipr.presentation.overview.category.view.CategoryView;
import de.androidbytes.recipr.presentation.overview.category.view.adapter.CategoryAdapter;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.OnRecipeClickListener;

import javax.inject.Inject;
import java.util.List;

public class CategoryActivity extends PresenterActivity<CategoryComponent, CategoryPresenter> implements CategoryView, OnRecipeClickListener {
    private static final String CATEGORY_NAME_EXTRA = "CATEGORY_NAME_EXTRA";

    @Inject
    CategoryPresenter presenter;

    @Inject
    Tracker tracker;

    @Inject
    CategoryAdapter categoryAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_category)
    RecyclerView recyclerView;

    @BindInt(R.integer.favoritesOverviewColumnCount)
    int columnCount;

    public static Intent getLaunchIntent(Context context, String categoryName) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(CATEGORY_NAME_EXTRA, categoryName);
        return intent;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjection();
        setUpToolbar();
        setUpLayout(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName(CategoryActivity.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected CategoryComponent onCreateNonConfigurationComponent() {
        String categoryName = getIntent().getStringExtra(CATEGORY_NAME_EXTRA);
        return DaggerCategoryComponent.builder()
                .applicationComponent(((ReciprApplication) getApplicationContext()).getApplicationComponent())
                .categoryModule(new CategoryModule(getLoggedInUserId(), categoryName))
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
        recyclerView.setAdapter(categoryAdapter);

        if (savedInstanceState == null) {
            initializeData();
        }
    }

    private void initializeData() {
        presenter.loadData();
    }

    @Override
    public void renderCategoryRecipes(List<RecipeViewModel> recipeViewModels) {
        categoryAdapter.swapData(recipeViewModels);
        categoryAdapter.setOnRecipeClickListener(this);
    }

    @Override
    public void renderTitle(String categoryName) {
        setTitle(categoryName);
    }

    @Override
    public void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel) {
        presenter.onRecipeClicked(rootImageView, recipeViewModel);
    }
}
