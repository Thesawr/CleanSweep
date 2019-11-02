package edu.depaul.cdm;

public class PowerManagement {

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
