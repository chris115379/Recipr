package de.androidbytes.recipr.presentation.login.view;

import de.androidbytes.recipr.domain.model.User;

/**
 * View Interface for the LoginScreen
 */
public interface LoginView {

    /**
     * Display the Google Sign In Account Chooser
     */
    void renderGoogleSignIn();

    /**
     * Display a SignUp Failed message
     */
    void renderError(String message);

    void registerUser(User user);

    void destroy();
}
