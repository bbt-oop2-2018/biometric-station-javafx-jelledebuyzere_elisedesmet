/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometricstationfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mqttservice.*;

/**
 *
 * @author jelle
 */
public class FXMLDocumentController implements Initializable, IMqttMessageHandler {

    @FXML
    private Label label;
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
    private NumberAxis y;
    @FXML
    private CategoryAxis hx;
    @FXML
    private NumberAxis hy;

    //for the linecharts
    XYChart.Series<String, Number> temperatureValues = new XYChart.Series();
    XYChart.Series<String, Number> heartbeatValues = new XYChart.Series();

    private int xValue = 0;
    private int xHeart = 0;
    String[] data;

    private Service serviceTemp;
    private Service serviceAcc;
    private Service servicePulse;

    public FXMLDocumentController() {
        serviceTemp = new Service("Jelle", "Temperature");
        serviceAcc = new Service("Jelle", "Accelerometer");
        servicePulse = new Service("Jelle", "Heartpulse");

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

        heartbeatChart.getData().add(heartbeatValues); //updates every second
        temperatureChart.getData().add(temperatureValues);

    }

    @Override
    public void messageArrived(String channel, String message) {

        switch (channel) {
            case "Temperature":
                Platform.runLater(() -> {
                    tempField.setText(message + "°C");
                    
                    temperatureValues.getData().add(new XYChart.Data<>(Integer.toString(xValue), Double.parseDouble(message)));
                    if (checkValue(xValue)) {
                        temperatureValues.getData().remove(0);
                    }
                    xValue++;
                });
                break;

            case "Accelerometer":
                data = message.split(";");
                accXField.setText(data[0]);
                accYField.setText(data[1]);
                accZField.setText(data[2]);
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
}
