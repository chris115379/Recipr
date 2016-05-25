package de.androidbytes.recipr.data.provider.recipe;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import de.androidbytes.recipr.data.provider.base.AbstractSelection;
import de.androidbytes.recipr.data.provider.category.CategoryColumns;
import de.androidbytes.recipr.data.provider.user.UserColumns;


/**
 * Selection for the {@code recipe} table.
 */
public class RecipeSelection extends AbstractSelection<RecipeSelection> {

    private static final String[] DEFAULT_PROJECTION = RecipeColumns.ALL_COLUMNS;

    @Override
    protected Uri baseUri() {
        return RecipeColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code RecipeCursor} object, which is positioned before the first entry, or null.
     */
    public RecipeCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new RecipeCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public RecipeCursor query(ContentResolver contentResolver) {
        return query(contentResolver, DEFAULT_PROJECTION);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code RecipeCursor} object, which is positioned before the first entry, or null.
     */
    public RecipeCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new RecipeCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public RecipeCursor query(Context context) {
        return query(context, null);
    }


    public RecipeSelection id(long... value) {
        addEquals("recipe." + RecipeColumns._ID, toObjectArray(value));
        return this;
    }

    public RecipeSelection idNot(long... value) {
        addNotEquals("recipe." + RecipeColumns._ID, toObjectArray(value));
        return this;
    }

    public RecipeSelection orderById(boolean desc) {
        orderBy("recipe." + RecipeColumns._ID, desc);
        return this;
    }

    public RecipeSelection orderById() {
        return orderById(false);
    }

    public RecipeSelection userId(long... value) {
        addEquals(RecipeColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public RecipeSelection userIdNot(long... value) {
        addNotEquals(RecipeColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public RecipeSelection userIdGt(long value) {
        addGreaterThan(RecipeColumns.USER_ID, value);
        return this;
    }

    public RecipeSelection userIdGtEq(long value) {
        addGreaterThanOrEquals(RecipeColumns.USER_ID, value);
        return this;
    }

    public RecipeSelection userIdLt(long value) {
        addLessThan(RecipeColumns.USER_ID, value);
        return this;
    }

    public RecipeSelection userIdLtEq(long value) {
        addLessThanOrEquals(RecipeColumns.USER_ID, value);
        return this;
    }

    public RecipeSelection orderByUserId(boolean desc) {
        orderBy(RecipeColumns.USER_ID, desc);
        return this;
    }

    public RecipeSelection orderByUserId() {
        orderBy(RecipeColumns.USER_ID, false);
        return this;
    }

    public RecipeSelection userGoogleId(String... value) {
        addEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public RecipeSelection userGoogleIdNot(String... value) {
        addNotEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public RecipeSelection userGoogleIdGt(String value) {
        addGreaterThan(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public RecipeSelection userGoogleIdGtEq(String value) {
        addGreaterThanOrEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public RecipeSelection userGoogleIdLt(String value) {
        addLessThan(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public RecipeSelection userGoogleIdLtEq(String value) {
        addLessThanOrEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public RecipeSelection orderByUserGoogleId(boolean desc) {
        orderBy(UserColumns.GOOGLE_ID, desc);
        return this;
    }

    public RecipeSelection orderByUserGoogleId() {
        orderBy(UserColumns.GOOGLE_ID, false);
        return this;
    }

    public RecipeSelection userName(String... value) {
        addEquals(UserColumns.NAME, value);
        return this;
    }

    public RecipeSelection userNameNot(String... value) {
        addNotEquals(UserColumns.NAME, value);
        return this;
    }

    public RecipeSelection userNameLike(String... value) {
        addLike(UserColumns.NAME, value);
        return this;
    }

    public RecipeSelection userNameContains(String... value) {
        addContains(UserColumns.NAME, value);
        return this;
    }

    public RecipeSelection userNameStartsWith(String... value) {
        addStartsWith(UserColumns.NAME, value);
        return this;
    }

    public RecipeSelection userNameEndsWith(String... value) {
        addEndsWith(UserColumns.NAME, value);
        return this;
    }

    public RecipeSelection orderByUserName(boolean desc) {
        orderBy(UserColumns.NAME, desc);
        return this;
    }

    public RecipeSelection orderByUserName() {
        orderBy(UserColumns.NAME, false);
        return this;
    }

    public RecipeSelection userImageUrl(String... value) {
        addEquals(UserColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection userImageUrlNot(String... value) {
        addNotEquals(UserColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection userImageUrlLike(String... value) {
        addLike(UserColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection userImageUrlContains(String... value) {
        addContains(UserColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection userImageUrlStartsWith(String... value) {
        addStartsWith(UserColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection userImageUrlEndsWith(String... value) {
        addEndsWith(UserColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection orderByUserImageUrl(boolean desc) {
        orderBy(UserColumns.IMAGE_URL, desc);
        return this;
    }

    public RecipeSelection orderByUserImageUrl() {
        orderBy(UserColumns.IMAGE_URL, false);
        return this;
    }

    public RecipeSelection categoryId(long... value) {
        addEquals(RecipeColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public RecipeSelection categoryIdNot(long... value) {
        addNotEquals(RecipeColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public RecipeSelection categoryIdGt(long value) {
        addGreaterThan(RecipeColumns.CATEGORY_ID, value);
        return this;
    }

    public RecipeSelection categoryIdGtEq(long value) {
        addGreaterThanOrEquals(RecipeColumns.CATEGORY_ID, value);
        return this;
    }

    public RecipeSelection categoryIdLt(long value) {
        addLessThan(RecipeColumns.CATEGORY_ID, value);
        return this;
    }

    public RecipeSelection categoryIdLtEq(long value) {
        addLessThanOrEquals(RecipeColumns.CATEGORY_ID, value);
        return this;
    }

    public RecipeSelection orderByCategoryId(boolean desc) {
        orderBy(RecipeColumns.CATEGORY_ID, desc);
        return this;
    }

    public RecipeSelection orderByCategoryId() {
        orderBy(RecipeColumns.CATEGORY_ID, false);
        return this;
    }

    public RecipeSelection categoryName(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public RecipeSelection categoryNameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public RecipeSelection categoryNameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public RecipeSelection categoryNameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public RecipeSelection categoryNameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public RecipeSelection categoryNameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public RecipeSelection orderByCategoryName(boolean desc) {
        orderBy(CategoryColumns.NAME, desc);
        return this;
    }

    public RecipeSelection orderByCategoryName() {
        orderBy(CategoryColumns.NAME, false);
        return this;
    }

    public RecipeSelection name(String... value) {
        addEquals(RecipeColumns.NAME, value);
        return this;
    }

    public RecipeSelection nameNot(String... value) {
        addNotEquals(RecipeColumns.NAME, value);
        return this;
    }

    public RecipeSelection nameLike(String... value) {
        addLike(RecipeColumns.NAME, value);
        return this;
    }

    public RecipeSelection nameContains(String... value) {
        addContains(RecipeColumns.NAME, value);
        return this;
    }

    public RecipeSelection nameStartsWith(String... value) {
        addStartsWith(RecipeColumns.NAME, value);
        return this;
    }

    public RecipeSelection nameEndsWith(String... value) {
        addEndsWith(RecipeColumns.NAME, value);
        return this;
    }

    public RecipeSelection orderByName(boolean desc) {
        orderBy(RecipeColumns.NAME, desc);
        return this;
    }

    public RecipeSelection orderByName() {
        orderBy(RecipeColumns.NAME, false);
        return this;
    }

    public RecipeSelection favorite(Boolean... value) {
        addEquals(RecipeColumns.FAVORITE, value);
        return this;
    }

    public RecipeSelection favoriteNot(Boolean... value) {
        addNotEquals(RecipeColumns.FAVORITE, value);
        return this;
    }

    public RecipeSelection orderByFavorite(boolean desc) {
        orderBy(RecipeColumns.FAVORITE, desc);
        return this;
    }

    public RecipeSelection orderByFavorite() {
        orderBy(RecipeColumns.FAVORITE, false);
        return this;
    }

    public RecipeSelection imageUrl(String... value) {
        addEquals(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection imageUrlNot(String... value) {
        addNotEquals(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection imageUrlLike(String... value) {
        addLike(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection imageUrlContains(String... value) {
        addContains(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection imageUrlStartsWith(String... value) {
        addStartsWith(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection imageUrlEndsWith(String... value) {
        addEndsWith(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public RecipeSelection orderByImageUrl(boolean desc) {
        orderBy(RecipeColumns.IMAGE_URL, desc);
        return this;
    }

    public RecipeSelection orderByImageUrl() {
        orderBy(RecipeColumns.IMAGE_URL, false);
        return this;
    }
}
