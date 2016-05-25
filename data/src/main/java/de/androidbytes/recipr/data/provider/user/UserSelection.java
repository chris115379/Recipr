package de.androidbytes.recipr.data.provider.user;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import de.androidbytes.recipr.data.provider.base.AbstractSelection;

/**
 * Selection for the {@code user} table.
 */
public class UserSelection extends AbstractSelection<UserSelection> {

    private static final String[] DEFAULT_PROJECTION = UserColumns.ALL_COLUMNS;

    @Override
    protected Uri baseUri() {
        return UserColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code UserCursor} object, which is positioned before the first entry, or null.
     */
    public UserCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new UserCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public UserCursor query(ContentResolver contentResolver) {
        return query(contentResolver, DEFAULT_PROJECTION);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code UserCursor} object, which is positioned before the first entry, or null.
     */
    public UserCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new UserCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public UserCursor query(Context context) {
        return query(context, null);
    }


    public UserSelection id(long... value) {
        addEquals("user." + UserColumns._ID, toObjectArray(value));
        return this;
    }

    public UserSelection idNot(long... value) {
        addNotEquals("user." + UserColumns._ID, toObjectArray(value));
        return this;
    }

    public UserSelection orderById(boolean desc) {
        orderBy("user." + UserColumns._ID, desc);
        return this;
    }

    public UserSelection orderById() {
        return orderById(false);
    }

    public UserSelection googleId(String... value) {
        addEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public UserSelection googleIdNot(String... value) {
        addNotEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public UserSelection googleIdGt(String value) {
        addGreaterThan(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public UserSelection googleIdGtEq(String value) {
        addGreaterThanOrEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public UserSelection googleIdLt(String value) {
        addLessThan(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public UserSelection googleIdLtEq(String value) {
        addLessThanOrEquals(UserColumns.GOOGLE_ID, value);
        return this;
    }

    public UserSelection orderByGoogleId(boolean desc) {
        orderBy(UserColumns.GOOGLE_ID, desc);
        return this;
    }

    public UserSelection orderByGoogleId() {
        orderBy(UserColumns.GOOGLE_ID, false);
        return this;
    }

    public UserSelection name(String... value) {
        addEquals(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameNot(String... value) {
        addNotEquals(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameLike(String... value) {
        addLike(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameContains(String... value) {
        addContains(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameStartsWith(String... value) {
        addStartsWith(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameEndsWith(String... value) {
        addEndsWith(UserColumns.NAME, value);
        return this;
    }

    public UserSelection orderByName(boolean desc) {
        orderBy(UserColumns.NAME, desc);
        return this;
    }

    public UserSelection orderByName() {
        orderBy(UserColumns.NAME, false);
        return this;
    }

    public UserSelection imageUrl(String... value) {
        addEquals(UserColumns.IMAGE_URL, value);
        return this;
    }

    public UserSelection imageUrlNot(String... value) {
        addNotEquals(UserColumns.IMAGE_URL, value);
        return this;
    }

    public UserSelection imageUrlLike(String... value) {
        addLike(UserColumns.IMAGE_URL, value);
        return this;
    }

    public UserSelection imageUrlContains(String... value) {
        addContains(UserColumns.IMAGE_URL, value);
        return this;
    }

    public UserSelection imageUrlStartsWith(String... value) {
        addStartsWith(UserColumns.IMAGE_URL, value);
        return this;
    }

    public UserSelection imageUrlEndsWith(String... value) {
        addEndsWith(UserColumns.IMAGE_URL, value);
        return this;
    }

    public UserSelection orderByImageUrl(boolean desc) {
        orderBy(UserColumns.IMAGE_URL, desc);
        return this;
    }

    public UserSelection orderByImageUrl() {
        orderBy(UserColumns.IMAGE_URL, false);
        return this;
    }

    public UserSelection loggedIn(Boolean... value) {
        addEquals(UserColumns.LOGGED_IN, value);
        return this;
    }

    public UserSelection loggedInNot(Boolean... value) {
        addNotEquals(UserColumns.LOGGED_IN, value);
        return this;
    }

    public UserSelection orderByLoggedIn(boolean desc) {
        orderBy(UserColumns.LOGGED_IN, desc);
        return this;
    }

    public UserSelection orderByLoggedIn() {
        orderBy(UserColumns.LOGGED_IN, false);
        return this;
    }
}
