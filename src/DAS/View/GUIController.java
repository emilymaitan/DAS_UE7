package DAS.View;

import DAS.Data.Integers;
import DAS.Functions.A2.Boxplot;
import DAS.Functions.A3.Correlation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GUIController implements Initializable {

    @FXML
    AnchorPane math_top;
    @FXML
    TextArea math_text;

    @FXML
    Canvas boxplot_chart;

    final double BOXPLOT_ROOT_X = 20;   // top left
    final double BOXPLOT_ROOT_Y = 40;   // top right
    final double BOXPLOT_HEIGHT = 400;
    final double BOXPLOT_WIDTH = 800;

    final double BOX_WIDTH = 100;       // with of a box in the boxplot

    @FXML
    ScatterChart<Number, Number> scatterplot;

    public void flushResetWindow() {
        while (math_top.getChildren().size() > 0) math_top.getChildren().remove(0);
        math_text.setText("");
    }

    /* ############################## A2 - DRAWING BOXPLOTS ##############################  */

    public void drawBoxplot(ActionEvent actionEvent) {
        flushResetWindow();

        Boxplot box1 = new Boxplot(Integers.getIntegers1());
        Boxplot box2 = new Boxplot(Integers.getIntegers2());
        double max = Math.max(box1.getMaximum(),box2.getMaximum());
        double min = Math.min(box1.getMinimum(),box2.getMinimum());

        initializeBoxplotView();

        GraphicsContext gc = boxplot_chart.getGraphicsContext2D();

        gc.strokeLine(BOXPLOT_ROOT_X+20,BOXPLOT_HEIGHT,BOXPLOT_WIDTH,BOXPLOT_HEIGHT);       // xAxis
        gc.strokeLine(BOXPLOT_ROOT_X+20,BOXPLOT_ROOT_Y,BOXPLOT_ROOT_X+20,BOXPLOT_HEIGHT);   // yAxis

        double box1_leftX = (BOXPLOT_WIDTH/4+20) - BOX_WIDTH/2;
        double box2_leftX = (BOXPLOT_WIDTH/2+20 + BOXPLOT_WIDTH/4) - BOX_WIDTH/2;
        double unit = (BOXPLOT_HEIGHT-BOXPLOT_ROOT_Y)/(max + min);

        // Achsenbeschriftung
        gc.strokeText(String.valueOf(max),BOXPLOT_ROOT_X,ty(unit,max));
        gc.strokeText(String.valueOf(max/2),BOXPLOT_ROOT_X,ty(unit,max/2));
        gc.strokeText(String.valueOf(max/4),BOXPLOT_ROOT_X,ty(unit,max/4));
        gc.strokeText(String.valueOf(max/2+max/4),BOXPLOT_ROOT_X,ty(unit,max/2+max/4));
        gc.strokeText(String.valueOf(min),BOXPLOT_ROOT_X,ty(unit,min));

        gc.setStroke(Color.LIGHTBLUE);
        gc.strokeLine(BOXPLOT_WIDTH/2+20,BOXPLOT_HEIGHT,BOXPLOT_WIDTH/2+20,BOXPLOT_ROOT_Y); // seperator

        gc.setStroke(Color.BLUE);
        strokeBoxplot(gc,unit,Integers.getIntegers1(),box1_leftX);
        gc.setStroke(Color.RED);
        strokeBoxplot(gc,unit,Integers.getIntegers2(),box2_leftX);

        math_top.getChildren().add(boxplot_chart);
        math_text.setText(box1.toString());
    }

    private void strokeBoxplot(GraphicsContext gc, double unit, ArrayList<Integer> values, double leftbound) {
        Boxplot box = new Boxplot(values);
        gc.strokeLine(leftbound,ty(unit, box.calcUpperBound()),leftbound+BOX_WIDTH,ty(unit, box.calcUpperBound()));   // upper
        gc.strokeLine(leftbound,ty(unit, box.calcLowerBound()),leftbound+BOX_WIDTH,ty(unit, box.calcLowerBound()));   // lower
        gc.strokeLine(leftbound+BOX_WIDTH/2,ty(unit, box.calcUpperBound()),leftbound+BOX_WIDTH/2,ty(unit, box.calcLowerBound()));
        gc.strokeRect(leftbound,ty(unit,box.getQuartile75()),BOX_WIDTH,unit*(box.getQuartile75()-box.getQuartile25()));

        if (box.calcUpperBound() != box.getMaximum()) {
            values.stream().filter(i -> i > box.calcUpperBound()).forEach(i -> {
                System.out.println("Outlier at " + i);
                gc.strokeOval(leftbound + BOX_WIDTH / 2, ty(unit, i), 2, 2);
            });
        }
    }

    /**
     * Translates a given value into the Y-value of the boxplot coordinate space.
     * @param unit The conversion unit.
     * @param originalY the value to be converted
     * @return Y coordinate of the translated value
     */
    private double ty (double unit, double originalY) {
        return BOXPLOT_HEIGHT-(unit*originalY);
    }

    /**
     * Translates a given value into the X-value of the boxplot coordinate space.
     * @param unit The conversion unit.
     * @param originalX the value to be converted
     * @return X coordinate of the translated value
     */
    private double tx (double unit, double originalX) {
        return unit*originalX+20;
    }

    private double[] trc(double unit, double originalX, double originalY) {
        double[] res = new double[2];
        res[0] = unit*originalX+20;
        res[1] = BOXPLOT_HEIGHT-(unit*originalY+20);
        return res;
    }


     /* ############################## A3 - CORRELATION ##############################  */

    public void drawCorrelation(ActionEvent actionEvent) {
        flushResetWindow();

        Correlation corr1 = new Correlation(Integers.getIntegers1());
        Correlation corr2 = new Correlation(Integers.getIntegers2());
        if (corr1.getValues().size() != corr2.getValues().size()) throw new IllegalArgumentException("Files must have same number of values!");

        initializeScatterplotView(new Boxplot(Integers.getIntegers1()), new Boxplot(Integers.getIntegers2()));
        XYChart.Series<Number, Number> data = new XYChart.Series<>();
        data.setName("Correlation Data");

        System.out.println("Values1: " + Integers.getIntegers1());

        for (int i = 0; i < corr1.getValues().size(); i++) {
            data.getData().add(new XYChart.Data<>(corr1.getValues().get(i),corr2.getValues().get(i)));
            System.out.println("PAIR: @ " + i + " (" + corr1.getValues().get(i) + ", " + corr2.getValues().get(i) + ")");
        }

        scatterplot.getData().add(data);

        math_top.getChildren().add(scatterplot);
    }

     /* ############################## A4 - Confidence Interval ##############################  */
     public void drawConfidence(ActionEvent actionEvent) {
         flushResetWindow();

         initializeConfidenceView();

     }

     /* ############################## View Container Initializers ##############################  */

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
        xAxis.setLabel("File 1");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(Math.min(data1.getMinimum(),data2.getMinimum())-5);
        xAxis.setUpperBound(Math.max(data1.getMaximum(),data2.getMaximum())+5);

        yAxis.setLabel("File 2");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setLowerBound(Math.min(data1.getMinimum(),data2.getMinimum())-5);
        yAxis.setUpperBound(Math.max(data1.getMaximum(),data2.getMaximum())+5);

        scatterplot = new ScatterChart<>(xAxis,yAxis);
        scatterplot.setTitle("Comparison of Values in Files 1 and 2");
        AnchorPane.setTopAnchor(scatterplot, 0.0);
        AnchorPane.setBottomAnchor(scatterplot, 0.0);
        AnchorPane.setLeftAnchor(scatterplot, 0.0);
        AnchorPane.setRightAnchor(scatterplot, 0.0);
        scatterplot.setLayoutX(20);
        scatterplot.setLayoutY(40);
    }

    private void initializeConfidenceView() {

    }

    /** Initializes all view variables **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing... ");
    }
}
