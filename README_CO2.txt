RECIPE FINDER – CO2 IMPLEMENTATION

Phase 1 was extended with three CO2 string-pattern matching algorithms.

FILES
-----
src/Main.java
    Menu, user input, algorithm selection, search and console output.

src/Recipe.java
    Recipe data model.

src/RecipeReader.java
    Reads recipes.csv and creates Recipe objects.

src/StringAlgorithms.java
    Contains all three CO2 algorithms:
    1. KMP
    2. Rabin-Karp with rolling hash
    3. Z-Function

data/recipes.csv
    Existing Phase 1 recipe dataset.

WHERE EACH ALGORITHM IS USED
----------------------------
1. KMP
   Main.java calls:
   StringAlgorithms.kmpContains(recipe.getRecipeName(), query)

   Purpose:
   Recipe-name / keyword pattern matching.

2. Rabin-Karp
   Main.java calls:
   StringAlgorithms.rabinKarpContains(recipe.getSearchableText(), query)

   Purpose:
   Ingredient / keyword-style searching.

   IMPORTANT:
   The current Phase 1 CSV contains RecipeID, RecipeName, Cuisine,
   Category, CookingTime, Budget and Rating. It does NOT contain an
   Ingredients column. Therefore, the current implementation searches
   the available recipe text (name + cuisine + category), not actual
   ingredient lists.

   When an Ingredients column/dataset is added later, getSearchableText()
   can be extended to include it.

3. Z-Function
   Main.java calls:
   StringAlgorithms.zFunctionContains(recipe.getRecipeName(), query)

   Purpose:
   Additional substring/pattern detection.

HOW TO RUN
----------
From the RecipeFinder project root:

javac -d bin src/*.java

java -cp bin Main

The project expects:

RecipeFinder/
├── data/
│   └── recipes.csv
├── src/
│   ├── Main.java
│   ├── Recipe.java
│   ├── RecipeReader.java
│   └── StringAlgorithms.java
└── bin/

The existing CSV must have this seven-column structure:

RecipeID,RecipeName,Cuisine,Category,CookingTime,Budget,Rating
