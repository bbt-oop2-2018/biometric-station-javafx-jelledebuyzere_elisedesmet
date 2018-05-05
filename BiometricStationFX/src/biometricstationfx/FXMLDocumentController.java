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
import javafx.scene.control.TextArea;
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

    private Service service = new Service("Jelle", "Temperature");

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        service.setMessageHandler(this);
    }

    @Override
    public void messageArrived(String channel, String message) {
        if (channel.equals("Temperature")) {

            System.out.println("Message: " + message);

        }
    }
}
