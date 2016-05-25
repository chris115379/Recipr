package de.androidbytes.recipr.domain.model;

import java.util.List;

public class Recipe {

    private long id;
    private String name;
    private boolean favorite;
    private String imageUrl;

    private User user;
    private Category category;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    public Recipe(
            String name,
            List<Ingredient> ingredients,
            List<Step> steps,
            boolean favorite,
            String imageUrl,
            User user,
            Category category) {
        this(
                -1,
                name,
                ingredients,
                steps,
                favorite,
                imageUrl,
                user,
                category
        );
    }

    public Recipe(
            long id,
            String name,
            List<Ingredient> ingredients,
            List<Step> steps,
            boolean favorite,
            String imageUrl,
            User user,
            Category category
    ) {
        this.name = name;
        this.id = id;
        this.favorite = favorite;
        this.imageUrl = imageUrl;
        this.category = category;
        this.user = user;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public User getUser() {
        return user;
    }
    
    public Category getCategory() {
        return category;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
