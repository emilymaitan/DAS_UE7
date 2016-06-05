package DAS.View;

import DAS.Data.Integers;
import DAS.Functions.A2.Boxplot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;


public class GUIController {

    @FXML
    AnchorPane math_top;

    @FXML
    TextArea math_text;

    @FXML
    ScatterChart<Integer, Integer> scatterplot;

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
    }
}
