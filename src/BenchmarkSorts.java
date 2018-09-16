import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Class initializes the Sort class and dataSet array.
 *
 * @author Lance Gundersen
 * @version 1.0
 * @since 2018-09-13
 */
public class BenchmarkSorts {

    private HeapSort heapSort;

    private int[][][] dataSet;

    private long[] iterativeArrayAverageCriticalOperationCount;
    private long[] iterativeArrayAverageExecutionTime;
    private long[][] iterativeArrayCount;
    private long[][] iterativeArrayTime;

    private long[] recursiveArrayAverageCriticalOperationCount;
    private long[] recursiveArrayAverageExecutionTime;
    private long[][] recursiveArrayCount;
    private long[][] recursiveArrayTime;

    /**
     * Public method per UML.
     *
     * Class Constructor.
     *
     * The dataSet array is: 50 runs per project description.
     *
     * @param sizes DataSet array size
     */
    BenchmarkSorts(int[] sizes) {

        heapSort = new HeapSort();
        dataSet = new int[sizes.length][50][];

        for (int i = 0; i < dataSet.length; i++) {

            for (int j = 0; j < dataSet[i].length; j++)
                dataSet[i][j] = randomizedData(sizes[i]);

            iterativeArrayAverageCriticalOperationCount = new long[dataSet.length];
            iterativeArrayAverageExecutionTime = new long[dataSet.length];
            recursiveArrayAverageCriticalOperationCount = new long[dataSet.length];
            recursiveArrayAverageExecutionTime = new long[dataSet.length];
        }
        iterativeArrayCount = new long[dataSet.length][dataSet[0].length];
        iterativeArrayTime = new long[dataSet.length][dataSet[0].length];
        recursiveArrayCount = new long[dataSet.length][dataSet[0].length];
        recursiveArrayTime = new long[dataSet.length][dataSet[0].length];
    }

    /**
     * Verifies input list is sorted
     *
     * @param list input list
     * @return boolean
     */
    private static boolean sorted(int[] list) {
        for (int i = 0; i < list.length - 1; i++)
            if (list[i] > list[i + 1])
                return false;
        return true;
    }

    /**
     * Public method per UML.
     *
     * Calls the heapSort method on the iterative and recursive arrays.
     *
     * @throws UnsortedException Check for unsorted array.
     */
    public void runSorts() throws UnsortedException {

        for (int i = 0; i < dataSet.length; i++) {

            for (int j = 0; j < dataSet[i].length; j++) {
                int[] arrayA, arrayB;
                arrayA = dataSet[i][j].clone();
                arrayB = dataSet[i][j].clone();
                heapSort.recursiveSort(arrayA);

                if (!sorted(arrayA))
                    throw new UnsortedException("Recursive sort did not return a sorted array.\n");

                recursiveArrayCount[i][j] = heapSort.getCount();
                recursiveArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();
                heapSort.iterativeSort(arrayB);

                if (!sorted(arrayB))
                    throw new UnsortedException("Iterative sort did not return a sorted array.\n");

                iterativeArrayCount[i][j] = heapSort.getCount();
                iterativeArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();
            }
            recursiveArrayAverageCriticalOperationCount[i] = getAverage(recursiveArrayCount[i]);
            recursiveArrayAverageExecutionTime[i] = getAverage(recursiveArrayTime[i]);
            iterativeArrayAverageCriticalOperationCount[i] = getAverage(iterativeArrayCount[i]);
            iterativeArrayAverageExecutionTime[i] = getAverage(iterativeArrayTime[i]);
        }

    }

    /**
     * Returns Average
     *
     * @param data input data
     * @return average
     */
    private long getAverage(long[] data) {
        long sum = 0;
        for (long aData : data) sum += aData;
        return (sum / data.length);
    }

    /**
     * Returns Coefficient Variance
     *
     * @param data input data
     * @return coefficient
     */
    private double getCoefficientVariance(long[] data) {
        long mean = getAverage(data);
        long sum = 0;
        for (long aData : data)
            sum += Math.pow(aData - mean, 2);
        return Math.sqrt(sum / (data.length - 1));
    }

    /**
     * Returns random integers
     *
     * @param size input size of data
     * @return array of integers
     */
    private int[] randomizedData(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++)
            data[i] = (int) (Math.random() * Integer.MAX_VALUE);
        return data;
    }

    /**
     * Public method per UML.
     *
     * Initializes the web view display and builds the html table for the results.
     *
     * @return VBox
     */
    public VBox displayReport() {
        WebView myWebView = new WebView();
        WebEngine engine = myWebView.getEngine();
        StringBuilder dataString = new StringBuilder();
        VBox root = new VBox();

        String top = "<html><body>" +
                "\n<style type =\"text/css\">\n" +
                ".header{text-align:center;}" +
                ".tg  {border-collapse:collapse;border-spacing:0;}\n" +
                ".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}\n" +
                ".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}\n" +
                ".tg .tg-baqh{text-align:center;vertical-align:top}\n" +
                ".tg .tg-73oq{border-color:#000000;text-align:left;vertical-align:top}\n" +
                "</style>\n" +
                "<h2 class=\"header\">UMUC CMSC 451 Project 1</h2>\n" +
                "<h2 class=\"header\">Lance Gundersen</h2>\n" +
                "<h4 class=\"header\">16SEP2018</h4>" +
                "<table class=\"tg\">\n" +
                "  <tr>\n" +
                "    <th class=\"tg-baqh\">Data Set Size n</th>\n" +
                "    <th class=\"tg-baqh\" colspan=\"4\">Iterative</th>\n" +
                "    <th class=\"tg-baqh\" colspan=\"4\">Recursive</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\">Average Critical Operation Count</td>\n" +
                "    <td class=\"tg-73oq\">Coefficient of Variance of Count</td>\n" +
                "    <td class=\"tg-73oq\">Average Execution Time</td>\n" +
                "    <td class=\"tg-73oq\">Coefficient of Variance Time</td>\n" +
                "    <td class=\"tg-73oq\">Average Critical Operation Count</td>\n" +
                "    <td class=\"tg-73oq\">Coefficient of Variance of Count</td>\n" +
                "    <td class=\"tg-73oq\">Average Execution Time</td>\n" +
                "    <td class=\"tg-73oq\">Coefficient of Variance Time</td>\n" +
                "  </tr>\n";

        for (int i = 0; i < dataSet.length; i++) {
            dataString.append("<tr><td class=\"tg-73oq\">" + dataSet[i][0].length + "</td>");

            dataString.append("<td class=\"tg-73oq\">").append(iterativeArrayAverageCriticalOperationCount[i]).append("</td>");
            dataString.append("<td class=\"tg-73oq\">" + getCoefficientVariance(iterativeArrayCount[i]) + "</td>");
            dataString.append("<td class=\"tg-73oq\">" + iterativeArrayAverageExecutionTime[i] + "</td>");
            dataString.append("<td class=\"tg-73oq\">" + Math.round(getCoefficientVariance(iterativeArrayTime[i]) * 1000.0) / 1000.0 + "</td>");

            dataString.append("<td class=\"tg-73oq\">" + recursiveArrayAverageCriticalOperationCount[i] + "</td>");
            dataString.append("<td class=\"tg-73oq\">" + Math.round(getCoefficientVariance(recursiveArrayCount[i]) * 1000.0) / 1000.0 + "</td>");
            dataString.append("<td class=\"tg-73oq\">" + recursiveArrayAverageExecutionTime[i] + "</td>");
            dataString.append("<td class=\"tg-73oq\">" + Math.round(getCoefficientVariance(recursiveArrayTime[i]) * 1000.0) / 1000.0 + "</td></tr>");
        }

        String bottom = "\n</table></body></html>";

        engine.loadContent(top + dataString + bottom);

        root.getChildren().addAll(myWebView);

        return root;

    }


}
