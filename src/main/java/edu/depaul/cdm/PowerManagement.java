package edu.depaul.cdm;

public class PowerManagement {
    private int batteryPower;
    private int powerThreshold;
	private Boolean lowPower;
	private int buffer = 5; //Just in case
	private int xPos,yPos;

	PowerManagement(int xPos,int yPos)
	{
		this.batteryPower = 250;
		this.powerThreshold=0;
		this.lowPower = false;
		this.xPos =xPos;
		this.yPos=yPos;
	}

    public void setBatteryPower(int batteryPower) {
        this.batteryPower = batteryPower;
    }

    public int getBatteryPower() {
        return batteryPower;
    }

    public Boolean lowPowerAlert(){ //Will return true if power is less then 20%
        if(lowPower){return true;}
        return false;
    }
    public void vacuum(FloorTypeSensor floorTypeSensor){
		String floorType = floorTypeSensor.checkFloorType(xPos,yPos).name();
        switch (floorType){
            case "LOW": // bare floor
                updateThreshold(1); // uses 1 unit od power
                break;
            case "MED": //low-pile carpet
                updateThreshold(2); //uses 2 units of power
                break;
            case "HIGH": //high-pile carpet
                updateThreshold(3); //Uses 3 units of power
                break;
			case "OBS":
				break;
            default:
                break;
        }
    }

	void setPowerThreshold(int x)
	{
		this.powerThreshold = x;
	}

	int getPowerThreshold()
	{
		return this.powerThreshold;
	}

	//TODO
	//Called every time after battery decrease from move
	void updateThreshold(int x)
	{
		int currentBattery = getBatteryPower();
		int lowBattery = (int) (currentBattery*.8);	//This is the 80% battery threshold
		int currentThreshold = getPowerThreshold();
		
		currentThreshold += x;
		setPowerThreshold(currentThreshold);

		//If powerThreshold is greater then 80% battery life then set low power flag to true
		if(lowBattery > (currentThreshold+buffer) )
		{
			lowPower=true;
		}
	}

	public void recharge() throws InterruptedException{
		Thread.sleep(2000);
		setPowerThreshold(0);
	}
}
