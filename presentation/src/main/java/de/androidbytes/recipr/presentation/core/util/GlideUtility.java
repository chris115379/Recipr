package de.androidbytes.recipr.presentation.core.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import de.androidbytes.recipr.presentation.R;

/**
 * Created by Christoph on 20.05.2016.
 */
public class GlideUtility {

    public static void loadRecipeImage(Context context, String imageUrl, ImageView recipeImageView) {
        Glide.with(context).load(imageUrl).placeholder(R.drawable.recipe_placeholder).into(recipeImageView);
    }

}
