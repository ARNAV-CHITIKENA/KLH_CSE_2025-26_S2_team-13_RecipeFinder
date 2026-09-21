import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class RecipeReader {

    // =========================================================
    // LOAD ALL RECIPES
    // =========================================================

    public static ArrayList<Recipe> loadRecipes() {

        ArrayList<Recipe> recipes =
                new ArrayList<>();

        File dataFolder =
                new File("data");

        System.out.println();
        System.out.println("========================================");
        System.out.println("          LOADING RECIPE DATA");
        System.out.println("========================================");

        if (!dataFolder.exists()
                || !dataFolder.isDirectory()) {

            System.out.println();
            System.out.println(
                    "Data folder not found: "
                            + dataFolder.getAbsolutePath()
            );

            return recipes;
        }


        // =====================================================
        // FIND ALL RECIPE TEXT FILES
        // =====================================================

        File[] files =
                dataFolder.listFiles(
                        (directory, filename) -> {

                            String lowerName =
                                    filename.toLowerCase();

                            return lowerName.startsWith("recipes_")
                                    && lowerName.endsWith(".txt");
                        }
                );


        if (files == null || files.length == 0) {

            System.out.println();
            System.out.println(
                    "No recipe text files found in data folder."
            );

            return recipes;
        }


        // =====================================================
        // SORT FILES
        // =====================================================

        Arrays.sort(
                files,
                Comparator.comparing(File::getName)
        );


        // =====================================================
        // READ EACH FILE
        // =====================================================

        for (File file : files) {

            System.out.println(
                    "Reading: "
                            + file.getName()
            );

            readFile(
                    file,
                    recipes
            );
        }


        // =====================================================
        // FINAL RESULT
        // =====================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println(
                "Total recipes loaded: "
                        + recipes.size()
        );
        System.out.println("========================================");

        return recipes;
    }


    // =========================================================
    // READ ONE FILE
    // =========================================================

    private static void readFile(
            File file,
            ArrayList<Recipe> recipes) {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            String line;

            int recipeId = 0;
            String recipeName = "";
            String cuisine = "";
            String category = "";

            ArrayList<String> steps =
                    new ArrayList<>();


            while ((line = reader.readLine()) != null) {

                line = line.trim();


                // =================================================
                // NEW RECIPE
                // =================================================

                if (line.startsWith("RECIPE ID:")) {

                    // Save previous recipe
                    if (recipeId != 0
                            && !recipeName.isEmpty()) {

                        recipes.add(
                                new Recipe(
                                        recipeId,
                                        recipeName,
                                        cuisine,
                                        category,
                                        new ArrayList<>(steps)
                                )
                        );
                    }


                    // Reset values
                    recipeId = 0;
                    recipeName = "";
                    cuisine = "";
                    category = "";

                    steps.clear();


                    // Read recipe ID
                    try {

                        String idText =
                                line.substring(
                                        "RECIPE ID:".length()
                                ).trim();

                        recipeId =
                                Integer.parseInt(idText);

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Invalid Recipe ID: "
                                        + line
                        );
                    }
                }


                // =================================================
                // RECIPE NAME
                // =================================================

                else if (line.startsWith("RECIPE NAME:")) {

                    recipeName =
                            line.substring(
                                    "RECIPE NAME:".length()
                            ).trim();
                }


                // =================================================
                // REGION / CUISINE
                // =================================================

                else if (
                        line.startsWith(
                                "REGION / CUISINE:"
                        )
                ) {

                    cuisine =
                            line.substring(
                                    "REGION / CUISINE:".length()
                            ).trim();
                }


                // =================================================
                // CATEGORY
                // =================================================

                else if (line.startsWith("CATEGORY:")) {

                    category =
                            line.substring(
                                    "CATEGORY:".length()
                            ).trim();
                }


                // =================================================
                // PREPARATION STEPS
                // =================================================

                else if (
                        line.startsWith(
                                "PREPARATION STEPS:"
                        )
                ) {

                    // Preparation steps follow
                    continue;
                }


                // =================================================
                // RECIPE STEP
                // =================================================

                else if (
                        line.matches(
                                "\\d+\\.\\s+.*"
                        )
                ) {

                    steps.add(line);
                }


                // =================================================
                // STOP READING PROJECT DESCRIPTION
                // =================================================

                else if (
                        line.startsWith(
                                "TEXT FILE STRUCTURE FOR JAVA IMPLEMENTATION"
                        )
                ) {

                    break;
                }
            }


            // =====================================================
            // SAVE LAST RECIPE
            // =====================================================

            if (recipeId != 0
                    && !recipeName.isEmpty()) {

                recipes.add(
                        new Recipe(
                                recipeId,
                                recipeName,
                                cuisine,
                                category,
                                new ArrayList<>(steps)
                        )
                );
            }

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Error reading file: "
                            + file.getName()
            );

            System.out.println(
                    "Reason: "
                            + e.getMessage()
            );
        }
    }
}   