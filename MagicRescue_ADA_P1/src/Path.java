import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Path {
    private static int[] EMPTY_HAND = {0, 0};
    private static int[] HARP_HAND = {1, 4};
    private static int[] POTION_HAND = {2, 5};
    private static int[] CLOAK_HAND = {3, 6};

    private static int[] CACHE;


    private static final int DOG = 4;
    private static final int TROLL = 5;
    private static final int DRAGON = 6;
    private static final int maxValue = Integer.MAX_VALUE;

    private static int minValue;


    private final int numPaths;

    private final ArrayList<Integer> testResults;

    public Path(int numPaths) {
        this.numPaths = numPaths;
        testResults = new ArrayList<Integer>(numPaths);
    }

    public ArrayList<Integer> getTestResults() {
        return testResults;
    }

    public void fillTestResults(BufferedReader in) throws IOException {
        for (int i = 0; i < numPaths; i++) {
            String route = in.readLine();
            int[][] dp = new int[4][1];
            CACHE = new int[]{0, 0, 0, 0};
            minValue = 0;
            calculateResults(route, dp);

            testResults.add(minValue);

        }
    }

    public void calculateResults(String route, int[][] dp) {
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
        minValue = Arrays.stream(CACHE).min().getAsInt();
    }

    public void updateEmptyHand(int[][] dp) {
        if (CACHE[0] != maxValue) {
            dp[EMPTY_HAND[0]][0] = CACHE[0] + 1;
        } else {
            minValue = Arrays.stream(CACHE).min().getAsInt();
            dp[EMPTY_HAND[0]][0] = minValue + 2;
        }

    }

    public void updateDP(int[][] dp, int[] object) {
        for (int i = 1; i < dp.length; i++) {
            if (dp[object[0]][0] != 0 && dp[object[0]][0] != maxValue) {
                dp[object[0]][0] = CACHE[object[0]] + 3;
            } else
                dp[object[0]][0] = maxValue;
        }
    }

    private void updateMatchingDP(int[][] dp, int[] object) {
        //System.out.println(dp[EMPTY_HAND[0]][0]);
        //System.out.println(minCol);
        minValue = Arrays.stream(CACHE).min().getAsInt();
        //System.out.println("MIN " + minValue);
        if (CACHE[0] == minValue) {
            dp[object[0]][0] = minValue + 2;
        } else {
            dp[object[0]][0] = minValue + 3;
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
        if (CACHE[object[0]] != maxValue && object[1] >= monster) {
            dp[object[0]][0] = CACHE[object[0]] + object[1];
        } else
            dp[object[0]][0] = maxValue;

    }

    private void updateCache(int[][] dp) {
        for (int i = 0; i < dp.length; i++)
            CACHE[i] = dp[i][0];

    }


}
