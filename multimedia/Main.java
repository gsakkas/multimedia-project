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
		ArrayList<Integer> times = new ArrayList<Integer>();
		int[] arr;
		for (Flights flight : flights) {
			ArrayList<int[]> path = flight.PathFind(airports, map.getGrid());
			mapP.simulateFlight(path);
			times.add(1000*5*60*20/flight.getSpeed());
		}
		int gcd = gcd(times.stream().mapToInt(i -> i).toArray());
		// while (true){
		// 	try{Thread.sleep(gcd);} catch(InterruptedException e){System.out.println(e);}
		// }
	}
	
	public static int gcd(int[] numbers) {
        int gcd=1;
        int index=2;
        if(numbers.length==1){
            gcd=numbers[0];
        }
        if(numbers.length>1){
           gcd=euclidGcd(numbers[0],numbers[1]); 
        }
        while(index<numbers.length){
            gcd=euclidGcd(gcd,numbers[index]);
            index++;
        }
        return gcd;
    }
	
	public static int euclidGcd(int num1,int num2){
        int temp=0;
        while(num2!=0){
            temp=num2;
            num2=num1%num2;
            num1=temp;
        }
        num1=num1<0 ? num1 * (-1):num1;
        return num1;
    }
}