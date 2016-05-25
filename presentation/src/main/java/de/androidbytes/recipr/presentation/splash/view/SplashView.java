package de.androidbytes.recipr.presentation.splash.view;

import android.content.Context;
import de.androidbytes.recipr.domain.model.User;

/**
 * Created by Christoph on 22.05.2016.
 */
public interface SplashView {
    void registerUser(User user);

    void destroy();

    Context getContext();
}
