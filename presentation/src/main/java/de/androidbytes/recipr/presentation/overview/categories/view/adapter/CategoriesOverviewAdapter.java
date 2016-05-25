package de.androidbytes.recipr.presentation.overview.categories.view.adapter;

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
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.OnRecipeClickListener;
import de.androidbytes.recipr.presentation.overview.core.view.adapter.ManagedBaseAdapter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CategoriesOverviewAdapter extends ManagedBaseAdapter<CategoriesOverviewAdapter.CategoriesOverviewViewHolder, RecipeViewModel> {

    private final Context context;
    private final List<RecipeViewModel> categorizedRecipes;
    private OnRecipeClickListener onRecipeClickListener;

    @Inject
    public CategoriesOverviewAdapter(Context context) {
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
    public CategoriesOverviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_layout_recipe_overview, parent, false);
        final CategoriesOverviewViewHolder viewHolder = new CategoriesOverviewViewHolder(view);

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecipeClickListener != null) {
                    onRecipeClickListener.onRecipeClicked(viewHolder.recipeImage, categorizedRecipes.get(getCorrespondingAdapterPosition(viewHolder.getAdapterPosition())));
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoriesOverviewViewHolder holder, int position) {
        GlideUtility.loadRecipeImage(context, categorizedRecipes.get(position).getImageUrl(), holder.recipeImage);
        holder.recipeTitle.setText(categorizedRecipes.get(position).getRecipeTitle());
    }

    @Override
    public int getItemCount() {
        return categorizedRecipes.size();
    }

    static class CategoriesOverviewViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        @BindView(R.id.iv_recipe_image)
        ImageView recipeImage;
        @BindView(R.id.tv_recipe_title)
        TextView recipeTitle;

        CategoriesOverviewViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, rootView);
        }
    }
}
