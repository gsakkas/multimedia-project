package multimedia;

import java.io.*;
import java.util.ArrayList;

class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length != 2) {
			System.out.println("Call the program with exactly one argument!");
			System.out.println("argument 1: path to map file");
			System.out.println("argument 2: path to airports file");
			System.exit(-1);
		}
		ArrayList<Airports> airports = new ArrayList<Airports>();
		Airports.ReadAirports(args[1], airports);
		Map map = new Map(args[0], airports);
	}
}