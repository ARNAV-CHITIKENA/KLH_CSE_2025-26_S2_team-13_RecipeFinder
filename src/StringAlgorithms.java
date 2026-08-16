public class StringAlgorithms {

    /*
     * CO2 ALGORITHM 1: KMP
     * Used for recipe-name / keyword pattern matching.
     *
     * Returns true when the pattern occurs in the text.
     */
    public static boolean kmpContains(String text, String pattern) {

        if (text == null || pattern == null) {
            return false;
        }

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        if (pattern.isEmpty()) {
            return true;
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

            } else if (j > 0) {
                j = lps[j - 1];

            } else {
                i++;
            }
        }

        return false;
    }

    /*
     * LPS = Longest Prefix Suffix.
     * This is the preprocessing step used by KMP.
     */
    private static int[] buildLPS(String pattern) {

        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;

            } else if (length > 0) {
                length = lps[length - 1];

            } else {
                lps[i] = 0;
                i++;
            }
        }

        return lps;
    }

    /*
     * CO2 ALGORITHM 2: RABIN-KARP
     * Uses rolling hash for keyword searching.
     *
     * The hash match is followed by direct verification to avoid
     * false positives caused by hash collisions.
     */
    public static boolean rabinKarpContains(String text, String pattern) {

        if (text == null || pattern == null) {
            return false;
        }

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            return true;
        }

        if (m > n) {
            return false;
        }

        final long BASE = 256;
        final long MOD = 1_000_000_007L;

        long patternHash = 0;
        long windowHash = 0;
        long highestPower = 1;

        for (int i = 0; i < m - 1; i++) {
            highestPower = (highestPower * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {
            patternHash =
                    (patternHash * BASE + pattern.charAt(i)) % MOD;

            windowHash =
                    (windowHash * BASE + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {

            if (patternHash == windowHash) {

                boolean match = true;

                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    return true;
                }
            }

            if (i < n - m) {

                windowHash =
                        (windowHash
                                - text.charAt(i) * highestPower) % MOD;

                if (windowHash < 0) {
                    windowHash += MOD;
                }

                windowHash =
                        (windowHash * BASE
                                + text.charAt(i + m)) % MOD;
            }
        }

        return false;
    }

    /*
     * CO2 ALGORITHM 3: Z-FUNCTION
     * Searches for a pattern by building:
     *
     * pattern + separator + text
     *
     * If a Z-value equals the pattern length, the pattern occurs.
     */
    public static boolean zFunctionContains(String text, String pattern) {

        if (text == null || pattern == null) {
            return false;
        }

        text = text.toLowerCase();
        pattern = pattern.toLowerCase();

        if (pattern.isEmpty()) {
            return true;
        }

        // Use a separator that is not expected in recipe text.
        String combined = pattern + "\u0000" + text;

        int[] z = buildZArray(combined);

        for (int i = pattern.length() + 1; i < combined.length(); i++) {
            if (z[i] == pattern.length()) {
                return true;
            }
        }

        return false;
    }

    private static int[] buildZArray(String s) {

        int n = s.length();
        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {

            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            while (i + z[i] < n &&
                   s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }

            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }

        return z;
    }
}
