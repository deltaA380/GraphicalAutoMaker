package tools;

import java.io.*;

import gui.Points;

public class Open {
	
	public static void open(String path, Points points) {
		BufferedReader reader;
		try {
			points.clearPoints();
			if (!path.endsWith(".csv")) path += ".csv";
			reader = new BufferedReader(new FileReader(path));
	    	reader.readLine();
	    	String[] line = {"0", "0", "0"};
	    	Integer[] point = new Integer[2];
	    	try {
	    		while ((line = reader.readLine().split(",")) != null) {
		    		point[0] = Integer.parseInt(line[0]);
		    		point[1] = Integer.parseInt(line[1]);
		    		points.add(point);
	    		}
	    	} catch(java.lang.NullPointerException e) {
	    		
	    	}
	    	reader.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	}
}
