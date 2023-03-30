/**
 * @authors: Joao Lopes 60055, Jose Romano 59241
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Path {

    /**
     * on each const with HAND on the name, the first int represents the index of that option in our two-dimensional
     * array, the second represents the cost to pass a plot with that item *when the heroes encounter a plot with a monster*
     */
    private static final int[] EMPTY_HAND = {1, 0};
    private static final int[] HARP_HAND = {2, 4};
    private static final int[] POTION_HAND = {3, 5};
    private static final int[] CLOAK_HAND = {4, 6};

    private static final int DOG = 4;
    private static final int TROLL = 5;
    private static final int DRAGON = 6;


    private static final int maxValue = Integer.MAX_VALUE;

    private final int numPath;
    private final ArrayList<Integer> testResults;

    /**
     * This is the constructor of the Path class, which initializes the numPath attribute
     * and creates an empty ArrayList of integers, with an initial capacity equal to numPath.
     *
     * @param numPath
     */

    public Path(int numPath) {
        this.numPath = numPath;
        testResults = new ArrayList<Integer>(numPath);
    }

    //PUBLIC METHODS

    /**
     * Method that returns the ArrayList that contains the result of the minimum path cost
     * for each path inputed by the user
     *
     * @return testResults
     */
    public ArrayList<Integer> getTestResults() {
        return testResults;
    }

    /**
     * This method reads the input paths from a BufferedReader, creates a 2D array dp,
     * fills it with the maximum integer value, and then calls the calculateResults method
     * to update the array for each input path. The minimum time to cross the path is then
     * obtained with the getMinTime method and added to the testResults ArrayList.
     *
     * @param in buffered reader that will allow this class only in this method to receive inputs from the user
     * @throws IOException Exception
     */
    public void fillTestResults(BufferedReader in) throws IOException {
        for (int i = 0; i < numPath; i++) {
            String route = in.readLine();
            int n = route.length();
            int[][] dp = new int[5][n + 1];

            calculateResults(route, n, dp);
            testResults.add(getMinTime(dp, n));
        }
    }

    //PRIVATE METHODS

    /**
     * Updates the dynamic programming table based on the heroes actions and the items and monsters encountered.
     *
     * @param route a string representing the path
     * @param n     an integer representing the length of the path
     * @param dp    a two-dimensional integer array representing the dynamic programming table
     */
    private void calculateResults(String route, int n, int[][] dp) {
        for (int i = 1; i <= n; i++) {

            //current plot that heroes are currently in
            char currPlot = route.charAt(i - 1);

            // Iterate over the possible items that the player could be holding
            for (int j = 1; j < 5; j++) {
                // Handle the current plot based on its type
                switch (currPlot) {
                    // empty Easy-plot
                    case 'e' -> {
                        // Update the dynamic programming table for an empty hand
                        updateEmptyHandDP(dp, i);
                        // Update the dynamic programming table for each item the player could be holding
                        updateDP(dp, HARP_HAND[0], i);
                        updateDP(dp, POTION_HAND[0], i);
                        updateDP(dp, CLOAK_HAND[0], i);
                    }

                    //Easy-plot with harp
                    case 'h' -> {
                        // Update the dynamic programming table for an empty hand
                        updateEmptyHandDP(dp, i);
                        // Update the dynamic programming table for a matching harp in the hand
                        updateMatchingDP(dp, HARP_HAND[0], i);
                        // Update the dynamic programming table for each item the player could be holding
                        updateDP(dp, POTION_HAND[0], i);
                        updateDP(dp, CLOAK_HAND[0], i);
                    }

                    //Easy-plot with potion
                    case 'p' -> {
                        // Update the dynamic programming table for an empty hand
                        updateEmptyHandDP(dp, i);
                        // Update the dynamic programming table for a matching potion in the hand
                        updateMatchingDP(dp, POTION_HAND[0], i);
                        // Update the dynamic programming table for each item the player could be holding
                        updateDP(dp, HARP_HAND[0], i);
                        updateDP(dp, CLOAK_HAND[0], i);
                    }

                    //Easy-plot with cloak
                    case 'c' -> {
                        // Update the dynamic programming table for an empty hand
                        updateEmptyHandDP(dp, i);
                        // Update the dynamic programming table for a matching cloak in the hand
                        updateMatchingDP(dp, CLOAK_HAND[0], i);
                        // Update the dynamic programming table for each item the player could be holding
                        updateDP(dp, HARP_HAND[0], i);
                        updateDP(dp, POTION_HAND[0], i);
                    }

                    //Monster plot with a three-headed dog
                    case '3' -> passMonster(j, i, dp, DOG);

                    //Monster plot with a Troll
                    case 't' -> passMonster(j, i, dp, TROLL);


                    //Monster plot with a dragon
                    case 'd' -> passMonster(j, i, dp, DRAGON);
                    default -> {
                    }
                }
            }
        }
    }


    /**
     * Method responsible for updating the dynamic programming table when the heroes doesn't have anything
     * on their hands
     *
     * @param dp    a two-dimensional integer array representing the dynamic programming table
     * @param index the index of the column
     */
    private void updateEmptyHandDP(int[][] dp, int index) {
        if (dp[EMPTY_HAND[0]][index - 1] != maxValue)
            dp[EMPTY_HAND[0]][index] = dp[EMPTY_HAND[0]][index - 1] + 1;

            //get the minimum and ADD 1 because they enter the plot with and item and drop it
        else
            dp[EMPTY_HAND[0]][index] = getMinTime(dp, index - 1) + 2;
    }

    /**
     * Method responsible for updating the dynamic programming table for the given item the player could be holding
     *
     * @param dp     a two-dimensional integer array representing the dynamic programming table
     * @param object this integer represents the object that Harry and Ron would have on their hands, this object integer
     *               follows the format of the constants explained in the beginning of the code
     * @param index  the index of the column
     */
    private void updateDP(int[][] dp, int object, int index) {
        if (dp[object][index - 1] != maxValue && dp[object][index - 1] != 0)
            dp[object][index] = dp[object][index - 1] + 3;
        else
            dp[object][index] = maxValue;
    }


    /**
     * This method updates the dynamic programming 2D integer array dp with the given object and index.
     *
     * @param dp     a two-dimensional integer array representing the dynamic programming table
     * @param object index of the object that the heroes may have in hand
     * @param index  the index of the column
     */
    private void updateMatchingDP(int[][] dp, int object, int index) {
        if (dp[EMPTY_HAND[0]][index - 1] == getMinTime(dp, index - 1))
            dp[object][index] = getMinTime(dp, index - 1) + 2;
        else
            dp[object][index] = getMinTime(dp, index - 1) + 3;
    }

    /**
     * Method that, when the hero encounters a monster, will update the value in our dynamic programming array
     * the time it takes to pass the plot with the given object vs the monster.
     *
     * @param dp     a two-dimensional integer array representing the dynamic programming table
     * @param object index of the object that the heroes may have in hand
     * @param index  the index of the column
     */
    private void updateMonsterDP(int[][] dp, int[] object, int index, int monster) {
        if (dp[object[0]][index - 1] != maxValue && object[1] >= monster)
            dp[object[0]][index] = dp[object[0]][index - 1] + object[1];
        else
            dp[object[0]][index] = maxValue;

    }

    /**
     * Returns the minimum value in the specified column of the input 2D array.
     *
     * @param dp    a two-dimensional integer array representing the dynamic programming table
     * @param index the index of the column to search for the minimum value
     * @return the minimum value in the specified column of the 2D array
     */

    private int getMinTime(int[][] dp, int index) {
        int minTime = dp[1][index];
        for (int i = 1; i < dp.length; i++) {
            if (dp[i][index] < minTime) {
                minTime = dp[i][index];
            }
        }
        return minTime;
    }

    /**
     * This method updates the 2D integer array dp with the cost value of passing a specific monster,
     * based on the item the player has in hand, characterized by the int j.
     *
     * @param j       the index of the item in the player's hand
     * @param index   the index of the column to search for the minimum value
     * @param dp      a two-dimensional integer array representing the dynamic programming table
     * @param monster the cost it takes to pass the monster
     */
    private void passMonster(int j, int index, int[][] dp, int monster) {
        if (j == EMPTY_HAND[0]) updateMonsterDP(dp, EMPTY_HAND, index, monster);
        else if (j == HARP_HAND[0]) updateMonsterDP(dp, HARP_HAND, index, monster);
        else if (j == POTION_HAND[0]) updateMonsterDP(dp, POTION_HAND, index, monster);
        else if (j == CLOAK_HAND[0]) updateMonsterDP(dp, CLOAK_HAND, index, monster);
    }
}
