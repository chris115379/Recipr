package de.androidbytes.recipr.presentation.overview.core.view.adapter;

import android.support.v7.widget.RecyclerView;
import de.androidbytes.recipr.presentation.core.view.adapter.BaseAdapter;
import de.androidbytes.recipr.presentation.overview.core.view.AdapterPositionMapper;

public abstract class ManagedBaseAdapter<VH extends RecyclerView.ViewHolder, D> extends BaseAdapter<VH, D> {

    private AdapterPositionMapper adapterPositionMapper;

    public void  registerAdapterPositionMapper(AdapterPositionMapper adapterPositionMapper) {
        this.adapterPositionMapper = adapterPositionMapper;
    }

    protected int getCorrespondingAdapterPosition(int offsetPosition) {
        if (adapterPositionMapper == null) {
            return offsetPosition;
        }
        return adapterPositionMapper.getCorrespondingPosition(offsetPosition);
    }

}
