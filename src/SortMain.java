import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by aargento on 8/25/17.
 *
 * Reference: JVM Warmup code adapted from http://www.baeldung.com/java-jvm-warmup
 */

public class SortMain {

    // warm up the Java Virtual Machine
    static {
        jvmLoader.load();
    }

    // JVM warmup classes
    static class jvmWarmUp{
        void m(){
        }
    }//end jvmWarmUp

    private static class jvmLoader {
        static void load(){
            for (int i = 0; i < 10000; i++){
                jvmWarmUp warmUp = new jvmWarmUp();
                warmUp.m();
            }
        }
    }//end jvmLoader

    // Method to set UI font
    public static void setUIFont (javax.swing.plaf.FontUIResource f){

        java.util.Enumeration keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }//end setUIFont

    // Constructor
    SortMain() {

        // Define data set sizes
        // int[] dataSetSizes = new int[] {1000, 3000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 16000};
        int[] dataSetSizes = new int[] {1000, 3000};

        BenchmarkSorts benchmarkSorts = new BenchmarkSorts(dataSetSizes);

        // Call BenchmarkSorts --> runSorts method and catch any unhandled exception
        try{
            benchmarkSorts.runSorts();
        } catch (UnsortedException e) {
            System.out.print(e.getMessage());
        }

        // Use StringBuilder to display data on GUI
        StringBuilder report = benchmarkSorts.displayReport();
        System.out.print(String.valueOf(report));

    }//end constructor

    public static void main(String[] args) {

        // Warm up the Java Virtual Machine
        jvmLoader.load();

        // Call constructor
        new SortMain();

    }//end main

}//end SortMain