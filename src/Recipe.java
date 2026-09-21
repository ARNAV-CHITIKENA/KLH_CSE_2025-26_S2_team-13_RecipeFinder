import java.util.ArrayList;

public class Recipe {

    private int recipeId;
    private String recipeName;
    private String cuisine;
    private String category;
    private ArrayList<String> steps;

    public Recipe(
            int recipeId,
            String recipeName,
            String cuisine,
            String category,
            ArrayList<String> steps) {

        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.cuisine = cuisine;
        this.category = category;
        this.steps = steps;
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

    // =====================================================
    // DISPLAY COMPLETE RECIPE
    // =====================================================

    public void displayRecipe() {

        System.out.println();
        System.out.println(
            "========================================"
        );

        System.out.println(
            "             RECIPE FOUND"
        );

        System.out.println(
            "========================================"
        );

        System.out.println(
            "Recipe ID : " + recipeId
        );

        System.out.println(
            "Recipe    : " + recipeName
        );

        System.out.println(
            "Cuisine   : " + cuisine
        );

        System.out.println(
            "Category  : " + category
        );

        System.out.println();

        System.out.println(
            "PREPARATION STEPS:"
        );

        System.out.println(
            "----------------------------------------"
        );

        // Display every preparation step
        for (int i = 0; i < steps.size(); i++) {

            System.out.println(
                (i + 1) + ". " + steps.get(i)
            );
        }

        System.out.println(
            "========================================"
        );
    }
}