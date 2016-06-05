package DAS.View;

import DAS.Data.Integers;
import DAS.Functions.A2.Boxplot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class GUIController implements Initializable {

    @FXML
    AnchorPane math_top;
    @FXML
    TextArea math_text;

    @FXML
    Canvas boxplot_chart;

    final double BOXPLOT_ROOT_X = 20;   // bottom left
    final double BOXPLOT_ROOT_Y = 40;   // bottom right
    final double BOXPLOT_HEIGHT = 400;
    final double BOXPLOT_WIDTH = 800;

    @FXML
    ScatterChart<Number, Number> scatterplot;

    public void flushResetWindow() {
        while (math_top.getChildren().size() > 0) math_top.getChildren().remove(0);
        math_text.setText("");
    }

    public void drawBoxplot(ActionEvent actionEvent) {
        flushResetWindow();

        Boxplot box1 = new Boxplot(Integers.getIntegers1());
        Boxplot box2 = new Boxplot(Integers.getIntegers2());

        initializeBoxplotView();

        GraphicsContext gc = boxplot_chart.getGraphicsContext2D();

        gc.strokeLine(BOXPLOT_ROOT_X+20,BOXPLOT_HEIGHT,BOXPLOT_WIDTH,BOXPLOT_HEIGHT);       // xAxis
        gc.strokeLine(BOXPLOT_ROOT_X+20,BOXPLOT_ROOT_Y,BOXPLOT_ROOT_X+20,BOXPLOT_HEIGHT);   // yAxis

        gc.strokeText(String.valueOf(Math.min(box1.getMinimum(),box2.getMinimum())), BOXPLOT_ROOT_X,BOXPLOT_HEIGHT);
        gc.strokeText(String.valueOf(Math.max(box1.getMaximum(),box2.getMaximum())), BOXPLOT_ROOT_X,BOXPLOT_ROOT_Y);

        math_top.getChildren().add(boxplot_chart);
        math_text.setText(box1.toString());
    }

    public void drawCorrelation(ActionEvent actionEvent) {
        flushResetWindow();

        initializeScatterplotView(new Boxplot(Integers.getIntegers1()), new Boxplot(Integers.getIntegers2()));
        XYChart.Series<String, Number> data = new XYChart.Series<>();

        math_top.getChildren().add(scatterplot);
    }

    private void initializeBoxplotView() {
        boxplot_chart = new Canvas();

        boxplot_chart.setHeight(BOXPLOT_HEIGHT);
        boxplot_chart.setWidth(BOXPLOT_WIDTH);
        boxplot_chart.setLayoutX(BOXPLOT_ROOT_X);
        boxplot_chart.setLayoutY(BOXPLOT_ROOT_Y);

        AnchorPane.setTopAnchor(boxplot_chart, 0.0);
        AnchorPane.setBottomAnchor(boxplot_chart, 0.0);
        AnchorPane.setLeftAnchor(boxplot_chart, 0.0);
        AnchorPane.setRightAnchor(boxplot_chart, 0.0);
    }

    private void initializeScatterplotView (Boxplot data1, Boxplot data2) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Values_1");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(Math.min(data1.getMinimum(),data2.getMinimum()));
        xAxis.setUpperBound(Math.max(data1.getMaximum(),data2.getMaximum()));

        yAxis.setLabel("Values_2");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setLowerBound(Math.min(data1.getMinimum(),data2.getMinimum()));
        yAxis.setUpperBound(Math.max(data1.getMaximum(),data2.getMaximum()));

        scatterplot = new ScatterChart<Number, Number>(xAxis,yAxis);
        AnchorPane.setTopAnchor(scatterplot, 0.0);
        AnchorPane.setBottomAnchor(scatterplot, 0.0);
        AnchorPane.setLeftAnchor(scatterplot, 0.0);
        AnchorPane.setRightAnchor(scatterplot, 0.0);
        scatterplot.setLayoutX(20);
        scatterplot.setLayoutY(40);
    }

    /** Initializes all view variables **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
