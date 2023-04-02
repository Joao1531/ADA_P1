/**
 * @author Joao Lopes 60055
 * @author Jose Romano 59241
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Path {
    // constants

    /**
     * on each const with HAND on the name, the first int represents the index of that option in our two-dimensional
     * array, the second represents the cost to pass a plot with that item *when the heroes encounter a plot with a monster*
     */
    private static final int[] EMPTY_HAND = {0, 0};
    private static final int[] HARP_HAND = {1, 4};
    private static final int[] POTION_HAND = {2, 5};
    private static final int[] CLOAK_HAND = {3, 6};
    /**
     * on each const that has the type of monster represents the minimum time that takes to pass each one.
     */
    private static final int DOG = 4;
    private static final int TROLL = 5;
    private static final int DRAGON = 6;
    private static final int maxValue = Integer.MAX_VALUE;
    private final int numPaths; // number of paths to be tested
    private final ArrayList<Integer> testResults; // ArrayList that stores the minimum time to complete the route of each test.

    /**
     * This is the constructor of the Path class, which initializes the numPaths attribute
     * and creates an empty ArrayList of integers, with an initial capacity equal to numPath.
     *
     * @param numPaths The number of paths to be tested
     */
    public Path(int numPaths) {
        this.numPaths = numPaths;
        testResults = new ArrayList<>(numPaths);
    }
    //PUBLIC METHODS

    /**
     * Method that returns the ArrayList that contains the result of the minimum path cost
     * for each path submitted by the user
     *
     * @return testResults
     */
    public ArrayList<Integer> getTestResults() {
        return testResults;
    }

    /**
     * This method reads the input paths from a BufferedReader, creates a 2D array dp with constant size (4x2)
     * and then calls the calculateResults method to update the array for each input path.
     * The minimum time to cross the path is then obtained with the getMinTime method
     * and added to the testResults ArrayList.
     *
     * @param input buffered reader that will allow this class only in this method to receive inputs from the user
     * @throws IOException Exception
     */
    public void fillTestResults(BufferedReader input) throws IOException {
        for (int i = 0; i < numPaths; i++) {
            String route = input.readLine();
            int[][] dp = new int[4][2];
            calculateResults(route, dp);
            testResults.add(getMinTime(dp));

        }
    }

    //PRIVATE METHODS

    /**
     * Updates the dynamic programming table based on the heroes actions and the items and monsters encountered.
     *
     * @param route a string representing the path
     * @param dp    a constant two-dimensional integer array representing the dynamic programming table
     */

    private void calculateResults(String route, int[][] dp) {
        int strLength = route.length();
        for (int i = 0; i < strLength; i++) {
            char currPlot = route.charAt(i);
            switch (currPlot) {
                case 'e' -> {
                    updateEmptyHand(dp);
                    updateDP(dp, HARP_HAND);
                    updateDP(dp, POTION_HAND);
                    updateDP(dp, CLOAK_HAND);
                    updateCache(dp);
                }
                case 'h' -> {
                    updateEmptyHand(dp);
                    updateMatchingDP(dp, HARP_HAND);
                    updateDP(dp, POTION_HAND);
                    updateDP(dp, CLOAK_HAND);
                    updateCache(dp);

                }
                case 'p' -> {
                    updateEmptyHand(dp);
                    updateMatchingDP(dp, POTION_HAND);
                    updateDP(dp, HARP_HAND);
                    updateDP(dp, CLOAK_HAND);
                    updateCache(dp);

                }
                case 'c' -> {
                    updateEmptyHand(dp);
                    updateMatchingDP(dp, CLOAK_HAND);
                    updateDP(dp, POTION_HAND);
                    updateDP(dp, HARP_HAND);
                    updateCache(dp);

                }
                case '3' -> {
                    passMonster(dp, DOG);
                    updateCache(dp);

                }

                case 't' -> {
                    passMonster(dp, TROLL);
                    updateCache(dp);

                }
                case 'd' -> {
                    passMonster(dp, DRAGON);
                    updateCache(dp);

                }

                default -> {
                }
            }
        }
    }

    /**
     * Method responsible for updating the dynamic programming table when the heroes have anything
     * on their hands
     *
     * @param dp a constant two-dimensional integer array representing the dynamic programming table
     */
    private void updateEmptyHand(int[][] dp) {
        int emptyHand = dp[0][0];
        if (emptyHand != maxValue) {
            dp[EMPTY_HAND[0]][1] = emptyHand + 1;
        } else {
            dp[EMPTY_HAND[0]][1] = getMinTime(dp) + 2;
        }

    }

    /**
     * Method responsible for updating the dynamic programming table for the given item the player could be holding
     *
     * @param dp     a constant two-dimensional integer array representing the dynamic programming table
     * @param object this integer represents the object that Harry and Ron would have on their hands, this object integer
     *               follows the format of the constants explained in the beginning of the code
     */
    private void updateDP(int[][] dp, int[] object) {
        int previousValue = dp[object[0]][0];
            if (previousValue != 0 && previousValue != maxValue) {
                dp[object[0]][1] = previousValue + 3;
            } else
                dp[object[0]][1] = maxValue;
    }

    /**
     * This method updates the dynamic programming 2D integer array dp when the item in the plot is the same as the one Ron and Harry might be holding.
     *
     * @param dp     a constant two-dimensional integer array representing the dynamic programming table
     * @param object index of the object that the heroes may have in hand
     */
    private void updateMatchingDP(int[][] dp, int[] object) {
        int firstValue = dp[0][0];
        if (firstValue == getMinTime(dp)) {
            dp[object[0]][1] = getMinTime(dp) + 2;
        } else {
            dp[object[0]][1] = getMinTime(dp) + 3;
        }
    }

    /**
     * Method that specifies the situation the heroes are facing and redirects them to the right case, depending on the item they are holding.
     *
     * @param dp      a constant two-dimensional integer array representing the dynamic programming table
     * @param monster the monster that the heroes are facing in the plot
     */
    private void passMonster(int[][] dp, int monster) {
        for (int i = 0; i < dp.length; i++) {
            if (i == EMPTY_HAND[0]) updateMonsterDP(dp, EMPTY_HAND, monster);
            else if (i == HARP_HAND[0]) updateMonsterDP(dp, HARP_HAND, monster);
            else if (i == POTION_HAND[0]) updateMonsterDP(dp, POTION_HAND, monster);
            else if (i == CLOAK_HAND[0]) updateMonsterDP(dp, CLOAK_HAND, monster);
        }
    }

    /**
     * Method that, when the hero encounters a monster, will update the value in our dynamic programming array
     * the time it takes to pass the plot with the given object vs the monster.
     *
     * @param dp     a constant two-dimensional integer array representing the dynamic programming table
     * @param object index of the object that the heroes may have in hand
     */
    private void updateMonsterDP(int[][] dp, int[] object, int monster) {
        int previousValue = dp[object[0]][0];
        if (previousValue != maxValue && object[1] >= monster) {
            dp[object[0]][1] = previousValue + object[1];
        } else
            dp[object[0]][1] = maxValue;

    }

    /**
     * This method simply updates the first collumn of the matrix, used as the cache of the values we had the plot before, and then resets the collumn
     * regarding the present plot to zero.
     *
     * @param dp a constant two-dimensional integer array representing the dynamic programming table
     */
    private void updateCache(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = dp[i][1];
            dp[i][1] = 0;
        }
    }

    /**
     * Returns the minimum value in the specified column of the input 2D array.
     *
     * @param dp a constant two-dimensional integer array representing the dynamic programming table
     * @return the minimum value in the specified column of the 2D array - in this case, the minimum value of passing the whole route.
     */
    private int getMinTime(int[][] dp) {
        int minTime = dp[0][0];
        for (int i = 1; i < dp.length; i++) {
            if (dp[i][0] < minTime) {
                minTime = dp[i][0];
            }
        }
        return minTime;
    }


}
