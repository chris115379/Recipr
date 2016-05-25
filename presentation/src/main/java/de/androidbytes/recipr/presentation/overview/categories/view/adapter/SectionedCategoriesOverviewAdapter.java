package de.androidbytes.recipr.presentation.overview.categories.view.adapter;

import android.content.Context;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.overview.core.view.OnRecipeClickListener;
import de.androidbytes.recipr.presentation.overview.core.view.adapter.SectionedGridRecyclerViewAdapter;

import javax.inject.Inject;

public class SectionedCategoriesOverviewAdapter extends SectionedGridRecyclerViewAdapter<CategoriesOverviewAdapter> {

    @Inject
    public SectionedCategoriesOverviewAdapter(Context context, CategoriesOverviewAdapter baseAdapter) {
        super(
                context,
                R.layout.item_layout_categories_overview_section_header,
                R.id.tv_section_header_title,
                R.layout.item_layout_categories_overview_section_footer,
                R.id.tv_section_footer_title,
                R.string.section_footer_title,
                baseAdapter
        );
    }
}
