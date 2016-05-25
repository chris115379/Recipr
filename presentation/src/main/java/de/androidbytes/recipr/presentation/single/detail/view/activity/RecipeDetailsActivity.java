package de.androidbytes.recipr.presentation.single.detail.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.linearlistview.LinearListView;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.ReciprApplication;
import de.androidbytes.recipr.presentation.core.util.GlideUtility;
import de.androidbytes.recipr.presentation.core.view.activity.PresenterActivity;
import de.androidbytes.recipr.presentation.login.view.activity.LoginActivity;
import de.androidbytes.recipr.presentation.single.core.view.adapter.IngredientListAdapter;
import de.androidbytes.recipr.presentation.single.core.view.adapter.StepListAdapter;
import de.androidbytes.recipr.presentation.single.detail.di.DaggerRecipeDetailComponent;
import de.androidbytes.recipr.presentation.single.detail.di.RecipeDetailComponent;
import de.androidbytes.recipr.presentation.single.detail.di.RecipeDetailModule;
import de.androidbytes.recipr.presentation.single.detail.model.RecipeDetails;
import de.androidbytes.recipr.presentation.single.detail.presenter.RecipeDetailsPresenter;
import de.androidbytes.recipr.presentation.single.detail.view.RecipeDetailsView;

import javax.inject.Inject;


public class RecipeDetailsActivity extends PresenterActivity<RecipeDetailComponent, RecipeDetailsPresenter> implements RecipeDetailsView {

    private static final String RECIPE_ID_EXTRA = "RECIPE_ID_EXTRA";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ingredients_list)
    LinearListView ingredientsListView;

    @BindView(R.id.steps_list)
    LinearListView stepListView;

    @BindView(R.id.iv_recipe_image)
    ImageView recipeImage;

    @BindView(R.id.fab)
    FloatingActionButton favoriteActionButton;

    @BindView(R.id.collapsingToolbarLayout)
    @Nullable
    CollapsingToolbarLayout collapsingToolbarLayout;


    @Inject
    RecipeDetailsPresenter presenter;

    @Inject
    Tracker tracker;

    @Inject
    IngredientListAdapter ingredientAdapter;

    @Inject
    StepListAdapter stepAdapter;

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
        tracker.setScreenName(RecipeDetailsActivity.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected RecipeDetailComponent onCreateNonConfigurationComponent() {
        long recipeId = getIntent().getLongExtra(RECIPE_ID_EXTRA, -1);
        return DaggerRecipeDetailComponent.builder()
                .applicationComponent(((ReciprApplication) getApplicationContext()).getApplicationComponent())
                .recipeDetailModule(new RecipeDetailModule(getLoggedInUserId(), recipeId))
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

        ingredientsListView.setAdapter(ingredientAdapter);
        stepListView.setAdapter(stepAdapter);

        if (savedInstanceState == null) {
            initializeData();
        }
    }

    private void initializeData() {
        presenter.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ingredientAdapter.detach();
        stepAdapter.detach();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_detail_recipe;
    }

    public static Intent getLaunchIntent(Context context, long id) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_ID_EXTRA, id);
        return intent;
    }

    @Override
    public void renderRecipeDetails(RecipeDetails recipeDetails) {
        if (collapsingToolbarLayout != null) {
            collapsingToolbarLayout.setTitle(recipeDetails.getName());
        } else {
            setTitle(recipeDetails.getName());
        }

        ingredientAdapter.swapData(recipeDetails.getIngredients());
        stepAdapter.swapData(recipeDetails.getSteps());

        updateImage(recipeDetails.getImageUrl());

        updateFavoriteState(recipeDetails.isFavorite());
    }

    private void updateImage(String imageUrl) {
        GlideUtility.loadRecipeImage(this, imageUrl, recipeImage);
    }

    @OnClick(R.id.fab)
    void onFavoriteActionButtonClicked() {
        presenter.changeFavorStateOfRecipe();
    }

    private void updateFavoriteState(boolean favorite) {
            favoriteActionButton.setImageResource(favorite ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp);
    }
}
