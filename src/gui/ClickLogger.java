package gui;

import java.awt.event.*;


public class ClickLogger implements MouseListener{
	
	Points points;
	Field field;
	public ClickLogger(Field field) {
		super();
		this.field = field;
		points = Points.getInstance(this.field);
	}
	
	@Override
    public void mouseClicked(MouseEvent e) {
    	int x=e.getX();
        int y=e.getY();
        Integer[] point = {x, y};
        points.add(point);
    }

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}
}
