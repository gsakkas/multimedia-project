package multimedia;

import java.io.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JLabel;

public class Flights {
	private int FlightID;
	private int FlightStart;
	private int AirportTakeOff;
	private int AirportLanding;
	private String FlightName;
	private Airplanes AirplaneType;
	private int FlightSpeed;
	private int FlightHeight;
	private int	FlightFuels;
	private int TimeToNextBlock;

	// Helper variables
	private ArrayList<int[]> Path;
	private int Counter;
	private int[] PreviousPanel;
	private JLabel ImageToDelete;
	private boolean FinishedSim;
	private boolean removedFromInfoBar;
	private int Fuels;
	private int FuelCons;
	private int TimeToStart;
	private int quants;
	private int MaxQuants;

	public Flights(int fid, int fst, int at, int al, String fn, Airplanes atype, int fs, int fh, int ff) {
		this.FlightID = fid;
		this.FlightStart = fst;
		this.AirportTakeOff = at;
		this.AirportLanding = al;
		this.FlightName = fn;
		this.AirplaneType = atype;
		this.FlightSpeed = fs;
		this.FlightHeight = fh;
		this.FlightFuels = ff;
		this.TimeToNextBlock = 100*5*60*20/this.FlightSpeed;
		this.Counter = 0;
		this.Path = null;
		this.PreviousPanel = null;
		this.ImageToDelete = null;
		this.FinishedSim = false;
		this.removedFromInfoBar = false;
		this.Fuels = this.FlightFuels;
		this.FuelCons = atype.getFuelConsumption();
		this.TimeToStart = fst * 5000;
		this.quants = 0;
	}

	static public void ReadFlights(String InputFile, 
		ArrayList<Flights> list, ArrayList<Airports> airports) throws IOException {
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(InputFile));
		}
		catch (FileNotFoundException e) {
			System.out.println(InputFile + " not found!");
			System.exit(-1);
		}

		String line = null;
		while((line = br.readLine()) != null) {
			String[] values = line.split(",");
			int fid = Integer.parseInt(values[0]);
			int fst = Integer.parseInt(values[1]);
			int at = Integer.parseInt(values[2]);
			int al = Integer.parseInt(values[3]);
			String fn = values[4];
			Airplanes atype = null;
			if (values[5].equals("1"))
				atype = new Airplanes(1, 60, 110, 280, 8000, 700, 3);
			else if (values[5].equals("2"))
				atype = new Airplanes(2, 100, 220, 4200, 16000, 1200, 9);
			else if (values[5].equals("3"))
				atype = new Airplanes(3, 140, 280, 16000, 28000, 2300, 15);
			else
				throw new IllegalArgumentException("Not valid airplane type!");
			int fs = Integer.parseInt(values[6]);
			int fh = Integer.parseInt(values[7]);
			int ff = Integer.parseInt(values[8]);
			Flights temp = new Flights(fid, fst, at, al, fn, atype, fs, fh, ff);
			if (temp.checkFlight(airports)) list.add(temp);
		}
		br.close();
	}

	public ArrayList<int[]> PathFind (ArrayList<Airports> airports, int[][] Grid) {
		ArrayList<int[]> path = new ArrayList<int[]>();
		ArrayList<int[]> prev = new ArrayList<int[]>();
		Airports takeoff = null, land = null;
		for (Airports airport: airports) {
			if (airport.getID() == this.AirportTakeOff)
				takeoff = airport;
			else if (airport.getID() == this.AirportLanding)
				land = airport;
		}
		
		double fh = (double)this.FlightHeight*0.3048;
		int xs = takeoff.getX();
		int ys = takeoff.getY();
		int xsd = takeoff.getdX();
		int ysd = takeoff.getdY();
		int xe = land.getX();
		int ye = land.getY();
		int xed = land.getdX();
		int yed = land.getdY();
		
		int[] temp0 = {xs, ys};
		path.add(temp0);
		
		// BFS
		Queue<int[]> queue = new LinkedList<int[]>();
		int[] temp_p = {xsd, ysd};
		queue.add(temp_p);
		int[][] visited = new int[30][60];
		for (int i=0; i<30; i++) {
			for (int j=0; j<60; j++) {
				visited[i][j] = 0;
			}
		}
		int[][][] previous = new int[30][60][2];
		previous[xsd][ysd][0] = -1;
		previous[xsd][ysd][1] = -1;
		int[] node = queue.remove();
		while(node[0] != xed || node[1] != yed) {
			if (visited[node[0]][node[1]] < 2) {
				visited[node[0]][node[1]] = 2;
				if (node[0]+1 < 30) {
					if (Grid[node[0]+1][node[1]] < fh && visited[node[0]+1][node[1]] == 0) {
						previous[node[0]+1][node[1]][0] = node[0];
						previous[node[0]+1][node[1]][1] = node[1];
						visited[node[0]+1][node[1]] = 1;
						int[] temp = {node[0]+1, node[1]};
						queue.add(temp);
					}
				}
				if (node[1]+1 < 60) {
					if (Grid[node[0]][node[1]+1] < fh && visited[node[0]][node[1]+1] == 0) {
						previous[node[0]][node[1]+1][0] = node[0];
						previous[node[0]][node[1]+1][1] = node[1];
						visited[node[0]][node[1]+1] = 1;
						int[] temp = {node[0], node[1]+1};
						queue.add(temp);
					}
				}
				if (node[0]-1 >= 0) {
					if (Grid[node[0]-1][node[1]] < fh && visited[node[0]-1][node[1]] == 0) {
						previous[node[0]-1][node[1]][0] = node[0];
						previous[node[0]-1][node[1]][1] = node[1];
						visited[node[0]-1][node[1]] = 1;
						int[] temp = {node[0]-1, node[1]};
						queue.add(temp);
					}
				}
				if (node[1]-1 >= 0) {
					if (Grid[node[0]][node[1]-1] < fh && visited[node[0]][node[1]-1] == 0) {
						previous[node[0]][node[1]-1][0] = node[0];
						previous[node[0]][node[1]-1][1] = node[1];
						visited[node[0]][node[1]-1] = 1;
						int[] temp = {node[0], node[1]-1};
						queue.add(temp);
					}
				}
				if (node[0]+1 < 30 && node[1]+1 < 60) {
					if (Grid[node[0]+1][node[1]+1] < fh && visited[node[0]+1][node[1]+1] == 0) {
						previous[node[0]+1][node[1]+1][0] = node[0];
						previous[node[0]+1][node[1]+1][1] = node[1];
						visited[node[0]+1][node[1]+1] = 1;
						int[] temp = {node[0]+1, node[1]+1};
						queue.add(temp);
					}
				}
				if (node[0]-1 >= 0 && node[1]+1 < 60) {
					if (Grid[node[0]-1][node[1]+1] < fh && visited[node[0]-1][node[1]+1] == 0) {
						previous[node[0]-1][node[1]+1][0] = node[0];
						previous[node[0]-1][node[1]+1][1] = node[1];
						visited[node[0]-1][node[1]+1] = 1;
						int[] temp = {node[0]-1, node[1]+1};
						queue.add(temp);
					}
				}
				if (node[0]+1 < 30 && node[1]-1 >= 0) {
					if (Grid[node[0]+1][node[1]-1] < fh && visited[node[0]+1][node[1]-1] == 0) {
						previous[node[0]+1][node[1]-1][0] = node[0];
						previous[node[0]+1][node[1]-1][1] = node[1];
						visited[node[0]+1][node[1]-1] = 1;
						int[] temp = {node[0]+1, node[1]-1};
						queue.add(temp);
					}
				}
				if (node[0]-1 >= 0 && node[1]-1 >= 0) {
					if (Grid[node[0]-1][node[1]-1] < fh && visited[node[0]-1][node[1]-1] == 0) {
						previous[node[0]-1][node[1]-1][0] = node[0];
						previous[node[0]-1][node[1]-1][1] = node[1];
						visited[node[0]-1][node[1]-1] = 1;
						int[] temp = {node[0]-1, node[1]-1};
						queue.add(temp);
					}
				}
			}
			node = queue.remove();
		}
		
		while(node[0] != xsd || node[1] != ysd) {
			int[] temp = {previous[node[0]][node[1]][0], previous[node[0]][node[1]][1]};
			prev.add(temp);
			node = temp;
		}
		
		Collections.reverse(prev);	
		path.addAll(prev);
		
		int[] temp9 = {xe, ye};
		path.add(temp9);
		
		return path;
	}
	
	public int getTime() {
		return this.TimeToNextBlock;
	}

	public int getHeight() {
		return this.FlightHeight;
	}

	public int getAirplaneType() {
		return this.AirplaneType.getType();
	}

	public void setPath(ArrayList<int[]> p) {
		this.Path = p;
	}

	public int[] removeFromPath() {
		return this.Path.remove(0);
	}

	public int[] checkFirstInPath() {
		return this.Path.get(0);
	}

	public void updateQuants() {
		if (this.quants + 1 >= this.MaxQuants)
			this.quants = 0;
		else
			this.quants++;
	}

	public int getQuants() {
		return this.quants;
	}

	public void setMaxQuants(int time) {
		this.MaxQuants = this.TimeToNextBlock / time;
	}

	public int getMaxQuants() {
		return this.MaxQuants;
	}

	public boolean checkPath() {
		return this.Path.isEmpty();
	}

	public boolean updateCounter(int time) {
		if (this.TimeToStart > 0) {
			this.TimeToStart -= time;
		}
		if (this.TimeToStart <= 0) {
			if (this.Counter == 0) {
				this.Counter += time;
				if (this.Counter >= this.TimeToNextBlock) {
					this.Counter = 0;
				}
				return true;
			}
			else {
				this.Counter += time;
				if (this.Counter >= this.TimeToNextBlock) {
					this.Counter = 0;
				}
				return false;
			}
		}
		return false;
	}

	public boolean updateFuels() {
		if (TimeToStart <= 0) {
			int posothta = FuelCons*20 / MaxQuants;
			boolean noFuels = (Fuels - posothta < 0);
			if (Fuels - posothta >= 0)
				Fuels -= posothta;
			else
				Fuels = 0;
			return noFuels;
		}
		else
			return false;
	}

	public boolean checkFlight(ArrayList<Airports> airports) {
		boolean check = true;
		boolean check1 = false;
		
		for (Airports airport : airports) {
			if (airport.getID() == AirportTakeOff) {
				if (airport.getCategory() == 1)
					check1 = (airport.getState() && (AirplaneType.getType() == 1));
				else if (airport.getCategory() == 2)
					check1 = (airport.getState() && (AirplaneType.getType() > 1));
				else
					check1 = airport.getState();
			}
		}
		
		if (!check1) {
			check = false;
			System.out.println("Take off Airport not found!");
		}
		
		check1 = false;
		for (Airports airport : airports) {
			if (airport.getID() == AirportLanding) {
				if (airport.getCategory() == 1)
					check1 = (airport.getState() && (AirplaneType.getType() == 1));
				else if (airport.getCategory() == 2)
					check1 = (airport.getState() && (AirplaneType.getType() > 1));
				else
					check1 = airport.getState();
			}
		}
		
		if (!check1) {
			check = false;
			System.out.println("Landing Airport not found!");
		}

		if (FlightSpeed > AirplaneType.getMaxSpeed()) {
			check = false;
			System.out.println("Not valid flight speed!");
		}
		
		if (FlightHeight > AirplaneType.getMaxHeight()) {
			check = false;
			System.out.println("Not valid flight height!");
		}
		
		if (FlightFuels > AirplaneType.getMaxFuelCapacity()) {
			check = false;
			System.out.println("Not valid flight fuels!");
		}
		return check;
	}

	public int[] getPreviousPanel() {
		return this.PreviousPanel;
	}

	public void setPreviousPanel(int[] pp) {
		this.PreviousPanel = pp;
	}

	public JLabel getImage() {
		return this.ImageToDelete;
	}

	public void setImage(JLabel img) {
		this.ImageToDelete = img;
	}

	public boolean getRemoved() {
		return this.removedFromInfoBar;
	}

	public void setRemoved() {
		this.removedFromInfoBar = !this.removedFromInfoBar;
	}

	public void finishSim() {
		this.FinishedSim = true;
	}

	public boolean SimStatus() {
		return this.FinishedSim;
	}

	public String showFlightInfo(ArrayList<Airports> airports) {
		String takeOffAirportName = "NONE";
		String landingAirportName = "NONE";
		for (Airports airport : airports) {
			if (airport.getID() == AirportTakeOff) {
				takeOffAirportName = airport.getName();
			}
			if (airport.getID() == AirportLanding) {
				landingAirportName = airport.getName();
			}
		}
		
		return "Flight " + FlightID + ": " + 
				"\n   -Take Off Airport: " + takeOffAirportName + 
				"\n   -Landing Airport: " + landingAirportName +
				"\n   -Airplane Type: " + AirplaneType.getType() +
				"\n   -Flight Status: " + "Running\n";
	}

	public String showAircraftInfo(ArrayList<Airports> airports) {
		String takeOffAirportName = "NONE";
		String landingAirportName = "NONE";
		for (Airports airport : airports) {
			if (airport.getID() == AirportTakeOff) {
				takeOffAirportName = airport.getName();
			}
			if (airport.getID() == AirportLanding) {
				landingAirportName = airport.getName();
			}
		}
		
		return "Aircraft " + FlightID + ": " + 
				"\n   -Take Off Airport: " + takeOffAirportName + 
				"\n   -Landing Airport: " + landingAirportName +
				"\n   -Flight Speed: " + FlightSpeed +
				"\n   -Flight Height: " + FlightHeight +
				"\n   -Flight Fuels: " + Fuels +
				"\n";
	}

	public void resetVars() {
		this.Counter = 0;
		this.Path = null;
		this.PreviousPanel = null;
		this.ImageToDelete = null;
		this.FinishedSim = false;
		this.removedFromInfoBar = false;
		this.Fuels = this.FlightFuels;
		this.TimeToStart = this.FlightStart * 5000;
		this.quants = 0;
	}
}