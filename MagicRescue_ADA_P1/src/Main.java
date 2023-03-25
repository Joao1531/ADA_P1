import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


        //int tests = Integer.parseInt(in.readLine()); usar para efetuar o numero de testes pedido.
        String route = in.readLine();
        int[][] dp = new int[5][route.length() + 1]; // o 3 representa as possibilidades que ha de passar em cada plot. Sem objeto (0),
        // com intuito de apanhar um objeto caso nao tenha ou de dropar um (1),
        // ou de passar já com objeto e ignorra o que há (2)
        int maxValue = Integer.MAX_VALUE; // Preencher a matriz com o maior numero possivel.
        fillDP(dp, maxValue);

        for (int i = 0; i < route.length(); i++) {
            if (route.charAt(i) == 'e') { // Caso seja easy plot e esteja vazia:
                // Para a coluna onde o objetivo é não ter um único objeto
                if (dp[0][i - 1] != maxValue)
                    dp[0][i] = dp[0][i - 1] + 1;
                else
                    dp[0][i] = getMinTime(dp, i - 1) + 2;
                for (int j = 1; j < dp.length; j++) {
                    if (dp[j][i - 1] == 0 || dp[j][i - 1] == maxValue)
                        dp[j][i] = maxValue;
                    else
                        dp[j][i] = dp[j][i - 1] + 3;
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
}