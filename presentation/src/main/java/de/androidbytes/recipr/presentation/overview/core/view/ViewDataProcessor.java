package de.androidbytes.recipr.presentation.overview.core.view;

public interface ViewDataProcessor<D, VD> {

    VD processData(D data);

}
