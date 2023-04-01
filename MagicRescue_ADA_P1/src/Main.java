/**
 * @author Joao Lopes 60055
 * @author Jose Romano 59241
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The user first inputs the number of path that he will enter. Then, the main creates the Path
 * Class by giving it the number inputed. The class Path works as a System class and will receive
 * the inputs directly and in the end it sends an ArrayList<Integer> that will contain all the
 * results.
 */
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

    /**
     * this method retrieves the minimum time for passing each route in the amount of tests asked.
     *
     * @param results - Stores  the minimum values of passing each route inputted.
     */
    private static void printResults(ArrayList<Integer> results) {
        for (int minValue : results) {
            System.out.println(minValue);
        }
    }

}