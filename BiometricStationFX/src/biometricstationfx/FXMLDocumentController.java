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
    private LineChart temperatureChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

    XYChart.Series temperatureValues = new XYChart.Series();

    private double temp;
    private double xValue = 1;
    String[] data;

    private Service serviceTemp;
    private Service serviceAcc;
    private Service servicePulse;
    private BioData bioData;

    public FXMLDocumentController() {
        serviceTemp = new Service("Jelle", "Temperature");
        serviceAcc = new Service("Jelle", "Accelerometer");
        servicePulse = new Service("Jelle", "Heartpulse");

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Welcome to our Biometric datacenter!");
        serviceTemp.setMessageHandler(this);
        serviceAcc.setMessageHandler(this);
        servicePulse.setMessageHandler(this);
        //TEST
    }

    @Override
    public void messageArrived(String channel, String message) {

        switch (channel) {
            case "Temperature":
                tempField.setText(message + "°C");
                bioData.setTempData(Double.parseDouble(message));
                System.out.println(bioData.getTempData());
                break;

            case "Accelerometer":
                data = message.split(";");
                accXField.setText(data[0]);
                accYField.setText(data[1]);
                accZField.setText(data[2]);
                break;

            case "Heartpulse":
                heartField.setText(message + " BPM");
                break;
            case "default":
                break;
        }
    }
}
