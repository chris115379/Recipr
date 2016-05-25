package de.androidbytes.recipr.presentation.single.core.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.core.view.adapter.DataAdapter;
import de.androidbytes.recipr.presentation.single.core.model.StepViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class StepListAdapter extends BaseAdapter implements DataAdapter<StepViewModel> {

    private Context context;
    private List<StepViewModel> steps = new ArrayList<>(0);

    @Inject
    public StepListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < steps.size()) {
            return steps.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PreparationViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_layout_preparation_list, parent, false);

            viewHolder = new PreparationViewHolder();
            viewHolder.indexTextView = ((TextView) convertView.findViewById(R.id.tv_stepIndex));
            viewHolder.instructionTextView = ((TextView) convertView.findViewById(R.id.tv_stepInstruction));

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (PreparationViewHolder) convertView.getTag();
        }

        StepViewModel step = steps.get(position);

        if (step != null) {
            String index = "Step " + step.getNumber() + ":";
            viewHolder.indexTextView.setText(index);
            viewHolder.instructionTextView.setText(step.getInstruction());
        }

        return convertView;
    }

    public void detach() {
        this.context = null;
        this.steps = null;
    }

    @Override
    public void swapData(List<StepViewModel> data) {
        this.steps.clear();
        this.steps.addAll(data);
        notifyDataSetChanged();
    }

    public void addStep(StepViewModel step) {
        this.steps.add(step);
    }

    public List<StepViewModel> getData() {
        return steps;
    }

    static class PreparationViewHolder {
        TextView indexTextView;
        TextView instructionTextView;
    }
}
