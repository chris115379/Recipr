package de.androidbytes.recipr.data.entity;

import de.androidbytes.recipr.data.provider.category.CategoryContentValues;
import de.androidbytes.recipr.data.provider.category.CategoryCursor;
import de.androidbytes.recipr.domain.model.Category;
import lombok.Getter;

@Getter
public class CategoryEntity {

    private long id = -1;
    private long userId;
    private String name;

    public CategoryEntity(long id, long userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public CategoryEntity(CategoryCursor cursor) {
        this(
                cursor.getId(),
                cursor.getUserId(),
                cursor.getName()
        );
    }

    public CategoryEntity(Category category) {
        this(
                category.getId(),
                category.getUser().getId(),
                category.getName()
        );
    }

    public CategoryContentValues getContentValues() {
        CategoryContentValues categoryContentValues = new CategoryContentValues();
        categoryContentValues.putUserId(userId);
        categoryContentValues.putName(name);
        return categoryContentValues;
    }
}
