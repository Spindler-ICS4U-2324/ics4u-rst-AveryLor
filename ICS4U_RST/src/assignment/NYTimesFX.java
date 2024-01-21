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
import javafx.scene.image.ImageView;
import javafx.scene.text.FontWeight;

public class NYTimesFX extends Application {

	private static final int GAP = 15;
	private static final int LENGTH = 5;
	private static final int HEIGHT = 6;
	private static final int LARGE_FONT = 40; 
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
	private TextField txtUsername, txtPassword, txtNewUsername, txtNewPassword;
	private BackgroundImage homepageBackgroundImage; 
	private Background homepageBackground; 
	
	private String FILE = "data/accountDB";
	private static int numGuesses = 0; 
	private boolean gameEnded; 
	private Scene wordleScene; 

	
	@Override
	public void stop() throws Exception { // At the end of the program, save all of the information into the text file 
		loginCheck.saveAllAccounts(FILE);
	}
	
	@Override
	public void start(Stage myStage) throws Exception {
		this.myStage = myStage;
		
		GridPane root = new GridPane();
		root.setHgap(GAP);
		root.setVgap(GAP);
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.CENTER);

		ImageView imgSymbol = new ImageView(getClass().getResource("/images/symbol.png").toString());
		imgSymbol.setFitWidth(100); // Set the desired width
		imgSymbol.setFitHeight(100); // Set the desired height
		root.add(imgSymbol, 0, 0); // Assuming you want it in the top-right corner (adjust column index as needed)
		
		Label lblTitle = new Label();
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT)); // Change "Arial" to your desired font family
		lblTitle.setText("Login Page");
		root.add(lblTitle, 1, 0, 1, 1);
		GridPane.setHalignment(lblTitle, HPos.LEFT);

		txtUsername = new TextField();
		txtUsername.setFont(Font.font("Times New Roman", FONT));
		root.add(txtUsername, 1, 1);

		txtPassword = new TextField();
		txtPassword.setFont(Font.font("Times New Roman", FONT));
		root.add(txtPassword, 1, 2);

		Button btnSubmit = new Button();
		btnSubmit.setFont(Font.font("Times New Roman",FONT));
		btnSubmit.setText("Submit");
		btnSubmit.setOnAction(event -> checkCredentials());
		root.add(btnSubmit, 0, 3, 2, 1);
		GridPane.setHalignment(btnSubmit, HPos.CENTER);

		Label lblUsername = new Label();
		lblUsername.setFont(Font.font("Times New Roman",FONT));
		lblUsername.setText("Username:");
		root.add(lblUsername, 0, 1, 1, 1);
		GridPane.setHalignment(lblUsername, HPos.LEFT);

		Label lblPassword = new Label();
		lblPassword.setFont(Font.font("Times New Roman", FONT));
		lblPassword.setText("Password:");
		root.add(lblPassword, 0, 2, 1, 1);
		GridPane.setHalignment(lblPassword, HPos.LEFT);

		Label lblNoAccount = new Label(); 
		lblNoAccount.setFont(Font.font("Times New Roman", SMALL_FONT));
		lblNoAccount.setText("Do not have an account? Create one below!");
		root.add(lblNoAccount, 0, 5, 2, 1);  // Set row span to 2
		GridPane.setHalignment(lblNoAccount, HPos.CENTER);
		
		Button btnCreateNewAccount = new Button();
		btnCreateNewAccount.setFont(Font.font("Times New Roman", FONT)); 
		btnCreateNewAccount.setText("Create New Account");
		btnCreateNewAccount.setOnAction(event -> myStage.setScene(getCreateAccountScene()));
		root.add(btnCreateNewAccount, 0, 6, 2, 1);
		GridPane.setHalignment(btnCreateNewAccount, HPos.CENTER);

	    // Creating a background image
		Image image = new Image("/images/loginPageBG.png");
		homepageBackgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		homepageBackground = new Background(homepageBackgroundImage);
	    root.setBackground(homepageBackground);
		
	    // Setting the scene 
		Scene scene = new Scene(root, LOGIN_SCREEN_WIDTH, LOGIN_SCREEN_HEIGHT);
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
			txtUsername.clear();
			txtPassword.clear();
		}
	}
	
	private void createNewAccount() {
		String newUsername = txtNewUsername.getText();
		String newPassword = txtNewPassword.getText();
	
		try {
			loginCheck.createNewAccount(newUsername, newPassword);
			myStage.setScene(getHomeScene());
		} catch (IllegalArgumentException e) {
			showAlert(AlertType.ERROR, "Username error", e.getMessage()); 
			txtUsername.clear();
			txtPassword.clear();
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
	
	private Scene getCreateAccountScene() {
		GridPane newAccountRoot = new GridPane();
		newAccountRoot.setHgap(GAP);
		newAccountRoot.setVgap(GAP);
		newAccountRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		newAccountRoot.setAlignment(Pos.CENTER);

		ImageView imgSymbol = new ImageView(getClass().getResource("/images/symbol.png").toString());
		imgSymbol.setFitWidth(100); // Set the desired width
		imgSymbol.setFitHeight(100); // Set the desired height
		newAccountRoot.add(imgSymbol, 0, 0); // Assuming you want it in the top-right corner (adjust column index as needed)
		
		Label lblTitle = new Label();
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT)); // Change "Arial" to your desired font family
		lblTitle.setText("Create A New Account");
		newAccountRoot.add(lblTitle, 1, 0, 1, 1);
		GridPane.setHalignment(lblTitle, HPos.LEFT);

		txtNewUsername = new TextField();
		txtNewUsername.setFont(Font.font("Times New Roman", FONT));
		newAccountRoot.add(txtNewUsername, 1, 1);

		txtNewPassword = new TextField();
		txtNewPassword.setFont(Font.font("Times New Roman", FONT));
		newAccountRoot.add(txtNewPassword, 1, 2);

		Label lblNewUsername = new Label();
		lblNewUsername.setFont(Font.font("Times New Roman",FONT));
		lblNewUsername.setText("Enter Username:");
		newAccountRoot.add(lblNewUsername, 0, 1, 1, 1);
		GridPane.setHalignment(lblNewUsername, HPos.LEFT);

		Label lblNewPassword = new Label();
		lblNewPassword.setFont(Font.font("Times New Roman", FONT));
		lblNewPassword.setText("Enter Password:");
		newAccountRoot.add(lblNewPassword, 0, 2, 1, 1);
		GridPane.setHalignment(lblNewPassword, HPos.LEFT);
		
		Button btnCreateNewAccount = new Button();
		btnCreateNewAccount.setFont(Font.font("Times New Roman", FONT)); 
		btnCreateNewAccount.setText("Create New Account");
		btnCreateNewAccount.setOnAction(event -> createNewAccount());
		newAccountRoot.add(btnCreateNewAccount, 0, 3, 2, 1);
		GridPane.setHalignment(btnCreateNewAccount, HPos.CENTER);
		
		homepageBackground = new Background(homepageBackgroundImage);
	    newAccountRoot.setBackground(homepageBackground);

		return new Scene(newAccountRoot, HOME_SCREEN_WIDTH, HOME_SCREEN_HEIGHT);
	}
	

	

	private Scene getWordleScene() {
		// Instantiating the WordleFX scene 
		wordleGame = new WordleFX(); 
		numGuesses = 0; 
		
		// Setting variables 
		gameEnded = false; 
		
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

		wordleScene = new Scene(wordleRoot, WORDLE_SCREEN_WIDTH, WORDLE_SCREEN_HEIGHT);
		wordleScene.setOnKeyPressed(event -> handleKeyStroke(event));


		return wordleScene;
	}

	private void handleKeyStroke(KeyEvent event) {
		// Variables 
		boolean letterPlaced = false;
	    char character = event.getCode().toString().charAt(0);
	    
	    // Game ended 
	    if (gameEnded) {
	    	return; 
	    }

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
	            	showAlert(AlertType.INFORMATION, "You Win!", "The word was: " + wordleGame.getKeyword()); 
	            	gameEnded = true; 
	            } else if (WordleFX.isBoardFull(box)) {
	            	showAlert(AlertType.INFORMATION, "alert", "The word was: " + wordleGame.getKeyword()); 
	            	gameEnded = true; 
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
