package de.androidbytes.recipr.presentation.overview.categories.view.fragment;

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
import de.androidbytes.recipr.presentation.overview.categories.di.CategoriesOverviewComponent;
import de.androidbytes.recipr.presentation.overview.categories.di.CategoriesOverviewModule;
import de.androidbytes.recipr.presentation.overview.categories.di.DaggerCategoriesOverviewComponent;
import de.androidbytes.recipr.presentation.overview.categories.model.CategoryContainer;
import de.androidbytes.recipr.presentation.overview.categories.presenter.CategoriesOverviewPresenter;
import de.androidbytes.recipr.presentation.overview.categories.view.CategoriesOverviewView;
import de.androidbytes.recipr.presentation.overview.categories.view.OnExpandSectionClickListener;
import de.androidbytes.recipr.presentation.overview.categories.view.adapter.SectionedCategoriesOverviewAdapter;
import de.androidbytes.recipr.presentation.overview.core.model.RecipeViewModel;
import de.androidbytes.recipr.presentation.overview.core.view.OnRecipeClickListener;
import de.androidbytes.recipr.presentation.overview.core.view.adapter.SectionedGridRecyclerViewAdapter.Section;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CategoriesOverviewFragment extends BaseFragment<CategoriesOverviewComponent, CategoriesOverviewPresenter> implements CategoriesOverviewView, OnRecipeClickListener, OnExpandSectionClickListener {

    @Inject
    CategoriesOverviewPresenter presenter;

    @Inject
    Tracker tracker;

    @Inject
    SectionedCategoriesOverviewAdapter sectionedCategoriesOverviewAdapter;
    
    @BindView(R.id.rv_categories_overview)
    RecyclerView recyclerView;

    @BindInt(R.integer.categoriesOverviewColumnCount)
    int categoriesOverviewColumnCount;

    @BindView(R.id.categories_overview_empty_view)
    TextView emptyView;

    public CategoriesOverviewFragment() {
    }

    public CategoriesOverviewFragment newInstance(String title) {
        CategoriesOverviewFragment fragment = new CategoriesOverviewFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), categoriesOverviewColumnCount));
        recyclerView.setAdapter(sectionedCategoriesOverviewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
        tracker.setScreenName(CategoriesOverviewFragment.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void initializeData() {
        presenter.loadData();
    }

    @Override
    protected CategoriesOverviewComponent onCreateNonConfigurationComponent() {

        int maxColumnCount = getResources().getInteger(R.integer.categoriesOverviewLandscapeColumnCount);

        return DaggerCategoriesOverviewComponent.builder()
                .applicationComponent(((ReciprApplication) getContext().getApplicationContext()).getApplicationComponent())
                .categoriesOverviewModule(new CategoriesOverviewModule(getLoggedInUserId(), maxColumnCount))
                .build();
    }

    @Override
    public void renderCategoriesOverview(List<CategoryContainer> categoryContainers) {

        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

        List<RecipeViewModel> categoryRecipes = new ArrayList<>();
        List<Section> sections = new ArrayList<>(categoryContainers.size());

        for (CategoryContainer categoryContainer : categoryContainers) {
            List<RecipeViewModel> categoryItems = categoryContainer.getCategorizedRecipes();
            int categoryItemCount = categoryItems.size();
            sections.add(new Section(categoryContainer.getCategoryName(), categoryItemCount));
            categoryRecipes.addAll(categoryItems);
        }

        sectionedCategoriesOverviewAdapter.setSections(sections.toArray(new Section[sections.size()]));
        sectionedCategoriesOverviewAdapter.swapData(categoryRecipes);
        sectionedCategoriesOverviewAdapter.setOnRecipeClickedListener(this);
        sectionedCategoriesOverviewAdapter.setOnExpandSectionClickListener(this);

    }

    @Override
    public void renderEmptyView() {
        emptyView.setText(getString(R.string.categories_overview_empty_message, getLoggedInUser().getName()));

        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRecipeClicked(ImageView rootImageView, RecipeViewModel recipeViewModel) {
        presenter.onRecipeClicked(rootImageView, recipeViewModel);
    }

    @Override
    public void onExpandSectionClicked(String categoryName) {
        presenter.onExpandSectionClicked(categoryName);
    }
}
