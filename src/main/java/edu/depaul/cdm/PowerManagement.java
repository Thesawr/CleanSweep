package edu.depaul.cdm;

public class PowerManagement {
    private double batteryPower;
    private double powerThreshold;
	private Boolean lowPower;
	private double buffer = 6; //Just in case
	private int[][] floor;

	private static volatile PowerManagement instance = null;

	private PowerManagement() {
		this.batteryPower = 250;
		this.powerThreshold=0;
		this.lowPower = false;
	}

	public static PowerManagement getInstance()
	{
		if (instance == null)
		{
			synchronized(PowerManagement.class)
			{
				if (instance == null)
				{
					instance = new PowerManagement();
				}
			}
		}
		return instance;
	}


	public void set_floor(int[][] floor_)
	{
		this.floor = floor_;
	}



	public void switch_floor_types(int currentX, int currentY,int previousX, int previousY)
	{

		//TODO CHECK FOR tiles numbered 4 & 6 and add methidology for it
		System.out.println(String.format("C: (%d,%d), P: (%d,%d)",currentX,currentY,previousX,previousY));	//Statement to see if switching floor types
		int currentPos = floor[currentX][currentY]+1;	//Need to add 1 so that floor properly represents the power consumption need for that floor type
		int previousPos = floor[previousX][previousY]+1;
		if (currentPos==4||currentPos==6){
			currentPos=1;
		}
		if (previousPos==4||previousPos==6){
			previousPos=1;
		}
		double powerConsumption = average_cost(currentPos,previousPos);
		updateThreshold(powerConsumption);


	}

	private double average_cost(double curr_cell, double next_cell)
	{
		return (curr_cell + next_cell) / 2.0;
	}

//    public void setBatteryPower(double batteryPower) {
////        this.batteryPower = batteryPower;
////    }

    public double getBatteryPower() {
        return batteryPower;
    }

    public Boolean lowPowerAlert(){ //Will return true if power is less then 20%
		return lowPower;
    }

    public void vacuum(int xPos, int yPos){
		FloorTypeSensor floorTypeSensor = new FloorTypeSensor(floor);
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

	void setPowerThreshold(double x)
	{
		this.powerThreshold = x;
	}

	double getPowerThreshold()
	{
		return this.powerThreshold;
	}


	//Called every time after battery decrease from move
	public void updateThreshold(double x)	//Main method, always call this when decrementing power
	{
		double currentBattery = getBatteryPower();
		int lowBattery = (int) (batteryPower*.8);	//This is the 80% battery threshold
		double currentThreshold = getPowerThreshold();
		
		currentThreshold += x;
		setPowerThreshold(currentThreshold);

		//If powerThreshold is greater then 80% battery life then set low power flag to true
		if(lowBattery < (currentThreshold+buffer) )
		{
			lowPower=true;
		}

	}

	void consumeBattery(int powerused)
	{
		double currentBattery = getBatteryPower();
		currentBattery -= powerused;
		//setBatteryPower(currentBattery);
	}
	
	public void recharge() throws InterruptedException{
		System.out.println("Recharging!");
		Thread.sleep(2000);
		setPowerThreshold(0);
		lowPower=false;
		System.out.println();
		//setBatteryPower(250);
	}
}
