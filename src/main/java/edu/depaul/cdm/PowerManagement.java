package edu.depaul.cdm;

public class PowerManagement {
    private double batteryPower;
    private double powerThreshold;
	private Boolean lowPower;
	private double buffer = 6; //Just in case
	//private int xPos,yPos;
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
	
	PowerManagement(int xPos,int yPos)
	{
		this.batteryPower = 250;
		this.powerThreshold=0;
		this.lowPower = false;
		//this.xPos =xPos;
		//this.yPos=yPos;
	}

	public void set_floor(int[][] floor_)
	{
		this.floor = floor_;
	}

	/*
	public void set_x_y_coords(int xPos,int yPos)
	{
		this.xPos =xPos;
		this.yPos=yPos;
	}*/

	public void switch_floor_types(int currentX, int currentY,int previousX, int previousY)
	{
		System.out.println(String.format("C: (%d,%d), P: (%d,%d)",currentX,currentY,previousX,previousY));
		int currentPos = floor[currentX][currentY];
		int previousPos = floor[previousX][previousY];
		double powerConsumption = (double) ((currentPos+previousPos)/2.0);
		updateThreshold(powerConsumption);
		// if bare floor
//		if(this.floor[x][y] == 0)
//		{
//			return 1;
//		}
//		// if low level
//		else if(this.floor[x][y] == 1)
//		{
//			return 2;
//		}
//		// if high level
//		else if(this.floor[x][y] == 2)
//		{
//			return 3;
//		}
//		// if start
//		else if(this.floor[x][y] == 6)
//		{
//			return 1;
//		}
//		return 0;
	}

	public int average_cost(int curr_cell, int next_cell)
	{
		int temp = (curr_cell + next_cell) / 2;

		return temp;
	}

    public void setBatteryPower(double batteryPower) {
        this.batteryPower = batteryPower;
    }

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

	//TODO
	//Called every time after battery decrease from move
	void updateThreshold(double x)
	{
		double currentBattery = getBatteryPower();
		int lowBattery = (int) (currentBattery*.8);	//This is the 80% battery threshold
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
		setBatteryPower(currentBattery);
	}
	
	public void recharge() throws InterruptedException{
		Thread.sleep(2000);
		setPowerThreshold(0);
		setBatteryPower(250);
	}
}
