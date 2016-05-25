package de.androidbytes.recipr.presentation.overview.category.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.core.util.GlideUtility;
import de.androidbytes.recipr.presentation.core.view.adapter.BaseAdapter;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.OnRecipeClickListener;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter<CategoryAdapter.FavoritesOverviewViewHolder, RecipeViewModel> {

    private final Context context;
    private final List<RecipeViewModel> categorizedRecipes;
    private OnRecipeClickListener onRecipeClickListener;

    @Inject
    public CategoryAdapter(Context context) {
        categorizedRecipes = new ArrayList<>();
        this.context = context;
    }

    @Override
    public void swapData(List<RecipeViewModel> newCategorizedRecipes) {
        categorizedRecipes.clear();
        categorizedRecipes.addAll(newCategorizedRecipes);
        notifyDataSetChanged();
    }

    public void setOnRecipeClickListener(OnRecipeClickListener onRecipeClickListener) {
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @Override
    public FavoritesOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_layout_recipe_overview, parent, false);
        final FavoritesOverviewViewHolder viewHolder = new FavoritesOverviewViewHolder(view);

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecipeClickListener != null) {
                    onRecipeClickListener.onRecipeClicked(viewHolder.recipeImage, categorizedRecipes.get(viewHolder.getAdapterPosition()));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesOverviewViewHolder holder, int position) {
        GlideUtility.loadRecipeImage(context, categorizedRecipes.get(position).getImageUrl(), holder.recipeImage);
        holder.recipeTitle.setText(categorizedRecipes.get(position).getRecipeTitle());
    }

    @Override
    public int getItemCount() {
        return categorizedRecipes.size();
    }

    static class FavoritesOverviewViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        @BindView(R.id.iv_recipe_image)
        ImageView recipeImage;
        @BindView(R.id.tv_recipe_title)
        TextView recipeTitle;

        FavoritesOverviewViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
