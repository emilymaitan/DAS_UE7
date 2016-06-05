package DAS.View;

import DAS.Data.Integers;
import DAS.Functions.A2.Boxplot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;


public class GUIController implements Initializable {

    @FXML
    AnchorPane math_top;

    @FXML
    TextArea math_text;

    @FXML
    AreaChart<String, Number> boxplot_bg = null;

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

        

        math_text.setText(box1.toString());
    }

    public void drawCorrelation(ActionEvent actionEvent) {
        flushResetWindow();

        initializeScatterplotView(new Boxplot(Integers.getIntegers1()),new Boxplot(Integers.getIntegers2()));
        XYChart.Series<String,Number> data = new XYChart.Series<>();

        math_top.getChildren().add(scatterplot);
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
    }

    /** Initializes all view variables **/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
