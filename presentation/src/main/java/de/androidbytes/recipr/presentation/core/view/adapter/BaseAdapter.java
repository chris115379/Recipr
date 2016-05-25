package de.androidbytes.recipr.presentation.core.view.adapter;

import android.support.v7.widget.RecyclerView;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<VH> implements DataAdapter<D> {
}
