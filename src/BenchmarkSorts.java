import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

class BenchmarkSorts {

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

    void runSorts() throws UnsortedException {

        for (int i = 0; i < dataSet.length; i++) {

            for (int j = 0; j < dataSet[i].length; j++) {
                int[] arrayA, arrayB;
                arrayA = dataSet[i][j].clone();
                arrayB = dataSet[i][j].clone();
                heapSort.recursiveSort(arrayA);

                if (!sorted(arrayA))
                    JOptionPane.showMessageDialog(null, "Sorted array not returned by recursive sort", "Recursive Error", JOptionPane.ERROR_MESSAGE);

                rArrayCount[i][j] = heapSort.getCount();
                rArrayTime[i][j] = heapSort.getTime();
                heapSort.reset();
                heapSort.iterativeSort(arrayB);

                if (!sorted(arrayB))
                    JOptionPane.showMessageDialog(null, "Sorted array not returned by iterative sort", "Iterative Error", JOptionPane.ERROR_MESSAGE);

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

    public void displayReport() {

        // TODO: Return the graphic table

        // Define output label components
        JTextArea outputLabel = new JTextArea();
        outputLabel.setEditable(false);

        // Define JPanels
        JPanel panelOutput = new JPanel();
        panelOutput.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        panelOutput.setPreferredSize(new Dimension(690, 175));

        // Add components to Output JPanel
        panelOutput.add(outputLabel);

        // Define container to group panels
        Container containerPnlGrp = new Container();
        containerPnlGrp.setLayout(new BoxLayout(containerPnlGrp, BoxLayout.Y_AXIS));
        containerPnlGrp.add(panelOutput);

        // Create frame and define parameters of GUI
        JFrame frameMainGUI = new JFrame();
        frameMainGUI.setTitle("CMSC 451 Project 1");
        frameMainGUI.setPreferredSize(new Dimension(970, 395));
        frameMainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMainGUI.add(containerPnlGrp);
        frameMainGUI.pack();
        frameMainGUI.setLocationRelativeTo(null);
        frameMainGUI.setVisible(true);

        StringBuilder sb = new StringBuilder();


        // TODO: Gross!
        sb.append("\nData\t\t          Iterative\t\t\t\t\t          Recursive\nSet\nSize n\n\n");
        sb.append("\tAverage\tCoefficient of \tAverage\tCoefficient of\t\tAverage\tCoefficient of \tAverage\tCoefficient of \n");
        sb.append("\tCritical\tVariance of \tExecution\tVariance of\t\tCritical\tVariance of \tExecution\tVariance of\n");
        sb.append("\tOperation\tCount\tTime\tTime\t\tOperation\tCount\tTime\tTime\n");
        sb.append("\tCount\t\t\t\t\tCount\n\n");

        for (int i = 0; i < dataSet.length; i++) {

            // Data set size n
            int dssLength = (dataSet[i][0].length);
            sb.append(dssLength);

            // ITERATIVE
            // Average critical operation count
            sb.append("\t").append(iArrayAverageCriticalOperationCount[i]);

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
            double recursiveCountSd = Math.round(getCoefficientVariance(rArrayCount[i]) * 1000.0) / 1000.0;
            sb.append("\t").append(recursiveCountSd);

            // Average execution time
            double averageRecursiveExecutionTime = (rArrayAverageExecutionTime[i]);
            sb.append("\t").append(averageRecursiveExecutionTime);

            // Coefficient of variance of time
            sb.append("\t").append(Math.round(getCoefficientVariance(rArrayTime[i]) * 1000.0) / 1000.0).append("\n");
        }

        outputLabel.setText(String.valueOf(sb));

    }

}
