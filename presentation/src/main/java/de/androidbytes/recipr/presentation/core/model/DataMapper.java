package de.androidbytes.recipr.presentation.core.model;

import java.util.Collection;
import java.util.List;

/**
 * Created by Christoph on 08.05.2016.
 */
public interface DataMapper<D, V> {

    V transform(D model);
    List<V> transform(Collection<D> modelCollection);

}
