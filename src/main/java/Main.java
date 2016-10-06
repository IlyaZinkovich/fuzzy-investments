import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        List<List<Double>> A = new ArrayList<>();
        A.add(Arrays.asList(300D, 700D, 800D));
        A.add(Arrays.asList(400D, 800D, 800D));
        A.add(Arrays.asList(500D, 900D, 600D));

        List<List<Double>> percents = new ArrayList<>();
        percents.add(Arrays.asList(3D, 7D, 12D));
        percents.add(Arrays.asList(5D, 9D, 11D));

        List<List<Double>> left = new ArrayList<>();
        List<List<Double>> right = new ArrayList<>();

        int alphas = 10;

        calculatePercents(percents, left, right, alphas);

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Value");
        yAxis.setLabel("Alpha");

        final AreaChart<Number, Number> lineChart =
                new AreaChart<>(xAxis, yAxis);

        for (int a = 0; a < A.size(); a++) {
            List<Double> leftValue = new ArrayList<>();
            List<Double> rightValue = new ArrayList<>();

            calculateValues(A.get(a), percents, left, right, alphas, leftValue, rightValue);

            XYChart.Series series = new XYChart.Series();
            series.setName("A" + (a + 1));
            for (int i = 0; i <= alphas; i++) {
                series.getData().add(new XYChart.Data(leftValue.get(i), i * 0.1D));
            }
            for (int i = alphas; i >= 0; i--) {
                series.getData().add(new XYChart.Data(rightValue.get(i), i * 0.1D));
            }
            lineChart.getData().add(series);
        }

        Scene scene = new Scene(lineChart, 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    private void calculateValues(List<Double> a, List<List<Double>> percents, List<List<Double>> left, List<List<Double>> right, double alphas, List<Double> leftValue, List<Double> rightValue) {
        for (int currentAlpha = 0; currentAlpha <= alphas; currentAlpha++) {
            for (int percent = 1; percent < percents.size(); percent++) {
                double lvalue = -a.get(0);
                double rvalue = -a.get(0);
                lvalue += a.get(percent) * (100D / (100D + right.get(percent - 1).get(currentAlpha)));
                rvalue += a.get(percent) * (100D / (100D + left.get(percent - 1).get(currentAlpha)));
                leftValue.add(lvalue);
                rightValue.add(rvalue);
            }
        }
    }

    private void calculatePercents(List<List<Double>> percents, List<List<Double>> left, List<List<Double>> right, double alphas) {
        for (List<Double> percent : percents) {
            List<Double> leftPercents = new ArrayList<>();
            List<Double> rightPercents = new ArrayList<>();
            for (double alpha = 0; alpha <= 1; alpha += 1 / alphas) {
                double leftForAlpha = percent.get(0) + (percent.get(1) - percent.get(0)) * alpha;
                leftPercents.add(leftForAlpha);
                double rightForAlpha = percent.get(2) - (percent.get(2) - percent.get(1)) * alpha;
                rightPercents.add(rightForAlpha);
            }
            left.add(leftPercents);
            right.add(rightPercents);
        }
    }
}
