package assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class NYTimesFX extends Application {

	private static final int GAP = 15;
	private static final int LENGTH = 5;
	private static final int HEIGHT = 6;
	private static final int FONT = 20;
	private static final int SMALL_FONT = 15;
	private static final int LOGIN_SCREEN_WIDTH = 400;
	private static final int LOGIN_SCREEN_HEIGHT = 500;
	private static final int HOME_SCREEN_WIDTH = 1000;
	private static final int HOME_SCREEN_HEIGHT = 1000;
	private static final int WORDLE_SCREEN_WIDTH = 1100;
	private static final int WORDLE_SCREEN_HEIGHT = 1100;
	
	private LoginSystem loginCheck = new LoginSystem();
	private WordleFX wordleGame; 
	private Square[][] box;
	private Stage myStage;

	// JavaFX elements
	private TextField txtUsername, txtPassword;
	private Button btnSubmit;
	
	private static int numGuesses = 0; 

	@Override
	public void start(Stage myStage) throws Exception {
		this.myStage = myStage;
		
		GridPane root = new GridPane();
		root.setHgap(GAP);
		root.setVgap(GAP);
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.CENTER);

		Image symbol = new Image("/images/symbol.png");
		root.add(symbol, 2, 0); // Assuming you want it in the top-right corner (adjust column index as needed)
		
		Label lblTitle = new Label();
		lblTitle.setFont(Font.font(FONT));
		lblTitle.setText("Login Page");
		root.add(lblTitle, 1, 0, 1, 1);
		GridPane.setHalignment(lblTitle, HPos.LEFT);

		txtUsername = new TextField();
		txtUsername.setFont(Font.font(FONT));
		root.add(txtUsername, 1, 1);

		txtPassword = new TextField();
		txtPassword.setFont(Font.font(FONT));
		root.add(txtPassword, 1, 2);

		btnSubmit = new Button();
		btnSubmit.setFont(Font.font(FONT));
		btnSubmit.setText("Submit");
		btnSubmit.setOnAction(event -> checkCredentials());
		root.add(btnSubmit, 1, 3);

		Label lblUsername = new Label();
		lblUsername.setFont(Font.font(FONT));
		lblUsername.setText("Username:");
		root.add(lblUsername, 0, 1, 1, 1);
		GridPane.setHalignment(lblUsername, HPos.LEFT);

		Label lblPassword = new Label();
		lblPassword.setFont(Font.font(FONT));
		lblPassword.setText("Password:");
		root.add(lblPassword, 0, 2, 1, 1);
		GridPane.setHalignment(lblPassword, HPos.LEFT);

		Label lblNoAccount = new Label(); 
		lblNoAccount.setFont(Font.font(SMALL_FONT));
		lblNoAccount.setText("Do not have an account? Create one below!");
		root.add(lblNoAccount, 0, 5, 2, 1);  // Set row span to 2
		GridPane.setHalignment(lblNoAccount, HPos.CENTER);
		

	    // Creating a background image
		Image image = new Image("/images/loginPageBG.png");
	    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
	    

		
		Scene scene = new Scene(root, LOGIN_SCREEN_WIDTH, LOGIN_SCREEN_HEIGHT);
		Background background = new Background(backgroundImage);
	    root.setBackground(background);
		myStage.setTitle("NYTimesFX");
		myStage.setScene(scene);
		myStage.show();

	}

	private void checkCredentials() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();

		if (loginCheck.checkCredentials(password, username)) {
			myStage.setScene(getHomeScene());
		} else {
			showAlert(AlertType.ERROR, "Invalid Credentials", "Please enter a valid username and password.");
		}
	}

	private Scene getHomeScene() {
		GridPane homeRoot = new GridPane();
		homeRoot.setHgap(GAP);
		homeRoot.setVgap(GAP);
		homeRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		homeRoot.setAlignment(Pos.CENTER);

		Label lblHome = new Label();
		lblHome.setFont(Font.font(FONT));
		lblHome.setText("NYTimes");
		homeRoot.add(lblHome, 0, 0, 1, 1);
		GridPane.setHalignment(lblHome, HPos.LEFT);

		// Button to move to the Wordle game
		Button btnWordleGame = new Button();
		btnWordleGame.setFont(Font.font(FONT));
		btnWordleGame.setText("Wordle");
		btnWordleGame.setOnAction(event -> myStage.setScene(getWordleScene()));
		homeRoot.add(btnWordleGame, 0, 0, 1, 10);

		return new Scene(homeRoot, HOME_SCREEN_WIDTH, HOME_SCREEN_HEIGHT);
	}

	private Scene getWordleScene() {
		// Instantiating the WordleFX scene 
		wordleGame = new WordleFX(); 
		numGuesses = 0; 
		
		GridPane wordleRoot = new GridPane();
		wordleRoot.setHgap(GAP);
		wordleRoot.setVgap(GAP);
		wordleRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		wordleRoot.setAlignment(Pos.CENTER);

		box = new Square[HEIGHT][LENGTH];
		for (int col = 0; col <= LENGTH - 1; col++) {
			for (int row = 0; row <= HEIGHT - 1; row++) {
				box[row][col] = new Square();
				box[row][col].setLetter(Square.BLANK); // Initialize letter as a blank space
				wordleRoot.add(box[row][col], col, row);
			}
		}

		Button btnReturnHome = new Button();
		btnReturnHome.setFont(Font.font(SMALL_FONT));
		btnReturnHome.setText("Return Home");
		btnReturnHome.setOnAction(event -> myStage.setScene(getHomeScene()));
		GridPane.setHalignment(btnReturnHome, HPos.CENTER);
		wordleRoot.add(btnReturnHome, 2, 7); // Added 1 to the row index

		Scene wordleScene = new Scene(wordleRoot, WORDLE_SCREEN_WIDTH, WORDLE_SCREEN_HEIGHT);
		wordleScene.setOnKeyPressed(event -> handleKeyStroke(event));

		return wordleScene; 
	}

	private void handleKeyStroke(KeyEvent event) {
		boolean letterPlaced = false;
	    char character = event.getCode().toString().charAt(0);
	    

	    for (int row = 0; row < HEIGHT; row++) {
	        for (int col = 0; col < LENGTH; col++) {
	            if (box[row][col].getLetter() == Square.BLANK) {
	                box[row][col].setLetter(character);
	                letterPlaced = true;
	                break; // exit the inner loop after placing the character
	            }
	        }

	        if (letterPlaced) {
	            break; // exit the outer loop after placing the character
	        }
	    }

	    if (letterPlaced) {
	        if (WordleFX.isRowFull(numGuesses, box)) {
	        	numGuesses++;
	            if (wordleGame.wordClean(box, numGuesses - 1)) {
	            	showAlert(AlertType.INFORMATION, "You Win!", "The word was: "+ wordleGame.getKeyword()); 
	            } else if (WordleFX.isBoardFull(box)) {
	            	showAlert(AlertType.INFORMATION, "alert", "The word was: " + wordleGame.getKeyword()); 
	            }
	        }
	    }
	}


	private void showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
