# Recipe Finder --- Text-Based Recipe Search System

## 1. Project Overview

**Recipe Finder** is a Java-based text-file recipe search system
developed to demonstrate **file handling, data structures, string
processing, and pattern-matching algorithms**.

The system stores **150 recipes** in three plain-text data files and
allows users to search recipe names and complete recipe descriptions,
including preparation steps.

The project is designed as a **console-based Java application** and
focuses particularly on efficient text searching using classical
string-matching algorithms.

------------------------------------------------------------------------

## 2. Project Objectives

-   Store and manage 150 recipe records using plain-text files.
-   Read and parse structured recipe information using Java file
    handling.
-   Search recipes by name and recipe text.
-   Implement and compare classical string-matching algorithms.
-   Demonstrate the practical use of **KMP, Rabin-Karp, and Z-Function**
    pattern matching.
-   Support multi-pattern searching.
-   Maintain an organized and scalable project structure.
-   Provide a simple console interface for recipe retrieval.

------------------------------------------------------------------------

## 3. Dataset

The project contains **150 recipes**, divided into three files:

``` text
data/
├── recipes_01_to_50.txt
├── recipes_51_to_100.txt
└── recipes_101_to_150.txt
```

  File                         Recipe IDs   Number of Recipes
  -------------------------- ------------ -------------------
  `recipes_01_to_50.txt`            1--50                  50
  `recipes_51_to_100.txt`         51--100                  50
  `recipes_101_to_150.txt`       101--150                  50
  **Total**                    **1--150**             **150**

Each recipe contains:

-   Recipe ID
-   Recipe Name
-   Region / Cuisine
-   Category
-   10 ordered preparation steps

### Example record format

``` text
RECIPE ID: 1
RECIPE NAME: Hyderabadi Chicken Biryani
REGION / CUISINE: Telangana / Hyderabad
CATEGORY: Main Course

PREPARATION STEPS:
1. Wash and soak basmati rice for about 20 minutes.
2. Marinate chicken...
...
10. Gently mix and serve hot.
```

------------------------------------------------------------------------

## 4. Algorithms Used

### 4.1 KMP --- Knuth-Morris-Pratt Algorithm

**Purpose:** Exact pattern matching in recipe text.

KMP preprocesses the search pattern and constructs an **LPS (Longest
Proper Prefix which is also a Suffix)** array.

Instead of repeatedly comparing characters after a mismatch, KMP uses
the LPS information to determine where the next comparison should begin.

**Time Complexity:**

-   Preprocessing: `O(m)`
-   Searching: `O(n)`
-   Overall: `O(n + m)`

where:

-   `n` = length of text
-   `m` = length of search pattern

**Application in Recipe Finder:**

KMP can search for a user-entered keyword such as:

``` text
biryani
```

inside recipe names and preparation text.

------------------------------------------------------------------------

### 4.2 Rabin-Karp Algorithm

**Purpose:** Pattern matching using hashing.

Rabin-Karp calculates a hash value for the search pattern and compares
it with rolling hash values of text windows.

When hash values match, the characters are verified to avoid false
matches caused by hash collisions.

**Average Time Complexity:**

-   Approximately `O(n + m)` for typical cases

**Worst Case:**

-   `O(nm)`

**Application in Recipe Finder:**

Rabin-Karp is useful for searching a keyword across multiple recipe
records and can be extended naturally for multiple-pattern searching.

------------------------------------------------------------------------

### 4.3 Z-Function / Z-Algorithm

**Purpose:** Linear-time exact pattern matching.

The Z-Algorithm constructs a **Z-array**, where `Z[i]` represents the
length of the substring starting at position `i` that matches the prefix
of the combined string.

For pattern searching, the strings can be combined as:

``` text
Pattern + "$" + Text
```

Occurrences of the pattern can then be identified from the Z-array.

**Time Complexity:**

``` text
O(n + m)
```

**Application in Recipe Finder:**

The algorithm can be used to locate exact occurrences of a search
pattern in recipe names or complete recipe text.

------------------------------------------------------------------------

### 4.4 Multi-Pattern Search

The project also considers **multi-pattern searching**, where multiple
keywords can be searched against recipe text.

Example:

``` text
["chicken", "rice", "spicy"]
```

The search system can identify recipes containing one or more of the
requested patterns.

Multi-pattern searching is useful when users want to find recipes based
on several ingredients or keywords.

------------------------------------------------------------------------

## 5. Algorithm Summary

  -----------------------------------------------------------------------
  Algorithm         Main Technique    Typical           Use in Project
                                      Complexity        
  ----------------- ----------------- ----------------- -----------------
  **KMP**           LPS / prefix      `O(n + m)`        Exact pattern
                    information                         search

  **Rabin-Karp**    Rolling hash      Average           Text/keyword
                                      `O(n + m)`        search

  **Z-Algorithm**   Z-array / prefix  `O(n + m)`        Exact pattern
                    matching                            search

  **Multi-Pattern   Search for        Depends on        Multiple keyword
  Search**          multiple keywords implementation    queries
  -----------------------------------------------------------------------

> **Note:** The complexity above describes the string-matching operation
> for a single text/pattern pair. Total runtime also depends on the
> number of recipes and the amount of recipe text searched.

------------------------------------------------------------------------

## 6. Data Structures Used

The application can organize parsed recipe records using Java
collections.

### Recipe Object

Each recipe is represented as a `Recipe` object containing:

``` text
recipeId
recipeName
regionCuisine
category
preparationSteps
```

### List of Recipes

A collection such as:

``` java
List<Recipe>
```

can store all 150 parsed recipe objects.

This allows the application to:

-   Iterate through recipes
-   Search recipe names
-   Search complete recipe text
-   Display matching recipes

------------------------------------------------------------------------

## 7. File Handling

The project uses **plain text files instead of CSV**.

`RecipeReader.java` is responsible for:

1.  Opening the recipe files.
2.  Reading the files line by line.
3.  Detecting recipe boundaries.
4.  Extracting recipe ID.
5.  Extracting recipe name.
6.  Extracting region/cuisine.
7.  Extracting category.
8.  Reading the ten preparation steps.
9.  Creating `Recipe` objects.
10. Adding the objects to the recipe collection.

The three input files should be loaded into the same recipe collection
so that the search system operates over all **150 recipes**.

------------------------------------------------------------------------

## 8. Project Structure

``` text
RecipeFinder/
│
├── data/
│   ├── recipes_01_to_50.txt
│   ├── recipes_51_to_100.txt
│   └── recipes_101_to_150.txt
│
├── src/
│   ├── Main.java
│   ├── Recipe.java
│   ├── RecipeReader.java
│   └── StringAlgorithms.java
│
└── README.md
```

### File Responsibilities

  File                      Responsibility
  ------------------------- ---------------------------------------------
  `Main.java`               Program entry point and console interaction
  `Recipe.java`             Recipe data model
  `RecipeReader.java`       Reading and parsing recipe files
  `StringAlgorithms.java`   String-matching algorithm implementations
  `data/*.txt`              Recipe dataset

------------------------------------------------------------------------

## 9. Search Workflow

``` text
User enters search keyword
          ↓
      Main.java
          ↓
Read/Search recipe collection
          ↓
Select string-matching algorithm
          ↓
KMP / Rabin-Karp / Z-Algorithm
          ↓
Compare keyword with recipe text
          ↓
Identify matching recipes
          ↓
Display recipe ID, name and details
```

------------------------------------------------------------------------

## 10. Search Scope

The search can operate on:

-   Recipe name
-   Region / cuisine
-   Category
-   Preparation steps
-   Complete recipe text

For example, searching:

``` text
chicken
```

can return recipes whose names or preparation text contain the term.

Searching:

``` text
rice
```

can identify recipes containing the term in their recipe information or
preparation steps.

------------------------------------------------------------------------

## 11. CO2 / String Matching Relevance

The project demonstrates the practical application of **string matching
algorithms** to a real-world text-search problem.

The main CO2 concepts demonstrated are:

-   Pattern matching
-   Exact string searching
-   Prefix-based matching
-   Hash-based matching
-   Multiple-pattern searching
-   Time-complexity comparison

The recipe name and complete recipe text are suitable inputs for
demonstrating these algorithms.

------------------------------------------------------------------------

## 12. Advantages of the Approach

-   Simple text-based storage.
-   Easy to inspect and modify recipe records.
-   No database dependency.
-   Clear separation between data, model, file reader, and algorithms.
-   Multiple string-matching algorithms can be compared on the same
    dataset.
-   The system can be extended with additional recipes or search
    methods.

------------------------------------------------------------------------

## 13. Limitations

-   Plain-text files provide less structured querying than a database.
-   Exact string matching may not handle spelling variations or synonyms
    automatically.
-   Search performance depends on the amount of recipe text and number
    of recipes.
-   The current dataset contains 150 recipes; larger datasets would
    require more scalable indexing/search techniques.

------------------------------------------------------------------------

## 14. Future Enhancements

Possible extensions include:

-   Ingredient-based filtering.
-   Cuisine/category filtering.
-   Case-insensitive search.
-   Partial and fuzzy matching.
-   Ranking recipes by relevance.
-   Trie-based prefix searching.
-   Aho-Corasick for large-scale multi-pattern matching.
-   Database integration.
-   Graphical or web-based user interface.

------------------------------------------------------------------------

## 15. Technologies Used

  Technology                 Purpose
  -------------------------- -------------------------------
  **Java**                   Application development
  **Java Collections**       Recipe storage and management
  **File I/O**               Reading recipe text files
  **KMP**                    Exact pattern matching
  **Rabin-Karp**             Hash-based pattern matching
  **Z-Algorithm**            Linear-time pattern matching
  **Multi-Pattern Search**   Multiple keyword searching
  **VS Code**                Development environment

------------------------------------------------------------------------

## 16. How to Run

### Step 1 --- Compile

From the project root:

``` bash
javac -d out src/*.java
```

### Step 2 --- Run

``` bash
java -cp out Main
```

> If your project uses a package declaration, use the corresponding
> package name in the compile and run commands.

------------------------------------------------------------------------

## 17. Important Implementation Note

The three recipe files are **data files only**. Splitting the dataset
does not change the string-matching algorithms.

If the existing `RecipeReader.java` currently reads only:

``` text
recipes.txt
```

it should be updated to read:

``` text
recipes_01_to_50.txt
recipes_51_to_100.txt
recipes_101_to_150.txt
```

and combine the parsed records into the same `List<Recipe>`.

`Recipe.java` and the core implementations in `StringAlgorithms.java`
generally do not need to change merely because the dataset has been
divided into three files.

------------------------------------------------------------------------

## 18. Project Summary

**Recipe Finder** demonstrates how classical string-matching techniques
can be applied to a practical text-search application.

The system manages **150 structured recipe records** stored across three
text files and provides a foundation for comparing:

**KMP → Rabin-Karp → Z-Algorithm → Multi-Pattern Search**

The project combines **Java file handling, object-oriented design,
collections, and algorithmic string processing** in a single
application.

------------------------------------------------------------------------

## 19. Academic Statement

This project is intended as an academic demonstration of data
structures, file processing, and string-matching algorithms. The
reported algorithmic complexities refer to the underlying
pattern-matching operations and should be interpreted together with the
cost of reading and scanning the complete recipe collection.
