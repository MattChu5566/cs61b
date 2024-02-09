import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        int[] dice = { 1, 2, 3, 4, 5, 6 };
        return dice;
    }

    /**
     * Returns the order depending on the customer.
     * If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     * If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     * In any other case, return an empty String[] of size 3.
     */
    public static String[] takeOrder(String customer) {
        String[] ErgunOrder = { "beyti", "pizza", "hamburger", "tea" };
        String[] ErikOrder = { "sushi", "pasta", "avocado", "coffee" };
        String[] emptyOrder = { "", "", "" };
        if (customer.equals("Ergun"))
            return ErgunOrder;
        if (customer.equals("Erik"))
            return ErikOrder;
        return emptyOrder;
    }

    /**
     * Returns the positive difference between the maximum element and minimum
     * element of the given array.
     * Assumes array is nonempty.
     */
    public static int findMinMax(int[] array) {
        int min = array[0];
        int max = array[0];
        for (int i : array) {
            if (i < min)
                min = i;
            if (i > max)
                max = i;
        }
        return max - min;
    }

    /**
     * Uses recursion to compute the hailstone sequence as a list of integers
     * starting from an input number n.
     * Hailstone sequence is described as:
     * - Pick a positive integer n as the start
     * - If n is even, divide n by 2
     * - If n is odd, multiply n by 3 and add 1
     * - Continue this process until n is 1
     */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        list.add(x);
        int result;
        if (x % 2 == 0) {
            result = x / 2;
            return hailstoneHelper(result, list);
        } else if (x == 1) {
            return list;
        } else {
            result = 3 * x + 1;
            return hailstoneHelper(result, list);
        }
    }

}
