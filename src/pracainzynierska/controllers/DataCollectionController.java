package pracainzynierska.controllers;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

import static jssc.SerialPort.MASK_RXCHAR;

public class DataCollectionController implements Initializable {

    @FXML
    private ComboBox comboBox1;

    @FXML
    private Button connectButton;

    @FXML
    private Circle LED;

    @FXML
    private Label connectLabel;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private TextArea textArea1;

    private MainController mainController;

    public String getPortName() {
        return portName;
    }

    private String portName;

    private ObjectProperty<String> selectedPort = new SimpleObjectProperty<>();
    private SerialPort arduinoPort = null;
    private SerialPort serialPort = null;
    private PrintWriter saveDataFile;
    static boolean isConnected = false;
    public String st;
    Boolean isReading = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea1.setVisible(false);
        textArea1.setEditable(false);
        detectPort();
    }

    @FXML
    void detectPort() {
        ObservableList<String> portList = FXCollections.observableArrayList();

        String[] serialPortNames = SerialPortList.getPortNames();
        for(String name: serialPortNames){
            System.out.println(name);
            portList.add(name);
        }

        comboBox1.setItems(portList);
        setSelectedPort((String) comboBox1.getSelectionModel().getSelectedItem());
        portName = selectedPort.getValue();
    }

    @FXML
    void connect() {
        serialPort = new SerialPort(portName);

        if(connectButton.getText().equals("Połącz") && !selectedPort.getValue().equals(null)) {
            connectArduino(serialPort);
        }
        else if (connectButton.getText().equals("Połącz") && selectedPort.getValue().equals(null)){
            System.out.println("Wybierz port");
        }
        else disconnectArduino();
    }

    @FXML
    void startReading() throws FileNotFoundException, SerialPortException {
        saveDataFile = new PrintWriter("data.txt");

        serialPort.removeEventListener();
        serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
            if(serialPortEvent.isRXCHAR()) try {
                st = serialPort.readString(serialPortEvent.getEventValue());
                System.out.println(st);
                saveDataFile.println(st);
                serialPort.writeString("1");
                serialPort.writeString("2");
            } catch (SerialPortException ex) {
                ex.printStackTrace();
            }
        });

                saveDataFile.println(serialPort.readString());
                isReading = true;

    }

    @FXML
    void stopReading() {
        saveDataFile.close();
        isReading = false;
    }

    private void connectArduino(SerialPort serialPort) {


        System.out.println("connectArduino");

        try {
            serialPort.openPort();
            serialPort.setParams(
                    SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setEventsMask(MASK_RXCHAR);

            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
            if(serialPortEvent.isRXCHAR()) try {
                st = serialPort.readString(serialPortEvent.getEventValue());
                System.out.println(st);
                serialPort.writeString("1");
                serialPort.writeString("2");
                String data = String.valueOf(st);
                String[] lines = data.split(System.getProperty("line.separator"));
                Platform.runLater(() -> {
                    for(int i = 0; i < lines.length; i++){
                        textArea1.appendText(lines[i] + "\r\n");
                    }
                });
            } catch (SerialPortException ex) {
                ex.printStackTrace();
            }
        });

            arduinoPort = serialPort;
            LED.setFill(Color.GREEN);
            connectLabel.setText("Połączono");
            connectButton.setText("Rozłącz");
            startButton.setDisable(false);
            stopButton.setDisable(false);
            isConnected = true;

        } catch (SerialPortException ex) {
            ex.printStackTrace();
            System.out.println("SerialPortException: " + ex.toString());
        }
    }

    private void disconnectArduino(){
        System.out.println("disconnectArduino()");

        if(arduinoPort != null){
            try {
                arduinoPort.removeEventListener();

                if(arduinoPort.isOpened()){
                    arduinoPort.closePort();
                    LED.setFill(Color.RED);
                    connectLabel.setText("Rozłączono");
                    connectButton.setText("Połącz");
                    startButton.setDisable(true);
                    stopButton.setDisable(true);
                    isConnected = false;
                }

            } catch (SerialPortException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void drawChart() {
        if(isReading)
            saveDataFile.close();

        if(connectButton.getText().equals("Rozłącz"))
            disconnectArduino();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/pracainzynierska/screens/ChartScreen.fxml"));

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChartController chartController = loader.getController();
        chartController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    void visualise() {
        if(isReading)
            saveDataFile.close();

        if(connectButton.getText().equals("Rozłącz"))
            disconnectArduino();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/pracainzynierska/screens/VisualisationScreen.fxml"));

        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        VisualisationController visualisationController = loader.getController();
        visualisationController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    void viewData() {
        if(!textArea1.isVisible()) {
        textArea1.setVisible(true);
        }
        else {
            textArea1.setVisible(false);
        }
    }


    public void setSelectedPort(String selectedPort) {
        this.selectedPort.set(selectedPort);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
