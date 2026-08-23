import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Load recipes from data/recipes.txt
        ArrayList<Recipe> recipes =
                RecipeReader.loadRecipes();

        System.out.println();
        System.out.println("========================================");
        System.out.println("             RECIPE FINDER");
        System.out.println("========================================");

        System.out.println(
                "Recipes loaded: " + recipes.size()
        );

        if (recipes.isEmpty()) {

            System.out.println();
            System.out.println("No recipes were loaded.");
            System.out.println(
                    "Check that data/recipes.txt exists."
            );

            scanner.close();
            return;
        }

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("1. Search Recipe");
            System.out.println("2. Exit");
            System.out.println("----------------------------------------");

            System.out.print("Enter your choice: ");

            String choice =
                    scanner.nextLine().trim();

            switch (choice) {

                case "1":

                    searchRecipes(
                            recipes,
                            scanner
                    );

                    break;

                case "2":

                    running = false;

                    System.out.println();
                    System.out.println(
                            "Thank you for using Recipe Finder!"
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please enter 1 or 2."
                    );
            }
        }

        scanner.close();
    }


    // =====================================================
    // RECIPE SEARCH
    // =====================================================

    private static void searchRecipes(
            ArrayList<Recipe> recipes,
            Scanner scanner) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           RECIPE SEARCH");
        System.out.println("========================================");

        System.out.print(
                "Enter recipe name: "
        );

        String query =
                scanner.nextLine().trim();

        if (query.isEmpty()) {

            System.out.println(
                    "Search query cannot be empty."
            );

            return;
        }

        System.out.println();
        System.out.println(
                "Searching recipes.txt..."
        );

        // =================================================
        // SELECT ALGORITHM
        // =================================================

        System.out.println();
        System.out.println(
                "Select String Matching Algorithm:"
        );

        System.out.println("1. KMP");
        System.out.println("2. Rabin-Karp");
        System.out.println("3. Z-Function");

        System.out.print(
                "Enter algorithm choice: "
        );

        String algorithmChoice =
                scanner.nextLine().trim();

        String algorithmName;

        if (algorithmChoice.equals("1")) {

            algorithmName = "KMP";

        } else if (algorithmChoice.equals("2")) {

            algorithmName = "Rabin-Karp";

        } else if (algorithmChoice.equals("3")) {

            algorithmName = "Z-Function";

        } else {

            System.out.println(
                    "Invalid algorithm choice."
            );

            return;
        }

        System.out.println();
        System.out.println("SEARCH SUMMARY");
        System.out.println("========================================");

        System.out.println(
                "Query           : " + query
        );

        System.out.println(
                "Algorithm       : " + algorithmName
        );

        int recordsChecked = 0;
        int matchesFound = 0;

        // =================================================
        // SEARCH EVERY RECIPE
        // =================================================

        for (Recipe recipe : recipes) {

            recordsChecked++;

            boolean match = false;

            // KMP
            if (algorithmChoice.equals("1")) {

                match =
                        StringAlgorithms.kmpSearch(
                                recipe.getRecipeName(),
                                query
                        );
            }

            // Rabin-Karp
            else if (algorithmChoice.equals("2")) {

                match =
                        StringAlgorithms.rabinKarpSearch(
                                recipe.getRecipeName(),
                                query
                        );
            }

            // Z-Function
            else if (algorithmChoice.equals("3")) {

                match =
                        StringAlgorithms.zFunctionSearch(
                                recipe.getRecipeName(),
                                query
                        );
            }

            if (match) {

                matchesFound++;

                System.out.println();
                System.out.println(
                        "Match found at Recipe ID: "
                                + recipe.getRecipeId()
                );

                // Display complete recipe
                recipe.displayRecipe();
            }
        }

        // =================================================
        // SEARCH RESULT
        // =================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("SEARCH RESULT");
        System.out.println("========================================");

        System.out.println(
                "Query           : " + query
        );

        System.out.println(
                "Algorithm       : " + algorithmName
        );

        System.out.println(
                "Records Checked : " + recordsChecked
        );

        System.out.println(
                "Matches Found   : " + matchesFound
        );

        System.out.println("========================================");

        if (matchesFound == 0) {

            System.out.println();
            System.out.println(
                    "No matching recipe found."
            );
        }
    }
}