package de.androidbytes.recipr.data.entity.mapper;

import de.androidbytes.recipr.data.entity.CategoryEntity;
import de.androidbytes.recipr.data.entity.UserEntity;
import de.androidbytes.recipr.domain.model.Category;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CategoryMapper {

    @Inject
    UserMapper userMapper;

    @Inject public CategoryMapper() {}

    public Category map(CategoryEntity categoryEntity, UserEntity userEntity) {
        return new Category(
                categoryEntity.getId(),
                userMapper.map(userEntity),
                categoryEntity.getName()
        );
    }

}
