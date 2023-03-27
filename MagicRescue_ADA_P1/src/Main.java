import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    private static final int[] EMPTY_HAND = {1, Integer.MAX_VALUE};
    private static final int[] HARP_HAND = {2, 4};
    private static final int[] POTION_HAND = {3, 5};
    private static final int[] CLOAK_HAND = {4, 6};


    public static void main(String[] args) throws IOException {


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int tests = Integer.parseInt(in.readLine());


        int maxValue = Integer.MAX_VALUE; // Preencher a matriz com o maior numero possivel.

        ArrayList<Integer> testResults = new ArrayList<>();
        printResults(in,testResults,tests,maxValue);

    }

    private static void printResults(BufferedReader in,ArrayList<Integer> testResults,int tests,int maxValue ) throws IOException {

        for ( int i = 0; i < tests ; i++){
            String route = in.readLine();
            int n = route.length();
            int[][] dp = new int[5][n + 1];
            fillDP(dp, maxValue);
            calculateResults(route,n,dp,maxValue);
            testResults.add(getMinTime(dp, n));
        }
        for (int i : testResults)
            System.out.println(i);
    }

    private static void calculateResults(String route,int n,int [][] dp,int maxValue ){
        for (int i = 1; i <= n; i++) {
            //pode dar problemas quando ha 1 plot apenas

            //current plot that we are in
            char currPlot = route.charAt(i - 1);

            for (int j = 1; j < 5; j++) { //atencao ao <= pode dar erro
                if (currPlot == 'e' || currPlot == 'h' || currPlot == 'p' || currPlot == 'c') {
                    updateEmptyHandDP(dp, i, maxValue);
                    if (currPlot == 'e') {
                        updateDP(dp, HARP_HAND[0], i, maxValue);
                        updateDP(dp, POTION_HAND[0], i, maxValue);
                        updateDP(dp, CLOAK_HAND[0], i, maxValue);

                    }
                    if (currPlot == 'h') {
                        updateMatchingDP(dp, HARP_HAND[0], i, maxValue);
                        updateDP(dp, POTION_HAND[0], i, maxValue);
                        updateDP(dp, CLOAK_HAND[0], i, maxValue);

                    }
                    if (currPlot == 'p') {
                        updateMatchingDP(dp, POTION_HAND[0], i, maxValue );
                        updateDP(dp, HARP_HAND[0], i, maxValue);
                        updateDP(dp, CLOAK_HAND[0], i, maxValue);
                    }
                    if (currPlot == 'c') {
                        updateMatchingDP(dp, CLOAK_HAND[0], i, maxValue);
                        updateDP(dp, HARP_HAND[0], i, maxValue);
                        updateDP(dp, POTION_HAND[0], i, maxValue);
                    }

                } else if (currPlot == '3' || currPlot == 't' || currPlot == 'd') {

                    if (currPlot == '3') {
                        if (j == HARP_HAND[0]) {
                            if (dp[HARP_HAND[0]][i - 1] != maxValue && dp[HARP_HAND[0]][i - 1] != 0)
                                dp[HARP_HAND[0]][i] = dp[HARP_HAND[0]][i - 1] + 4;
                        } else if (j == POTION_HAND[0]) {
                            if (dp[POTION_HAND[0]][i - 1] != maxValue && dp[POTION_HAND[0]][i - 1] != 0)
                                dp[POTION_HAND[0]][i] = dp[POTION_HAND[0]][i - 1] + 5;
                        } else if (j == CLOAK_HAND[0]) {
                            if (dp[CLOAK_HAND[0]][i - 1] != maxValue && dp[CLOAK_HAND[0]][i - 1] != 0)
                                dp[CLOAK_HAND[0]][i] = dp[CLOAK_HAND[0]][i - 1] + 6;
                        }
                    }
                    if (currPlot == 't') {
                        if (j == POTION_HAND[0]) {
                            if (dp[POTION_HAND[0]][i - 1] != maxValue && dp[POTION_HAND[0]][i - 1] != 0)
                                dp[POTION_HAND[0]][i] = dp[POTION_HAND[0]][i - 1] + 5;
                        } else if (j == CLOAK_HAND[0]) {
                            if (dp[CLOAK_HAND[0]][i - 1] != maxValue && dp[CLOAK_HAND[0]][i - 1] != 0)
                                dp[CLOAK_HAND[0]][i] = dp[CLOAK_HAND[0]][i - 1] + 6;
                        }

                    }
                    if (currPlot == 'd') {
                        if (j == CLOAK_HAND[0]) {
                            if (dp[CLOAK_HAND[0]][i - 1] != maxValue && dp[CLOAK_HAND[0]][i - 1] != 0)
                                dp[CLOAK_HAND[0]][i] = dp[CLOAK_HAND[0]][i - 1] + 6;
                        }
                    }
                }
            }
        }
    }


    private static void fillDP(int[][] dp, int maxValue) {
        Arrays.fill(dp[0], 0);
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) {
                dp[i][j] = maxValue;
            }
        }
    }

    public static int getMinTime(int[][] dp, int index) {
        int minTime = dp[1][index];
        for (int i = 1; i < dp.length; i++) {
            if (dp[i][index] < minTime) {
                minTime = dp[i][index];
            }
        }
        return minTime;
    }

    private static void updateEmptyHandDP(int[][] dp, int index, int maxValue) {
        if (dp[EMPTY_HAND[0]][index - 1] != maxValue)
            dp[EMPTY_HAND[0]][index] = dp[EMPTY_HAND[0]][index - 1] + 1;
            //get the minimum and ADD 1 as we "dropped the item"
        else
            dp[EMPTY_HAND[0]][index] = getMinTime(dp, index - 1) + 2;
    }

    private static void updateDP(int[][] dp, int object, int index, int maxValue) {
        if (dp[object][index - 1] != maxValue && dp[object][index - 1] != 0)
            dp[object][index] = dp[object][index - 1] + 3;
    }

    //este esta a falhar.. TRATAR
    private static void updateMatchingDP(int[][] dp, int object, int index, int maxValue) {
       // System.out.println("MINTIME " + getMinTime(dp,index-1) + " " + dp[object][index]);
        if(dp[EMPTY_HAND[0]][index-1] == getMinTime(dp,index-1) )
            if(dp[EMPTY_HAND[0]][index-1] == maxValue)
                dp[object][index] = 2;
            else
                dp[object][index] = getMinTime(dp,index-1) + 2;
        else
            dp[object][index] = getMinTime(dp,index-1) + 3;


    }
 //Por usar
    private static void updateMonsterDP(int[][] dp, int[] object, int index, int maxValue) {
        if (dp[object[0]][index - 1] != maxValue)
            dp[object[0]][index] = dp[object[0]][index - 1] + object[1];
    }





}