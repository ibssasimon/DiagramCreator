import acm.program.*;
import acm.graphics.*;
import acm.gui.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

/*
 * This program allows for the user to input text (up to 10 characters), and converts that
 * text into a textBox that appears on a canvas. These boxes are able to be moved if they are
 * dragged. Controls include: adding a textBox, removing a textBox by input, and clearing the
 * canvas. Best wishes, Simon :)
 * */
public class DiagramCreator extends GraphicsProgram {
	
	
	/*CONSTANTS*/
	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;
	
	
	public void init() {
		initializeControlBar();
	}
	
	
	
	//Adds JComponents to the canvas
	private void initializeControlBar() {
		add(new JLabel("Name "), SOUTH);
		add(textField, SOUTH);
		textField.addActionListener(this);
		add(add, SOUTH);
		add(remove, SOUTH);
		add(clear, SOUTH);
		addActionListeners();
		addMouseListeners();
	}
	
	
	//Responds based on the action performed on JComponents
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == add) {
			addLabel(textField.getText());
		} else if(e.getSource() == remove) {
			removeObject(textField.getText());
		} else if(e.getSource() == clear) {
			removeAll();
		}
	}
	
	

	//Creates a GCompound box and adds it to canvas. Also adds to HashMap
	private GCompound addLabel(String text) {
		double xLocation = getWidth()/2 - BOX_WIDTH/2;
		double yLocation = getHeight()/2;
		
		
		GCompound gcomp = new GCompound();
		GRect rect = new GRect(BOX_WIDTH, BOX_HEIGHT);
		GLabel label = new GLabel(text);
		label.move(BOX_WIDTH/2 - label.getWidth()/2, BOX_HEIGHT/2);
		gcomp.add(rect);
		gcomp.add(label);
		gcomp.addMouseMotionListener(this);
		
		add(gcomp, xLocation, yLocation);
		content.put(text, gcomp);
		return(gcomp);
	}
	
	
	//Checks to see if mouse is dragged within the application dimensions
	public void mouseDragged(MouseEvent e) {
		if(e.getX() >= 0 && e.getX() < getWidth()) {
			if(e.getY() >= 0 && e.getY() < getHeight()) {
				//Moves currently typed box
				if(inDataBase()) {
					getBox(e);
				}
			}
		}
	}
	
	//Checks to see if a box is already in HashMap
	private boolean inDataBase() {
		boolean in = false;
		
		if(content.containsKey(textField.getText())) {
			in = true;
		}
		return in;
	}
	
	
	//Checks for an existing box, then moves it
	private void getBox(MouseEvent e) {
		//Adding 20 pixels accounts for movement of mouse when dragging object. Bugs R Cool :)
		double x = e.getX() + 20;
		double y = e.getY() + 20;
		GObject obj = getElementAt(x, y);
		
		if(obj != null) {
			obj.setLocation(e.getX() - BOX_WIDTH/2, e.getY());
		}
	}
	
	/*
	 * Removes the object on the graphics so long as the user does not input
	 * the same text twice on graphics. If more than one of the same text is inputed
	 * then this method shall remove the most recent occurrence of that text. 
	 * 
	 * 
	 * Removes the object from the database*/
	private void removeObject(String text) {
		remove(content.get(text));
		content.remove(content.get(text));
	}
	
	
	
	
	
	
	/*IVARS*/
	private JTextField textField = new JTextField(10);
	private JButton add = new JButton("Add");
	private JButton remove = new JButton("Remove");
	private JButton clear = new JButton("Clear");
	private HashMap <String, GCompound> content = new HashMap <String, GCompound>();
}
