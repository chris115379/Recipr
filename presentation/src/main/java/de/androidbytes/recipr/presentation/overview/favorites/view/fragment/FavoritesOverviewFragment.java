package de.androidbytes.recipr.presentation.overview.favorites.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindInt;
import butterknife.BindView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.ReciprApplication;
import de.androidbytes.recipr.presentation.core.view.fragment.BaseFragment;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.OnRecipeClickListener;
import de.androidbytes.recipr.presentation.overview.favorites.di.DaggerFavoritesOverviewComponent;
import de.androidbytes.recipr.presentation.overview.favorites.di.FavoritesOverviewComponent;
import de.androidbytes.recipr.presentation.overview.favorites.di.FavoritesOverviewModule;
import de.androidbytes.recipr.presentation.overview.favorites.presenter.FavoritesOverviewPresenter;
import de.androidbytes.recipr.presentation.overview.favorites.view.FavoritesOverviewView;
import de.androidbytes.recipr.presentation.overview.favorites.view.adapter.FavoritesOverviewAdapter;

import javax.inject.Inject;
import java.util.List;

public class FavoritesOverviewFragment extends BaseFragment<FavoritesOverviewComponent, FavoritesOverviewPresenter> implements FavoritesOverviewView, OnRecipeClickListener {

    @Inject
    FavoritesOverviewPresenter presenter;

    @Inject
    Tracker tracker;

    @Inject
    FavoritesOverviewAdapter favoritesOverviewAdapter;
    
    @BindView(R.id.rv_favorites_overview)
    RecyclerView recyclerView;

    @BindInt(R.integer.favoritesOverviewColumnCount)
    int columnCount;

    @BindView(R.id.favorites_overview_empty_view)
    TextView emptyView;

    public FavoritesOverviewFragment() {}

    public FavoritesOverviewFragment newInstance(String title) {
        return new FavoritesOverviewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        recyclerView.setAdapter(favoritesOverviewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
        tracker.setScreenName(FavoritesOverviewFragment.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void initializeData() {
        presenter.loadData();
    }

    @Override
    protected FavoritesOverviewComponent onCreateNonConfigurationComponent() {

        return DaggerFavoritesOverviewComponent.builder()
                .applicationComponent(((ReciprApplication) getContext().getApplicationContext()).getApplicationComponent())
                .favoritesOverviewModule(new FavoritesOverviewModule(getLoggedInUserId()))
                .build();
    }

    @Override
    public void renderFavoritesOverview(List<RecipeViewModel> recipeViewModels) {

        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        favoritesOverviewAdapter.swapData(recipeViewModels);
        favoritesOverviewAdapter.setOnRecipeClickListener(this);
    }

    @Override
    public void renderEmptyView() {
        emptyView.setText(getString(R.string.favorites_overview_empty_message, getLoggedInUser().getName()));

        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel) {
        presenter.onRecipeClicked(rootImageView, recipeViewModel);
    }
}
