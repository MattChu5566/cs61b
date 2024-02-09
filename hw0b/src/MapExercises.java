import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /**
     * Returns a map from every lower case letter to the number corresponding to
     * that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> resultMap = new TreeMap<>();
        int i = 1;
        for (char c = 'a'; c <= 'z'; c++) {
            resultMap.put(c, i);
            i++;
        }
        return resultMap;
    }

    /**
     * Returns a map from the integers in the list to their squares. For example, if
     * the input list
     * is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to
     * 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> squareMap = new TreeMap<>();
        for (int i : nums) {
            squareMap.put(i, i * i);
        }
        return squareMap;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> countMap = new TreeMap<>();
        for (String s : words) {
            if (countMap.get(s) == null) {
                countMap.put(s, 1);
            } else {
                int count = countMap.get(s);
                countMap.put(s, ++count);
            }
        }
        return countMap;
    }
}
