package multimedia;

import java.io.*;

public class Map {
	private static int DimX = 30;
	private static int DimY = 60;
	private int Grid[][];

	public Map(String InputFile) throws FileNotFoundException, IOException {
		this.Grid = new int[DimX][DimY];
		ReadMap(InputFile);
	}

	private void ReadMap(String InputFile) throws FileNotFoundException, IOException {
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

	public void print() {
		for (int i = 0; i < DimX; i++) {
			for (int j = 0; j < DimY; j++) {
				System.out.printf("" + this.Grid[i][j]);
				if (j < DimY - 1) System.out.printf(",");
			}
			System.out.println("");
		}
	}
}