package com.example.linearequations.GUI;

import com.example.linearequations.HelloController;
import com.example.linearequations.checks.GetFunctionValue;
import javafx.application.Platform;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class Plot {

    // Bisection -- False Position
    public LineChart<Number, Number> draw(List<Double> xl, List<Double> xu, double xr) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("f(x) and Boundary Functions");

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("f(x)");
        GetFunctionValue getFunctionValue = new GetFunctionValue();

        int size = xl.size();
        System.out.println("THE Size: " + size);

        if (xl.size() > 0 && xu.size() > 0) {
            double xRight = xl.get(0) - 0.1, xLeft = xu.get(0) + 0.1, j = xRight, val;
            List<Double> Y = new ArrayList<>();
            double v = (Math.abs(xRight) + Math.abs(xLeft)) / (size + 300);
            while (j < xLeft) {
                val = getFunctionValue.parseFun(j);
                series1.getData().add(new XYChart.Data<>(j, val));
                Y.add(val);
                j += v;
            }
            lineChart.getData().add(series1);
            Platform.runLater(() ->
                    series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: black;")
            );

            for (int i = 0; i < size; i++) {
                XYChart.Series<Number, Number> series2 = new XYChart.Series<>(); // xL
                XYChart.Series<Number, Number> series3 = new XYChart.Series<>();  // xU
                for (double value : Y) {
                    series2.getData().add(new XYChart.Data<>(xl.get(i), value));
                    series3.getData().add(new XYChart.Data<>(xu.get(i), value));
                }
                Platform.runLater(() ->
                        series2.getNode().lookup(".chart-series-line")
                                .setStyle("-fx-stroke: red; -fx-stroke-width: 1px;")
                );

                Platform.runLater(() ->
                        series3.getNode().lookup(".chart-series-line")
                                .setStyle("-fx-stroke: blue; -fx-stroke-width: 1px;")
                );
                lineChart.getData().add(series2);
                lineChart.getData().add(series3);
            }

            XYChart.Series<Number, Number> series4 = new XYChart.Series<>();
            for (double value : Y) {
                series4.getData().add(new XYChart.Data<>(xr, value));
            }
            Platform.runLater(() ->
                    series4.getNode().lookup(".chart-series-line")
                            .setStyle("-fx-stroke: green; -fx-stroke-width: 1.5px;")
            );
            lineChart.getData().add(series4);

            lineChart.setCreateSymbols(false);
        }
        return lineChart;
    }

    // Fixed Point
    public LineChart<Number, Number> draw(double x, double start) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("g(x) and Boundary Functions");

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();  // g(x)
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>(); // x
        XYChart.Series<Number, Number> series3 = new XYChart.Series<>(); // xr
        series1.setName("g(x)");

        GetFunctionValue getFunctionValue = new GetFunctionValue();

        double v = Math.abs(Math.abs(x) - Math.abs(start));
        double x0 = x - v, x1 = x + v, val;
        System.out.println(HelloController.getExpression());
        while (x0 < x1) {
            // System.out.println("HELLO!");
            val = getFunctionValue.parseFun(x0);
            series1.getData().add(new XYChart.Data<>(x0, val));
            series2.getData().add(new XYChart.Data<>(x0, x0));
            series3.getData().add(new XYChart.Data<>(x, val));
            x0 += v / 400.0;
        }

        Platform.runLater(() ->
                series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: black;")
        );

        Platform.runLater(() ->
                series2.getNode().lookup(".chart-series-line")
                        .setStyle("-fx-stroke: red; -fx-stroke-width: 1.5px")
        );

        Platform.runLater(() ->
                series3.getNode().lookup(".chart-series-line")
                        .setStyle("-fx-stroke: blue; -fx-stroke-width: 1.5px")
        );

        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
        lineChart.getData().add(series3);

        lineChart.setCreateSymbols(false);
        return lineChart;
    }

    // Newton Rasphson
    public LineChart<Number, Number> draw(double[] values) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("f(x) and Boundary Functions");

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();  // Main Function
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>(); // Tangent
        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();  // XR
        series1.setName("f(x)");

        GetFunctionValue getFunctionValue = new GetFunctionValue();

        double c_value = values[0], slope = values[1], x = values[2], startX = values[3],
                j = Math.min(x, startX), val;

        double v = (Math.abs(x) + Math.abs(startX));
        j -= v;
        while (j < Math.max(x, startX) + v) {
            System.out.println("HELLO!");
            val = getFunctionValue.parseFun(j);
            series1.getData().add(new XYChart.Data<>(j, val));
            series2.getData().add(new XYChart.Data<>(j, slope * j + c_value));
            series3.getData().add(new XYChart.Data<>(x, val));
            j += v / 300.0;
        }
        Platform.runLater(() ->
                series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: black;")
        );

        Platform.runLater(() ->
                series2.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: red; -fx-stroke-width: 2px")
        );

        Platform.runLater(() ->
                series3.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: blue; -fx-stroke-width: 1.5px")
        );
        lineChart.getData().add(series1);
        lineChart.getData().add(series2);
        lineChart.getData().add(series3);

        lineChart.setCreateSymbols(false);
        return lineChart;
    }

    // Secant
    public LineChart<Number, Number> draw(List<Double> values) {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("f(x) and Boundary Functions");

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();  // Main Function
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>(); // Root
        series1.setName("f(x)");

        GetFunctionValue getFunctionValue = new GetFunctionValue();

        double x0 = values.get(0), x1 = values.get(1), xr, val;
        try {
            xr = values.get(2);
        } catch (NullPointerException e) {
            return lineChart;
        }
        double v = Math.abs(x0) + Math.abs(x1), j = xr - v;
        // System.out.println("Values: " + x0 + " " + x1 + " " + xr + " " + v);
        while (j < xr + v) {
            // System.out.println("HELLO!");
            val = getFunctionValue.parseFun(j);
            series1.getData().add(new XYChart.Data<>(j, val));
            series2.getData().add(new XYChart.Data<>(xr, val));
            j += v / 300.0;
        }
        Platform.runLater(() ->
                series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: black;")
        );

        Platform.runLater(() ->
                series2.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: red; -fx-stroke-width: 1.5px")
        );


        lineChart.getData().add(series1);
        lineChart.getData().add(series2);

        lineChart.setCreateSymbols(false);
        return lineChart;
    }


}
