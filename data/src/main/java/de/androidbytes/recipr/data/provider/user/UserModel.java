package de.androidbytes.recipr.data.provider.user;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import de.androidbytes.recipr.data.provider.base.BaseModel;

/**
 * A user is an account assigned to the physical human using Plannr.
 */
public interface UserModel extends BaseModel {

    /**
     * Google Id. Provided by the Authorization Service like Google.
     * Can be {@code null}.
     */
    @Nullable
    String getGoogleId();

    /**
     * name of the user account.
     * Cannot be {@code null}.
     */
    @NonNull
    String getName();

    /**
     * Url to an image source used as a profile picture of the specific user account.
     * Can be {@code null}.
     */
    @Nullable
    String getImageUrl();

    Boolean isLoggedIn();
}
