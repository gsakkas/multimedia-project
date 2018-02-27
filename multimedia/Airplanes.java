package multimedia;

public class Airplanes {
	private int SpeedUpDown;
	private int MaxSpeed;
	private int MaxFuelCapacity;
	private int FuelConsumption;
	private int MaxHeight;
	private int RateUpDown;
	private int Type;

	public Airplanes(int sud, int ms, int mfc, int fc, int mh, int rud, int type) {
		this.SpeedUpDown = sud;
		this.MaxSpeed = ms;
		this.MaxFuelCapacity = mfc;
		this.FuelConsumption = fc;
		this.MaxHeight = mh;
		this.RateUpDown = rud;
		this.Type = type;
	}
}