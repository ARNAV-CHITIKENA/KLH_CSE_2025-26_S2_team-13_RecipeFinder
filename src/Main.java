import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // =====================================================
        // LOAD RECIPES
        // =====================================================

        System.out.println("========================================");
        System.out.println("             RECIPE QUEST");
        System.out.println("========================================");

        ArrayList<Recipe> recipes =
                RecipeReader.loadRecipes();

        if (recipes.isEmpty()) {

            System.out.println();
            System.out.println("No recipes were loaded.");
            System.out.println("Please check the data folder.");

            scanner.close();
            return;
        }

        // =====================================================
        // MAIN MENU
        // =====================================================

        while (true) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("              MAIN MENU");
            System.out.println("========================================");
            System.out.println("1. Search Recipe");
            System.out.println("2. Max Flow");
            System.out.println("3. Exit");
            System.out.println("========================================");

            System.out.print("Enter your choice: ");

            String choice =
                    scanner.nextLine().trim();


            // =================================================
            // SEARCH RECIPE
            // =================================================

            if (choice.equals("1")) {

                System.out.println();
                System.out.println("========================================");
                System.out.println("           RECIPE SEARCH");
                System.out.println("========================================");

                System.out.print("Enter recipe name: ");

                String query =
                        scanner.nextLine().trim();

                if (query.isEmpty()) {

                    System.out.println();
                    System.out.println(
                            "Search query cannot be empty."
                    );

                    continue;
                }


                // =============================================
                // SEARCH ALGORITHM MENU
                // =============================================

                System.out.println();
                System.out.println("========================================");
                System.out.println("        SELECT SEARCH ALGORITHM");
                System.out.println("========================================");
                System.out.println("1. KMP");
                System.out.println("2. Rabin-Karp");
                System.out.println("3. Z-Function");
                System.out.println("4. Edit Distance");
                System.out.println("5. Compare All Algorithms");
                System.out.println("6. Back to Main Menu");
                System.out.println("========================================");

                System.out.print("Enter algorithm choice: ");

                String algorithmChoice =
                        scanner.nextLine().trim();


                // =============================================
                // KMP
                // =============================================

                if (algorithmChoice.equals("1")) {

                    runKMP(recipes, query);
                }


                // =============================================
                // RABIN-KARP
                // =============================================

                else if (algorithmChoice.equals("2")) {

                    runRabinKarp(recipes, query);
                }


                // =============================================
                // Z-FUNCTION
                // =============================================

                else if (algorithmChoice.equals("3")) {

                    runZFunction(recipes, query);
                }


                // =============================================
                // EDIT DISTANCE
                // =============================================

                else if (algorithmChoice.equals("4")) {

                    runEditDistance(recipes, query);
                }


                // =============================================
                // COMPARE ALL ALGORITHMS
                // =============================================

                else if (algorithmChoice.equals("5")) {

                    compareAllAlgorithms(recipes, query);
                }


                // =============================================
                // BACK TO MAIN MENU
                // =============================================

                else if (algorithmChoice.equals("6")) {

                    continue;
                }


                else {

                    System.out.println();
                    System.out.println(
                            "Invalid algorithm choice."
                    );
                }
            }


            // =================================================
            // MAX FLOW
            // =================================================

            else if (choice.equals("2")) {

                runMaxFlow();
            }


            // =================================================
            // EXIT
            // =================================================

            else if (choice.equals("3")) {

                System.out.println();
                System.out.println("========================================");
                System.out.println(
                        "       Thank you for using Recipe Quest!"
                );
                System.out.println("========================================");

                break;
            }


            else {

                System.out.println();
                System.out.println(
                        "Invalid choice. Please try again."
                );
            }
        }

        scanner.close();
    }


    // =========================================================
    // KMP SEARCH
    // =========================================================

    private static void runKMP(
            ArrayList<Recipe> recipes,
            String query) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             KMP SEARCH");
        System.out.println("========================================");

        System.out.println(
                "Search Query: " + query
        );

        System.out.println(
                "Searching recipes..."
        );

        int recordsChecked = 0;
        int matchesFound = 0;

        long startTime =
                System.nanoTime();

        for (Recipe recipe : recipes) {

            recordsChecked++;

            if (StringAlgorithms.kmpSearch(
                    recipe.getRecipeName(),
                    query)) {

                matchesFound++;

                System.out.println();
                System.out.println(
                        "Match found at Recipe ID: "
                                + recipe.getRecipeId()
                );

                recipe.displayRecipe();

                System.out.println(
                        "----------------------------------------"
                );
            }
        }

        long endTime =
                System.nanoTime();

        long executionTime =
                endTime - startTime;

        printResult(
                "KMP RESULT",
                recordsChecked,
                matchesFound,
                executionTime
        );
    }


    // =========================================================
    // RABIN-KARP SEARCH
    // =========================================================

    private static void runRabinKarp(
            ArrayList<Recipe> recipes,
            String query) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          RABIN-KARP SEARCH");
        System.out.println("========================================");

        System.out.println(
                "Search Query: " + query
        );

        System.out.println(
                "Searching recipes..."
        );

        int recordsChecked = 0;
        int matchesFound = 0;

        long startTime =
                System.nanoTime();

        for (Recipe recipe : recipes) {

            recordsChecked++;

            if (StringAlgorithms.rabinKarpSearch(
                    recipe.getRecipeName(),
                    query)) {

                matchesFound++;

                System.out.println();
                System.out.println(
                        "Match found at Recipe ID: "
                                + recipe.getRecipeId()
                );

                recipe.displayRecipe();

                System.out.println(
                        "----------------------------------------"
                );
            }
        }

        long endTime =
                System.nanoTime();

        long executionTime =
                endTime - startTime;

        printResult(
                "RABIN-KARP RESULT",
                recordsChecked,
                matchesFound,
                executionTime
        );
    }


    // =========================================================
    // Z-FUNCTION SEARCH
    // =========================================================

    private static void runZFunction(
            ArrayList<Recipe> recipes,
            String query) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           Z-FUNCTION SEARCH");
        System.out.println("========================================");

        System.out.println(
                "Search Query: " + query
        );

        System.out.println(
                "Searching recipes..."
        );

        int recordsChecked = 0;
        int matchesFound = 0;

        long startTime =
                System.nanoTime();

        for (Recipe recipe : recipes) {

            recordsChecked++;

            if (StringAlgorithms.zFunctionSearch(
                    recipe.getRecipeName(),
                    query)) {

                matchesFound++;

                System.out.println();
                System.out.println(
                        "Match found at Recipe ID: "
                                + recipe.getRecipeId()
                );

                recipe.displayRecipe();

                System.out.println(
                        "----------------------------------------"
                );
            }
        }

        long endTime =
                System.nanoTime();

        long executionTime =
                endTime - startTime;

        printResult(
                "Z-FUNCTION RESULT",
                recordsChecked,
                matchesFound,
                executionTime
        );
    }


    // =========================================================
    // EDIT DISTANCE
    // =========================================================

    private static void runEditDistance(
            ArrayList<Recipe> recipes,
            String query) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("            EDIT DISTANCE");
        System.out.println("========================================");

        System.out.println(
                "Search Query: " + query
        );

        System.out.println(
                "Finding closest recipe name..."
        );

        int recordsChecked = 0;

        int minimumDistance =
                Integer.MAX_VALUE;

        Recipe closestRecipe = null;

        long startTime =
                System.nanoTime();

        for (Recipe recipe : recipes) {

            recordsChecked++;

            int distance =
                    StringAlgorithms.editDistance(
                            query,
                            recipe.getRecipeName()
                    );

            if (distance < minimumDistance) {

                minimumDistance = distance;
                closestRecipe = recipe;
            }
        }

        long endTime =
                System.nanoTime();

        long executionTime =
                endTime - startTime;


        System.out.println();
        System.out.println("========================================");
        System.out.println("          EDIT DISTANCE RESULT");
        System.out.println("========================================");

        System.out.println(
                "Records Checked : "
                        + recordsChecked
        );

        if (closestRecipe != null) {

            System.out.println(
                    "Closest Recipe  : "
                            + closestRecipe.getRecipeName()
            );

            System.out.println(
                    "Recipe ID       : "
                            + closestRecipe.getRecipeId()
            );

            System.out.println(
                    "Edit Distance   : "
                            + minimumDistance
            );

        } else {

            System.out.println(
                    "No recipe available."
            );
        }

        System.out.println(
                "Execution Time  : "
                        + executionTime
                        + " ns"
        );

        System.out.println(
                "Execution Time  : "
                        + (executionTime / 1_000_000.0)
                        + " ms"
        );

        System.out.println(
                "========================================"
        );
    }


    // =========================================================
    // COMPARE ALL ALGORITHMS
    // =========================================================

    private static void compareAllAlgorithms(
            ArrayList<Recipe> recipes,
            String query) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       STRING ALGORITHM COMPARISON");
        System.out.println("========================================");

        System.out.println(
                "Search Query: " + query
        );

        System.out.println(
                "Total Recipes: "
                        + recipes.size()
        );


        // =====================================================
        // KMP
        // =====================================================

        int kmpRecordsChecked = 0;
        int kmpMatchesFound = 0;

        ArrayList<Recipe> kmpMatches =
                new ArrayList<>();

        long kmpStartTime =
                System.nanoTime();

        for (Recipe recipe : recipes) {

            kmpRecordsChecked++;

            if (StringAlgorithms.kmpSearch(
                    recipe.getRecipeName(),
                    query)) {

                kmpMatchesFound++;
                kmpMatches.add(recipe);
            }
        }

        long kmpEndTime =
                System.nanoTime();

        long kmpTime =
                kmpEndTime - kmpStartTime;


        // =====================================================
        // RABIN-KARP
        // =====================================================

        int rkRecordsChecked = 0;
        int rkMatchesFound = 0;

        long rkStartTime =
                System.nanoTime();

        for (Recipe recipe : recipes) {

            rkRecordsChecked++;

            if (StringAlgorithms.rabinKarpSearch(
                    recipe.getRecipeName(),
                    query)) {

                rkMatchesFound++;
            }
        }

        long rkEndTime =
                System.nanoTime();

        long rkTime =
                rkEndTime - rkStartTime;


        // =====================================================
        // Z-FUNCTION
        // =====================================================

        int zRecordsChecked = 0;
        int zMatchesFound = 0;

        long zStartTime =
                System.nanoTime();

        for (Recipe recipe : recipes) {

            zRecordsChecked++;

            if (StringAlgorithms.zFunctionSearch(
                    recipe.getRecipeName(),
                    query)) {

                zMatchesFound++;
            }
        }

        long zEndTime =
                System.nanoTime();

        long zTime =
                zEndTime - zStartTime;


        // =====================================================
        // EDIT DISTANCE
        // =====================================================

        int editRecordsChecked = 0;

        int minimumDistance =
                Integer.MAX_VALUE;

        Recipe closestRecipe = null;

        long editStartTime =
                System.nanoTime();

        for (Recipe recipe : recipes) {

            editRecordsChecked++;

            int distance =
                    StringAlgorithms.editDistance(
                            query,
                            recipe.getRecipeName()
                    );

            if (distance < minimumDistance) {

                minimumDistance = distance;
                closestRecipe = recipe;
            }
        }

        long editEndTime =
                System.nanoTime();

        long editTime =
                editEndTime - editStartTime;


        // =====================================================
        // KMP RESULT
        // =====================================================

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("KMP");
        System.out.println("----------------------------------------");

        System.out.println(
                "Records Checked : "
                        + kmpRecordsChecked
        );

        System.out.println(
                "Matches Found   : "
                        + kmpMatchesFound
        );

        System.out.println(
                "Execution Time  : "
                        + kmpTime
                        + " ns"
        );

        System.out.println(
                "Execution Time  : "
                        + (kmpTime / 1_000_000.0)
                        + " ms"
        );


        // =====================================================
        // RABIN-KARP RESULT
        // =====================================================

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("RABIN-KARP");
        System.out.println("----------------------------------------");

        System.out.println(
                "Records Checked : "
                        + rkRecordsChecked
        );

        System.out.println(
                "Matches Found   : "
                        + rkMatchesFound
        );

        System.out.println(
                "Execution Time  : "
                        + rkTime
                        + " ns"
        );

        System.out.println(
                "Execution Time  : "
                        + (rkTime / 1_000_000.0)
                        + " ms"
        );


        // =====================================================
        // Z-FUNCTION RESULT
        // =====================================================

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("Z-FUNCTION");
        System.out.println("----------------------------------------");

        System.out.println(
                "Records Checked : "
                        + zRecordsChecked
        );

        System.out.println(
                "Matches Found   : "
                        + zMatchesFound
        );

        System.out.println(
                "Execution Time  : "
                        + zTime
                        + " ns"
        );

        System.out.println(
                "Execution Time  : "
                        + (zTime / 1_000_000.0)
                        + " ms"
        );


        // =====================================================
        // EDIT DISTANCE RESULT
        // =====================================================

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("EDIT DISTANCE");
        System.out.println("----------------------------------------");

        System.out.println(
                "Records Checked : "
                        + editRecordsChecked
        );

        if (closestRecipe != null) {

            System.out.println(
                    "Closest Recipe  : "
                            + closestRecipe.getRecipeName()
            );

            System.out.println(
                    "Recipe ID       : "
                            + closestRecipe.getRecipeId()
            );

            System.out.println(
                    "Edit Distance   : "
                            + minimumDistance
            );
        }

        System.out.println(
                "Execution Time  : "
                        + editTime
                        + " ns"
        );

        System.out.println(
                "Execution Time  : "
                        + (editTime / 1_000_000.0)
                        + " ms"
        );


        // =====================================================
        // MATCHING RECIPES
        // =====================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("          MATCHING RECIPES");
        System.out.println("========================================");

        if (kmpMatches.isEmpty()) {

            System.out.println(
                    "No exact/substring recipe name match found."
            );

        } else {

            for (Recipe recipe : kmpMatches) {

                System.out.println();
                System.out.println(
                        "Match found at Recipe ID: "
                                + recipe.getRecipeId()
                );

                recipe.displayRecipe();

                System.out.println(
                        "----------------------------------------"
                );
            }
        }


        // =====================================================
        // SEARCH SUMMARY
        // =====================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("             SEARCH SUMMARY");
        System.out.println("========================================");

        System.out.println(
                "Search Query       : "
                        + query
        );

        System.out.println(
                "Total Recipes      : "
                        + recipes.size()
        );

        System.out.println(
                "KMP Matches        : "
                        + kmpMatchesFound
        );

        System.out.println(
                "Rabin-Karp Matches : "
                        + rkMatchesFound
        );

        System.out.println(
                "Z-Function Matches : "
                        + zMatchesFound
        );

        if (closestRecipe != null) {

            System.out.println(
                    "Closest Recipe     : "
                            + closestRecipe.getRecipeName()
            );

            System.out.println(
                    "Edit Distance      : "
                            + minimumDistance
            );
        }

        System.out.println(
                "========================================"
        );
    }


    // =========================================================
    // COMMON RESULT DISPLAY
    // =========================================================

    private static void printResult(
            String title,
            int recordsChecked,
            int matchesFound,
            long executionTime) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          " + title);
        System.out.println("========================================");

        System.out.println(
                "Records Checked : "
                        + recordsChecked
        );

        System.out.println(
                "Matches Found   : "
                        + matchesFound
        );

        System.out.println(
                "Execution Time  : "
                        + executionTime
                        + " ns"
        );

        System.out.println(
                "Execution Time  : "
                        + (executionTime / 1_000_000.0)
                        + " ms"
        );

        if (matchesFound == 0) {

            System.out.println();
            System.out.println(
                    "No matching recipe found."
            );
        }

        System.out.println(
                "========================================"
        );
    }


    // =========================================================
    // MAX FLOW DEMONSTRATION
    // =========================================================

    private static void runMaxFlow() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        RECIPE SUPPLY NETWORK");
        System.out.println("========================================");

        /*
         * Node representation:
         *
         * 0 = SOURCE
         * 1 = VEGETABLES
         * 2 = MEAT
         * 3 = SPICES
         * 4 = RICE
         * 5 = RECIPE
         * 6 = SINK
         */

        int vertices = 7;

        int source = 0;
        int sink = 6;


        // =====================================================
        // CREATE CAPACITY GRAPH
        // =====================================================

        int[][] capacity =
                new int[vertices][vertices];


        // =====================================================
        // SOURCE -> INGREDIENTS
        // =====================================================

        capacity[0][1] = 10;
        capacity[0][2] = 15;
        capacity[0][3] = 10;
        capacity[0][4] = 8;


        // =====================================================
        // INGREDIENTS -> RECIPE
        // =====================================================

        capacity[1][5] = 7;
        capacity[2][5] = 10;
        capacity[3][5] = 6;
        capacity[4][5] = 5;


        // =====================================================
        // RECIPE -> SINK
        // =====================================================

        capacity[5][6] = 20;


        // =====================================================
        // DISPLAY NETWORK
        // =====================================================

        System.out.println();

        System.out.println(
                "SOURCE -> VEGETABLES : 10"
        );

        System.out.println(
                "SOURCE -> MEAT       : 15"
        );

        System.out.println(
                "SOURCE -> SPICES     : 10"
        );

        System.out.println(
                "SOURCE -> RICE       : 8"
        );

        System.out.println();

        System.out.println(
                "VEGETABLES -> RECIPE : 7"
        );

        System.out.println(
                "MEAT -> RECIPE       : 10"
        );

        System.out.println(
                "SPICES -> RECIPE     : 6"
        );

        System.out.println(
                "RICE -> RECIPE       : 5"
        );

        System.out.println();

        System.out.println(
                "RECIPE -> SINK       : 20"
        );


        // =====================================================
        // START TIMER
        // =====================================================

        long startTime =
                System.nanoTime();


        // =====================================================
        // RUN EDMONDS-KARP MAX FLOW
        // =====================================================

        int maxFlow =
                MaxFlow.calculateMaxFlow(
                        capacity,
                        source,
                        sink
                );


        // =====================================================
        // END TIMER
        // =====================================================

        long endTime =
                System.nanoTime();

        long executionTime =
                endTime - startTime;


        // =====================================================
        // FINAL MAX FLOW SUMMARY
        // =====================================================

        System.out.println();

        System.out.println(
                "========================================"
        );

        System.out.println(
                "          MAX FLOW SUMMARY"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Algorithm        : Edmonds-Karp"
        );

        System.out.println(
                "Maximum Flow     : "
                        + maxFlow
        );

        System.out.println(
                "Execution Time   : "
                        + executionTime
                        + " ns"
        );

        System.out.println(
                "Execution Time   : "
                        + (executionTime / 1_000_000.0)
                        + " ms"
        );

        System.out.println(
                "========================================"
        );
    }
}