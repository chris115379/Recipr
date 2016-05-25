package de.androidbytes.recipr.domain.repository;

import de.androidbytes.recipr.domain.model.Category;
import rx.Observable;

import java.util.List;

/**
 * Created by Christoph on 17.05.2016.
 */
public interface CategoryRepository {
    Observable<List<Category>> findAllCategories(long userId);
}
