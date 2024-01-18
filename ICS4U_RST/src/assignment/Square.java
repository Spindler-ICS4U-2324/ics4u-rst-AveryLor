package assignment;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Square extends Button {

	// Images 
	private static final Image IMG_BLANK = new Image(Square.class.getResource("/images/blank.png").toString());
	private static final Image IMG_GREEN = new Image(Square.class.getResource("/images/green.png").toString());
	private static final Image IMG_YELLOW = new Image(Square.class.getResource("/images/yellow.png").toString());
	private static final Image IMG_GRAY = new Image(Square.class.getResource("/images/gray.png").toString());

	// Corresponding values for the images 
	public static final char BLANK = ' ';
	public static final char GREEN = 'G';
	public static final char YELLOW = 'Y';
	public static final char GRAY = 'A'; 
	
	private char value;
	
	public Square() {
		super();
		value = BLANK;
		setGraphic(new ImageView(IMG_BLANK));
	}
	
	public void playSquare(char val) {
		value = val;
		switch (value) {
		case GREEN:
			setGraphic(new ImageView(IMG_GREEN));
			break;
		case YELLOW:
			setGraphic(new ImageView(IMG_YELLOW));
			break;
		}
	}

	public char getValue() {
		return value;
	}
	
	public void clear() {
	    value = BLANK; // Reset the value to BLANK
	    setGraphic(new ImageView(IMG_BLANK)); // Set the graphic to the blank image
	}
	
	public void setGreen() {
		value = GREEN; // Set the value of the image to green 
		setGraphic(new ImageView(IMG_GREEN));
	}
	
	public void setYellow() {
		value = YELLOW; // Set the value of the image to yellow 
		setGraphic(new ImageView(IMG_YELLOW));
	}
	
	public void setGray() {
		value = GRAY; // Set the value of the image to gray 
		setGraphic(new ImageView(IMG_GRAY));
	}
	
	public void setBlank() {
		value = BLANK;
		setGraphic(new ImageView(IMG_BLANK));
			
		
	}
}
