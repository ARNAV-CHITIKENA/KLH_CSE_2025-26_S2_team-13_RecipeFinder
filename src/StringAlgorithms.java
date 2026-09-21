public class StringAlgorithms {

    // =========================================================
    // NORMALIZE TEXT
    // =========================================================

    private static String normalize(String text) {

        if (text == null) {
            return "";
        }

        return text.toLowerCase().trim();
    }


    // =========================================================
    // KMP - KNUTH MORRIS PRATT
    // =========================================================

    public static boolean kmpSearch(String text, String pattern) {

        text = normalize(text);
        pattern = normalize(pattern);

        if (pattern.isEmpty()) {
            return true;
        }

        if (text.isEmpty()) {
            return false;
        }

        if (pattern.length() > text.length()) {
            return false;
        }

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {

                i++;
                j++;

                if (j == pattern.length()) {
                    return true;
                }

            } else {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return false;
    }


    // =========================================================
    // BUILD LPS ARRAY FOR KMP
    // =========================================================

    private static int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(length)) {

                length++;
                lps[i] = length;
                i++;

            } else {

                if (length != 0) {

                    length = lps[length - 1];

                } else {

                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }


    // =========================================================
    // RABIN-KARP
    // =========================================================

    public static boolean rabinKarpSearch(
            String text,
            String pattern) {

        text = normalize(text);
        pattern = normalize(pattern);

        if (pattern.isEmpty()) {
            return true;
        }

        if (text.isEmpty()) {
            return false;
        }

        int n = text.length();
        int m = pattern.length();

        if (m > n) {
            return false;
        }

        final int BASE = 256;
        final long PRIME = 1000000007L;

        long patternHash = 0;
        long textHash = 0;

        long highestPower = 1;

        // BASE^(m-1)
        for (int i = 0; i < m - 1; i++) {

            highestPower =
                    (highestPower * BASE) % PRIME;
        }

        // Calculate initial hash
        for (int i = 0; i < m; i++) {

            patternHash =
                    (BASE * patternHash
                            + pattern.charAt(i))
                            % PRIME;

            textHash =
                    (BASE * textHash
                            + text.charAt(i))
                            % PRIME;
        }

        // Slide the pattern across the text
        for (int i = 0; i <= n - m; i++) {

            // Hash values are equal
            if (patternHash == textHash) {

                boolean match = true;

                // Verify characters
                for (int j = 0; j < m; j++) {

                    if (text.charAt(i + j)
                            != pattern.charAt(j)) {

                        match = false;
                        break;
                    }
                }

                if (match) {
                    return true;
                }
            }

            // Calculate next window hash
            if (i < n - m) {

                textHash =
                        (BASE *
                                (textHash
                                        - (text.charAt(i)
                                        * highestPower) % PRIME
                                        + PRIME)
                                + text.charAt(i + m))
                                % PRIME;
            }
        }

        return false;
    }


    // =========================================================
    // Z-FUNCTION SEARCH
    // =========================================================

    public static boolean zFunctionSearch(
            String text,
            String pattern) {

        text = normalize(text);
        pattern = normalize(pattern);

        if (pattern.isEmpty()) {
            return true;
        }

        if (text.isEmpty()) {
            return false;
        }

        String combined =
                pattern + "#" + text;

        int[] z = calculateZ(combined);

        int patternLength = pattern.length();

        for (int i = 0; i < z.length; i++) {

            if (z[i] == patternLength) {
                return true;
            }
        }

        return false;
    }


    // =========================================================
    // CALCULATE Z ARRAY
    // =========================================================

    private static int[] calculateZ(String text) {

        int n = text.length();

        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {

            if (i <= right) {

                z[i] =
                        Math.min(
                                right - i + 1,
                                z[i - left]
                        );
            }

            while (
                    i + z[i] < n &&
                    text.charAt(z[i])
                            == text.charAt(i + z[i])
            ) {

                z[i]++;
            }

            if (i + z[i] - 1 > right) {

                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }


    // =========================================================
    // EDIT DISTANCE
    // WAGNER-FISCHER DYNAMIC PROGRAMMING
    // =========================================================

    public static int editDistance(
            String first,
            String second) {

        first = normalize(first);
        second = normalize(second);

        int m = first.length();
        int n = second.length();

        // DP table
        int[][] dp = new int[m + 1][n + 1];

        // Convert first string to empty string
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // Convert empty string to second string
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Fill DP table
        for (int i = 1; i <= m; i++) {

            for (int j = 1; j <= n; j++) {

                if (first.charAt(i - 1)
                        == second.charAt(j - 1)) {

                    dp[i][j] =
                            dp[i - 1][j - 1];

                } else {

                    int insertion =
                            dp[i][j - 1];

                    int deletion =
                            dp[i - 1][j];

                    int substitution =
                            dp[i - 1][j - 1];

                    dp[i][j] =
                            1 + Math.min(
                                    insertion,
                                    Math.min(
                                            deletion,
                                            substitution
                                    )
                            );
                }
            }
        }

        return dp[m][n];
    }
}