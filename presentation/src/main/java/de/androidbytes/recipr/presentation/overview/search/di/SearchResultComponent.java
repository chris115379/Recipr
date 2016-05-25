package de.androidbytes.recipr.presentation.overview.search.di;

import dagger.Component;
import de.androidbytes.recipr.presentation.core.di.components.ApplicationComponent;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;
import de.androidbytes.recipr.presentation.overview.search.presenter.SearchResultPresenter;
import de.androidbytes.recipr.presentation.overview.search.view.activity.SearchResultActivity;
import nz.bradcampbell.compartment.HasPresenter;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = SearchResultModule.class)
public interface SearchResultComponent extends HasPresenter<SearchResultPresenter> {
    void inject(SearchResultActivity categoryActivity);
}
