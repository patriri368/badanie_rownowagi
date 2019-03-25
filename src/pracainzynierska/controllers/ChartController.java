package pracainzynierska.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import pracainzynierska.utils.EulerAngles;
import pracainzynierska.utils.EulerDataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ChartController implements Initializable {


    DataCollectionController dataCollectionController = new DataCollectionController();
    private MainController mainController;

    @FXML
    private ScatterChart<Number, Number> scatterChart1;
    @FXML
    private ScatterChart<Number, Number> scatterChart2;
    @FXML
    private ScatterChart<Number, Number> scatterChart3;

    private XYChart.Series<Number, Number> euler0SerieX;
    private XYChart.Series<Number, Number> euler0SerieY;
    private XYChart.Series<Number, Number> euler0SerieZ;
    private XYChart.Series<Number, Number> euler1SerieX;
    private XYChart.Series<Number, Number> euler1SerieY;
    private XYChart.Series<Number, Number> euler1SerieZ;

    private List<EulerAngles> eulerAnglesList1;
    private List<EulerAngles> eulerAnglesList2;
    private Map<String, List<EulerAngles>> eulerAnglesMap;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scatterChart1.getXAxis().setLabel("Numer pomiaru");
        scatterChart1.getYAxis().setLabel("Kąt[°]");
        scatterChart2.getXAxis().setLabel("Numer pomiaru");
        scatterChart2.getYAxis().setLabel("Kąt[°]");
        scatterChart3.getXAxis().setLabel("Numer pomiaru");
        scatterChart3.getYAxis().setLabel("Kąt[°]");

        scatterChart1.setLegendSide(Side.RIGHT);
        scatterChart2.setLegendSide(Side.RIGHT);
        scatterChart3.setLegendSide(Side.RIGHT);

        euler0SerieX = new XYChart.Series<>();
        euler0SerieX.setName("Czujnik 0");
        euler0SerieY = new XYChart.Series<>();
        euler0SerieY.setName("Czujnik 0");
        euler0SerieZ = new XYChart.Series<>();
        euler0SerieZ.setName("Czujnik 0");
        euler1SerieX = new XYChart.Series<>();
        euler1SerieX.setName("Czujnik 1");
        euler1SerieY = new XYChart.Series<>();
        euler1SerieY.setName("Czujnik 1");
        euler1SerieZ = new XYChart.Series<>();
        euler1SerieZ.setName("Czujnik 1");

        try {
            eulerAnglesMap = EulerDataUtil.readExaminationData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        eulerAnglesList1 = eulerAnglesMap.get("euler0");
        eulerAnglesList2 = eulerAnglesMap.get("euler1");

        for (int i = 0; i < eulerAnglesList1.size(); i++) {
            euler0SerieX.getData().add(new XYChart.Data(String.valueOf(i), eulerAnglesList1.get(i).getEulerX()));
            euler0SerieY.getData().add(new XYChart.Data(String.valueOf(i), eulerAnglesList1.get(i).getEulerY()));
            euler0SerieZ.getData().add(new XYChart.Data(String.valueOf(i), eulerAnglesList1.get(i).getEulerZ()));
        }

        for (int i = 0; i < eulerAnglesList2.size(); i++) {
            euler1SerieX.getData().add(new XYChart.Data(String.valueOf(i), eulerAnglesList2.get(i).getEulerX()));
            euler1SerieY.getData().add(new XYChart.Data(String.valueOf(i), eulerAnglesList2.get(i).getEulerY()));
            euler1SerieZ.getData().add(new XYChart.Data(String.valueOf(i), eulerAnglesList2.get(i).getEulerZ()));
        }

        scatterChart1.getData().addAll(euler0SerieX, euler1SerieX);
        scatterChart2.getData().addAll(euler0SerieY, euler1SerieY);
        scatterChart3.getData().addAll(euler0SerieZ, euler1SerieZ);
    }

    @FXML
    void linkback() {
        mainController.loadDataCollectionScreen();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
