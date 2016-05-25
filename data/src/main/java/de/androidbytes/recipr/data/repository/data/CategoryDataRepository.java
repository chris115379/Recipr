package de.androidbytes.recipr.data.repository.data;

import android.content.ContentResolver;
import android.net.Uri;
import de.androidbytes.recipr.data.entity.CategoryEntity;
import de.androidbytes.recipr.data.exception.DatabaseException;
import de.androidbytes.recipr.data.provider.category.CategoryColumns;
import de.androidbytes.recipr.data.provider.category.CategoryContentValues;
import de.androidbytes.recipr.data.provider.category.CategoryCursor;
import de.androidbytes.recipr.data.provider.category.CategorySelection;
import de.androidbytes.recipr.domain.model.Category;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

public class CategoryDataRepository {

    private ContentResolver contentResolver;

    @Inject
    UserDataRepository userRepository;

    @Inject
    public CategoryDataRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public List<CategoryEntity> findAllOfUser(long userId) {
        CategorySelection categorySelection = new CategorySelection();
        CategoryCursor categoryCursor = categorySelection.query(contentResolver);

        return extractEntitiesFromCursor(categoryCursor);
    }

    public CategoryEntity findCategoryById(long userId, long categoryId) {
        CategorySelection categorySelection = new CategorySelection();
        categorySelection.id(categoryId).and().userId(userId);
        CategoryCursor categoryCursor = categorySelection.query(contentResolver);
        if (categoryCursor.moveToFirst()) {
            return extractEntityFromCursor(categoryCursor);
        } else {
            throw new DatabaseException("A category with the id of '" + categoryId + "' was not found for the current user.");
        }
    }

    public CategoryEntity findByUserIdAndName(long userId, String categoryName) {
        CategorySelection categorySelection = new CategorySelection();
        categorySelection.userId(userId).and().name(categoryName);
        CategoryCursor categoryCursor = categorySelection.query(contentResolver);

        if (categoryCursor != null && categoryCursor.moveToFirst()) {
            return extractEntityFromCursor(categoryCursor);
        }

        throw new DatabaseException("A " + Category.class.getSimpleName() + " with the name '" + name + "' could not be found.");
    }

    public CategoryEntity createOrUpdate(CategoryEntity categoryEntity) {

        try {
            categoryEntity = findByUserIdAndName(categoryEntity.getUserId(), categoryEntity.getName());
        } catch (DatabaseException e) {
            // empty
        }


        if (categoryEntity.getId() == -1)
            return createCategory(categoryEntity);
        else
            return updateCategory(categoryEntity);
    }

    private CategoryEntity updateCategory(CategoryEntity categoryEntity) {
        CategorySelection categorySelection = new CategorySelection();
        categorySelection.id(categoryEntity.getId());
        contentResolver.update(CategoryColumns.CONTENT_URI, categoryEntity.getContentValues().values(), categorySelection.sel(), null);
        CategoryCursor categoryCursor = categorySelection.query(contentResolver);

        if (categoryCursor.moveToFirst()) {
            return extractEntityFromCursor(categoryCursor);
        }

        throw new DatabaseException("Error updating category with name '" + categoryEntity.getName() + "'");
    }

    private CategoryEntity createCategory(CategoryEntity categoryEntity) {
        CategoryContentValues contentValues = categoryEntity.getContentValues();
        Uri createdCategoryUri = contentResolver.insert(CategoryColumns.CONTENT_URI, contentValues.values());
        if (createdCategoryUri != null) {
            long createdCategoryId = Long.parseLong(createdCategoryUri.getLastPathSegment());
            CategoryEntity categoryById = findCategoryById(categoryEntity.getUserId(), createdCategoryId);
            return categoryById;
        }

        throw new DatabaseException("Could not create category with name '" + categoryEntity.getName() + "'");
    }

    private List<CategoryEntity> extractEntitiesFromCursor(CategoryCursor categoryCursor) {
        List<Long> ids = new ArrayList<>(categoryCursor.getCount());
        List<CategoryEntity> categoryEntities = new ArrayList<>(categoryCursor.getCount());
        while (categoryCursor.moveToNext()) {
            CategoryEntity categoryEntity = extractEntityFromCursor(categoryCursor);
            if (!ids.contains(categoryEntity.getName())) {
                categoryEntities.add(categoryEntity);
                ids.add(categoryEntity.getId());
            }
        }
        return categoryEntities;
    }

    private CategoryEntity extractEntityFromCursor(CategoryCursor categoryCursor) {
        return new CategoryEntity(categoryCursor);
    }
}
