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
import javafx.scene.control.Label;
import mqttservice.*;

/**
 *
 * @author jelle
 */
public class FXMLDocumentController implements Initializable, IMqttMessageHandler {
    
    @FXML
    private Label label;
    
    Service service;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new Service("Jelle", "temperature");
        service.IMqttMessageHandler(this);
    }    

    @Override
    public void messageArrived(String channel, String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
