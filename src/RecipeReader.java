import java.io.*;
import java.util.ArrayList;

public class RecipeReader {

    public static ArrayList<Recipe> loadRecipes(String filePath) {

        ArrayList<Recipe> recipes = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            String line;

            // Skip CSV header
            br.readLine();

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length < 7) {
                    System.out.println("Skipping invalid CSV row: " + line);
                    continue;
                }

                int recipeId = Integer.parseInt(data[0].trim());
                String recipeName = data[1].trim();
                String cuisine = data[2].trim();
                String category = data[3].trim();
                int cookingTime = Integer.parseInt(data[4].trim());
                double budget = Double.parseDouble(data[5].trim());
                double rating = Double.parseDouble(data[6].trim());

                recipes.add(new Recipe(
                        recipeId,
                        recipeName,
                        cuisine,
                        category,
                        cookingTime,
                        budget,
                        rating
                ));
            }

        } catch (IOException e) {
            System.out.println("Error reading: " + filePath);
            System.out.println("Make sure the CSV file is inside the data folder.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric value found in recipes.csv.");
        }

        return recipes;
    }
}
