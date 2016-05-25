package de.androidbytes.recipr.domain.model;

public class Category {

    private long id;
    private String name;
    private User user;

    public Category(User user, String name) {
        this(
                -1,
                user,
                name
        );
    }

    public Category(long id, User user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }
}
