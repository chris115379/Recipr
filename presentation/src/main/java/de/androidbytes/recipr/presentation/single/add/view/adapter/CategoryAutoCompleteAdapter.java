package de.androidbytes.recipr.presentation.single.add.view.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import de.androidbytes.recipr.presentation.core.di.scopes.PerActivity;

import javax.inject.Inject;
import java.util.Collection;

@PerActivity
public class CategoryAutoCompleteAdapter extends ArrayAdapter<String> {

    @Inject
    public CategoryAutoCompleteAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public void swapData(Collection<String> data) {
        clear();
        addAll(data);
    }

}
