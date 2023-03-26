import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main {
    private static final int EMPTY_HAND = 1;
    private static final int HARP_HAND = 2;
    private static final int POTION_HAND = 3;
    private static final int CLOAK_HAND = 4;

    public static void main(String[] args) throws IOException {


        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


        //int tests = Integer.parseInt(in.readLine()); usar para efetuar o numero de testes pedido.
        String route = in.readLine();
        int n = route.length();
        int[][] dp = new int[5][n + 1]; // o 3 representa as possibilidades que ha de passar em cada plot. Sem objeto (0),
        // com intuito de apanhar um objeto caso nao tenha ou de dropar um (1),
        // ou de passar já com objeto e ignorra o que há (2)
        int maxValue = Integer.MAX_VALUE; // Preencher a matriz com o maior numero possivel.
        fillDP(dp, maxValue);

        for (int i = 1; i <= n; i++) {
            //pode dar problemas quando ha 1 plot apenas

            //current plot that we are in
            char currPlot = route.charAt(i - 1);

            for (int j = 1; j < 5; j++) { //atencao ao <= pode dar erro
                //case 1: if we are in an easy(empty)-plot or item plot
                if (currPlot == 'e' || currPlot == 'h' || currPlot == 'p' || currPlot == 'c') {
                    //if we are empty handed
                    if (j == EMPTY_HAND) {
                        if (dp[EMPTY_HAND][i - 1] != maxValue)
                            dp[EMPTY_HAND][i] = dp[EMPTY_HAND][i - 1] + 1;
                            //get the minimum and ADD 1 as we "dropped the item"
                        else
                            dp[EMPTY_HAND][i] = getMinTime(dp, i - 1) + 2;
                        //qualquer que seja o item que tenhamos na mao, vamos sempre fazer isto
                    } else {
                        if (currPlot == 'h') {
                            if (dp[HARP_HAND][i - 1] != maxValue && dp[HARP_HAND][i - 1] != 0)
                                dp[HARP_HAND][i] = dp[HARP_HAND][i - 1] + 3;
                            else
                                dp[HARP_HAND][i] = getMinTime(dp, i - 1) + 2;
                        }
                        if (currPlot == 'p') {
                            if (dp[POTION_HAND][i - 1] != maxValue && dp[POTION_HAND][i - 1] != 0)
                                dp[POTION_HAND][i] = dp[POTION_HAND][i - 1] + 3;
                            else
                                dp[POTION_HAND][i] = getMinTime(dp, i - 1) + 2;
                        }
                        if (currPlot == 'c') {
                            if (dp[CLOAK_HAND][i - 1] != maxValue && dp[CLOAK_HAND][i - 1] != 0)
                                dp[CLOAK_HAND][i] = dp[CLOAK_HAND][i - 1] + 3;
                            else
                                dp[CLOAK_HAND][i] = getMinTime(dp, i - 1) + 2;
                        }
                    }
                } else if (currPlot == '3' || currPlot == 't' || currPlot == 'd') {
                    //pode faltar o empty
                    if (currPlot == '3') {
                        if (j == HARP_HAND) {
                            if (dp[HARP_HAND][i - 1] != maxValue)
                                dp[HARP_HAND][i] = dp[HARP_HAND][i - 1] + 4;
                        } else if (j == POTION_HAND) {
                            if (dp[POTION_HAND][i - 1] != maxValue)
                                dp[POTION_HAND][i] = dp[POTION_HAND][i - 1] + 5;
                        } else if (j == CLOAK_HAND) {
                            if (dp[CLOAK_HAND][i - 1] != maxValue)
                                dp[CLOAK_HAND][i] = dp[CLOAK_HAND][i - 1] + 6;
                        }
                    }
                    if (currPlot == 't') {
                        //pode faltar o harp
                        if (j == POTION_HAND) {
                            if (dp[POTION_HAND][i - 1] != maxValue)
                                dp[POTION_HAND][i] = dp[POTION_HAND][i - 1] + 5;
                        } else if (j == CLOAK_HAND) {
                            if (dp[CLOAK_HAND][i - 1] != maxValue)
                                dp[CLOAK_HAND][i] = dp[CLOAK_HAND][i - 1] + 6;
                        }

                    }
                    if (currPlot == 'd') {
                        //pode faltar o harp e talvez o potion
                        if (j == CLOAK_HAND) {
                            if (dp[CLOAK_HAND][i - 1] != maxValue)
                                dp[CLOAK_HAND][i] = dp[CLOAK_HAND][i - 1] + 6;
                        }
                    }
                }
            }
        }
        printMatrix(dp);
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

    public static void printMatrix (int [][]dp ){
        for (int i = 0 ; i < dp.length ; i++ ){
            for (int j = 0 ; j < dp[i].length ; j++ ){
                System.out.println(dp[i][j] + " ");
            }
            System.out.println();
        }
    }
}