import javax.swing.*;
import java.util.Formatter;

/**
 * Created by aargento on 8/25/17.
 */

class BenchmarkSorts {

    HeapSort heapSort;

    // Data set array
    private int[][][] dataSet;

    // Iterative arrays
    private long [] iArrayAverageCriticalOperationCount;
    private long[] iArrayAverageExecutionTime;
    private long [][] iArrayCount;
    private long[][] iArrayTime;

    // Recursive arrays
    private long[] rArrayAverageCriticalOperationCount;
    private long[] rArrayAverageExecutionTime;
    private long[][] rArrayCount;
    private long[][] rArrayTime;

    // Construct arrays
    BenchmarkSorts(int[] sizes) {
        heapSort = new HeapSort();
        dataSet = new int[sizes.length][50][];

        for(int i = 0; i < dataSet.length; i++) {

            for(int j = 0; j < dataSet[i].length; j++) {
                dataSet[i][j] = randomizedData(sizes[i]);
            }

            iArrayAverageCriticalOperationCount = new long [dataSet.length];
            iArrayAverageExecutionTime = new long[dataSet.length];
            rArrayAverageCriticalOperationCount = new long[dataSet.length];
            rArrayAverageExecutionTime = new long[dataSet.length];
        }

        iArrayCount = new long [dataSet.length][dataSet[0].length];
        iArrayTime = new long[dataSet.length][dataSet[0].length];
        rArrayCount = new long [dataSet.length][dataSet[0].length];
        rArrayTime = new long[dataSet.length][dataSet[0].length];
    }

    // Run sorts and save results
    void runSorts() throws UnsortedException {

        for(int i = 0; i < dataSet.length; i++) {

            for(int j = 0; j < dataSet[i].length; j++) {

                // Generate and populate 2 arrays with same data
                int[] arrayA, arrayB;
                arrayA = dataSet[i][j].clone();
                arrayB = dataSet[i][j].clone();

                // Run recursiveSort on arrayA
                heapSort.recursiveSort(arrayA);

                // Throw Error GUI for recursive error
                if(!sorted(arrayA)) {
                    JOptionPane.showMessageDialog(null, "Sorted array not returned by recursive sort",
                            "Recursive Error", JOptionPane.ERROR_MESSAGE);
                }

                // Retrieve count and time from recursive action
                rArrayCount[i][j] = heapSort.getCount();
                rArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();

                // Run iterativeSort on arrayB
                heapSort.iterativeSort(arrayB);

                // Throw Error GUI for iterative sort
                if(!sorted(arrayB)) {
                    JOptionPane.showMessageDialog(null, "Sorted array not returned by iterative sort",
                            "Iterative Error", JOptionPane.ERROR_MESSAGE);
                }

                // Retrieve count and time from iterative action
                iArrayCount[i][j] = heapSort.getCount();
                iArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();

            }

            // Recursive average critical operation count
            rArrayAverageCriticalOperationCount[i] = getAverage(rArrayCount[i]);

            // Recursive average run time
            rArrayAverageExecutionTime[i] = getAverage(rArrayTime[i]);

            // Iterative average critical operation count
            iArrayAverageCriticalOperationCount[i] = getAverage(iArrayCount[i]);

            // Iterative average run time
            iArrayAverageExecutionTime[i] = getAverage(iArrayTime[i]);
        }

    }//end runSorts

    // Get average of data input
    private long getAverage(long [] data){
        long sum = 0;

        for (long aData : data) sum += aData;
        return (sum / data.length);
    }

    // Get coefficient variance of data input
    private double getCoefficientVariance (long[] data){
        long mean = getAverage(data);
        long sum = 0;

        for (long aData : data) {
            sum += Math.pow(aData - mean, 2);
        }

        return Math.sqrt(sum/(data.length - 1));
    }

    // Check sort was completed correctly
    private static boolean sorted(int[] list) {
        for(int i = 0; i < list.length-1; i++)
            if(list[i] > list[i+1])
                return false;
        return true;
    }//end sorted

    // Generate random data set
    private int[] randomizedData(int size) {
        int[] data = new int[size];
        for(int i = 0; i < size; i++)
            data[i] = (int)(Math.random() * Integer.MAX_VALUE);
        return data;
    }//end randomizedData

    // Create string to be displayed in GUI
    StringBuilder displayReport() {

        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s %30s %20s %5s %5s", "STUDENT ID", "EMAIL ID", "NAME", "AGE", "GRADE");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");


        StringBuilder sb = new StringBuilder();

        System.out.printf("+-------+-------------------------------------------------------------+-------------------------------------------------------------+%n");
        System.out.printf("| Data  | Iterative                                                   | Recursive                                                   |%n");
        System.out.printf("+-------+------------+------------------------------------------------+-------------------------------------------------------------+%n");
        System.out.printf("|       | Average    | Coefficient of  | Average     | Coefficient of | Average    | Coefficient of  | Average     | Coefficient of |%n");
        System.out.printf("|       | Critical   | Variance of     | Execution   | Varience of    | Critical   | Variance of     | Execution   | Varience of    |%n");
        System.out.printf("|       | Operation  | Count           | Time        | Time           | Operation  | Count           | Time        | Time           |%n");
        System.out.printf("|       | Count      |                 |             |                | Count      |                 |             |                |%n");
        System.out.printf("+-------+-------------------------------------------------------------+-------------------------------------------------------------+%n");

        for(int i = 0; i < dataSet.length; i++) {

            // Data set size n
            int dssLength = (dataSet[i][0].length);

            // ITERATIVE
            // Average critical operation count
//            sb.append("\t").append(iArrayAverageCriticalOperationCount[i]);

            // Coefficient of variance of count
            double iterativeVarianceCount = getCoefficientVariance(iArrayCount[i]);
            sb.append("\t").append(iterativeVarianceCount);

            // Average execution time
            double averageIterativeExecutionTime = (iArrayAverageExecutionTime[i]);
            sb.append("\t").append(averageIterativeExecutionTime);

            // Coefficient of variance of time
            sb.append("\t").append(Math.round(getCoefficientVariance(iArrayTime[i]) * 1000.0) / 1000.0).append("\t");

            /// RECURSIVE
            // Average critical operation count
            sb.append("\t").append(rArrayAverageCriticalOperationCount[i]);

            // Coefficient of variance of count
            double recursiveCountSd = Math.round(getCoefficientVariance(rArrayCount[i])*1000.0)/1000.0;
            sb.append("\t").append(recursiveCountSd);

            // Average execution time
            double averageRecursiveExecutionTime = (rArrayAverageExecutionTime[i]);
            sb.append("\t").append(averageRecursiveExecutionTime);

            // Coefficient of variance of time
            sb.append("\t").append(Math.round(getCoefficientVariance(rArrayTime[i])*1000.0)/1000.0).append("\n");

            System.out.printf("| " + dssLength + "  | " + iArrayAverageCriticalOperationCount[i] + "        | " + iterativeVarianceCount + "      |%n");
        }

        return sb;
    }//end displayReport

}//end BenchmarkSorts