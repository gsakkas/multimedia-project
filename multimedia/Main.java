package multimedia;

import java.io.*;

class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		if (args.length != 1) {
			System.out.println("Call the program with exactly one argument!");
			System.out.println("argument 1: path to map file");
			System.exit(-1);
		}
		Map map = new Map(args[0]);
		map.print();
	}
}