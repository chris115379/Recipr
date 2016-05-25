package de.androidbytes.recipr.presentation.overview.core.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.ReciprApplication;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.navigation.Navigator;
import de.androidbytes.recipr.presentation.core.view.activity.BaseActivity;
import de.androidbytes.recipr.presentation.overview.categories.view.fragment.CategoriesOverviewFragment;
import de.androidbytes.recipr.presentation.overview.core.di.DaggerOverviewComponent;
import de.androidbytes.recipr.presentation.overview.core.di.OverviewComponent;
import de.androidbytes.recipr.presentation.overview.core.view.adapter.OverviewViewPagerAdapter;
import de.androidbytes.recipr.presentation.overview.favorites.view.fragment.FavoritesOverviewFragment;

import javax.inject.Inject;

public class OverviewActivity extends BaseActivity implements SearchView.OnQueryTextListener, ResultCallback {

    private OverviewViewPagerAdapter overviewViewPagerAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    Navigator navigator;

    private MenuItem searchItem;

    private boolean searchViewExpanded = false;

    @Inject
    public OverviewActivity() {
        overviewViewPagerAdapter = new OverviewViewPagerAdapter(this.getSupportFragmentManager());
    }

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OverviewActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjection();
        setUpToolbar();
        setUpLayout();
    }

    private void setUpInjection() {
        ApplicationComponent applicationComponent = ((ReciprApplication) getApplication()).getApplicationComponent();
        OverviewComponent overviewComponent = DaggerOverviewComponent.builder()
                .applicationComponent(applicationComponent)
                .build();
        overviewComponent.inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overview_menu, menu);

        searchItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final boolean handled;

        switch (item.getItemId()) {
            case R.id.menu_search:
                searchViewExpanded = true;
                handled = true;
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }

        return handled;
    }

    private void setUpToolbar() {
        ActionBar actionBar = setSupportActionBarAndReturn(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    private void setUpLayout() {
        populateViewPagerAdapter();
        setUpViewPager();
    }

    private void populateViewPagerAdapter() {
        overviewViewPagerAdapter.addFragment(new FavoritesOverviewFragment(), "Favorites");
        overviewViewPagerAdapter.addFragment(new CategoriesOverviewFragment(), "Categories");
    }

    private void setUpViewPager() {
        viewPager.setAdapter(overviewViewPagerAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_overview;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.fab)
    void onAddRecipeButtonClicked() {
        navigator.navigateToAddRecipe(this);
    }

    @Override
    public void onBackPressed() {
        if (!searchViewExpanded && !collapseSearchView()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null && query.length() > 0) {
            navigator.navigateToSearchResult(this, query);
            collapseSearchView();
            return true;
        } else {
            return false;
        }
    }

    private boolean collapseSearchView() {
        searchViewExpanded = false;
        return MenuItemCompat.isActionViewExpanded(searchItem) && MenuItemCompat.collapseActionView(searchItem);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onResult(@NonNull Result result) {
        if (result.getStatus().isSuccess()) {
            navigator.navigateToLogin(this);
        }
    }
}
