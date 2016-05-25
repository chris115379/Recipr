package de.androidbytes.recipr.presentation.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;
import de.androidbytes.recipr.data.repository.domain.RecipeDomainWidgetRepository;
import de.androidbytes.recipr.domain.model.Recipe;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.single.detail.view.activity.RecipeDetailsActivity;

import java.io.File;
import java.util.List;
import java.util.Random;

public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String LAUNCH_ACTIVITY = "LAUNCH_ACTIVITY";
    private static final String RECIPE_ID_EXTRA = "RECIPE_ID_EXTRA";
    private static final String RECIPE_IMAGE_URL_EXTRA = "RECIPE_IMAGE_URL_EXTRA";
    private RecipeDomainWidgetRepository recipeDomainWidgetRepository;
    private List<Recipe> allRecipes;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        init(context);

        for (int widgetId : appWidgetIds) {

            int size = allRecipes.size();
            if (size > 0) {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_random_recipe);
                final int randomIndex = new Random().nextInt(size);
                Recipe randomRecipe = allRecipes.get(randomIndex);

                remoteViews.setTextViewText(R.id.widget_tv_recipe_title, randomRecipe.getName());
                String imageUrl = randomRecipe.getImageUrl();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    File imgFile = new File(imageUrl);
                    if (imgFile.exists()) {
                        final Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        remoteViews.setImageViewBitmap(R.id.widget_iv_recipe_image, myBitmap);
                    }
                } else {
                    remoteViews.setImageViewResource(R.id.widget_iv_recipe_image, R.drawable.recipe_placeholder);
                }

                Intent launchIntent = new Intent(context, RecipeWidgetProvider.class);
                launchIntent.putExtra(RECIPE_ID_EXTRA, randomRecipe.getId());
                launchIntent.putExtra(RECIPE_IMAGE_URL_EXTRA, imageUrl);
                launchIntent.setAction(LAUNCH_ACTIVITY);
                PendingIntent pendingLaunchIntent = PendingIntent.getBroadcast(context, 0, launchIntent, 0);
                remoteViews.setOnClickPendingIntent(R.id.widget_iv_recipe_image, pendingLaunchIntent);

                Intent intent = new Intent(context, RecipeWidgetProvider.class);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.widget_tv_recipe_title, pendingIntent);

                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
    }

    private void init(Context context) {
        if (recipeDomainWidgetRepository == null && allRecipes == null) {
            this.recipeDomainWidgetRepository = new RecipeDomainWidgetRepository(context.getContentResolver());
            this.allRecipes = recipeDomainWidgetRepository.findAllRecipes();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(LAUNCH_ACTIVITY)) {
            Intent launchIntent = RecipeDetailsActivity.getLaunchIntent(context, intent.getLongExtra(RECIPE_ID_EXTRA, -1));
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
        } else {
            super.onReceive(context, intent);
        }
    }
}
