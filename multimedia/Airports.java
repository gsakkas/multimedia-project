package multimedia;

import java.io.*;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.util.ArrayList;

class Airports {
	private int ID;
	private int cordX, cordY;
	private int diadX, diadY;
	private String Name;
	private int Category;
	private boolean Open;
	private String Direction;
	private JLabel img;
	
	public Airports(int id, int cx, int cy, String name, int cat, boolean open, String dir){
		if (cat < 0 || cat > 3)
			throw new IllegalArgumentException("Not valid category!");
		if (!"NSWE".contains(dir))
			throw new IllegalArgumentException("Not valid category!");
		this.ID = id;
		this.cordX = cx;
		this.cordY = cy;
		
		this.diadX = cx;
		this.diadY = cy;

		if (dir.equals("N")) this.diadX--;
		else if (dir.equals("E")) this.diadY++;
		else if (dir.equals("S")) this.diadX++;
		else if (dir.equals("W")) this.diadY--;
		
		this.Name = name;
		this.Category = cat;
		this.Open = open;
		this.Direction = dir;
		this.img = new JLabel(new ImageIcon("./SimulationIcons/airport.png"));
	}

	static public void ReadAirports(String InputFile, ArrayList<Airports> list) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(InputFile));
		}
		catch (FileNotFoundException e) {
			System.out.println(InputFile + " not found!");
			System.exit(-1);
		}
		
		String line = null;
		while((line = br.readLine()) != null){
			String[] values = line.split(",");
			int id = Integer.parseInt(values[0]);
			int cx = Integer.parseInt(values[1]);
			int cy = Integer.parseInt(values[2]);
			String name = values[3];
			String dir = "N";
			if (values[4].equals("1")) dir = "N";
			else if (values[4].equals("2")) dir = "E";
			else if (values[4].equals("3")) dir = "S";
			else if (values[4].equals("4")) dir = "W";
			int cat = Integer.parseInt(values[5]);
			boolean open = (values[6] == "1");
			list.add(new Airports(id, cx, cy, name, cat, open, dir));
		}
		br.close();
	}

	public int getID() {
		return this.ID;
	}

	public int getX() {
		return this.cordX;
	}

	public int getY() {
		return this.cordY;
	}
	
	public int getdX() {
		return this.diadX;
	}

	public int getdY() {
		return this.diadY;
	}

	public JLabel getImg() {
		return this.img;
	}
}