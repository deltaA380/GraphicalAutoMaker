package gui;

import javax.swing.*;

public class WaypointEditor {
	Field field;
	public WaypointEditor(Field field) {
		this.field = field;
		editPoints();
	}
	
	public void editPoints() {
		Points points = Points.getInstance(field);
		String[] list = new String[points.points.size()];
		for (int i = 0; i < points.points.size(); i++) {
			list[i] = Double.toString(points.pointsFeet.get(i)[0]) +", "+ Double.toString(points.pointsFeet.get(i)[1]);
		}
		try {
			String selectedPoint = ListDialog.showDialog(
	                field.frame,
	                null,
	                "Current waypoints:",
	                "Name Chooser",
	                list,
	                list[0],
	                list[0]);
			
			String[] temp = selectedPoint.split(", ");
			Double[] point = new Double[2];
			point[0] = Double.parseDouble(temp[0]);
			point[1] = Double.parseDouble(temp[1]);
			points.remove(points.find(point));
		} catch(java.lang.ArrayIndexOutOfBoundsException e) { //no waypoints
			JOptionPane.showMessageDialog(field.frame,
				    "There are no waypoints to edit",
				    "No Waypoints",
				    JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) { // Cancel button
			//pass
		}
	}
}
