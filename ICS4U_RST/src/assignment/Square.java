package assignment;

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


	public int getValue() {
		return value;
	}

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

	public void setLetter(char letter) {
		this.letter = letter; 
		letterLabel.setText(String.valueOf(letter));
	}
	
	public char getLetter() {
		return letter; 
	}
	
 }
