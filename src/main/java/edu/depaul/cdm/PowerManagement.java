package edu.depaul.cdm;

public class PowerManagement {
    private int batteryPower = 250;
    private int powerThreshold = (int) (250*.2);    //power threshold is at 20%

    public void setBatteryPower(int batteryPower) {
        this.batteryPower = batteryPower;
    }

    public int getBatteryPower() {
        return batteryPower;
    }

    public Boolean lowPowerAlert(){ //Will return true if power is less then 20%
        if(batteryPower<powerThreshold){return true;}
        return false;
    }
    public void vacuum(){

    }


}
