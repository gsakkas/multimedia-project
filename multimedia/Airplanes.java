package multimedia;

public class Airplanes {
	private int SpeedUpDown;
	private int MaxSpeed;
	private int MaxFuelCapacity;
	private int FuelConsumption;
	private int MaxHeight;
	private int RateUpDown;
	private int Type;

	public Airplanes(int type, int sud, int ms, int mfc, int mh, int rud, int fc) {
		this.Type = type;
		this.SpeedUpDown = sud;
		this.MaxSpeed = ms;
		this.MaxFuelCapacity = mfc;
		this.MaxHeight = mh;
		this.RateUpDown = rud;
		this.FuelConsumption = fc;
	}
	
	public int getType() {
		return Type;
	}

	public int getMaxSpeed() {
		return this.MaxSpeed;
	}
	
	public int getMaxHeight() {
		return this.MaxHeight;
	}
	
	public int getMaxFuelCapacity() {
		return this.MaxFuelCapacity;
	}
	
	public int getFuelConsumption() {
		return this.FuelConsumption;
	}
}