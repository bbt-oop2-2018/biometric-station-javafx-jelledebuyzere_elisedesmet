/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometricstationfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mqttservice.*;

/**
 *
 * @author Jelle
 */
public class FXMLDocumentController implements Initializable, IMqttMessageHandler {

    @FXML
    private AnchorPane pane;
    @FXML
    private TextField tempField;
    @FXML
    private TextField heartField;
    @FXML
    private TextField accXField;
    @FXML
    private TextField accYField;
    @FXML
    private TextField accZField;
    @FXML
    private LineChart<String, Number> temperatureChart;
    @FXML
    private LineChart<String, Number> heartbeatChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private CategoryAxis hx;

    //Variables for the linechart.
    XYChart.Series<String, Number> temperatureValues = new XYChart.Series();
    XYChart.Series<String, Number> heartbeatValues = new XYChart.Series();

    private int xValue = 0;
    private int xHeart = 0;
    String[] data;

    private Service serviceTemp;
    private Service serviceAcc;
    private Service servicePulse;

    public FXMLDocumentController() {
        serviceTemp = new Service("Elise", "Temperature");
        serviceAcc = new Service("Elise", "Accelerometer");
        servicePulse = new Service("Elise", "Heartpulse");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Welcome to our Biometric datacenter!");
        x.setLabel("Seconds");
        hx.setLabel("Seconds");

        temperatureValues.setName("Temperature Data");
        heartbeatValues.setName("Heartbeat Data");

        serviceTemp.setMessageHandler(this);
        serviceAcc.setMessageHandler(this);
        servicePulse.setMessageHandler(this);

        heartbeatChart.getData().add(heartbeatValues); //Data updates every second.
        temperatureChart.getData().add(temperatureValues);

        disconnectClientOnClose();

    }

    @Override
    public void messageArrived(String channel, String message) {

        switch (channel) {
            case "Temperature":
                Platform.runLater(() -> {
                    /* If you need to update a GUI component from a non-GUI thread, you can use that to put your update in a queue and it will be handle by the GUI thread as soon as possible. */
                    tempField.setText(message + "°C");

                    temperatureValues.getData().add(new XYChart.Data<>(Integer.toString(xValue), Double.parseDouble(message)));
                    if (checkValue(xValue)) {
                        temperatureValues.getData().remove(0);
                    }
                    xValue++;

                });
                break;

            case "Accelerometer":
                Platform.runLater(() -> {
                    data = message.split(";");
                    accXField.setText(data[0]);
                    accYField.setText(data[1]);
                    accZField.setText(data[2]);
                });
                break;

            case "Heartpulse":
                Platform.runLater(() -> {
                    heartField.setText(message + " BPM");

                    heartbeatValues.getData().add(new XYChart.Data<>(Integer.toString(xHeart), Integer.parseInt(message)));
                    if (checkValue(xHeart)) {
                        heartbeatValues.getData().remove(0);
                    }
                    xHeart++;
                });
                break;
            case "default":
                break;
        }
    }

    public boolean checkValue(int number) {
        return number > 10;
    }

    private void disconnectClientOnClose() {
        // Source: https://stackoverflow.com/a/30910015
        pane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        ((Stage) newWindow).setOnCloseRequest((event) -> {
                            serviceTemp.disconnect();
                            servicePulse.disconnect();
                            serviceAcc.disconnect();
                        });
                    }
                });
            }
        });
    }
}
