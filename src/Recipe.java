import java.util.ArrayList;

public class Recipe {

    private int recipeId;
    private String recipeName;
    private String cuisine;
    private String category;
    private ArrayList<String> steps;

    // CO5 Knapsack fields.
    // -1 means the value is not present in the TXT file.
    private int cost;
    private int value;

    public Recipe(
            int recipeId,
            String recipeName,
            String cuisine,
            String category,
            ArrayList<String> steps) {

        this(recipeId, recipeName, cuisine, category, steps, -1, -1);
    }

    public Recipe(
            int recipeId,
            String recipeName,
            String cuisine,
            String category,
            ArrayList<String> steps,
            int cost,
            int value) {

        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.cuisine = cuisine;
        this.category = category;
        this.steps = steps;
        this.cost = cost;
        this.value = value;
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

    public ArrayList<String> getSteps() {
        return steps;
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }

    public boolean hasKnapsackData() {
        return cost >= 0 && value >= 0;
    }

    // =====================================================
    // DISPLAY COMPLETE RECIPE
    // =====================================================

    public void displayRecipe() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             RECIPE FOUND");
        System.out.println("========================================");
        System.out.println("Recipe ID : " + recipeId);
        System.out.println("Recipe    : " + recipeName);
        System.out.println("Cuisine   : " + cuisine);
        System.out.println("Category  : " + category);

        if (hasKnapsackData()) {
            System.out.println("Cost      : " + cost);
            System.out.println("Value     : " + value);
        }

        System.out.println();
        System.out.println("PREPARATION STEPS:");
        System.out.println("----------------------------------------");

        for (int i = 0; i < steps.size(); i++) {
            System.out.println((i + 1) + ". " + steps.get(i));
        }

        System.out.println("========================================");
    }
}
