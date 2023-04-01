import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    //private static int[] test = {1,0};
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int numPaths = Integer.parseInt(in.readLine());
        Path path = new Path(numPaths);
        path.fillTestResults(in);
        ArrayList<Integer> results = path.getTestResults();
        for (int minValue : results) {
            System.out.println(minValue);
        }
        in.close();

    }

}