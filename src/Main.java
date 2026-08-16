import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String FILE_PATH = "data/recipes.csv";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Recipe> recipes =
                RecipeReader.loadRecipes(FILE_PATH);

        System.out.println("========================================");
        System.out.println("             RECIPE FINDER");
        System.out.println("========================================");
        System.out.println("Recipes loaded: " + recipes.size());

        if (recipes.isEmpty()) {
            System.out.println("\nNo recipes were loaded.");
            System.out.println("Check that data/recipes.csv exists.");
            scanner.close();
            return;
        }

        boolean running = true;

        while (running) {

            System.out.println("\n1. Search Recipe");
            System.out.println("2. Exit");
            System.out.print("\nEnter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    searchRecipes(recipes, scanner);
                    break;

                case "2":
                    System.out.println("\nThank you for using Recipe Finder!");
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid choice. Enter 1 or 2.");
            }
        }

        scanner.close();
    }

    private static void searchRecipes(
            ArrayList<Recipe> recipes,
            Scanner scanner) {

        System.out.print("\nEnter search keyword: ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        System.out.println("\nSelect search method:");
        System.out.println("1. KMP");
        System.out.println("2. Rabin-Karp");
        System.out.println("3. Z-Function");

        System.out.print("\nEnter choice: ");
        String method = scanner.nextLine().trim();

        System.out.println("\nSearching...");
        System.out.println("Total recipes loaded: " + recipes.size());

        int recordsChecked = 0;
        int matchesFound = 0;

        for (Recipe recipe : recipes) {

            recordsChecked++;

            boolean matched = false;

            switch (method) {

                case "1":
                    // KMP: recipe name / keyword search
                    matched = StringAlgorithms.kmpContains(
                            recipe.getRecipeName(),
                            query
                    );
                    break;

                case "2":
                    // Rabin-Karp: searchable recipe text.
                    // Current CSV does not yet contain ingredients.
                    matched = StringAlgorithms.rabinKarpContains(
                            recipe.getSearchableText(),
                            query
                    );
                    break;

                case "3":
                    // Z-Function: additional substring search
                    matched = StringAlgorithms.zFunctionContains(
                            recipe.getRecipeName(),
                            query
                    );
                    break;

                default:
                    System.out.println("Invalid algorithm choice.");
                    return;
            }

            if (matched) {

                matchesFound++;

                System.out.println("\n----------------------------------------");
                System.out.println("Match " + matchesFound);
                System.out.println("Recipe ID     : " + recipe.getRecipeId());
                System.out.println("Recipe Name   : " + recipe.getRecipeName());
                System.out.println("Cuisine       : " + recipe.getCuisine());
                System.out.println("Category      : " + recipe.getCategory());
                System.out.println("Cooking Time  : "
                        + recipe.getCookingTime() + " minutes");
                System.out.println("Budget        : ₹" + recipe.getBudget());
                System.out.println("Rating        : " + recipe.getRating());
                System.out.println("----------------------------------------");
            }
        }

        System.out.println("\n========================================");
        System.out.println("SEARCH SUMMARY");
        System.out.println("========================================");
        System.out.println("Query           : " + query);
        System.out.println("Algorithm       : " + getAlgorithmName(method));
        System.out.println("Records Checked : " + recordsChecked);
        System.out.println("Matches Found   : " + matchesFound);
        System.out.println("========================================");
    }

    private static String getAlgorithmName(String method) {

        switch (method) {
            case "1":
                return "KMP";
            case "2":
                return "Rabin-Karp";
            case "3":
                return "Z-Function";
            default:
                return "Unknown";
        }
    }
}
