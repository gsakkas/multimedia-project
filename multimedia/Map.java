package multimedia;

import java.io.*;
import java.util.ArrayList;

public class Map {
	private static int DimX = 30;
	private static int DimY = 60;
	private int Grid[][];

	/**
	 * Returns an new Map object that reads from the given input file. 
	 *
	 * @param  InputFile 	the String name of the input file 
	 */
	public Map(String InputFile) throws IOException {
		this.Grid = new int[DimX][DimY];
		ReadMap(InputFile);
	}

	private void ReadMap(String InputFile) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(InputFile));
		}
		catch (FileNotFoundException e) {
			System.out.println(InputFile + " not found!");
			System.exit(-1);
		}

		String line = null;
		int linesRead = 0;
		while((line = br.readLine()) != null){
			String[] values = line.split(",");

			for (int i = 0; i < this.DimY; i++) {
				try {
					this.Grid[linesRead][i] = Integer.parseInt(values[i]);
				}
				catch (NumberFormatException nfe) {
					continue;
				}
			}
			linesRead++;
		}
		br.close();
	}

	/**
	 * Just prints the grid of the Map object
	 *
	 */
	public void print() {
		for (int i = 0; i < DimX; i++) {
			for (int j = 0; j < DimY; j++) {
				System.out.printf("" + this.Grid[i][j]);
				if (j < DimY - 1) System.out.printf(",");
			}
			System.out.println("");
		}
	}

	/**
	 * Just returns the dimension Y of the Map onbject. 
	 *
	 * @return the dimension X of the Map onbject
	 */
	public int getDimX() {
		return this.DimX;
	}

	/**
	 * Just returns the dimension Y of the Map onbject. 
	 *
	 * @return the dimension Y of the Map onbject
	 */
	public int getDimY() {
		return this.DimY;
	}

	/**
	 * Just returns the grid of the heights at each cell of the Map onbject. 
	 *
	 * @return the Grid with the heights of the Map onbject
	 */
	public int[][] getGrid() {
		return this.Grid;
	}
}