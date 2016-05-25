package de.androidbytes.recipr.data.repository.data;

import android.content.ContentResolver;
import android.net.Uri;
import de.androidbytes.recipr.data.entity.UserEntity;
import de.androidbytes.recipr.data.exception.DatabaseException;
import de.androidbytes.recipr.data.provider.user.UserColumns;
import de.androidbytes.recipr.data.provider.user.UserContentValues;
import de.androidbytes.recipr.data.provider.user.UserCursor;
import de.androidbytes.recipr.data.provider.user.UserSelection;

import javax.inject.Inject;

/**
 * Created by Christoph on 21.05.2016.
 */
public class UserDataRepository {

    private ContentResolver contentResolver;

    @Inject
    public UserDataRepository(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public UserEntity findById(long userId) {
        UserSelection userSelection = new UserSelection();
        userSelection.id(userId);
        UserCursor userCursor = userSelection.query(contentResolver);

        if (userCursor.moveToFirst()) {
            return new UserEntity(userCursor);
        }

        throw new DatabaseException("No user with id '" + userId + "' found.");
    }

    public UserEntity loginUser(UserEntity userEntity) {


        UserEntity loggedInUser = findLoggedInUser();
        if (loggedInUser != null) {
            if (loggedInUser.getId() == userEntity.getId()) {
                return loggedInUser;
            } else {
                logoutUser(loggedInUser);
            }
        }

        long userId = userEntity.getId();
        if (userId == -1) {
            UserCursor userCursor = createUser(userEntity);
            if (userCursor.moveToFirst())
                userId = userCursor.getId();
        }

        UserSelection userSelection = new UserSelection();
        userSelection.id(userId);
        UserContentValues loginUserContentValues = new UserEntity(
                userId,
                userEntity.getGoogleId(),
                userEntity.getName(),
                userEntity.getImageUrl(),
                true
        ).getContentValues();

        loginUserContentValues.update(contentResolver, userSelection);

        return findLoggedInUser();

    }

    private UserCursor createUser(UserEntity userEntity) {
        UserContentValues contentValues = userEntity.getContentValues();
        Uri createdUserUri = contentResolver.insert(UserColumns.CONTENT_URI, contentValues.values());
        if (createdUserUri != null) {
            return new UserCursor(contentResolver.query(createdUserUri, null, null, null, null));
        }

        throw new DatabaseException("Could not create user with name '" + userEntity.getName() + "'");

    }

    public UserEntity findLoggedInUser() {

        UserSelection userSelection = new UserSelection();
        userSelection.loggedIn(true);
        UserCursor userCursor = userSelection.query(contentResolver);

        if (userCursor.moveToFirst() && userCursor.getCount() == 1) {
            return new UserEntity(userCursor);
        } else {
            if (userCursor.getCount() > 1) {
                logoutAllUsers(userCursor);
            }

            return null;
        }
    }

    private void logoutAllUsers(UserCursor userCursor) {
        do {
            UserEntity userEntity = new UserEntity(userCursor);
            logoutUser(userEntity);
        } while (userCursor.moveToNext());
    }

    private void logoutUser(UserEntity userEntity) {
        UserContentValues userContentValues = userEntity.getContentValues().putLoggedIn(false);
        UserSelection userSelection = new UserSelection();
        userSelection.id(userEntity.getId());
        userContentValues.update(contentResolver, userSelection);
    }
}
