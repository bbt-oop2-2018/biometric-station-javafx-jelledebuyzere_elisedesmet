/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometricstationfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
    private TextArea area;
    @FXML
    private TextField tempField;
    @FXML
    private TextField heartField;
    @FXML
    private TextField accXField;
    @FXML 
//    private LineChart temperatureChart;
    private Double temp;
    
    
    private double xValue = 0.0;
    
    
    //private XYChart.Series temperatureValues;
    private Service serviceTemp= new Service("Jelle", "Temperature");
    private Service serviceAcc= new Service("Jelle", "Accelerometer");
    private Service servicePulse= new Service("Jelle", "Heartpulse");
    

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("hello world!");
        serviceTemp.setMessageHandler(this);
        serviceAcc.setMessageHandler(this);
        servicePulse.setMessageHandler(this);
        
//        temperatureValues = new XYChart.Series();
//        temperatureValues.setName("Temperature (Celsius)");
//        temperatureChart.getData().add(temperatureValues);

    }

    @Override
    public void messageArrived(String channel, String message) {
//        temp = Double.parseDouble(message);
//        System.out.println("this is a parsed data " + temp);
//        temperatureValues.getData().add(new XYChart.Data(xValue, temp));
//        xValue += 0.1;
        System.out.println("temperature reading");

        switch (channel) { //TODO: testing (without break; it will run but after a couple of seconds it will crash and give an arrayoutofbounds exception
            case "Temperature":
                 System.out.println("temperature reading");//test
                tempField.getText().trim();
                tempField.setText(message + "°C");
                
            case "Accelerometer":
                //System.out.println(message);
                
            case "Heartpulse":
                //System.out.println("Message: " + message); //test
                heartField.getText().trim();
                heartField.setText(message + "BPM");
            case "default":
                //tempField.getText().trim();
                break;
                
        }

//        if (channel.equals("Temperature")) { //other way 
//        }
    }
}
