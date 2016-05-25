package de.androidbytes.recipr.data.provider.ingredient;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import de.androidbytes.recipr.data.provider.base.AbstractSelection;
import de.androidbytes.recipr.data.provider.category.CategoryColumns;
import de.androidbytes.recipr.data.provider.recipe.RecipeColumns;
import de.androidbytes.recipr.data.provider.user.UserColumns;

/**
 * Selection for the {@code ingredient} table.
 */
public class IngredientSelection extends AbstractSelection<IngredientSelection> {

    private static final String[] DEFAULT_PROJECTION = IngredientColumns.ALL_COLUMNS;

    @Override
    protected Uri baseUri() {
        return IngredientColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code IngredientCursor} object, which is positioned before the first entry, or null.
     */
    public IngredientCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new IngredientCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public IngredientCursor query(ContentResolver contentResolver) {
        return query(contentResolver, DEFAULT_PROJECTION);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code IngredientCursor} object, which is positioned before the first entry, or null.
     */
    public IngredientCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new IngredientCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public IngredientCursor query(Context context) {
        return query(context, null);
    }


    public IngredientSelection id(long... value) {
        addEquals("ingredient." + IngredientColumns._ID, toObjectArray(value));
        return this;
    }

    public IngredientSelection idNot(long... value) {
        addNotEquals("ingredient." + IngredientColumns._ID, toObjectArray(value));
        return this;
    }

    public IngredientSelection orderById(boolean desc) {
        orderBy("ingredient." + IngredientColumns._ID, desc);
        return this;
    }

    public IngredientSelection orderById() {
        return orderById(false);
    }

    public IngredientSelection recipeId(long... value) {
        addEquals(IngredientColumns.RECIPE_ID, toObjectArray(value));
        return this;
    }

    public IngredientSelection recipeIdNot(long... value) {
        addNotEquals(IngredientColumns.RECIPE_ID, toObjectArray(value));
        return this;
    }

    public IngredientSelection recipeIdGt(long value) {
        addGreaterThan(IngredientColumns.RECIPE_ID, value);
        return this;
    }

    public IngredientSelection recipeIdGtEq(long value) {
        addGreaterThanOrEquals(IngredientColumns.RECIPE_ID, value);
        return this;
    }

    public IngredientSelection recipeIdLt(long value) {
        addLessThan(IngredientColumns.RECIPE_ID, value);
        return this;
    }

    public IngredientSelection recipeIdLtEq(long value) {
        addLessThanOrEquals(IngredientColumns.RECIPE_ID, value);
        return this;
    }

    public IngredientSelection orderByRecipeId(boolean desc) {
        orderBy(IngredientColumns.RECIPE_ID, desc);
        return this;
    }

    public IngredientSelection orderByRecipeId() {
        orderBy(IngredientColumns.RECIPE_ID, false);
        return this;
    }

    public IngredientSelection recipeUserId(long... value) {
        addEquals(RecipeColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public IngredientSelection recipeUserIdNot(long... value) {
        addNotEquals(RecipeColumns.USER_ID, toObjectArray(value));
        return this;
    }

    public IngredientSelection recipeUserIdGt(long value) {
        addGreaterThan(RecipeColumns.USER_ID, value);
        return this;
    }

    public IngredientSelection recipeUserIdGtEq(long value) {
        addGreaterThanOrEquals(RecipeColumns.USER_ID, value);
        return this;
    }

    public IngredientSelection recipeUserIdLt(long value) {
        addLessThan(RecipeColumns.USER_ID, value);
        return this;
    }

    public IngredientSelection recipeUserIdLtEq(long value) {
        addLessThanOrEquals(RecipeColumns.USER_ID, value);
        return this;
    }

    public IngredientSelection orderByRecipeUserId(boolean desc) {
        orderBy(RecipeColumns.USER_ID, desc);
        return this;
    }

    public IngredientSelection orderByRecipeUserId() {
        orderBy(RecipeColumns.USER_ID, false);
        return this;
    }

    public IngredientSelection recipeUserGoogleId(String... value) {
        addEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public IngredientSelection recipeUserGoogleIdNot(String... value) {
        addNotEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public IngredientSelection recipeUserGoogleIdGt(String value) {
        addGreaterThan(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public IngredientSelection recipeUserGoogleIdGtEq(String value) {
        addGreaterThanOrEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public IngredientSelection recipeUserGoogleIdLt(String value) {
        addLessThan(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public IngredientSelection recipeUserGoogleIdLtEq(String value) {
        addLessThanOrEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public IngredientSelection orderByRecipeUserGoogleId(boolean desc) {
        orderBy(UserColumns.GOOGLE_ID, desc);
        return this;
    }

    public IngredientSelection orderByRecipeUserGoogleId() {
        orderBy(UserColumns.GOOGLE_ID, false);
        return this;
    }

    public IngredientSelection recipeUserName(String... value) {
        addEquals(UserColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeUserNameNot(String... value) {
        addNotEquals(UserColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeUserNameLike(String... value) {
        addLike(UserColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeUserNameContains(String... value) {
        addContains(UserColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeUserNameStartsWith(String... value) {
        addStartsWith(UserColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeUserNameEndsWith(String... value) {
        addEndsWith(UserColumns.NAME, value);
        return this;
    }

    public IngredientSelection orderByRecipeUserName(boolean desc) {
        orderBy(UserColumns.NAME, desc);
        return this;
    }

    public IngredientSelection orderByRecipeUserName() {
        orderBy(UserColumns.NAME, false);
        return this;
    }

    public IngredientSelection recipeUserImageUrl(String... value) {
        addEquals(UserColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeUserImageUrlNot(String... value) {
        addNotEquals(UserColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeUserImageUrlLike(String... value) {
        addLike(UserColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeUserImageUrlContains(String... value) {
        addContains(UserColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeUserImageUrlStartsWith(String... value) {
        addStartsWith(UserColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeUserImageUrlEndsWith(String... value) {
        addEndsWith(UserColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection orderByRecipeUserImageUrl(boolean desc) {
        orderBy(UserColumns.IMAGE_URL, desc);
        return this;
    }

    public IngredientSelection orderByRecipeUserImageUrl() {
        orderBy(UserColumns.IMAGE_URL, false);
        return this;
    }

    public IngredientSelection recipeCategoryId(long... value) {
        addEquals(RecipeColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public IngredientSelection recipeCategoryIdNot(long... value) {
        addNotEquals(RecipeColumns.CATEGORY_ID, toObjectArray(value));
        return this;
    }

    public IngredientSelection recipeCategoryIdGt(long value) {
        addGreaterThan(RecipeColumns.CATEGORY_ID, value);
        return this;
    }

    public IngredientSelection recipeCategoryIdGtEq(long value) {
        addGreaterThanOrEquals(RecipeColumns.CATEGORY_ID, value);
        return this;
    }

    public IngredientSelection recipeCategoryIdLt(long value) {
        addLessThan(RecipeColumns.CATEGORY_ID, value);
        return this;
    }

    public IngredientSelection recipeCategoryIdLtEq(long value) {
        addLessThanOrEquals(RecipeColumns.CATEGORY_ID, value);
        return this;
    }

    public IngredientSelection orderByRecipeCategoryId(boolean desc) {
        orderBy(RecipeColumns.CATEGORY_ID, desc);
        return this;
    }

    public IngredientSelection orderByRecipeCategoryId() {
        orderBy(RecipeColumns.CATEGORY_ID, false);
        return this;
    }

    public IngredientSelection recipeCategoryName(String... value) {
        addEquals(CategoryColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeCategoryNameNot(String... value) {
        addNotEquals(CategoryColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeCategoryNameLike(String... value) {
        addLike(CategoryColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeCategoryNameContains(String... value) {
        addContains(CategoryColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeCategoryNameStartsWith(String... value) {
        addStartsWith(CategoryColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeCategoryNameEndsWith(String... value) {
        addEndsWith(CategoryColumns.NAME, value);
        return this;
    }

    public IngredientSelection orderByRecipeCategoryName(boolean desc) {
        orderBy(CategoryColumns.NAME, desc);
        return this;
    }

    public IngredientSelection orderByRecipeCategoryName() {
        orderBy(CategoryColumns.NAME, false);
        return this;
    }

    public IngredientSelection recipeName(String... value) {
        addEquals(RecipeColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeNameNot(String... value) {
        addNotEquals(RecipeColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeNameLike(String... value) {
        addLike(RecipeColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeNameContains(String... value) {
        addContains(RecipeColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeNameStartsWith(String... value) {
        addStartsWith(RecipeColumns.NAME, value);
        return this;
    }

    public IngredientSelection recipeNameEndsWith(String... value) {
        addEndsWith(RecipeColumns.NAME, value);
        return this;
    }

    public IngredientSelection orderByRecipeName(boolean desc) {
        orderBy(RecipeColumns.NAME, desc);
        return this;
    }

    public IngredientSelection orderByRecipeName() {
        orderBy(RecipeColumns.NAME, false);
        return this;
    }

    public IngredientSelection recipeRating(Integer... value) {
        addEquals(RecipeColumns.FAVORITE, value);
        return this;
    }

    public IngredientSelection recipeRatingNot(Integer... value) {
        addNotEquals(RecipeColumns.FAVORITE, value);
        return this;
    }

    public IngredientSelection recipeRatingGt(int value) {
        addGreaterThan(RecipeColumns.FAVORITE, value);
        return this;
    }

    public IngredientSelection recipeRatingGtEq(int value) {
        addGreaterThanOrEquals(RecipeColumns.FAVORITE, value);
        return this;
    }

    public IngredientSelection recipeRatingLt(int value) {
        addLessThan(RecipeColumns.FAVORITE, value);
        return this;
    }

    public IngredientSelection recipeRatingLtEq(int value) {
        addLessThanOrEquals(RecipeColumns.FAVORITE, value);
        return this;
    }

    public IngredientSelection orderByRecipeRating(boolean desc) {
        orderBy(RecipeColumns.FAVORITE, desc);
        return this;
    }

    public IngredientSelection orderByRecipeRating() {
        orderBy(RecipeColumns.FAVORITE, false);
        return this;
    }

    public IngredientSelection recipeImageUrl(String... value) {
        addEquals(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeImageUrlNot(String... value) {
        addNotEquals(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeImageUrlLike(String... value) {
        addLike(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeImageUrlContains(String... value) {
        addContains(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeImageUrlStartsWith(String... value) {
        addStartsWith(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection recipeImageUrlEndsWith(String... value) {
        addEndsWith(RecipeColumns.IMAGE_URL, value);
        return this;
    }

    public IngredientSelection orderByRecipeImageUrl(boolean desc) {
        orderBy(RecipeColumns.IMAGE_URL, desc);
        return this;
    }

    public IngredientSelection orderByRecipeImageUrl() {
        orderBy(RecipeColumns.IMAGE_URL, false);
        return this;
    }

    public IngredientSelection name(String... value) {
        addEquals(IngredientColumns.NAME, value);
        return this;
    }

    public IngredientSelection nameNot(String... value) {
        addNotEquals(IngredientColumns.NAME, value);
        return this;
    }

    public IngredientSelection nameLike(String... value) {
        addLike(IngredientColumns.NAME, value);
        return this;
    }

    public IngredientSelection nameContains(String... value) {
        addContains(IngredientColumns.NAME, value);
        return this;
    }

    public IngredientSelection nameStartsWith(String... value) {
        addStartsWith(IngredientColumns.NAME, value);
        return this;
    }

    public IngredientSelection nameEndsWith(String... value) {
        addEndsWith(IngredientColumns.NAME, value);
        return this;
    }

    public IngredientSelection orderByName(boolean desc) {
        orderBy(IngredientColumns.NAME, desc);
        return this;
    }

    public IngredientSelection orderByName() {
        orderBy(IngredientColumns.NAME, false);
        return this;
    }

    public IngredientSelection quantity(String... value) {
        addEquals(IngredientColumns.QUANTITY, value);
        return this;
    }

    public IngredientSelection quantityNot(String... value) {
        addNotEquals(IngredientColumns.QUANTITY, value);
        return this;
    }

    public IngredientSelection quantityGt(int value) {
        addGreaterThan(IngredientColumns.QUANTITY, value);
        return this;
    }

    public IngredientSelection quantityGtEq(int value) {
        addGreaterThanOrEquals(IngredientColumns.QUANTITY, value);
        return this;
    }

    public IngredientSelection quantityLt(int value) {
        addLessThan(IngredientColumns.QUANTITY, value);
        return this;
    }

    public IngredientSelection quantityLtEq(int value) {
        addLessThanOrEquals(IngredientColumns.QUANTITY, value);
        return this;
    }

    public IngredientSelection orderByQuantity(boolean desc) {
        orderBy(IngredientColumns.QUANTITY, desc);
        return this;
    }

    public IngredientSelection orderByQuantity() {
        orderBy(IngredientColumns.QUANTITY, false);
        return this;
    }

    public IngredientSelection unit(String... value) {
        addEquals(IngredientColumns.UNIT, value);
        return this;
    }

    public IngredientSelection unitNot(String... value) {
        addNotEquals(IngredientColumns.UNIT, value);
        return this;
    }

    public IngredientSelection unitLike(String... value) {
        addLike(IngredientColumns.UNIT, value);
        return this;
    }

    public IngredientSelection unitContains(String... value) {
        addContains(IngredientColumns.UNIT, value);
        return this;
    }

    public IngredientSelection unitStartsWith(String... value) {
        addStartsWith(IngredientColumns.UNIT, value);
        return this;
    }

    public IngredientSelection unitEndsWith(String... value) {
        addEndsWith(IngredientColumns.UNIT, value);
        return this;
    }

    public IngredientSelection orderByUnit(boolean desc) {
        orderBy(IngredientColumns.UNIT, desc);
        return this;
    }

    public IngredientSelection orderByUnit() {
        orderBy(IngredientColumns.UNIT, false);
        return this;
    }
}
