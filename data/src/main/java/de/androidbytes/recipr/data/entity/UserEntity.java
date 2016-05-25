package de.androidbytes.recipr.data.entity;

import de.androidbytes.recipr.data.provider.user.UserContentValues;
import de.androidbytes.recipr.data.provider.user.UserCursor;
import lombok.Getter;

@Getter
public class UserEntity {

    private long id = -1;
    private String googleId;
    private String name;
    private String imageUrl;
    private boolean loggedIn;

    public UserEntity(long id, String googleId, String name, String imageUrl, boolean loggedIn) {
        this.id = id;
        this.googleId = googleId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.loggedIn = loggedIn;
    }

    public UserEntity(UserCursor userCursor) {
        this(
                userCursor.getId(),
                userCursor.getGoogleId(),
                userCursor.getName(),
                userCursor.getImageUrl(),
                userCursor.isLoggedIn()
        );
    }

    public UserContentValues getContentValues() {
        final UserContentValues userContentValues = new UserContentValues();
        userContentValues.putGoogleId(googleId);
        userContentValues.putName(name);
        userContentValues.putImageUrl(imageUrl);
        userContentValues.putLoggedIn(loggedIn);
        return userContentValues;
    }
}
