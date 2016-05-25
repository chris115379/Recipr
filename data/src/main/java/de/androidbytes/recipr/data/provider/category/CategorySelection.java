package de.androidbytes.recipr.data.provider.category;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import de.androidbytes.recipr.data.provider.base.AbstractSelection;

/**
 * Selection for the {@code category} table.
 */
public class CategorySelection extends AbstractSelection<CategorySelection> {

    private static final String[] DEFAULT_PROJECTION = CategoryColumns.ALL_COLUMNS;

    @Override
    protected Uri baseUri() {
        return CategoryColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CategoryCursor} object, which is positioned before the first entry, or null.
     */
    public CategoryCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CategoryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public CategoryCursor query(ContentResolver contentResolver) {
        return query(contentResolver, DEFAULT_PROJECTION);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CategoryCursor} object, which is positioned before the first entry, or null.
     */
    public CategoryCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CategoryCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public CategoryCursor query(Context context) {
        return query(context, null);
    }


    public CategorySelection id(long... value) {
        addEquals("category." + CategoryColumns._ID, toObjectArray(value));
        return this;
    }

    public CategorySelection idNot(long... value) {
        addNotEquals("category." + CategoryColumns._ID, toObjectArray(value));
        return this;
    }

    public CategorySelection orderById(boolean desc) {
        orderBy("category." + CategoryColumns._ID, desc);
        return this;
    }

    public CategorySelection orderById() {
        return orderById(false);
    }

    public CategorySelection userId(long... value) {
        addEquals(CategoryColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public CategorySelection userIdNot(long... value) {
        addNotEquals(CategoryColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public CategorySelection userIdGt(long value) {
        addGreaterThan(CategoryColumns.USER_ID, value);
        return this;
    }

    public CategorySelection userIdGtEq(long value) {
        addGreaterThanOrEquals(CategoryColumns.USER_ID, value);
        return this;
    }

    public CategorySelection userIdLt(long value) {
        addLessThan(CategoryColumns.USER_ID, value);
        return this;
    }

    public CategorySelection userIdLtEq(long value) {
        addLessThanOrEquals(CategoryColumns.USER_ID, value);
        return this;
    }

    public CategorySelection orderByUserId(boolean desc) {
        orderBy(CategoryColumns.USER_ID, desc);
        return this;
    }

    public CategorySelection orderByUserId() {
        orderBy(CategoryColumns.USER_ID, false);
        return this;
    }

    public CategorySelection name(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection nameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public CategorySelection orderByName(boolean desc) {
        orderBy(CategoryColumns.NAME, desc);
        return this;
    }

    public CategorySelection orderByName() {
        orderBy(CategoryColumns.NAME, false);
        return this;
    }
}
