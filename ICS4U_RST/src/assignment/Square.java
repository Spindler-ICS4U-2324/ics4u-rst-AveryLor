package assignment;

/**
 * @author s453512 
 * Date: 2024-01-08 
 * Square.java 
 * Class that defines a Square javaFX component 
 */

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

public class Square extends Button {

	// Images
	private static final Image IMG_BLANK = new Image(Square.class.getResource("/images/blank.png").toString());
	private static final Image IMG_GREEN = new Image(Square.class.getResource("/images/green.png").toString());
	private static final Image IMG_YELLOW = new Image(Square.class.getResource("/images/yellow.png").toString());
	private static final Image IMG_GRAY = new Image(Square.class.getResource("/images/gray.png").toString());
	private static final int FONT = 20;
	private static final int SQUARE_SIZE = 60; 

	// Corresponding values for the images
	public static final char BLANK = ' ';
	
	public static final int BLANK_VALUE = 0; 
	public static final int YELLOW_VALUE = 1; 
	public static final int GREEN_VALUE = 2; 
	public static final int GRAY_VALUE = 3; 

	// Fields
	private Label letterLabel;
	private char letter;
	private int value; 

	 /**
     * Constructs a Square with initial values.
     */
	public Square() {
		value = BLANK_VALUE; 
		letter = BLANK; 
		// Create a StackPane to hold the imageView and letterLabel
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_BLANK);
		imageView.setFitWidth(SQUARE_SIZE);
		imageView.setFitHeight(SQUARE_SIZE);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
	}
	
	 /**
     * Gets the value associated with the square.
     *
     * @return The value of the square.
     */
	public int getValue() {
		return value;
	}

	/**
     * Clears the square by setting its letter to blank.
     */
	public void clear() {
		letter = BLANK; // Reset the value to BLANK
		
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_BLANK);
		imageView.setFitWidth(SQUARE_SIZE);
		imageView.setFitHeight(SQUARE_SIZE);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
		
		setGraphic(new ImageView(IMG_BLANK)); // Set the graphic to the blank image
	}

	/**
     * Sets the square to display a green image.
     */
	public void setGreen() {
		value = GREEN_VALUE; 
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_GREEN);
		imageView.setFitWidth(SQUARE_SIZE);
		imageView.setFitHeight(SQUARE_SIZE);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));
		letterLabel.setText(String.valueOf(letter));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		
		setGraphic(stackPane);
		
	}

	/**
     * Sets the square to display a yellow image.
     */
	public void setYellow() {
		value = YELLOW_VALUE; 
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_YELLOW);
		imageView.setFitWidth(SQUARE_SIZE);
		imageView.setFitHeight(SQUARE_SIZE);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));
		letterLabel.setText(String.valueOf(letter));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
	}

	/**
     * Sets the square to display a gray image.
     */
	public void setGray() {
		
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_GRAY);
		imageView.setFitWidth(SQUARE_SIZE);
		imageView.setFitHeight(SQUARE_SIZE);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));
		letterLabel.setText(String.valueOf(letter));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
		
	}
	
	/**
     * Sets the square to display a blank image.
     */
    public void setBlank() {
        letter = BLANK;

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

        // Create the ImageView and Label
        ImageView imageView = new ImageView(IMG_BLANK);
        imageView.setFitWidth(SQUARE_SIZE);
        imageView.setFitHeight(SQUARE_SIZE);

        letterLabel = new Label();
        letterLabel.setFont(Font.font(FONT));
        letterLabel.setText(String.valueOf(letter));

        // Add the ImageView and Label to the StackPane
        stackPane.getChildren().addAll(imageView, letterLabel);
        setGraphic(stackPane);

        setGraphic(new ImageView(IMG_BLANK));
    }


	/**
     * Sets the letter of the square.
     *
     * @param letter The letter to set.
     */
	public void setLetter(char letter) {
		this.letter = letter; 
		letterLabel.setText(String.valueOf(letter));
	}
	
	/**
     * Gets the letter of the square.
     *
     * @return The letter of the square.
     */
	public char getLetter() {
		return letter; 
	}
	
 }
