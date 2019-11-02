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
    public void vacuum(int floorType){
        int battery = getBatteryPower();
        switch (floorType){
            case 0: // bare floor
                battery-=1; // uses 1 unit od power
                setBatteryPower(battery);
                break;
            case 1: //low-pile carpet
                battery-=2; //uses 2 units of power
                setBatteryPower(battery);
                break;
            case 2: //high-pile carpet
                battery-=3; //Uses 3 units of power
                setBatteryPower(battery);
                break;
            default:
                break;
        }
    }


	private int battery;
	private int threshold;
	private int buffer = 5; //Just in case
	
	PowerManagement()
	{
		this.battery = 250;
		this.threshold = 0;
	}
	
	void setBattery(int x)
	{
		this.battery = x;
	}
	
	void setThreshold(int x)
	{
		this.threshold = x;
	}
	
	int getBattery()
	{
		return this.battery;
	}
	
	int getThreshold()
	{
		return this.threshold;
	}
	
	//TODO
	//Called every time after battery decrease from move
	void updateThreshold(int x)
	{
		int currentBattery = getBattery();
		int currentThreshold = getThreshold();
		
		currentThreshold += x;
		setThreshold(currentThreshold);
		
		if(currentBattery < (currentThreshold+buffer) )
		{
			//TODO
			//Call method to alert robot
			//Story responsible: Low Power Alert
		}
	}
}
