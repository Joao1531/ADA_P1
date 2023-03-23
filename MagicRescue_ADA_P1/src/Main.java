import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //int tests = Integer.parseInt(in.readLine()); usar para efetuar o numero de testes pedido.
        String route = in.readLine();
        int [][] cache = new int[route.length()][3]; // o 3 representa as possibilidades que ha de passar em cada plot. Sem objeto (0),
        // com intuito de apanhar um objeto caso nao tenha ou de dropar um (1),
        // ou de passar já com objeto e ignorra o que há (2)
    }

}