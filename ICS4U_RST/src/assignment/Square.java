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

	// Corresponding values for the images
	public static final char BLANK = ' ';
	public static final char GREEN = '&';
	public static final char YELLOW = '*';
	public static final char GRAY = '(';
	
	// Fields

	private Label letterLabel;

	private char value;

	public Square() {
		value = BLANK; 
		// Create a StackPane to hold the imageView and letterLabel
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_BLANK);
		imageView.setFitWidth(90);
		imageView.setFitHeight(90);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
	}


	public char getValue() {
		return value;
	}

	public void clear() {
		value = BLANK; // Reset the value to BLANK
		
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_BLANK);
		imageView.setFitWidth(90);
		imageView.setFitHeight(90);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
		
		setGraphic(new ImageView(IMG_BLANK)); // Set the graphic to the blank image
	}

	public void setGreen() {
		value = GREEN; // Set the value of the image to green
		
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_BLANK);
		imageView.setFitWidth(90);
		imageView.setFitHeight(90);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
		
		setGraphic(new ImageView(IMG_GREEN));
	}

	public void setYellow() {
		value = YELLOW; // Set the value of the image to yellow
		
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_BLANK);
		imageView.setFitWidth(90);
		imageView.setFitHeight(90);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
		
		setGraphic(new ImageView(IMG_YELLOW));
	}

	public void setGray() {
		value = GRAY; // Set the value of the image to gray
		
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_BLANK);
		imageView.setFitWidth(90);
		imageView.setFitHeight(90);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
		
		setGraphic(new ImageView(IMG_GRAY));
	}

	public void setBlank() {
		value = BLANK;
		
		StackPane stackPane = new StackPane();
		stackPane.setAlignment(Pos.CENTER); // Set the alignment of the StackPane to CENTER

		// Create the ImageView and Label
		ImageView imageView = new ImageView(IMG_BLANK);
		imageView.setFitWidth(90);
		imageView.setFitHeight(90);

		letterLabel = new Label();
		letterLabel.setFont(Font.font(FONT));

		// Add the ImageView and Label to the StackPane
		stackPane.getChildren().addAll(imageView, letterLabel);
		setGraphic(stackPane);
		
		setGraphic(new ImageView(IMG_BLANK));

	}

	public void setLetter(char value) {
		this.value = value; 
		letterLabel.setText(String.valueOf(value));
	}
	
	public char getLetter() {
		return value; 
	}
	
 }
