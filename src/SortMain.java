import javax.swing.*;

public class SortMain extends JFrame {

    static {
        jvmLoader.load();
    }

    private SortMain() {

        int[] dataSetSizes = new int[]{500, 1000, 1500, 2000};
        BenchmarkSorts benchmarkSorts = new BenchmarkSorts(dataSetSizes);
        try {
            benchmarkSorts.runSorts();
            benchmarkSorts.displayReport();
        } catch (UnsortedException e) {
            System.out.print(e.getMessage());
        }

    }

    public static void main(String[] args) {

//        jvmLoader.load();

        new SortMain();

    }

    static class jvmWarmUp {
        void m() {
        }
    }

    private static class jvmLoader {
        static void load() {
            for (int i = 0; i < 10000; i++) {
                jvmWarmUp warmUp = new jvmWarmUp();
                warmUp.m();
            }
        }
    }
}