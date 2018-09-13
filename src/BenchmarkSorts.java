import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BenchmarkSorts {

    HeapSort heapSort;

    private int[][][] dataSet;

    private long[] iArrayAverageCriticalOperationCount;
    private long[] iArrayAverageExecutionTime;
    private long[][] iArrayCount;
    private long[][] iArrayTime;

    private long[] rArrayAverageCriticalOperationCount;
    private long[] rArrayAverageExecutionTime;
    private long[][] rArrayCount;
    private long[][] rArrayTime;

    BenchmarkSorts(int[] sizes) {

        heapSort = new HeapSort();
        dataSet = new int[sizes.length][50][];

        for (int i = 0; i < dataSet.length; i++) {

            for (int j = 0; j < dataSet[i].length; j++)
                dataSet[i][j] = randomizedData(sizes[i]);

            iArrayAverageCriticalOperationCount = new long[dataSet.length];
            iArrayAverageExecutionTime = new long[dataSet.length];
            rArrayAverageCriticalOperationCount = new long[dataSet.length];
            rArrayAverageExecutionTime = new long[dataSet.length];
        }
        iArrayCount = new long[dataSet.length][dataSet[0].length];
        iArrayTime = new long[dataSet.length][dataSet[0].length];
        rArrayCount = new long[dataSet.length][dataSet[0].length];
        rArrayTime = new long[dataSet.length][dataSet[0].length];
    }

    private static boolean sorted(int[] list) {
        for (int i = 0; i < list.length - 1; i++)
            if (list[i] > list[i + 1])
                return false;
        return true;
    }

    public void runSorts() throws UnsortedException {

        for (int i = 0; i < dataSet.length; i++) {

            for (int j = 0; j < dataSet[i].length; j++) {
                int[] arrayA, arrayB;
                arrayA = dataSet[i][j].clone();
                arrayB = dataSet[i][j].clone();
                heapSort.recursiveSort(arrayA);

                if (!sorted(arrayA))
                    throw new UnsortedException("Recursive sort did not return a sorted array.\n");

                rArrayCount[i][j] = heapSort.getCount();
                rArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();
                heapSort.iterativeSort(arrayB);

                if (!sorted(arrayB))
                    throw new UnsortedException("Iterative sort did not return a sorted array.\n");

                iArrayCount[i][j] = heapSort.getCount();
                iArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();
            }
            rArrayAverageCriticalOperationCount[i] = getAverage(rArrayCount[i]);
            rArrayAverageExecutionTime[i] = getAverage(rArrayTime[i]);
            iArrayAverageCriticalOperationCount[i] = getAverage(iArrayCount[i]);
            iArrayAverageExecutionTime[i] = getAverage(iArrayTime[i]);
        }

    }

    private long getAverage(long[] data) {
        long sum = 0;
        for (long aData : data) sum += aData;
        return (sum / data.length);
    }

    private double getCoefficientVariance(long[] data) {
        long mean = getAverage(data);
        long sum = 0;
        for (long aData : data)
            sum += Math.pow(aData - mean, 2);
        return Math.sqrt(sum / (data.length - 1));
    }

    private int[] randomizedData(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++)
            data[i] = (int) (Math.random() * Integer.MAX_VALUE);
        return data;
    }

    public VBox displayReport() {
        WebView myWebView = new WebView();
        WebEngine engine = myWebView.getEngine();
        engine.loadContent("<html><body>" +
                "\n<style type =\"text/css\">\n" +
                ".tg  {border-collapse:collapse;border-spacing:0;}\n" +
                ".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}\n" +
                ".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}\n" +
                ".tg .tg-baqh{text-align:center;vertical-align:top}\n" +
                ".tg .tg-73oq{border-color:#000000;text-align:left;vertical-align:top}\n" +
                "</style>\n" +
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
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "    <td class=\"tg-73oq\"></td>\n" +
                "  </tr>\n" +
                "</table>" +
                "</body></html>");

        VBox root = new VBox();
        root.getChildren().addAll(myWebView);

        return root;

        // TODO: Output to something pretty

//        for (int i = 0; i < dataSet.length; i++) {
//
//            // Data set size n
//            int dataSetSize = (dataSet[i][0].length);
//
//            // ITERATIVE
//            // Average critical operation count
//            long l1 = iArrayAverageCriticalOperationCount[i];
//
//            // Coefficient of variance of count
//            double iterativeVarianceCount = getCoefficientVariance(iArrayCount[i]);
//
//            // Average execution time
//            double averageIterativeExecutionTime = (iArrayAverageExecutionTime[i]);
//
//            // Coefficient of variance of time
//            double iterativeCoefficientVariance = Math.round(getCoefficientVariance(iArrayTime[i]) * 1000.0) / 1000.0;
//
//            /// RECURSIVE
//            // Average critical operation count
//            long l = rArrayAverageCriticalOperationCount[i];
//
//            // Coefficient of variance of count
//            double recursiveCountSd = Math.round(getCoefficientVariance(rArrayCount[i]) * 1000.0) / 1000.0;
//
//            // Average execution time
//            double averageRecursiveExecutionTime = (rArrayAverageExecutionTime[i]);
//
//            // Coefficient of variance of time
//            double recursiveCoefficientVariance = Math.round(getCoefficientVariance(rArrayTime[i]) * 1000.0) / 1000.0;
//        }
    }


}
