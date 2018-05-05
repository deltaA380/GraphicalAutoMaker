package gui;

import tools.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Pick a spot on the field, and this app will convert the clicked
 * pixel's coordinate into a coordinate in meters, and send it off
 * to Pathfinder.
 * @author gcper
 *
 */
public class Field {
	
	JFrame frame;
	JLayeredPane pane;
	JMenuBar menuBar;
	JMenu tool;
	JMenu file;
	JMenuItem generate;
	JMenuItem editor;
	JMenuItem save;
	JMenuItem open;
	WaypointEditor waypointEditor;
	JFileChooser fc;
	ArrayList<JLabel> pointsDrawn;
	final int MENU_HEIGHT = 60;
	final int WIDTH = 1178;
	final int HEIGHT = 480+MENU_HEIGHT;
	final double SCALE = 74 / (double)WIDTH; // feet per pixel
	
	Logger logger;
	
	private Field() {
		logger = Logger.getLogger(this.getClass().getName());
		pointsDrawn = new ArrayList<JLabel>();
	}
	
	public void createAndShow() {
		// Windows file chooser look/feel
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }catch(Exception ex) {
	        ex.printStackTrace();
	    }
		logger.info("Initializing application");
		
		// Initialize GUI items
		frame = new JFrame("Auto Maker");
		pane = new JLayeredPane();
		menuBar = new JMenuBar();
		tool = new JMenu("Tools");
		file = new JMenu("File");
		generate = new JMenuItem("Generate");
		editor = new JMenuItem("Edit Waypoints");
		save = new JMenuItem("Save");
		open = new JMenuItem("Open");
		fc = new JFileChooser(System.getProperty("user.home"));
		
		// Startup methods for GUI items
		fc.setDialogTitle("Generate Java Code");
		createMenu();
		frame.setLayout(new BorderLayout());
		frame.add(pane);
		pane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logger.info("Initializing mouse listener");
		frame.addMouseListener(new ClickLogger(this));
		frame.setSize(WIDTH+15, HEIGHT);
		frame.setJMenuBar(menuBar);
        frame.setVisible(true);
	}
	
	private Field field;
	public void createMenu() {
		tool.setMnemonic(KeyEvent.VK_T);
		menuBar.add(tool);
		tool.add(generate);
		generate.setMnemonic(KeyEvent.VK_G);
		generate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK));
		tool.add(editor);
		editor.setMnemonic(KeyEvent.VK_E);
		editor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		menuBar.add(file);
		file.setMnemonic(KeyEvent.VK_F);
		file.add(save);
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		file.add(open);
		open.setMnemonic(KeyEvent.VK_O);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		
		
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileHidingEnabled(true);
		
		field = this;
		
		// Item generates java code from waypoints
		generate.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file (.txt)", "txt", "text");
				fc.setFileFilter(filter);
		    	fc.setSelectedFile(new File("auto.txt"));
		    	int approval = fc.showDialog(frame, "Generate");
		    	if (approval == JFileChooser.APPROVE_OPTION) {
		    		File newFile = fc.getSelectedFile();
		    		WaypointGenerator.generate(newFile.getPath(), Points.getInstance(field).pointsFeet);
		    		JOptionPane.showMessageDialog(frame,
		    				"Auto has been successfully generated to: "+
		    			    newFile.getPath());
		    	}
		    }
		});
		
		// Item edits/deletes waypoints
		editor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				waypointEditor = new WaypointEditor(field);
			}
		});
		
		// Saves a csv of the auto waypoints
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file (.csv)", "csv");
				fc.setFileFilter(filter);
		    	fc.setSelectedFile(new File("waypoints.csv"));
		    	int approval = fc.showDialog(frame, "Save");
		    	if (approval == JFileChooser.APPROVE_OPTION) {
		    		File newFile = fc.getSelectedFile();
		    		Save.save(newFile.getPath(), Points.getInstance(field).points);
		    		JOptionPane.showMessageDialog(frame,
		    				"Points have been successfully saved to: "+
		    			    newFile.getPath());
		    	}
			}
		});
		
		// Opens a csv of the auto waypoints
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file (.csv)", "csv");
				fc.setFileFilter(filter);
		    	int approval = fc.showDialog(frame, "Open");
		    	if (approval == JFileChooser.APPROVE_OPTION) {
		    		File newFile = fc.getSelectedFile();
		    		Open.open(newFile.getPath(), Points.getInstance(field));
		    		JOptionPane.showMessageDialog(frame,
		    				"Points have been successfully opened from: "+
		    			    newFile.getPath());
		    	}
			}
		});
		
	}
	
	
	public void drawPoint(int x, int y) {
		x -= 19;
		y -= 71;
		logger.info("Drawing point at "+(x)+" "+(y));
		Image image = null;
		try {
			image = ImageIO.read(this.getClass().getClassLoader().getResource("refs/arrow.png"))
					.getScaledInstance(25,  25, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		JLabel point = new JLabel(new ImageIcon(image));
		point.setBounds(x, y, 25, 25);
		pointsDrawn.add(point);
		pane.add(point, new Integer(-1));
		pane.setLayer(point,  1);
		frame.setVisible(true);
	}
	
	public void removePoint(int i) {
		pointsDrawn.get(i).setVisible(false);
		pointsDrawn.remove(i);
	}
	
	public void drawField() {
		logger.info("Drawing field");
		Image image = null;
		try {
			image = ImageIO.read(this.getClass().getClassLoader().getResource("refs/field.png"))
					.getScaledInstance(WIDTH, HEIGHT-MENU_HEIGHT, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			logger.severe("Field image not found! Should be in src/refs/");
			e.printStackTrace();
			System.exit(1);
		}
		JLabel field = new JLabel(new ImageIcon(image));
		field.setBounds(0, 0, WIDTH, HEIGHT-MENU_HEIGHT);
		pane.add(field, new Integer(1));
		pane.setLayer(field, 0);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Field field = new Field();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                field.createAndShow();
            }
        });
	}
}
