package de.androidbytes.recipr.domain.model;


public class User {

    private long id = -1;
    private String googleId;
    private String name;
    private String imageUrl;
    private boolean loggedIn;

    public User(long id, String googleId, String name, String imageUrl, boolean loggedIn) {
        this.id = id;
        this.googleId = googleId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.loggedIn = loggedIn;
    }

    public long getId() {
        return id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
