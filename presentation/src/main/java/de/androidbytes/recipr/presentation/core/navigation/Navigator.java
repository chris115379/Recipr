package de.androidbytes.recipr.presentation.core.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;
import de.androidbytes.recipr.presentation.core.view.activity.BaseActivity;
import de.androidbytes.recipr.presentation.login.view.activity.LoginActivity;
import de.androidbytes.recipr.presentation.overview.category.view.activity.CategoryActivity;
import de.androidbytes.recipr.presentation.overview.core.view.activity.OverviewActivity;
import de.androidbytes.recipr.presentation.overview.search.view.activity.SearchResultActivity;
import de.androidbytes.recipr.presentation.single.add.view.activity.AddRecipeActivity;
import de.androidbytes.recipr.presentation.single.detail.view.activity.RecipeDetailsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        // Empty Constructor required for Dependency Injection
    }

    public void navigateToRecipeDetailsView(Activity startingActivity, ImageView rootImageView, long recipeId) {
        if (startingActivity != null) {
            Intent intent = RecipeDetailsActivity.getLaunchIntent(startingActivity, recipeId);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(((BaseActivity) startingActivity), rootImageView, "recipeImage");
            startingActivity.startActivity(intent, options.toBundle());
        }
    }


    public void navigateToCategoryView(Context context, String categoryName) {
        if (context != null) {
            Intent intent = CategoryActivity.getLaunchIntent(context, categoryName);
            context.startActivity(intent);
        }
    }

    public void navigateToAddRecipe(Context context) {
        if (context != null) {
            Intent intent = AddRecipeActivity.getLaunchIntent(context);
            context.startActivity(intent);
        }
    }

    public void navigateToOverview(Context context) {
        if (context != null) {
            Intent intent = OverviewActivity.getCallingIntent(context);
            context.startActivity(intent);
        }
    }

    public void navigateToLogin(Context context) {
        if (context != null) {
            Intent intent = LoginActivity.getLaunchIntent(context);
            context.startActivity(intent);
        }
    }

    public void navigateToSearchResult(Context context, String query) {
        if(context !=null) {
            Intent intent = SearchResultActivity.getLaunchIntent(context, query);
            context.startActivity(intent);
        }
    }
}
