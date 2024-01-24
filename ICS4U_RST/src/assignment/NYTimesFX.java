package assignment;

/**
 * @author s453512 
 * Date: 2024-01-08 
 * StringFinder.java 
 * Client code for the NYTimesFX class which displays the JavaFX scene
 */

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
import javafx.scene.input.KeyCode;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import javafx.scene.layout.VBox;

public class NYTimesFX extends Application {
	
	// Constants for sizing 
	private static final int GAP = 15;
	private static final int LENGTH = 5;
	private static final int HEIGHT = 6;
	private static final int LARGE_FONT = 40;
	private static final int FONT = 20;
	private static final int SMALL_FONT = 15;
	private static final int LOGIN_SCREEN_WIDTH = 1000;
	private static final int LOGIN_SCREEN_HEIGHT = 700;
	private static final int HOME_SCREEN_WIDTH = 1000;
	private static final int HOME_SCREEN_HEIGHT = 700;
	private static final int WORDLE_SCREEN_WIDTH = 1000;
	private static final int WORDLE_SCREEN_HEIGHT = 700;
	private static final int SHOP_SCREEN_WIDTH = 1000;
	private static final int SHOP_SCREEN_HEIGHT = 700;
	private static final int INVENTORY_SCREEN_WIDTH = 1000; 
	private static final int INVENTORY_SCREEN_HEIGHT = 700; 
	private static final int PRODUCT_SIZE = 200; 

	// Class's instantiated 
	private AccountsManager manager = new AccountsManager();
	private WordleFX wordleGame;
	private Square[][] box;
	private Stage myStage; // Used for opening new scenes 
	private Account currentAccount;
	private String selectedItem; 

	// JavaFX elements
	TextField txtUsername, txtPassword, txtNewUsername, txtNewPassword;
	BackgroundImage loginPageBackgroundImage;
	Background loginPageBackground;
	ImageView imgSymbol = new ImageView(getClass().getResource("/images/symbol.png").toString());
	Label lblTrackPoints; 

	// Other fields kept throughout 
	private String FILE = "data/accountDB";
	private int numFullGuesses = 0; // Track number of guesses 
	private boolean gameEnded; // Wordle game ending 
	private Scene mainScene; 

	@Override
	public void stop() throws Exception { // At the end of the program, save all of the information into the text file
		ArrayList<Account> accountList = manager.getAccountList(); // Getting the accountList 
		AccountInformation.saveAllAccounts(FILE, accountList); // To pass it into the utility class to save all the information 
	}

	@Override
	public void start(Stage myStage) throws Exception { // Main
		// Setting up 
		this.myStage = myStage; // Used for setting multiple scenes 
		manager.loadAllAccounts(FILE); // Loading up all the accounts 

		// Setting up the GridPane 
		GridPane root = new GridPane();
		root.setHgap(GAP);
		root.setVgap(GAP);
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.CENTER);

		// NY Times symbol image 
		ImageView imgSymbol = new ImageView(getClass().getResource("/images/symbol.png").toString());
		imgSymbol.setFitWidth(100); 
		imgSymbol.setFitHeight(100); 
		root.add(imgSymbol, 0, 0); 

		// Title 
		Label lblTitle = new Label();
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT)); 
		lblTitle.setText("Login Page");
		root.add(lblTitle, 1, 0, 1, 1);
		GridPane.setHalignment(lblTitle, HPos.LEFT);

		// Label next to the username text field
		Label lblUsername = new Label();
		lblUsername.setFont(Font.font("Times New Roman", FONT));
		lblUsername.setText("Username:");
		root.add(lblUsername, 0, 1, 1, 1);
		GridPane.setHalignment(lblUsername, HPos.LEFT);
		
		// Label next to the password text field 
		Label lblPassword = new Label();
		lblPassword.setFont(Font.font("Times New Roman", FONT));
		lblPassword.setText("Password:");
		root.add(lblPassword, 0, 2, 1, 1);
		GridPane.setHalignment(lblPassword, HPos.LEFT);
		
		// Username enter text field 
		txtUsername = new TextField();
		txtUsername.setFont(Font.font("Times New Roman", FONT));
		root.add(txtUsername, 1, 1);

		// Password enter text field 
		txtPassword = new TextField();
		txtPassword.setFont(Font.font("Times New Roman", FONT));
		root.add(txtPassword, 1, 2);

		// Submit button for credentials 
		Button btnSubmit = new Button();
		btnSubmit.setFont(Font.font("Times New Roman", FONT));
		btnSubmit.setText("Submit");
		btnSubmit.setTextFill(Color.WHITE);
		btnSubmit.setStyle("-fx-background-color: black;");
		btnSubmit.setOnAction(event -> checkCredentials()); // On Submit, check the credentials 
		root.add(btnSubmit, 0, 3, 2, 1);
		GridPane.setHalignment(btnSubmit, HPos.CENTER);

		// Create new account label 
		Label lblNoAccount = new Label();
		lblNoAccount.setFont(Font.font("Times New Roman", SMALL_FONT));
		lblNoAccount.setText("Do not have an account? Create one below!");
		root.add(lblNoAccount, 0, 5, 2, 1); // Set row span to 2
		GridPane.setHalignment(lblNoAccount, HPos.CENTER);

		// Create new account button 
		Button btnCreateNewAccount = new Button();
		btnCreateNewAccount.setFont(Font.font("Times New Roman", FONT));
		btnCreateNewAccount.setText("Create New Account");
		btnCreateNewAccount.setStyle("-fx-background-color: black;");
		btnCreateNewAccount.setTextFill(Color.WHITE);
		btnCreateNewAccount.setOnAction(event -> myStage.setScene(getCreateAccountScene())); // Allow them to create a new account 
		root.add(btnCreateNewAccount, 0, 6, 2, 1);
		GridPane.setHalignment(btnCreateNewAccount, HPos.CENTER);

		// Creating a background image
		Image img = new Image("/images/loginPageBG.png");
		loginPageBackgroundImage = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
		BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		loginPageBackground = new Background(loginPageBackgroundImage);
		root.setBackground(loginPageBackground);

		// Setting the scene
		mainScene = new Scene(root, LOGIN_SCREEN_WIDTH, LOGIN_SCREEN_HEIGHT);
		myStage.setTitle("NYTimesFX");
		myStage.setScene(mainScene);
		myStage.show();

	}

	// Used for checking the user's credentials 
	private void checkCredentials() {
		// Variables
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		int accountIndex;

		// Processing 
		if (manager.checkCredentials(password, username)) { // If the user's credentials do pass 
			accountIndex = manager.findAccountIndexByUsername(username); // Getting the accountIndex via username 
			currentAccount = manager.getAccountByIndex(accountIndex); // To get the current account instance (of this user)
			myStage.setScene(getHomeScene()); // This is done to easily access the setters within the account class 
		
		} else { // If the user's credentials do not pass 
			showAlert(AlertType.ERROR, "Invalid Credentials", "Please enter a valid username and password.");
			txtUsername.clear(); 
			txtPassword.clear();
			return; // Force the re-prompt and display the error message
		}
	}

	// Used for creating a new account, similar to the checkCredentials() method 
	private void createNewAccount() {
		// Variables 
		String newUsername, newPassword;
		int accountIndex;
		
		// Processing and file handling 
		try {
			newUsername = txtNewUsername.getText(); // Getting text 
			newPassword = txtNewPassword.getText();
			manager.createNewAccount(newUsername, newPassword);
		} catch (IllegalArgumentException e) {
			showAlert(AlertType.ERROR, "Username taken", e.getMessage());
			txtNewUsername.clear(); // Forcing re-prompt if the user input is no-good 
			txtNewPassword.clear();
			return;
		}
		// Creating the new instance of account and sending the user to the home page 
		accountIndex = manager.findAccountIndexByUsername(newUsername);
		currentAccount = manager.getAccountByIndex(accountIndex);
		myStage.setScene(getHomeScene());
	}

	// Home screen scene 
	private Scene getHomeScene() {
	    // Setting up the GridPane 
		GridPane homeRoot = new GridPane();
	    homeRoot.setHgap(GAP);
	    homeRoot.setVgap(GAP);
	    homeRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
	    homeRoot.setAlignment(Pos.CENTER);

	    // Header
	    HBox headerBox = new HBox(GAP); 
	    headerBox.setAlignment(Pos.CENTER);
	    imgSymbol.setFitWidth(100);
	    imgSymbol.setFitHeight(100);
	    Label lblHome = new Label("New York Times Home Page");
	    lblHome.setFont(Font.font("Times New Roman", LARGE_FONT));
	    headerBox.getChildren().addAll(imgSymbol, lblHome);
	    homeRoot.add(headerBox, 1, 0, 6, 1); // Span 3 columns for header
	    GridPane.setHalignment(headerBox, HPos.CENTER);

	    // Wordle Image
	    ImageView imgWordle = new ImageView(getClass().getResource("/images/wordle.png").toString());
	    imgWordle.setFitWidth(300); // Adjust the width as needed
	    imgWordle.setFitHeight(300); // Adjust the height as needed
	    StackPane wordlePane = new StackPane(imgWordle);
	    homeRoot.add(wordlePane, 0, 1, 2, 1); // Span 2 columns for wordle
	    GridPane.setHalignment(wordlePane, HPos.CENTER);

	    // Wordle Button
	    Button btnWordleGame = new Button("Wordle");
	    btnWordleGame.setFont(Font.font("Times New Roman", FONT));
	    btnWordleGame.setTextFill(Color.WHITE);
	    btnWordleGame.setStyle("-fx-background-color: black;");
	    btnWordleGame.setOnAction(event -> myStage.setScene(getWordleScene()));
	    homeRoot.add(btnWordleGame, 1, 2, 1, 1); // Span 1 column for the button
	    GridPane.setHalignment(btnWordleGame, HPos.CENTER);
	    
	    // Points Shop
	    ImageView imgPointsShop = new ImageView(getClass().getResource("/images/pointsRedemptionShop.png").toString());
	    imgPointsShop.setFitWidth(300); // Adjust the width as needed
	    imgPointsShop.setFitHeight(300); // Adjust the height as needed
	    StackPane shopPane = new StackPane(imgPointsShop);
	    homeRoot.add(shopPane, 3, 1, 2, 1); // Span 2 columns for wordle
	    GridPane.setHalignment(shopPane, HPos.CENTER);

	    // Shop Button
	    Button btnPointsRedemption = new Button("Shop");
	    btnPointsRedemption.setFont(Font.font("Times New Roman", FONT));
	    btnPointsRedemption.setTextFill(Color.WHITE);
	    btnPointsRedemption.setStyle("-fx-background-color: black;");
	    btnPointsRedemption.setOnAction(event -> myStage.setScene(getPointsRedemptionShopScene()));
	    homeRoot.add(btnPointsRedemption, 3, 2, 2, 1); // Span 2 columns for the button
	    GridPane.setHalignment(btnPointsRedemption, HPos.CENTER);

	    // Inventory Image
	    ImageView imgInventory = new ImageView(getClass().getResource("/images/inventory.png").toString());
	    imgInventory.setFitWidth(300); // Adjust the width as needed
	    imgInventory.setFitHeight(300); // Adjust the height as needed
	    StackPane inventoryPane = new StackPane(imgInventory);
	    homeRoot.add(inventoryPane, 6, 1, 2, 1); // Span 2 columns for wordle
	    GridPane.setHalignment(inventoryPane, HPos.CENTER);
	    
	    // Inventory Button
	    Button btnInventory = new Button("Inventory");
	    btnInventory.setFont(Font.font("Times New Roman", FONT));
	    btnInventory.setTextFill(Color.WHITE);
	    btnInventory.setStyle("-fx-background-color: black;");
	    btnInventory.setOnAction(event -> myStage.setScene(getInventoryScene()));
	    homeRoot.add(btnInventory, 7, 2, 1, 1); // Span 1 column for the button
	    GridPane.setHalignment(btnInventory, HPos.LEFT);

	    // Points Box
	    VBox pointsBox = new VBox(GAP);
	    pointsBox.setAlignment(Pos.CENTER);
	    lblTrackPoints = new Label("Points: " + currentAccount.getPoints());
	    lblTrackPoints.setFont(Font.font("Times New Roman", FONT));
	    pointsBox.getChildren().add(lblTrackPoints);
	    homeRoot.add(pointsBox, 3, 4, 2, 1); // Span 2 columns for points
	    GridPane.setHalignment(pointsBox, HPos.CENTER);
	    
	    // Background
	    Image imgHomePageBackground = new Image("/images/homePageBG.png");
		BackgroundImage homePageBackgroundImage = new BackgroundImage(imgHomePageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background homePageBackground = new Background(homePageBackgroundImage);
		homeRoot.setBackground(homePageBackground);

		// Returning the scene so that it set 
	    return new Scene(homeRoot, HOME_SCREEN_WIDTH, HOME_SCREEN_HEIGHT);
	}

	// Inventory view 
	private Scene getInventoryScene() {
		// Setting up the GridPane 
	    GridPane inventoryRoot = new GridPane();
	    inventoryRoot.setHgap(GAP);
	    inventoryRoot.setVgap(GAP);
	    inventoryRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
	    inventoryRoot.setAlignment(Pos.CENTER);

	    // Title
	    Label lblInventoryTitle = new Label("Your Inventory");
	    lblInventoryTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT));
	    inventoryRoot.add(lblInventoryTitle, 0, 0, 2, 1); // Span 2 columns for the title
	    GridPane.setHalignment(lblInventoryTitle, HPos.CENTER);

	    // Description Label
	    Label lblInventoryDescription = new Label("Here are the items you have redeemed:");
	    lblInventoryDescription.setFont(Font.font("Times New Roman", FONT));
	    inventoryRoot.add(lblInventoryDescription, 0, 1, 2, 1); // Span 2 columns for the description
	    GridPane.setHalignment(lblInventoryDescription, HPos.CENTER);

	    // Assuming you have a method in your PointsRewardShop class to get the purchased items
	    ArrayList<AccountsManager.ShopItem> userItems = manager.getAccountByIndex(currentAccount.getAccountIndex()).getPurchasedItems();

	    // Create an ObservableList for the inventory items
	    ObservableList<String> inventoryItems = FXCollections.observableArrayList();
	    for (AccountsManager.ShopItem item : userItems) {
	        inventoryItems.add(item.getItemName()); // Fill the inventory with all the user's items 
	    }

	    // ListView to display the inventory items
	    ListView<String> inventoryListView = new ListView<>(inventoryItems);
	    inventoryListView.setPrefHeight(150); // Adjust the height as needed
	    inventoryListView.setStyle("-fx-font-size: " + FONT + "pt"); // Set font size
	    inventoryRoot.add(inventoryListView, 0, 2, 2, 1); // Span 2 columns for the ListView
	    GridPane.setHalignment(inventoryListView, HPos.CENTER);

	    // Return Home Button
	    Button btnReturnHome = new Button("Return Home");
	    btnReturnHome.setFont(Font.font("Times New Roman", FONT));
	    btnReturnHome.setTextFill(Color.WHITE);
	    btnReturnHome.setStyle("-fx-background-color: black;");
	    btnReturnHome.setOnAction(event -> myStage.setScene(getHomeScene())); // Set the action to return to the home scene
	    inventoryRoot.add(btnReturnHome, 0, 3, 2, 1); // Span 2 columns for the button
	    GridPane.setHalignment(btnReturnHome, HPos.CENTER);

	    // Background
	    Image imgInventoryPageBackground = new Image("/images/inventoryBG.png");
		BackgroundImage inventoryPageBackgroundImage = new BackgroundImage(imgInventoryPageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background inventoryPageBackground = new Background(inventoryPageBackgroundImage);
		inventoryRoot.setBackground(inventoryPageBackground);
	    
	    return new Scene(inventoryRoot, INVENTORY_SCREEN_WIDTH, INVENTORY_SCREEN_HEIGHT);
	}

	// Points Redemption Shop Scene 
	private Scene getPointsRedemptionShopScene() {
		// Initializing scene constraints 
		GridPane redemptionRoot = new GridPane();
		redemptionRoot.setHgap(GAP);
		redemptionRoot.setVgap(GAP);
		redemptionRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		redemptionRoot.setAlignment(Pos.CENTER);
		Scene redemptionScene = new Scene(redemptionRoot, SHOP_SCREEN_WIDTH, SHOP_SCREEN_HEIGHT);

		// Title 
		Label lblTitle = new Label("Points Redemption Shop");
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT));
		redemptionRoot.add(lblTitle, 0, 0, 3, 1); // Span 3 columns for the label
		GridPane.setHalignment(lblTitle, HPos.CENTER);

		// LCBO Giftcard
		ImageView imgLCBO = new ImageView(getClass().getResource("/images/lcboGiftcard.png").toString());
		imgLCBO.setFitWidth(PRODUCT_SIZE);
		imgLCBO.setFitHeight(PRODUCT_SIZE);
		StackPane lcboPane = new StackPane(imgLCBO);
		redemptionRoot.add(lcboPane, 0, 1, 2, 1); // Span 2 columns for wordle
		GridPane.setHalignment(lcboPane, HPos.CENTER);

		// LCBO Giftcard Button
		Button btnLCBO = new Button("Select");
		btnLCBO.setFont(Font.font("Times New Roman", FONT));
		btnLCBO.setTextFill(Color.WHITE);
		btnLCBO.setStyle("-fx-background-color: black;");
		btnLCBO.setOnAction(event -> selectedItem = AccountsManager.ShopItem.ITEM1.getItemName());
		redemptionRoot.add(btnLCBO, 0, 2, 2, 1); // Span 2 columns for the button
		GridPane.setHalignment(btnLCBO, HPos.CENTER);

		// Newspaper
		ImageView imgNewspaper = new ImageView(getClass().getResource("/images/newspaper.png").toString());
		imgNewspaper.setFitWidth(PRODUCT_SIZE);
		imgNewspaper.setFitHeight(PRODUCT_SIZE);
		StackPane newspaperPane = new StackPane(imgNewspaper);
		redemptionRoot.add(newspaperPane, 2, 1, 2, 1); // Span 2 columns for wordle
		GridPane.setHalignment(newspaperPane, HPos.CENTER);

		// Newspaper Button
		Button btnNewspaper = new Button("Select");
		btnNewspaper.setFont(Font.font("Times New Roman", FONT));
		btnNewspaper.setTextFill(Color.WHITE);
		btnNewspaper.setStyle("-fx-background-color: black;");
		btnNewspaper.setOnAction(event -> selectedItem = AccountsManager.ShopItem.ITEM2.getItemName());
		redemptionRoot.add(btnNewspaper, 2, 2, 2, 1); // Span 2 columns for the button
		GridPane.setHalignment(btnNewspaper, HPos.CENTER);

		// Costco Giftcard
		ImageView imgCostco = new ImageView(getClass().getResource("/images/costcoGiftcard.png").toString());
		imgCostco.setFitWidth(PRODUCT_SIZE);
		imgCostco.setFitHeight(PRODUCT_SIZE);
		StackPane inventoryCostco = new StackPane(imgCostco);
		redemptionRoot.add(inventoryCostco, 4, 1, 2, 1); // Span 2 columns for wordle
		GridPane.setHalignment(inventoryCostco, HPos.CENTER);

		// Costco Giftcard Button
		Button btnCostco = new Button("Select");
		btnCostco.setFont(Font.font("Times New Roman", FONT));
		btnCostco.setTextFill(Color.WHITE);
		btnCostco.setStyle("-fx-background-color: black;");
		btnCostco.setOnAction(event -> selectedItem = AccountsManager.ShopItem.ITEM3.getItemName());
		redemptionRoot.add(btnCostco, 4, 2, 2, 1); // Span 2 columns for the button
		GridPane.setHalignment(btnCostco, HPos.CENTER);

		// Coffee Giftcard
		ImageView imgCoffee = new ImageView(getClass().getResource("/images/coffee.png").toString());
		imgCoffee.setFitWidth(PRODUCT_SIZE);
		imgCoffee.setFitHeight(PRODUCT_SIZE);
		StackPane coffeePane = new StackPane(imgCoffee);
		redemptionRoot.add(coffeePane, 7, 1, 2, 1); // Span 2 columns for wordle
		GridPane.setHalignment(coffeePane, HPos.CENTER);

		// Coffee Giftcard Button
		Button btnCoffee = new Button("Select");
		btnCoffee.setFont(Font.font("Times New Roman", FONT));
		btnCoffee.setTextFill(Color.WHITE);
		btnCoffee.setStyle("-fx-background-color: black;");
		btnCoffee.setOnAction(event -> selectedItem = AccountsManager.ShopItem.ITEM4.getItemName());
		redemptionRoot.add(btnCoffee, 7, 2, 2, 1); // Span 2 columns for the button
		GridPane.setHalignment(btnCoffee, HPos.CENTER);

		// Redeem Points Button
		Button btnRedeemPoints = new Button("Redeem Points");
		btnRedeemPoints.setFont(Font.font("Times New Roman", FONT));
		redemptionRoot.add(btnRedeemPoints, 0, 4, 8, 1); // Span 8 columns for the button
		btnRedeemPoints.setTextFill(Color.WHITE);
		btnRedeemPoints.setStyle("-fx-background-color: red;");
		GridPane.setHalignment(btnRedeemPoints, HPos.LEFT);

		// Return Home Button
		Button btnReturnHome = new Button("Return Home");
		btnReturnHome.setFont(Font.font("Times New Roman", FONT));
		btnReturnHome.setTextFill(Color.WHITE);
		btnReturnHome.setStyle("-fx-background-color: black;");
		btnReturnHome.setOnAction(event -> myStage.setScene(getHomeScene()));
		redemptionRoot.add(btnReturnHome, 0, 5, 8, 1); // Span 8 columns for the button
		GridPane.setHalignment(btnReturnHome, HPos.LEFT);

		// Points Box
		VBox pointsBox = new VBox(GAP);
		pointsBox.setAlignment(Pos.BASELINE_LEFT);
		lblTrackPoints = new Label("Points: " + currentAccount.getPoints());
		lblTrackPoints.setFont(Font.font("Times New Roman", FONT));
		pointsBox.getChildren().add(lblTrackPoints);
		redemptionRoot.add(pointsBox, 0, 6, 8, 1); // Span 8 columns for points
		GridPane.setHalignment(pointsBox, HPos.LEFT);
		
		btnRedeemPoints.setOnAction(event -> { // Set the action for redeeming points when the button is clicked
		    String selectedShopItemName = selectedItem; // Get the name of the selected item
		    
		    // Check if a valid item name is selected
		    if (selectedShopItemName != null) {
		        AccountsManager.ShopItem selectedShopItem = manager.getShopItemByName(selectedShopItemName); // Retrieve the ShopItem object by its name
		        
		        // Check if a valid ShopItem is retrieved
		        if (selectedShopItem != null) {
		            boolean success = manager.redeemItem(selectedShopItem, currentAccount.getAccountIndex()); // Attempt to redeem the selected item
		            
		            // Handle successful redemption
		            if (success) {
		                currentAccount.setUserHasItems(true); // Set to true for file output
		                currentAccount.purchaseItem(selectedShopItem); // Mark the item as purchased
		                showAlert(AlertType.INFORMATION, "Success", "Thank you for redeeming your points!"); // Display a success message
		                lblTrackPoints.setText("Points: " + currentAccount.getPoints()); // Update the displayed points
		            } else {
		                // Handle insufficient points (e.g., display an error message)
		                showAlert(AlertType.ERROR, "Error", "There was a system error, you do not have enough points for a " + selectedItem + ".");
		            }
		        }
		    } else {
		        // Handle no item selected (e.g., display an error message)
		        showAlert(AlertType.ERROR, "Error", "There was no item selected.");
		    }
		});
	    
	    // Setting the background 
	    Image imgShopPageBackground = new Image("/images/wordleBG.png");
		BackgroundImage shopPageBackgroundImage = new BackgroundImage(imgShopPageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background shopPageBackground = new Background(shopPageBackgroundImage);
		redemptionRoot.setBackground(shopPageBackground);
	    
		// Return the scene 
		return redemptionScene;
	}

	// Create account scene 
	private Scene getCreateAccountScene() {
		// Setting up the GridPane 
		GridPane newAccountRoot = new GridPane();
		newAccountRoot.setHgap(GAP);
		newAccountRoot.setVgap(GAP);
		newAccountRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		newAccountRoot.setAlignment(Pos.CENTER);

		// NYTimes Symbol 
		imgSymbol.setFitWidth(100); 
		imgSymbol.setFitHeight(100); 
		newAccountRoot.add(imgSymbol, 0, 0); 

		// Adding a Title 
		Label lblTitle = new Label();
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT)); 
		lblTitle.setText("Create A New Account");
		newAccountRoot.add(lblTitle, 1, 0, 1, 1);
		GridPane.setHalignment(lblTitle, HPos.LEFT);

		// New username entry field  
		txtNewUsername = new TextField();
		txtNewUsername.setFont(Font.font("Times New Roman", FONT));
		newAccountRoot.add(txtNewUsername, 1, 1);

		// New password entry field 
		txtNewPassword = new TextField();
		txtNewPassword.setFont(Font.font("Times New Roman", FONT));
		newAccountRoot.add(txtNewPassword, 1, 2);

		// New username descriptive label 
		Label lblNewUsername = new Label();
		lblNewUsername.setFont(Font.font("Times New Roman", FONT));
		lblNewUsername.setText("Enter Username:");
		newAccountRoot.add(lblNewUsername, 0, 1, 1, 1);
		GridPane.setHalignment(lblNewUsername, HPos.LEFT);

		// New password descriptive label 
		Label lblNewPassword = new Label();
		lblNewPassword.setFont(Font.font("Times New Roman", FONT));
		lblNewPassword.setText("Enter Password:");
		newAccountRoot.add(lblNewPassword, 0, 2, 1, 1);
		GridPane.setHalignment(lblNewPassword, HPos.LEFT);

		// Create new account 
		Button btnCreateNewAccount = new Button();
		btnCreateNewAccount.setFont(Font.font("Times New Roman", FONT));
		btnCreateNewAccount.setText("Create New Account");
		btnCreateNewAccount.setTextFill(Color.WHITE);
		btnCreateNewAccount.setStyle("-fx-background-color: black;");
		btnCreateNewAccount.setOnAction(event -> createNewAccount()); // Event handler which will create the new account 
		newAccountRoot.add(btnCreateNewAccount, 0, 3, 2, 1);
		GridPane.setHalignment(btnCreateNewAccount, HPos.CENTER);

		// Set the Background 
		loginPageBackground = new Background(loginPageBackgroundImage);
		newAccountRoot.setBackground(loginPageBackground);

		// Return the scene 
		return new Scene(newAccountRoot, LOGIN_SCREEN_WIDTH, LOGIN_SCREEN_HEIGHT);
	}

	// The Wordle Scene 
	private Scene getWordleScene() {
		// Instantiating the WordleFX scene and resetting important variables 
		wordleGame = new WordleFX();
		numFullGuesses = 0; // No guesses made yet 
		gameEnded = false; // The game has not ended yet 

		// Setting up the Gridpane 
		GridPane wordleRoot = new GridPane();
		wordleRoot.setHgap(GAP);
		wordleRoot.setVgap(GAP);
		wordleRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		wordleRoot.setAlignment(Pos.CENTER);

		// Add the symbol image 
		imgSymbol.setFitWidth(100); // Set the desired width
		imgSymbol.setFitHeight(100); // Set the desired height
		wordleRoot.add(imgSymbol, 0, 0, 2, 1); // Assuming you want it in the top-right corner (adjust column index as needed)

		// Adding label at the top
		Label lblTitle = new Label("Wordle");
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT));
		wordleRoot.add(lblTitle, 1, 0, 3, 1); // Span 3 columns for the label
		GridPane.setHalignment(lblTitle, HPos.CENTER);

		// Setting the board up 
		box = new Square[HEIGHT][LENGTH]; // Initialize the size 
		for (int col = 0; col <= LENGTH - 1; col++) { // Go through all the columns 
			for (int row = 0; row <= HEIGHT - 1; row++) { // Go through all the rows
				box[row][col] = new Square();
				box[row][col].setLetter(Square.BLANK); // Initialize letter as a blank space
				wordleRoot.add(box[row][col], col, row + 1);
			}
		}

		// Adding the return home button 
		Button btnReturnHome = new Button();
		btnReturnHome.setFont(Font.font("Times New Roman", SMALL_FONT));
		btnReturnHome.setText("Return Home");
		btnReturnHome.setTextFill(Color.WHITE);
		btnReturnHome.setStyle("-fx-background-color: black;");
		btnReturnHome.setOnAction(event -> myStage.setScene(getHomeScene())); // If clicked, send them to the home scene 
		wordleRoot.add(btnReturnHome, 1, 8, 3, 1); // Added 1 to the row index
		GridPane.setHalignment(btnReturnHome, HPos.CENTER);

		// Points Box
	    VBox pointsBox = new VBox(GAP);
	    pointsBox.setAlignment(Pos.CENTER);
	    lblTrackPoints = new Label("Points: " + currentAccount.getPoints());
	    lblTrackPoints.setFont(Font.font("Times New Roman", FONT));
	    pointsBox.getChildren().add(lblTrackPoints);
	    wordleRoot.add(pointsBox, 3, 8, 2, 1); // Span 2 columns for points
	    GridPane.setHalignment(pointsBox, HPos.CENTER);
	    
	    // Setting up the background 
	    Image imgWordlePageBackground = new Image("/images/wordleBG.png");
		BackgroundImage wordlePageBackgroundImage = new BackgroundImage(imgWordlePageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background wordlePageBackground = new Background(wordlePageBackgroundImage);
		wordleRoot.setBackground(wordlePageBackground);

		// Declaring the scene as a variable so I can use .setOnKeyPressed(event)
		Scene wordleScene = new Scene(wordleRoot, WORDLE_SCREEN_WIDTH, WORDLE_SCREEN_HEIGHT);
		wordleScene.setOnKeyPressed(event -> handleKeyStroke(event));

		return wordleScene;
	}

	// If a key is pressed 
	private void handleKeyStroke(KeyEvent event) {
		// Variables
		boolean win; // The user has won 
		boolean letterPlaced = false; // The letter has not yet been placed in the board 
		char character = event.getCode().toString().charAt(0); // The user's character 

		// Game ended
		if (gameEnded) {
			return;
		}

		// If the user enters backspace 
		if (event.getCode() == KeyCode.BACK_SPACE) {
			handleBackspace(); // Handle backspace key press
			return;
		}
		
		// Check if the entered character forms a valid word
		for (int row = 0; row < HEIGHT; row++) { // Go through all the rows 
			for (int col = 0; col < LENGTH; col++) { // Go through all the columns 
				if (box[row][col].getLetter() == Square.BLANK) { // If the square is blank 
					box[row][col].setLetter(character); // Set the character to the current character 
					letterPlaced = true; // Now the letter is placed
					break; // exit the inner loop after placing the character
				}
			}

			if (letterPlaced) { // If the letter has been placed, we can now break out of this loop 
				break; // exit the outer loop after placing the character, don't enter it anywhere else 
			}
		}

		// Processing 
		if (WordleFX.isRowFull(numFullGuesses, box)) { // Check if the row is now full
			String currentGuess = WordleFX.interpretUserGuess(numFullGuesses, box); // Interpet the user's guess, get the String from it 
			if (WordleFX.isWordValid(currentGuess) == false) { // Display an error message or take appropriate action
				handleInvalidWord(numFullGuesses); // If the word is invalid, remove the guess and force the re-prompt 
				showAlert(AlertType.ERROR, "Invalid Word", "The entered word, " + currentGuess + " is not a valid word!");
				return;
			}

			// Otherwise, continue on and increment the number of guesses 
			numFullGuesses++;
			if (wordleGame.wordClean(box, numFullGuesses - 1)) { // Clean the word, if it cleans true, the user has suceeded 
				showAlert(AlertType.INFORMATION, "You Win! You get 100 points!", "The word was: " + wordleGame.getKeyword());
				win = true; // User has won 
				gameEnded = true; // Game has ended 
				manager.incLoyaltyPoints(win, currentAccount.getAccountIndex()); // Increment the loyalty points based on the result 
				lblTrackPoints.setText("Points: " + currentAccount.getPoints()); // Set the points 
			} else if (WordleFX.isBoardFull(box)) { // If the board is full, the user has lost 
				showAlert(AlertType.INFORMATION, "Good try! You get 50 points!", "The word was: " + wordleGame.getKeyword()); // You get 50 points, 
				win = false;
				gameEnded = true;
				manager.incLoyaltyPoints(win, currentAccount.getAccountIndex()); // Upddate the points 
				lblTrackPoints.setText("Points: " + currentAccount.getPoints()); // Update the points label  
			}
		}
	}

	// Handling backspace enters 
	private void handleBackspace() {
	    for (int col = LENGTH - 1; col >= 0; col--) { // Done this way to remove from the last char entered 
	        for (int row = HEIGHT - 1; row >= 0; row--) {
	            if (box[row][col].getLetter() != Square.BLANK) { // If not blank 
	                if (numFullGuesses == 0 || !WordleFX.isRowFull(row, box)) { // If in 0 remove, but check otherwise if the row is full yet 
	                    box[row][col].setLetter(Square.BLANK); // Set the letter to blank if the conditions are met 
	                    return; 
	                }
	            }
	        }
	    }
	}
	
	// Handling an invalid word 
	private void handleInvalidWord(int numFullGuesses) {
	    for (int col = 0; col < LENGTH; col++) { // Completely remove that row (set it all to blank)
	    	box[numFullGuesses][col].setLetter(Square.BLANK);
	    }
	}

	// Used for quickly showing information 
	private void showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	// Main
	public static void main(String[] args) {
		launch(args);
	}
}
