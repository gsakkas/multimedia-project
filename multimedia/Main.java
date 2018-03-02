package multimedia;

import java.io.*;
import java.util.ArrayList;

class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length != 3) {
			System.out.println("Call the program with exactly one argument!");
			System.out.println("argument 1: path to map file");
			System.out.println("argument 2: path to airports file");
			System.out.println("argument 3: path to flights file");
			System.exit(-1);
		}
		ArrayList<Airports> airports = new ArrayList<Airports>();
		Airports.ReadAirports(args[1], airports);
		ArrayList<Flights> flights = new ArrayList<Flights>();
		Flights.ReadFlights(args[2], flights);
		Map map = new Map(args[0]);
		MainWindow frame = new MainWindow(map, airports, flights);
		MapPanel mapP = frame.getMap();
		while (true){
			System.out.println("Main Loop!");
			if (frame.simRunning()) {
				simulateFlights(frame, flights, airports, mapP, map.getGrid());
			}
		}
	}

	public static void simulateFlights(
		MainWindow frame, ArrayList<Flights> flights,
		ArrayList<Airports> airports, MapPanel mapP, int[][] grid) {
		
		ArrayList<Integer> times = new ArrayList<Integer>();
		ArrayList<ArrayList<int[]>> list = new ArrayList<ArrayList<int[]>>();
		for (Flights flight : flights) {
			ArrayList<int[]> path = flight.PathFind(airports, grid);
			flight.setPath(path);
			times.add(flight.getTime());
		}
		int SleepTime = 1000;
		// int SleepTime = gcd(times.stream().mapToInt(i -> i).toArray());
		boolean allFlightsFinished = false;
		while (!allFlightsFinished && frame.simRunning()){
			sleep(SleepTime);
			frame.setTime(SleepTime/1000);
			allFlightsFinished = true;
			for (Flights flight : flights) {
				if (flight.updateCounter(SleepTime)) mapP.moveToNextPanel(flight);
				allFlightsFinished = flight.SimStatus();
			}
			mapP.redraw();
		}

		if (!frame.simRunning()) {
			for (Flights flight : flights) {
				mapP.clearImg(flight);
			}
			mapP.redraw();
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