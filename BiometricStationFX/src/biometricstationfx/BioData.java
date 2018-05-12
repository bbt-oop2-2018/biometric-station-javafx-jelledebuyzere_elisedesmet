/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometricstationfx;

/**
 *
 * @author jelle
 */
public class BioData {
    private double tempData;
    private int heartData;
    
    public BioData() {
    }
    

    public double getTempData() {
        return tempData;
    }

    public void setTempData(double tempData) {
        this.tempData = tempData;
    }

    public int getHeartData() {
        return heartData;
    }

    public void setHeartData(int heartData) {
        this.heartData = heartData;
    }

    
    
}
