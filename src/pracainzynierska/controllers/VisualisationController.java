package pracainzynierska.controllers;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import pracainzynierska.utils.EulerAngles;
import pracainzynierska.utils.EulerDataUtil;
import pracainzynierska.utils.MyNumber;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class VisualisationController implements Initializable {

    final MyNumber myNum = new MyNumber();
    private MainController mainController;
    private List<EulerAngles> eulerAnglesList1;
    private List<EulerAngles> eulerAnglesList2;
    private Map<String, List<EulerAngles>> eulerAnglesMap;
    @FXML
    private Circle sensor1;

    @FXML
    private Circle sensor2;

    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        myNum.setNumber(0);
        myNum.numberProperty().addListener((observable, oldValue, newValue) -> progressBar.progressProperty().bind(myNum.numberProperty()));

        try {
            eulerAnglesMap = EulerDataUtil.readExaminationData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        eulerAnglesList1 = eulerAnglesMap.get("euler0");
        eulerAnglesList2 = eulerAnglesMap.get("euler1");

        Polyline polyline = new Polyline();
        for (int i = 0; i < eulerAnglesList1.size(); i++) {
            polyline.getPoints().add(eulerAnglesList1.get(i).getEulerX());
            polyline.getPoints().add(eulerAnglesList1.get(i).getEulerY());
            myNum.setNumber((double) i / eulerAnglesList1.size());
        }

        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(eulerAnglesList1.size() * 0.5));
        transition.setNode(sensor1);
        transition.setPath(polyline);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();

        Polyline polyline2 = new Polyline();
        for (int i = 0; i < eulerAnglesList2.size(); i++) {
            polyline2.getPoints().add(eulerAnglesList2.get(i).getEulerX());
            polyline2.getPoints().add(eulerAnglesList2.get(i).getEulerY());
        }

        PathTransition transition2 = new PathTransition();
        transition2.setDuration(Duration.seconds(eulerAnglesList2.size() * 0.5));
        transition2.setNode(sensor2);
        transition2.setPath(polyline2);
        transition2.setCycleCount(PathTransition.INDEFINITE);
        transition2.play();

        final int[] secondsPassed = {0};
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (secondsPassed[0] < transition2.getDuration().toSeconds()) {
                    secondsPassed[0]++;
                    myNum.setNumber(secondsPassed[0] / transition2.getDuration().toSeconds());
                } else {
                    secondsPassed[0] = 0;
                }
            }
        };

        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    @FXML
    public void linkback() {
        mainController.loadDataCollectionScreen();
    }

    void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
