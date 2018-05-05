package gui;

import java.util.ArrayList;

public class Points {
	private static Points instance;
	public ArrayList<Double[]> pointsFeet;
	public ArrayList<Integer[]> points;
	Field field;
	
	private Points(Field field) {
		 pointsFeet = new ArrayList<Double[]>();
		 points = new ArrayList<Integer[]>();
		 this.field = field;
		 field.drawField();
	}
	
	public static Points getInstance(Field field) {
		if (instance == null) instance = new Points(field);
		return instance;
	}
	public void add(Integer[] point) {
		points.add(point);
		pointsFeet.add(convertToFeet(point));
		field.drawPoint(point[0], point[1]);
	}
	
	public void remove(int index) {
		pointsFeet.remove(index);
		points.remove(index);
		field.removePoint(index);
	}
	
	public void pop(int index) {
		pointsFeet.remove(index);
		points.remove(index);
	}
	
	public void clearPoints() {
		for (int i = 0; i < points.size(); i++) {
			field.removePoint(0);
		}
		pointsFeet.clear();
		points.clear();
		
	}
	
	public Double[] convertToFeet(Integer[] point) {
		int x = point[0] - 8;
		int y = point[1] - 42;
		Double[] feet = {
			(double)(Math.round((((double)x+12) * field.SCALE)*100))/100,
			(double)(Math.round((((double)y+18) * field.SCALE)*100))/100
		};
		return feet;
	}
	
	public Integer[] convertToPixels(Double[] point) {
		double x = point[0];
		double y = point[1];
		Integer[] pixels = {
			(int)(x / field.SCALE -4),
			(int)(y / field.SCALE -18+42)
		};
		return pixels;
	}
	
	public int find(Double[] a) {
		for (int i = 0; i < pointsFeet.size(); i++) {
			if (a[0] - pointsFeet.get(i)[0] == 0 && a[1]- pointsFeet.get(i)[1] == 0) return i;
		}
		return -1;
	}
}
