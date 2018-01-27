package multimedia;

class Airports {
	private int ID;
	private String Name;
	private int Category;
	private boolean Open;
	private String Direction;

	public void Airports(int id, String name, int cat, boolean open, String dir){
		if (cat < 0 || cat > 3)
			throw new IllegalArgumentException("Not valid category!");
		if (!"NSWE".contains(dir))
			throw new IllegalArgumentException("Not valid category!");
		this.ID = id;
		this.Name = name;
		this.Category = cat;
		this.Open = open;
		this.Direction = dir;
	}
}