package tools;

import java.io.*;
import java.util.ArrayList;

public class WaypointGenerator {
	
	public static void generate(String path, ArrayList<Double[]> waypoints) {
		System.out.println("Beginning waypoint generation");
		BufferedWriter writer;
		try {
			double x, y, d;
			if (!path.endsWith(".txt")) path += ".txt";
			writer = new BufferedWriter(new FileWriter(path));
	    	writer.write("Waypoint[] points = new Waypoint[] {\n");
	    	for (Double[] point : waypoints) {
	    		x = point[0]*0.3048;
	    		y = point[1]*0.3048;
	    		d = 0;
	    		if (waypoints.indexOf(point) != waypoints.size()-1)
	    			writer.write("\tnew Waypoint("+x+", "+y+", "+d+"),\n");
	    		else 
	    			writer.write("\tnew Waypoint("+x+", "+y+", "+d+")\n");
	    	}
	    	writer.write("};");
	    writer.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
