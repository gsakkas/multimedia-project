package multimedia;

import java.io.*;
import java.util.ArrayList;

public class Flights {
	private int FlightID;
	private int FlightStart;
	private int AirportTakeOff;
	private int AirportLanding;
	private String FlightName;
	private int AirplaneType;
	private int FlightSpeed;
	private int FlightHeight;
	private int	FlightFuels;

	public Flights(int fid, int fst, int at, int al, String fn, int atype, int fs, int fh, int ff) {
		if (atype < 0 || atype > 3)
			throw new IllegalArgumentException("Not valid airplane type!");
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
			System.out.println(InputFile + "not found!");
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
			int atype = Integer.parseInt(values[5]);
			int fh = Integer.parseInt(values[6]);
			int fs = Integer.parseInt(values[7]);
			int ff = Integer.parseInt(values[8]);
			list.add(new Flights(fid, fst, at, al, fn, atype, fh, fs, ff));
		}
		br.close();
	}

}