package DAS.View;

import DAS.Data.Integers;
import DAS.Logic.A2.Boxplot;
import DAS.Logic.A3.Correlation;
import DAS.Logic.A4.Confidence;
import DAS.Logic.A6.Differences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GUIController implements Initializable {

    @FXML
    AnchorPane math_top;
    @FXML
    AnchorPane math_bot;

    @FXML
    Canvas boxplot_chart;
    final double BOXPLOT_ROOT_X = 20;   // top left
    final double BOXPLOT_ROOT_Y = 40;   // top right
    final double BOXPLOT_HEIGHT = 400;
    final double BOXPLOT_WIDTH = 800;
    final double BOX_WIDTH = 100;       // witdh of a box in the boxplot

    @FXML
    ScatterChart<Number, Number> scatterplot;

    @FXML
    BarChart<String,Number> diffchart_1;
    BarChart<String,Number> diffchart_2;

    /* ############################## CLEAN EVERYTHING ##############################  */

    public void flushResetWindow() {
        while (math_top.getChildren().size() > 0) math_top.getChildren().remove(0);
        while (math_bot.getChildren().size() > 0) math_bot.getChildren().remove(0);
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
        double unit = (BOXPLOT_HEIGHT-BOXPLOT_ROOT_Y)/(Math.abs(max) + Math.abs(min));

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
        //math_text.setText(box1.toString());

        // add text to the plots
        GridPane gridpane = new GridPane();
        AnchorPane.setLeftAnchor(gridpane,0.0);
        AnchorPane.setRightAnchor(gridpane,0.0);
        AnchorPane.setTopAnchor(gridpane,0.0);
        AnchorPane.setBottomAnchor(gridpane,0.0);
        TextArea info1 = new TextArea(box1.toString());
        TextArea info2 = new TextArea(box2.toString());
        info1.setEditable(false);
        info2.setEditable(false);
        gridpane.add(info1,0,0);
        gridpane.add(info2,1,0);
        math_bot.getChildren().add(gridpane);
    }

    private void strokeBoxplot(GraphicsContext gc, double unit, ArrayList<Integer> values, double leftbound) {
        Boxplot box = new Boxplot(values);
        gc.strokeLine(leftbound,ty(unit, box.calcUpperBound()),leftbound+BOX_WIDTH,ty(unit, box.calcUpperBound()));   // upper
        gc.strokeLine(leftbound,ty(unit, box.calcLowerBound()),leftbound+BOX_WIDTH,ty(unit, box.calcLowerBound()));   // lower
        gc.strokeLine(leftbound,ty(unit, box.getQuartile50()), leftbound+BOX_WIDTH,ty(unit, box.getQuartile50()));
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

        for (int i = 0; i < corr1.getValues().size(); i++) {
            data.getData().add(new XYChart.Data<>(corr1.getValues().get(i),corr2.getValues().get(i)));
            //System.out.println("PAIR: @ " + i + " (" + corr1.getValues().get(i) + ", " + corr2.getValues().get(i) + ")");
        }

        scatterplot.getData().add(data);

        math_top.getChildren().add(scatterplot);

        // add text:
        // add text to the plots
        String[] text = {
                "DATASET FILE 1: \n" + corr1.toString(),
                "CORRELATION INFO: \n" +
                        "Correlation Coefficient: " + Correlation.corrCoeff(corr1,corr2) + "\n" +
                        "Covariance: " + Correlation.covariance(corr1,corr2),
                "DATASET FILE 2: \n" + corr2.toString()};
        drawTextCols(math_bot,text);
    }

     /* ############################## A4 - Confidence Interval ##############################  */
     public void drawConfidence(ActionEvent actionEvent) {
         flushResetWindow();
         initializeConfidenceView();
     }

    private void updateConfidence(double level) {
        TextArea left = null, right = null;
        GridPane gridpane = (GridPane)math_top.getChildren().get(1); // I know this
        left = (TextArea) gridpane.getChildren().get(0);
        right = (TextArea) gridpane.getChildren().get(1);

        Double[] bounds1 = Confidence.calcBounds(Integers.getIntegers1(),level);
        Double[] bounds2 = Confidence.calcBounds(Integers.getIntegers2(),level);

        left.setText(
                "DATASET FILE 1: \n" +
                "Lower Bound: " + bounds1[0] + "\n" +
                "Upper Bound: " + bounds1[1] + "\n \n" +
                        "Span: " + (bounds1[1]-bounds1[0]) + "\n" +
                        "Sample Mean: " + Integers.getIntegers1().stream().mapToDouble(Integer::intValue).sum()/Integers.getIntegers1().size()
        );
        right.setText(
                "DATASET FILE 2: \n" +
                        "Lower Bound: " + bounds2[0] + "\n" +
                        "Upper Bound: " + bounds2[1] + "\n \n" +
                        "Span: " + (bounds2[1]-bounds2[0]) + "\n" +
                        "Sample Mean: " + Integers.getIntegers2().stream().mapToDouble(Integer::intValue).sum()/Integers.getIntegers2().size()
        );
    }

    /* ############################## A5 - DIFFERENCES & BARCHARTS ##############################  */
    public void drawBarcharts() {
        flushResetWindow();
        initializeBarchartView();

        XYChart.Series<String, Number> data1 = new XYChart.Series<>();
        XYChart.Series<String, Number> data2 = new XYChart.Series<>();
        data1.setName("File 1");
        data2.setName("File 2");


    }

    /* ############################## A6 - DIFFERENCES & BOXPLOTS ##############################  */
    public void drawDifferences(ActionEvent actionEvent) {
        flushResetWindow();
        initializeBoxplotView();
        ArrayList<Integer> values = Differences.calcDifferences(
          Integers.getIntegers1(),Integers.getIntegers2()
        );
        Boxplot boxplot = new Boxplot(values);

        GraphicsContext gc = boxplot_chart.getGraphicsContext2D();
        gc.strokeLine(BOXPLOT_ROOT_X+20,BOXPLOT_HEIGHT,BOXPLOT_WIDTH,BOXPLOT_HEIGHT);       // xAxis
        gc.strokeLine(BOXPLOT_ROOT_X+20,BOXPLOT_ROOT_Y,BOXPLOT_ROOT_X+20,BOXPLOT_HEIGHT);   // yAxis

        double box_leftX = (BOXPLOT_WIDTH/2+20) - BOX_WIDTH/2;
        double unit = (BOXPLOT_HEIGHT-BOXPLOT_ROOT_Y)/(Math.abs(boxplot.getMaximum()) + Math.abs(boxplot.getMinimum()));
        strokeBoxplot(gc,unit,values,box_leftX);

        // Achsenbeschriftung
        gc.strokeText(String.valueOf(boxplot.getMaximum()),BOXPLOT_ROOT_X,ty(unit,boxplot.getMaximum()));
        gc.strokeText(String.valueOf(boxplot.getMaximum()/2),BOXPLOT_ROOT_X,ty(unit,boxplot.getMaximum()/2));
        gc.strokeText(String.valueOf(boxplot.getMaximum()/4),BOXPLOT_ROOT_X,ty(unit,boxplot.getMaximum()/4));
        gc.strokeText(String.valueOf(boxplot.getMaximum()/2+boxplot.getMaximum()/4),BOXPLOT_ROOT_X,ty(unit,boxplot.getMaximum()/2+boxplot.getMaximum()/4));
        gc.strokeText(String.valueOf(boxplot.getMinimum()),BOXPLOT_ROOT_X,ty(unit,boxplot.getMinimum()));

        math_top.getChildren().add(boxplot_chart);
    }

     /* ############################## View Container Initializers ##############################  */

    private void drawTextCols(AnchorPane parent, String[] text) {
        GridPane gridpane = new GridPane();
        AnchorPane.setLeftAnchor(gridpane,0.0);
        AnchorPane.setRightAnchor(gridpane,0.0);
        AnchorPane.setTopAnchor(gridpane,0.0);
        AnchorPane.setBottomAnchor(gridpane,0.0);

        for (int i = 0; i < text.length; i++) {
            TextArea ta = new TextArea(text[i]);
            ta.setEditable(false);
            gridpane.add(ta, i, 0);
        }
        parent.getChildren().add(gridpane);
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
        ToolBar levels = new ToolBar();
        levels.setPrefWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(levels,0.0);
        AnchorPane.setRightAnchor(levels,0.0);
        AnchorPane.setTopAnchor(levels,0.0);
        AnchorPane.setBottomAnchor(levels,420.0);

        final ToggleGroup lv = new ToggleGroup();

        double[] alphas = {80,90,95,97.5,99};
        for (double a : alphas) {
            RadioButton rb = new RadioButton(String.valueOf(a) + "%");
            rb.setUserData(a/100.0);
            rb.setToggleGroup(lv);
            levels.getItems().add(rb);
        }

        lv.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                updateConfidence((double)newValue.getUserData());
            }
        });

        math_top.getChildren().add(0,levels);


        GridPane gridpane = new GridPane();
        AnchorPane.setLeftAnchor(gridpane,0.0);
        AnchorPane.setRightAnchor(gridpane,0.0);
        AnchorPane.setTopAnchor(gridpane,80.0);
        AnchorPane.setBottomAnchor(gridpane, 0.0);
        gridpane.setPrefWidth(Double.MAX_VALUE);
        TextArea left = new TextArea("Please select a level...");
        TextArea right = new TextArea("Please select a level...");
        left.setEditable(false);
        right.setEditable(false);
        gridpane.add(left,0,0);
        gridpane.add(right,1,0);
        left.setId("conf_left");
        right.setId("conf_right");

        math_top.getChildren().add(1,gridpane);
    }

    private void initializeBarchartView() {
        GridPane gridpane = new GridPane();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Bins");
        yAxis.setLabel("Values");

        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        xAxis2.setLabel("Bins");
        yAxis2.setLabel("Values");

        diffchart_1 = new BarChart<String, Number>(xAxis, yAxis);
        diffchart_2 = new BarChart<String, Number>(xAxis2, yAxis2);

        gridpane.add(diffchart_1,0,0);
        gridpane.add(diffchart_2,1,0);

        AnchorPane.setTopAnchor(gridpane, 0.0);
        AnchorPane.setBottomAnchor(gridpane, 0.0);
        AnchorPane.setLeftAnchor(gridpane, 0.0);
        AnchorPane.setRightAnchor(gridpane, 0.0);

        math_top.getChildren().add(gridpane);
    }

    /** Initializes all view variables **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing... ");
    }
}
