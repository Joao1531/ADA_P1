import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int numPaths = Integer.parseInt(input.readLine());
        Path path = new Path(numPaths);
        path.fillTestResults(input);
        ArrayList<Integer> results = path.getTestResults();
        printResults(results);
        input.close();

    }
    private static void printResults(ArrayList<Integer> results){
        for (int minValue : results) {
            System.out.println(minValue);
        }
    }

}