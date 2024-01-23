package assignment;

import javafx.application.Application;
import javafx.scene.paint.Color;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;

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
	private static final int SHOP_SCREEN_WIDTH = 1000;
	private static final int SHOP_SCREEN_HEIGHT = 1000;

	private LoginSystem loginCheck = new LoginSystem();
	private WordleFX wordleGame;
	private Square[][] box;
	private RewardShop pointsShop = new RewardShop();
	private Stage myStage;
	private Account currentAccount;

	// JavaFX elements
	TextField txtUsername, txtPassword, txtNewUsername, txtNewPassword;
	BackgroundImage homepageBackgroundImage;
	Background homepageBackground;
	ImageView imgSymbol = new ImageView(getClass().getResource("/images/symbol.png").toString());
	Label lblTrackPoints; 

	private String FILE = "data/accountDB";
	private int numFullGuesses = 0;
	private boolean gameEnded;
	private Scene wordleScene;

	@Override
	public void stop() throws Exception { // At the end of the program, save all of the information into the text file
		ArrayList<Account> accountList = loginCheck.getAccountList();
		AccountInformation.saveAllAccounts(FILE, accountList);

	}

	@Override
	public void start(Stage myStage) throws Exception {
		this.myStage = myStage;

		loginCheck.loadAllAccounts(FILE);
		pointsShop.loadAllAccounts(FILE);

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
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT)); // Change "Arial" to your desired
																						// font family
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
		btnSubmit.setFont(Font.font("Times New Roman", FONT));
		btnSubmit.setText("Submit");
		btnSubmit.setTextFill(Color.WHITE);
		btnSubmit.setStyle("-fx-background-color: black;");
		btnSubmit.setOnAction(event -> myStage.setScene(getCreateAccountScene()));
		btnSubmit.setOnAction(event -> checkCredentials());
		root.add(btnSubmit, 0, 3, 2, 1);
		GridPane.setHalignment(btnSubmit, HPos.CENTER);

		Label lblUsername = new Label();
		lblUsername.setFont(Font.font("Times New Roman", FONT));
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
		root.add(lblNoAccount, 0, 5, 2, 1); // Set row span to 2
		GridPane.setHalignment(lblNoAccount, HPos.CENTER);

		Button btnCreateNewAccount = new Button();
		btnCreateNewAccount.setFont(Font.font("Times New Roman", FONT));
		btnCreateNewAccount.setText("Create New Account");
		btnCreateNewAccount.setStyle("-fx-background-color: black;");
		btnCreateNewAccount.setTextFill(Color.WHITE);
		btnCreateNewAccount.setOnAction(event -> myStage.setScene(getCreateAccountScene()));
		root.add(btnCreateNewAccount, 0, 6, 2, 1);
		GridPane.setHalignment(btnCreateNewAccount, HPos.CENTER);

		// Creating a background image
		Image image = new Image("/images/loginPageBG.png");
		homepageBackgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		homepageBackground = new Background(homepageBackgroundImage);
		root.setBackground(homepageBackground);

		// Setting the scene
		Scene scene = new Scene(root, LOGIN_SCREEN_WIDTH, LOGIN_SCREEN_HEIGHT);
		myStage.setTitle("NYTimesFX");
		myStage.setScene(scene);
		myStage.show();

	}

	private void checkCredentials() {
		// Variables
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		int accountIndex;

		if (loginCheck.checkCredentials(password, username)) {
			accountIndex = loginCheck.findAccountIndexByUsername(username);
			currentAccount = loginCheck.getAccountByIndex(accountIndex);
			myStage.setScene(getHomeScene());
		} else {
			showAlert(AlertType.ERROR, "Invalid Credentials", "Please enter a valid username and password.");
			txtUsername.clear();
			txtPassword.clear();
		}
	}

	private void createNewAccount() {
		String newUsername, newPassword;
		ArrayList<Account> accountList;
		int accountIndex;

		try {
			newUsername = txtNewUsername.getText();
			newPassword = txtNewPassword.getText();
			loginCheck.createNewAccount(newUsername, newPassword);
		} catch (IllegalArgumentException e) {
			showAlert(AlertType.ERROR, "Username taken", e.getMessage());
			txtNewUsername.clear();
			txtNewPassword.clear();
			return;
		}
		accountList = loginCheck.getAccountList();
		AccountInformation.saveAllAccounts(FILE, accountList);
		accountIndex = loginCheck.findAccountIndexByUsername(newUsername);
		currentAccount = loginCheck.getAccountByIndex(accountIndex);
		myStage.setScene(getHomeScene());
	}

	private Scene getHomeScene() {
		GridPane homeRoot = new GridPane();
		homeRoot.setHgap(GAP);
		homeRoot.setVgap(GAP);
		homeRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		homeRoot.setAlignment(Pos.CENTER);

		HBox headerBox = new HBox(GAP);
		headerBox.setAlignment(Pos.CENTER);

		imgSymbol.setFitWidth(100);
		imgSymbol.setFitHeight(100);

		Label lblHome = new Label("New York Times Home Page");
		lblHome.setFont(Font.font("Times New Roman", LARGE_FONT));

		headerBox.getChildren().addAll(imgSymbol, lblHome);
		homeRoot.add(headerBox, 0, 0, 2, 1); // Span 2 columns for header
		GridPane.setHalignment(headerBox, HPos.CENTER);

		ImageView wordle = new ImageView(getClass().getResource("/images/wordle.png").toString());
		wordle.setFitWidth(500);
		wordle.setFitHeight(500);

		StackPane wordlePane = new StackPane(wordle);
		homeRoot.add(wordlePane, 0, 1, 2, 1); // Span 2 columns for wordle
		GridPane.setHalignment(wordlePane, HPos.CENTER);

		Button btnWordleGame = new Button("Wordle");
		btnWordleGame.setFont(Font.font("Times New Roman", FONT));
		btnWordleGame.setTextFill(Color.WHITE);
		btnWordleGame.setStyle("-fx-background-color: black;");
		btnWordleGame.setOnAction(event -> myStage.setScene(getWordleScene()));
		homeRoot.add(btnWordleGame, 0, 2, 2, 1); // Span 2 columns for the button
		GridPane.setHalignment(btnWordleGame, HPos.CENTER);

		Button btnPointsRedemption = new Button("Shop");
		btnPointsRedemption.setFont(Font.font("Times New Roman", FONT));
		btnPointsRedemption.setTextFill(Color.WHITE);
		btnPointsRedemption.setStyle("-fx-background-color: black;");
		btnPointsRedemption.setOnAction(event -> myStage.setScene(getPointsRedemptionShopScene()));
		homeRoot.add(btnPointsRedemption, 2, 2, 2, 1); // Span 2 columns for the button
		GridPane.setHalignment(btnPointsRedemption, HPos.CENTER);

		return new Scene(homeRoot, HOME_SCREEN_WIDTH, HOME_SCREEN_HEIGHT);
	}

	private Scene getPointsRedemptionShopScene() {

		GridPane redemptionRoot = new GridPane();
		redemptionRoot.setHgap(GAP);
		redemptionRoot.setVgap(GAP);
		redemptionRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		redemptionRoot.setAlignment(Pos.CENTER);

		Label lblTitle = new Label("Points Redemption Shop");
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT));
		redemptionRoot.add(lblTitle, 0, 0, 3, 1); // Span 3 columns for the label
		GridPane.setHalignment(lblTitle, HPos.CENTER);

		// Mock data for the ComboBox, replace it with your actual items
		ObservableList<RewardShop.ShopItem> items = FXCollections.observableArrayList(RewardShop.ShopItem.ITEM1,
				RewardShop.ShopItem.ITEM2, RewardShop.ShopItem.ITEM3);

		ComboBox<RewardShop.ShopItem> itemComboBox = new ComboBox<>(items);
		itemComboBox.setPromptText("Select Item to Redeem");
		redemptionRoot.add(itemComboBox, 0, 1);

		Button btnRedeemPoints = new Button("Redeem Points");
		btnRedeemPoints.setFont(Font.font(SMALL_FONT));
		redemptionRoot.add(btnRedeemPoints, 0, 2);
		GridPane.setHalignment(btnRedeemPoints, HPos.CENTER);

		Button btnReturnHome = new Button("Return Home");
		btnReturnHome.setFont(Font.font(SMALL_FONT));
		btnReturnHome.setOnAction(event -> myStage.setScene(getHomeScene()));
		redemptionRoot.add(btnReturnHome, 0, 3); // Adjusted row index

		Scene redemptionScene = new Scene(redemptionRoot, SHOP_SCREEN_WIDTH, SHOP_SCREEN_HEIGHT);

		// Assuming you have a method to handle points redemption in your RewardShop
		// class
		btnRedeemPoints.setOnAction(event -> {
			RewardShop.ShopItem selectedShopItem = itemComboBox.getValue();
			if (selectedShopItem != null) {

				boolean success = pointsShop.redeemItem(selectedShopItem, currentAccount.getAccountIndex());
				if (success) {
					// Handle successful redemption (e.g., display a message)
				} else {
					// Handle insufficient points (e.g., display an error message)
				}
			} else {
				// Handle no item selected (e.g., display a message)
			}
		});

		return redemptionScene;
	}

	private Scene getCreateAccountScene() {
		GridPane newAccountRoot = new GridPane();
		newAccountRoot.setHgap(GAP);
		newAccountRoot.setVgap(GAP);
		newAccountRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		newAccountRoot.setAlignment(Pos.CENTER);

		imgSymbol.setFitWidth(100); // Set the desired width
		imgSymbol.setFitHeight(100); // Set the desired height
		newAccountRoot.add(imgSymbol, 0, 0); // Assuming you want it in the top-right corner (adjust column index as
												// needed)

		Label lblTitle = new Label();
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT)); // Change "Arial" to your desired
																						// font family
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
		lblNewUsername.setFont(Font.font("Times New Roman", FONT));
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
		btnCreateNewAccount.setTextFill(Color.WHITE);
		btnCreateNewAccount.setStyle("-fx-background-color: black;");
		btnCreateNewAccount.setOnAction(event -> createNewAccount());
		newAccountRoot.add(btnCreateNewAccount, 0, 3, 2, 1);
		GridPane.setHalignment(btnCreateNewAccount, HPos.CENTER);

		homepageBackground = new Background(homepageBackgroundImage);
		newAccountRoot.setBackground(homepageBackground);

		return new Scene(newAccountRoot, LOGIN_SCREEN_WIDTH + 300, LOGIN_SCREEN_HEIGHT);
	}

	private Scene getWordleScene() {
		// Instantiating the WordleFX scene
		wordleGame = new WordleFX();
		numFullGuesses = 0;

		// Setting variables
		gameEnded = false;

		GridPane wordleRoot = new GridPane();
		wordleRoot.setHgap(GAP);
		wordleRoot.setVgap(GAP);
		wordleRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		wordleRoot.setAlignment(Pos.CENTER);

		imgSymbol.setFitWidth(100); // Set the desired width
		imgSymbol.setFitHeight(100); // Set the desired height
		wordleRoot.add(imgSymbol, 0, 0); // Assuming you want it in the top-right corner (adjust column index as needed)

		// Adding label at the top
		Label lblTitle = new Label("Wordle");
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT));
		wordleRoot.add(lblTitle, 1, 0, 3, 1); // Span 3 columns for the label
		GridPane.setHalignment(lblTitle, HPos.CENTER);

		box = new Square[HEIGHT][LENGTH];
		for (int col = 0; col <= LENGTH - 1; col++) {
			for (int row = 0; row <= HEIGHT - 1; row++) {
				box[row][col] = new Square();
				box[row][col].setLetter(Square.BLANK); // Initialize letter as a blank space
				wordleRoot.add(box[row][col], col, row + 1);
			}
		}

		Button btnReturnHome = new Button();
		btnReturnHome.setFont(Font.font("Times New Roman", SMALL_FONT));
		btnReturnHome.setText("Return Home");
		btnReturnHome.setTextFill(Color.WHITE);
		btnReturnHome.setStyle("-fx-background-color: black;");
		btnReturnHome.setOnAction(event -> myStage.setScene(getHomeScene()));
		GridPane.setHalignment(btnReturnHome, HPos.CENTER);
		wordleRoot.add(btnReturnHome, 2, 8); // Added 1 to the row index

		wordleScene = new Scene(wordleRoot, WORDLE_SCREEN_WIDTH, WORDLE_SCREEN_HEIGHT);
		wordleScene.setOnKeyPressed(event -> handleKeyStroke(event));

		return wordleScene;
	}

	private void handleKeyStroke(KeyEvent event) {
		// Variables
		boolean win; 
		boolean letterPlaced = false;
		char character = event.getCode().toString().charAt(0);

		// Game ended
		if (gameEnded) {
			return;
		}

		if (event.getCode() == KeyCode.BACK_SPACE) {
			handleBackspace(); // Handle backspace key press
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
			if (WordleFX.isRowFull(numFullGuesses, box)) {
				numFullGuesses++;
				if (wordleGame.wordClean(box, numFullGuesses - 1)) {
					showAlert(AlertType.INFORMATION, "You Win!", "The word was: " + wordleGame.getKeyword());
					win = true; 
					gameEnded = true;
					pointsShop.incLoyaltyPoints(win, currentAccount.getAccountIndex());
				} else if (WordleFX.isBoardFull(box)) {
					showAlert(AlertType.INFORMATION, "alert", "The word was: " + wordleGame.getKeyword());
					win = false; 
					gameEnded = true;
				}
			}

		}
	
	}

	
	// TODO Fix the backspace algorithm 
	private void handleBackspace() {
	    // Handle backspace key press, e.g., clear the last filled letter
	    for (int col = 0; col < LENGTH; col--) {
	        for (int row = 0; row < HEIGHT; row--) {
	            if (box[row][col].getLetter() != Square.BLANK) {
	                box[row][col].setLetter(Square.BLANK);
	                return; // exit the method after handling backspace
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
