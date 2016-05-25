package de.androidbytes.recipr.presentation.single.core.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.core.view.adapter.DataAdapter;
import de.androidbytes.recipr.presentation.single.core.model.IngredientViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class IngredientListAdapter extends BaseAdapter implements DataAdapter<IngredientViewModel> {

    private Context context;
    private List<IngredientViewModel> ingredients = new ArrayList<>(0);

    @Inject
    public IngredientListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < ingredients.size()) {
            return ingredients.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<IngredientViewModel> getData() {
        return ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        IngredientViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_layout_ingredient_list, parent, false);

            viewHolder = new IngredientViewHolder();
            viewHolder.quantityTextView = ((TextView) convertView.findViewById(R.id.tv_quantity));
            viewHolder.unitTextView = ((TextView) convertView.findViewById(R.id.tv_unit));
            viewHolder.nameTextView = ((TextView) convertView.findViewById(R.id.tv_ingredient));

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (IngredientViewHolder) convertView.getTag();
        }

        IngredientViewModel ingredient = ingredients.get(position);

        if (ingredient != null) {
            viewHolder.quantityTextView.setText(ingredient.getQuantity());
            viewHolder.unitTextView.setText(ingredient.getUnit());
            viewHolder.nameTextView.setText(ingredient.getName());
        }

        return convertView;
    }

    public void detach() {
        this.context = null;
        this.ingredients = null;
    }

    @Override
    public void swapData(List<IngredientViewModel> data) {
        this.ingredients.clear();
        this.ingredients.addAll(data);
        notifyDataSetChanged();
    }

    public void addIngredient(IngredientViewModel ingredient) {
        this.ingredients.add(ingredient);
    }

    static class IngredientViewHolder {
        TextView quantityTextView;
        TextView unitTextView;
        TextView nameTextView;
    }
}
