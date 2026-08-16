public class Recipe {

    private int recipeId;
    private String recipeName;
    private String cuisine;
    private String category;
    private int cookingTime;
    private double budget;
    private double rating;

    public Recipe(int recipeId, String recipeName, String cuisine,
                  String category, int cookingTime,
                  double budget, double rating) {

        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.cuisine = cuisine;
        this.category = category;
        this.cookingTime = cookingTime;
        this.budget = budget;
        this.rating = rating;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getCategory() {
        return category;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public double getBudget() {
        return budget;
    }

    public double getRating() {
        return rating;
    }

    // Searchable text used by Rabin-Karp.
    // Phase 1 dataset currently has no ingredients column.
    public String getSearchableText() {
        return recipeName + " " + cuisine + " " + category;
    }
}
