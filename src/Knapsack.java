import java.util.ArrayList;
import java.util.Scanner;

public class Knapsack {

    // =========================================================
    // CO5 - KNAPSACK FPTAS
    //
    // COST and VALUE are read from each recipe TXT record when
    // COST: and VALUE: fields are present.
    // The user enters only the maximum budget/capacity.
    //
    // Backward compatibility:
    // If an old TXT file does not contain COST/VALUE, the program
    // asks for those missing values at runtime instead of failing.
    // =========================================================

    public static void run(ArrayList<Recipe> recipes, Scanner scanner) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       CO5 - KNAPSACK FPTAS");
        System.out.println("========================================");

        System.out.print("Enter recipe search query: ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Search query cannot be empty.");
            return;
        }

        ArrayList<Recipe> candidates = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (StringAlgorithms.kmpSearch(recipe.getRecipeName(), query)) {
                candidates.add(recipe);
            }
        }

        if (candidates.isEmpty()) {
            System.out.println();
            System.out.println("No matching recipes found.");
            System.out.println("Try another recipe name.");
            return;
        }

        System.out.println();
        System.out.println("Matching Recipes:");
        System.out.println("----------------------------------------");

        for (int i = 0; i < candidates.size(); i++) {
            Recipe recipe = candidates.get(i);
            System.out.println((i + 1) + ". " + recipe.getRecipeName()
                    + " (ID: " + recipe.getRecipeId() + ")");
        }

        System.out.println("----------------------------------------");
        System.out.print("Enter the number of recipes to consider (1-"
                + candidates.size() + "): ");

        int count = readInt(scanner, 1, candidates.size());
        ArrayList<Recipe> selectedCandidates = new ArrayList<>();

        System.out.println();
        System.out.println("Enter the recipe numbers you want to optimize.");

        for (int i = 0; i < count; i++) {
            System.out.print("Recipe " + (i + 1) + " number: ");
            int recipeNumber = readInt(scanner, 1, candidates.size());
            Recipe recipe = candidates.get(recipeNumber - 1);

            if (!selectedCandidates.contains(recipe)) {
                selectedCandidates.add(recipe);
            } else {
                System.out.println("Recipe already selected. Choose another recipe.");
                i--;
            }
        }

        System.out.print("Enter maximum budget/capacity: ");
        int capacity = readInt(scanner, 0, Integer.MAX_VALUE);

        int n = selectedCandidates.size();
        int[] costs = new int[n];
        int[] values = new int[n];

        boolean missingData = false;
        for (Recipe recipe : selectedCandidates) {
            if (!recipe.hasKnapsackData()) {
                missingData = true;
                break;
            }
        }

        if (missingData) {
            System.out.println();
            System.out.println("Some selected recipes do not have COST/VALUE in the TXT file.");
            System.out.println("To use automatic Knapsack data, add these fields to each recipe:");
            System.out.println("COST: <non-negative integer>");
            System.out.println("VALUE: <non-negative integer>");
            System.out.println();
            System.out.println("For backward compatibility, enter the missing values below.");
        } else {
            System.out.println();
            System.out.println("Cost and Value loaded from the TXT files:");
        }

        for (int i = 0; i < n; i++) {
            Recipe recipe = selectedCandidates.get(i);

            System.out.println();
            System.out.println((i + 1) + ". " + recipe.getRecipeName());

            if (recipe.getCost() >= 0) {
                costs[i] = recipe.getCost();
                System.out.println("Cost : " + costs[i] + " (from TXT)");
            } else {
                System.out.print("Cost: ");
                costs[i] = readInt(scanner, 0, Integer.MAX_VALUE);
            }

            if (recipe.getValue() >= 0) {
                values[i] = recipe.getValue();
                System.out.println("Value: " + values[i] + " (from TXT)");
            } else {
                System.out.print("Value: ");
                values[i] = readInt(scanner, 0, Integer.MAX_VALUE);
            }
        }

        Result result = solveFPTAS(selectedCandidates, costs, values, capacity);

        displayResult(selectedCandidates, costs, values, capacity, result);
    }

    // =========================================================
    // FPTAS
    // =========================================================

    private static Result solveFPTAS(
            ArrayList<Recipe> recipes,
            int[] costs,
            int[] values,
            int capacity) {

        int n = recipes.size();
        int maxValue = 0;

        for (int value : values) {
            maxValue = Math.max(maxValue, value);
        }

        if (n == 0 || maxValue == 0 || capacity == 0) {
            return new Result(new ArrayList<>(), 0, 0);
        }

        double epsilon = 0.25;
        double scalingFactor = (epsilon * maxValue) / n;
        if (scalingFactor < 1.0) {
            scalingFactor = 1.0;
        }

        int[] scaledValues = new int[n];
        for (int i = 0; i < n; i++) {
            scaledValues[i] = (int) Math.floor(values[i] / scalingFactor);
        }

        int totalScaledValue = 0;
        for (int value : scaledValues) {
            totalScaledValue += value;
        }

        final int INF = Integer.MAX_VALUE / 4;
        int[][] dp = new int[n + 1][totalScaledValue + 1];

        for (int i = 0; i <= n; i++) {
            for (int v = 0; v <= totalScaledValue; v++) {
                dp[i][v] = INF;
            }
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            int itemValue = scaledValues[i - 1];
            int itemCost = costs[i - 1];

            for (int v = 0; v <= totalScaledValue; v++) {
                dp[i][v] = dp[i - 1][v];

                if (v >= itemValue
                        && dp[i - 1][v - itemValue] != INF) {

                    long candidateCost = (long) dp[i - 1][v - itemValue] + itemCost;
                    if (candidateCost < dp[i][v]) {
                        dp[i][v] = candidateCost > INF ? INF : (int) candidateCost;
                    }
                }
            }
        }

        int bestScaledValue = 0;
        for (int v = 0; v <= totalScaledValue; v++) {
            if (dp[n][v] <= capacity) {
                bestScaledValue = v;
            }
        }

        ArrayList<Recipe> chosen = new ArrayList<>();
        int currentValue = bestScaledValue;

        for (int i = n; i >= 1; i--) {
            if (dp[i][currentValue] != dp[i - 1][currentValue]) {
                chosen.add(recipes.get(i - 1));
                currentValue -= scaledValues[i - 1];
            }
        }

        int totalCost = 0;
        int totalValue = 0;

        for (Recipe recipe : chosen) {
            int index = recipes.indexOf(recipe);
            totalCost += costs[index];
            totalValue += values[index];
        }

        return new Result(chosen, totalCost, totalValue);
    }

    // =========================================================
    // RESULT DISPLAY
    // =========================================================

    private static void displayResult(
            ArrayList<Recipe> recipes,
            int[] costs,
            int[] values,
            int capacity,
            Result result) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          FPTAS RESULT");
        System.out.println("========================================");
        System.out.println("Maximum Budget : " + capacity);
        System.out.println();
        System.out.println("Selected Recipes:");
        System.out.println("----------------------------------------");

        if (result.selectedRecipes.isEmpty()) {
            System.out.println("No recipe combination fits the budget.");
        } else {
            for (Recipe recipe : result.selectedRecipes) {
                int index = recipes.indexOf(recipe);
                System.out.println(recipe.getRecipeName()
                        + " (ID: " + recipe.getRecipeId() + ")");
                System.out.println("   Cost  : " + costs[index]);
                System.out.println("   Value : " + values[index]);
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("Total Cost       : " + result.totalCost);
        System.out.println("Total Value      : " + result.totalValue);
        System.out.println("Budget Remaining : " + (capacity - result.totalCost));
        System.out.println("Algorithm        : Knapsack FPTAS");
        System.out.println("Approximation ε  : 0.25");
        System.out.println("========================================");
    }

    private static int readInt(Scanner scanner, int min, int max) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Ask again below.
            }

            System.out.print("Enter a valid integer ("
                    + min + "-" + max + "): ");
        }
    }

    private static class Result {
        private final ArrayList<Recipe> selectedRecipes;
        private final int totalCost;
        private final int totalValue;

        private Result(ArrayList<Recipe> selectedRecipes,
                       int totalCost,
                       int totalValue) {
            this.selectedRecipes = selectedRecipes;
            this.totalCost = totalCost;
            this.totalValue = totalValue;
        }
    }
}
