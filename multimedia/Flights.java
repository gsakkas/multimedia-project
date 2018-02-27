package multimedia;

import java.io.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

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
	}

	static public void ReadFlights(String InputFile, ArrayList<Flights> list) throws FileNotFoundException, IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(InputFile));
		}
		catch (FileNotFoundException e) {
			System.out.println(InputFile + " not found!");
			System.exit(-1);
		}
		catch (IOException e) {
			e.printStackTrace();
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
				atype = new Airplanes(60, 110, 280, 8000, 700, 3, 1);
			else if (values[5].equals("2"))
				atype = new Airplanes(100, 220, 4200, 16000, 1200, 9, 2);
			else if (values[5].equals("3"))
				atype = new Airplanes(140, 280, 16000, 28000, 2300, 15, 3);
			else
				throw new IllegalArgumentException("Not valid airplane type!");
			int fs = Integer.parseInt(values[6]);
			int fh = Integer.parseInt(values[7]);
			int ff = Integer.parseInt(values[8]);
			list.add(new Flights(fid, fst, at, al, fn, atype, fs, fh, ff));
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
	
	
	public int getSpeed() {
		return this.FlightSpeed;
	}
}