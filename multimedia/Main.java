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
		Flights.ReadFlights(args[2], flights);
		MainWindow frame = new MainWindow(map, airports, flights);
		MapPanel mapP = frame.getMap();
		while (true){
			sleep(1);
			if (frame.simRunning()) {
				frame.resetVars();
				frame.addText("Simulation Started!");
				simulateFlights(frame, flights, airports, mapP, map.getGrid());
				frame.addText("Simulation Ended!");
			}
			if (frame.getLoadedStatus()) {
				frame.unloadFile();

				airports = new ArrayList<Airports>();
				flights = new ArrayList<Flights>();
				map = new Map(frame.getWorldFile());
				Airports.ReadAirports(frame.getAirportsFile(), airports);
				Flights.ReadFlights(frame.getFlightsFile(), flights);
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
		for (Flights flight : flights) {
			frame.addAircraft(1);
			ArrayList<int[]> path = flight.PathFind(airports, grid);
			flight.setPath(path);
			times.add(flight.getTime());
		}
		int SleepTime = 1000;
		// int SleepTime = gcd(times.stream().mapToInt(i -> i).toArray());
		boolean allFlightsFinished = false;
		while (!allFlightsFinished && frame.simRunning() && !frame.getLoadedStatus()){
			sleep(SleepTime/2);
			frame.setTime(SleepTime);
			allFlightsFinished = true;
			for (Flights flight : flights) {
				if (flight.updateCounter(SleepTime)) {
					mapP.moveToNextPanel(flight);
					if (flight.updateFuels()) flight.setPath(new ArrayList<int[]>());
				}
				allFlightsFinished = flight.SimStatus();
				if (flight.SimStatus() && !flight.getRemoved()) {
					frame.removeAircraft(1);
					if (flight.updateFuels())
						frame.setCrashes(1);
					else
						frame.setLandings(1);
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
		}

		if (!frame.simRunning() || frame.getLoadedStatus()) {
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
	
	public static int gcd(int[] numbers) {
		int gcd = 1;
		int index = 2;
		if (numbers.length == 1) {
			gcd = numbers[0];
		}
		if (numbers.length > 1) {
			gcd = euclidGcd(numbers[0], numbers[1]); 
		}
		while (index < numbers.length) {
			gcd = euclidGcd(gcd, numbers[index]);
			index++;
		}
		return gcd;
	}
	
	public static int euclidGcd(int num1, int num2){
		int temp = 0;
		while (num2 != 0) {
			temp = num2;
			num2 = num1 % num2;
			num1 = temp;
		}
		num1 = (num1 < 0) ? -num1 : num1;
		return num1;
	}
}