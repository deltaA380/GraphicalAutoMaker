package tools;

import java.io.*;
import java.util.ArrayList;

public class Save {
	
	public static void save(String path, ArrayList<Integer[]> waypoints) {
		System.out.println("Beginning waypoint saving");
		BufferedWriter writer;
		try {
			int x, y, d;
			if (!path.endsWith(".csv")) path += ".csv";
			writer = new BufferedWriter(new FileWriter(path));
	    	writer.write("x, y, d\n");
	    	for (Integer[] point : waypoints) {
	    		x = point[0];
	    		y = point[1];
	    		d = 0;
	    		System.err.println("save "+x+" "+y);
	    		writer.write(x+","+y+","+d+"\n");
	    	}
	    writer.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
