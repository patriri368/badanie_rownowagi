package pracainzynierska.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    StackPane mainStackPane;

    @FXML
    public void initialize() {
        loadDataCollectionScreen();
    }

    void loadDataCollectionScreen() {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/pracainzynierska/screens/DataCollectionScreen.fxml"));

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataCollectionController dataCollectionController = loader.getController();
        dataCollectionController.setMainController(this);
        setScreen(pane);
    }

    void setScreen(Pane pane) {
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(pane);
    }
}
