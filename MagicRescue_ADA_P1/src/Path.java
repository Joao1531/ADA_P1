import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Path {
    private static final int[] EMPTY_HAND = {0, 0};
    private static final int[] HARP_HAND = {1, 4};
    private static final int[] POTION_HAND = {2, 5};
    private static final int[] CLOAK_HAND = {3, 6};
    private static final int DOG = 4;
    private static final int TROLL = 5;
    private static final int DRAGON = 6;
    private static final int maxValue = Integer.MAX_VALUE;
    private final int numPaths;
    private final ArrayList<Integer> testResults;

    public Path(int numPaths) {
        this.numPaths = numPaths;
        testResults = new ArrayList<>(numPaths);
    }

    public ArrayList<Integer> getTestResults() {
        return testResults;
    }

    public void fillTestResults(BufferedReader in) throws IOException {
        for (int i = 0; i < numPaths; i++) {
            String route = in.readLine();
            int[][] dp = new int[4][2];
            calculateResults(route, dp);
            testResults.add(getMinTime(dp));

        }
    }

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

    private void updateEmptyHand(int[][] dp) {
        int emptyHand = dp[0][0];
        if (emptyHand != maxValue) {
            dp[EMPTY_HAND[0]][1] = emptyHand + 1;
        } else {
            dp[EMPTY_HAND[0]][1] = getMinTime(dp) + 2;
        }

    }

    private void updateDP(int[][] dp, int[] object) {
        int previousValue = dp[object[0]][0];
        for (int i = 1; i < dp.length; i++) {
            if (previousValue != 0 && previousValue != maxValue) {
                dp[object[0]][1] = previousValue + 3;
            } else
                dp[object[0]][1] = maxValue;
        }
    }

    private void updateMatchingDP(int[][] dp, int[] object) {
        int firstValue = dp[0][0];
        if (firstValue == getMinTime(dp)) {
            dp[object[0]][1] = getMinTime(dp) + 2;
        } else {
            dp[object[0]][1] = getMinTime(dp) + 3;
        }
    }

    private void passMonster(int[][] dp, int monster) {
        for (int i = 0; i < dp.length; i++) {
            if (i == EMPTY_HAND[0]) updateMonsterDP(dp, EMPTY_HAND, monster);
            else if (i == HARP_HAND[0]) updateMonsterDP(dp, HARP_HAND, monster);
            else if (i == POTION_HAND[0]) updateMonsterDP(dp, POTION_HAND, monster);
            else if (i == CLOAK_HAND[0]) updateMonsterDP(dp, CLOAK_HAND, monster);
        }
    }

    private void updateMonsterDP(int[][] dp, int[] object, int monster) {
        int previousValue = dp[object[0]][0];
        if (previousValue!= maxValue && object[1] >= monster) {
            dp[object[0]][1] = previousValue + object[1];
        } else
            dp[object[0]][1] = maxValue;

    }

    private void updateCache(int[][] dp) {
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = dp[i][1];
            dp[i][1] = 0;
        }
    }

    private int getMinTime(int[][]dp){
        int minTime = dp[0][0];
        for (int i = 1; i < dp.length; i++) {
            if (dp[i][0] < minTime) {
                minTime = dp[i][0];
            }
        }
        return minTime;
    }




}
