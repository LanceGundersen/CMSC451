import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SortMain extends Application {

    static {
        jvmLoader.load();
    }

    public static void main(String[] args) {

        Application.launch(args);

        //        jvmLoader.load();

        new SortMain();

    }

    public void start(Stage stage) {

        int[] dataSetSizes = new int[]{500, 1000, 1500, 2000};
        BenchmarkSorts benchmarkSorts = new BenchmarkSorts(dataSetSizes);
        try {
            benchmarkSorts.runSorts();

            VBox root = benchmarkSorts.displayReport();

            Scene scene = new Scene(root, 800, 400);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    static class jvmWarmUp {
        void m() {
        }
    }

    /**
     * JVM Loader reference https://www.baeldung.com/java-jvm-warmup
     */
    private static class jvmLoader {
        static void load() {
            for (int i = 0; i < 10000; i++) {
                jvmWarmUp warmUp = new jvmWarmUp();
                warmUp.m();
            }
        }
    }


}