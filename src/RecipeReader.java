import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class RecipeReader {

    public static ArrayList<Recipe> loadRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();
        File dataFolder = new File("data");

        System.out.println();
        System.out.println("========================================");
        System.out.println("          LOADING RECIPE DATA");
        System.out.println("========================================");

        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            System.out.println();
            System.out.println("Data folder not found: " + dataFolder.getAbsolutePath());
            return recipes;
        }

        File[] files = dataFolder.listFiles((directory, filename) -> {
            String lowerName = filename.toLowerCase();
            return lowerName.startsWith("recipes_") && lowerName.endsWith(".txt");
        });

        if (files == null || files.length == 0) {
            System.out.println();
            System.out.println("No recipe text files found in data folder.");
            return recipes;
        }

        Arrays.sort(files, Comparator.comparing(File::getName));

        for (File file : files) {
            System.out.println("Reading: " + file.getName());
            readFile(file, recipes);
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("Total recipes loaded: " + recipes.size());
        System.out.println("========================================");

        return recipes;
    }

    private static void readFile(File file, ArrayList<Recipe> recipes) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            int recipeId = 0;
            String recipeName = "";
            String cuisine = "";
            String category = "";
            int cost = -1;
            int value = -1;
            ArrayList<String> steps = new ArrayList<>();

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (line.startsWith("RECIPE ID:")) {

                    addRecipeIfValid(recipes, recipeId, recipeName, cuisine,
                            category, steps, cost, value);

                    recipeId = 0;
                    recipeName = "";
                    cuisine = "";
                    category = "";
                    cost = -1;
                    value = -1;
                    steps.clear();

                    try {
                        String idText = line.substring("RECIPE ID:".length()).trim();
                        recipeId = Integer.parseInt(idText);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Recipe ID: " + line);
                    }
                }

                else if (line.startsWith("RECIPE NAME:")) {
                    recipeName = line.substring("RECIPE NAME:".length()).trim();
                }

                else if (line.startsWith("REGION / CUISINE:")) {
                    cuisine = line.substring("REGION / CUISINE:".length()).trim();
                }

                else if (line.startsWith("CATEGORY:")) {
                    category = line.substring("CATEGORY:".length()).trim();
                }

                else if (line.startsWith("COST:")) {
                    cost = parseNonNegativeInteger(line.substring("COST:".length()).trim(), "COST");
                }

                else if (line.startsWith("VALUE:")) {
                    value = parseNonNegativeInteger(line.substring("VALUE:".length()).trim(), "VALUE");
                }

                else if (line.startsWith("PREPARATION STEPS:")) {
                    continue;
                }

                else if (line.matches("\\d+\\.\\s+.*")) {
                    steps.add(line);
                }

                else if (line.startsWith("TEXT FILE STRUCTURE FOR JAVA IMPLEMENTATION")) {
                    break;
                }
            }

            addRecipeIfValid(recipes, recipeId, recipeName, cuisine,
                    category, steps, cost, value);

        } catch (Exception e) {
            System.out.println();
            System.out.println("Error reading file: " + file.getName());
            System.out.println("Reason: " + e.getMessage());
        }
    }

    private static int parseNonNegativeInteger(String text, String fieldName) {
        try {
            int number = Integer.parseInt(text);
            if (number >= 0) {
                return number;
            }
            System.out.println("Invalid " + fieldName + ": value cannot be negative.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid " + fieldName + ": " + text);
        }
        return -1;
    }

    private static void addRecipeIfValid(
            ArrayList<Recipe> recipes,
            int recipeId,
            String recipeName,
            String cuisine,
            String category,
            ArrayList<String> steps,
            int cost,
            int value) {

        if (recipeId != 0 && !recipeName.isEmpty()) {
            recipes.add(new Recipe(
                    recipeId,
                    recipeName,
                    cuisine,
                    category,
                    new ArrayList<>(steps),
                    cost,
                    value));
        }
    }
}
