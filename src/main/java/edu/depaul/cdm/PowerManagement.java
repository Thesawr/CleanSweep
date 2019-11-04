package edu.depaul.cdm;

public class PowerManagement {
    private int batteryPower;
    private int powerThreshold;
	private Boolean lowPower;
	private int buffer = 6; //Just in case
	private int xPos,yPos;
	private int[][] floor;

	private static volatile PowerManagement instance = null;

	private PowerManagement() {}

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
		this.xPos =xPos;
		this.yPos=yPos;
	}

	public void set_floor(int[][] floor_)
	{
		this.floor = floor_;
	}

	public void set_x_y_coords(int xPos,int yPos)
	{
		this.xPos =xPos;
		this.yPos=yPos;
	}

	public int switch_floor_types(int x, int y)
	{
		// if bare floor
		if(this.floor[x][y] == 0)
		{
			return 1;
		}
		// if low level
		else if(this.floor[x][y] == 1)
		{
			return 2;
		}
		// if high level
		else if(this.floor[x][y] == 2)
		{
			return 3;
		}
		// if start
		else if(this.floor[x][y] == 6)
		{
			return 1;
		}
		return 0;
	}

	public int average_cost(int curr_cell, int next_cell)
	{
		int temp = (curr_cell + next_cell) / 2;

		return temp;
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

	void consumeBattery(int powerused)
	{
		int currentBattery = getBatteryPower();
		currentBattery -= powerused;
		setBatteryPower(currentBattery);
	}
	
	public void recharge() throws InterruptedException{
		Thread.sleep(2000);
		setPowerThreshold(0);
		setBatteryPower(250);
	}
}
