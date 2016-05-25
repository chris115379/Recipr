package de.androidbytes.recipr.data.repository.domain;

import de.androidbytes.recipr.data.entity.CategoryEntity;
import de.androidbytes.recipr.data.entity.UserEntity;
import de.androidbytes.recipr.data.entity.mapper.CategoryMapper;
import de.androidbytes.recipr.data.repository.data.CategoryDataRepository;
import de.androidbytes.recipr.data.repository.data.UserDataRepository;
import de.androidbytes.recipr.domain.model.Category;
import de.androidbytes.recipr.domain.repository.CategoryRepository;
import rx.Observable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CategoryDomainRepository implements CategoryRepository {

    @Inject
    CategoryDataRepository categoryRepository;

    @Inject
    UserDataRepository userRepository;

    @Inject
    CategoryMapper categoryMapper;

    @Inject
    public CategoryDomainRepository() {
    }

    @Override
    public Observable<List<Category>> findAllCategories(long userId) {

        List<CategoryEntity> categoryEntities = categoryRepository.findAllOfUser(userId);

        if (categoryEntities.size() > 0) {

            UserEntity userEntity = userRepository.findById(userId);

            List<Category> categories = new ArrayList<>(categoryEntities.size());
            for (CategoryEntity categoryEntity : categoryEntities) {
                categories.add(categoryMapper.map(categoryEntity, userEntity));
            }

            return Observable.just(categories);
        } else {
            return Observable.empty();
        }
    }
}
