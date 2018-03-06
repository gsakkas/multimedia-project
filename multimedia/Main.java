package multimedia;

import java.io.*;
import java.util.ArrayList;

class Main {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.out.println("Call the program with exactly one argument!");
			System.out.println("argument 1: path to map file");
			System.out.println("argument 2: path to airports file");
			System.out.println("argument 3: path to flights file");
			System.exit(-1);
		}
		ArrayList<Airports> airports = new ArrayList<Airports>();
		ArrayList<Flights> flights = new ArrayList<Flights>();
		Map map = new Map(args[0]);
		Airports.ReadAirports(args[1], airports);
		Flights.ReadFlights(args[2], flights, airports);
		MainWindow frame = new MainWindow(map, airports, flights);
		MapPanel mapP = frame.getMap();
		while (true){
			sleep(1);
			if (frame.simRunning()) {
				frame.resetVars();
				frame.addText("Simulation Started");
				simulateFlights(frame, flights, airports, mapP, map.getGrid());
				frame.addText("Simulation Ended");
				if (frame.simRestart()) {
					frame.setSimStatus(false);
					frame.setSimStatus(true);
				}
			}
			if (frame.getLoadedStatus()) {
				frame.addText("Loaded new files");
				frame.unloadFile();
				frame.addText("Reading all input files...");
				frame.addText("Read default input files");
				frame.addText("Map created!");
		

				airports = new ArrayList<Airports>();
				flights = new ArrayList<Flights>();
				map = new Map(frame.getWorldFile());
				Airports.ReadAirports(frame.getAirportsFile(), airports);
				Flights.ReadFlights(frame.getFlightsFile(), flights, airports);
				frame.setMap(map, airports);
				sleep(1);
				mapP = frame.getMap();
			}
		}
	}

	public static void simulateFlights(
		MainWindow frame, ArrayList<Flights> flights,
		ArrayList<Airports> airports, MapPanel mapP, int[][] grid) {
		
		ArrayList<Integer> times = new ArrayList<Integer>();
		ArrayList<ArrayList<int[]>> list = new ArrayList<ArrayList<int[]>>();
		frame.addText("Finding paths for Flights...");
		int SleepTime = 100;
		for (Flights flight : flights) {
			flight.setMaxQuants(SleepTime);
			frame.addAircraft(1);
			ArrayList<int[]> path = flight.PathFind(airports, grid);
			flight.setPath(path);
			times.add(flight.getTime());
		}
		frame.addText("Found paths for Flights");

		int quant = 1000 / SleepTime;
		boolean allFlightsFinished = false;
		int count = 0;
		while (!allFlightsFinished && frame.simRunning() && !frame.getLoadedStatus() && !frame.simRestart()){
			sleep(SleepTime);
			if (count == quant) {
				frame.setTime(1000);
				count = 0;
			}
			allFlightsFinished = true;
			for (Flights flight : flights) {
				if (flight.updateCounter(SleepTime)) {
					mapP.moveToNextPanel(flight);
					if (flight.updateFuels()) flight.setPath(new ArrayList<int[]>());
				}
				else {
					mapP.moveToNextPanelSlightly(flight);
					if (flight.updateFuels()) flight.setPath(new ArrayList<int[]>());
				}
				allFlightsFinished = flight.SimStatus();
				if (flight.SimStatus() && !flight.getRemoved()) {
					frame.removeAircraft(1);
					if (flight.updateFuels()) {
						frame.addText("One Flight crashed");
						frame.setCrashes(1);
					}
					else {
						frame.addText("One Flight finished normally");
						frame.setLandings(1);
					}
					flight.setRemoved();
				}
				for (Flights fl : flights) {
					if (flight != fl && Math.abs(flight.getHeight() - fl.getHeight()) < 500
						&& flight.getPreviousPanel() == fl.getPreviousPanel()) {
						flight.setPath(new ArrayList<int[]>());
						fl.setPath(new ArrayList<int[]>());
					}
				}
			}
			mapP.redraw();
			count++;
		}

		if (!frame.simRunning() || frame.getLoadedStatus() || frame.simRestart()) {
			for (Flights flight : flights) {
				mapP.clearImg(flight);
			}
			mapP.redraw();
		}
		else {
			frame.setSimStatus(false);
		}

		for (Flights flight : flights) {
			flight.resetVars();
		}
	}

	public static void sleep(int st) {
		try{Thread.sleep(st);} catch(InterruptedException e){System.out.println(e);}		
	}
}