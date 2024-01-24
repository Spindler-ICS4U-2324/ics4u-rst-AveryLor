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
import javafx.scene.control.ListView;
import java.util.ArrayList;
import javafx.scene.layout.VBox;

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
	private static final int HOME_SCREEN_HEIGHT = 700;
	private static final int WORDLE_SCREEN_WIDTH = 1000;
	private static final int WORDLE_SCREEN_HEIGHT = 700;
	private static final int SHOP_SCREEN_WIDTH = 1000;
	private static final int SHOP_SCREEN_HEIGHT = 700;
	private static final int INVENTORY_SCREEN_WIDTH = 1000; 
	private static final int INVENTORY_SCREEN_HEIGHT = 700; 
	private static final int PRODUCT_SIZE = 200; 


	private AccountsManager manager = new AccountsManager();
	private WordleFX wordleGame;
	private Square[][] box;
	private Stage myStage;
	private Account currentAccount;
	private String selectedItem; 

	// JavaFX elements
	TextField txtUsername, txtPassword, txtNewUsername, txtNewPassword;
	BackgroundImage loginPageBackgroundImage;
	Background loginPageBackground;
	ImageView imgSymbol = new ImageView(getClass().getResource("/images/symbol.png").toString());
	Label lblTrackPoints; 

	private String FILE = "data/accountDB";
	private int numFullGuesses = 0;
	private boolean gameEnded;
	private Scene wordleScene;

	@Override
	public void stop() throws Exception { // At the end of the program, save all of the information into the text file
		ArrayList<Account> accountList = manager.getAccountList();
		AccountInformation.saveAllAccounts(FILE, accountList);
	}

	@Override
	public void start(Stage myStage) throws Exception {
		this.myStage = myStage;

		manager.loadAllAccounts(FILE);

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
		Image img = new Image("/images/loginPageBG.png");
		loginPageBackgroundImage = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
		BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		loginPageBackground = new Background(loginPageBackgroundImage);
		root.setBackground(loginPageBackground);

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

		if (manager.checkCredentials(password, username)) {
			accountIndex = manager.findAccountIndexByUsername(username);
			currentAccount = manager.getAccountByIndex(accountIndex);
			myStage.setScene(getHomeScene());
		} else {
			showAlert(AlertType.ERROR, "Invalid Credentials", "Please enter a valid username and password.");
			txtUsername.clear();
			txtPassword.clear();
			return; 
		}
	}

	private void createNewAccount() {
		String newUsername, newPassword;
		ArrayList<Account> accountList;
		int accountIndex;

		try {
			newUsername = txtNewUsername.getText();
			newPassword = txtNewPassword.getText();
			manager.createNewAccount(newUsername, newPassword);
		} catch (IllegalArgumentException e) {
			showAlert(AlertType.ERROR, "Username taken", e.getMessage());
			txtNewUsername.clear();
			txtNewPassword.clear();
			return;
		}
		accountList = manager.getAccountList();
		AccountInformation.saveAllAccounts(FILE, accountList);
		accountIndex = manager.findAccountIndexByUsername(newUsername);
		currentAccount = manager.getAccountByIndex(accountIndex);
		myStage.setScene(getHomeScene());
	}

	private Scene getHomeScene() {
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
	    
	    Image imgHomePageBackground = new Image("/images/homePageBG.png");
		BackgroundImage homePageBackgroundImage = new BackgroundImage(imgHomePageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background homePageBackground = new Background(homePageBackgroundImage);
		homeRoot.setBackground(homePageBackground);

	    return new Scene(homeRoot, HOME_SCREEN_WIDTH, HOME_SCREEN_HEIGHT);
	}

	private Scene getInventoryScene() {
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
	        inventoryItems.add(item.getItemName()); // Assuming ShopItem has a getName() method
	    }

	    // Create a ListView to display the inventory items
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

	    Image imgInventoryPageBackground = new Image("/images/inventoryBG.png");
		BackgroundImage inventoryPageBackgroundImage = new BackgroundImage(imgInventoryPageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background inventoryPageBackground = new Background(inventoryPageBackgroundImage);
		inventoryRoot.setBackground(inventoryPageBackground);
	    
	    return new Scene(inventoryRoot, INVENTORY_SCREEN_WIDTH, INVENTORY_SCREEN_HEIGHT);
	}

	private Scene getPointsRedemptionShopScene() {
		// Assuming you have an enum representing gifts (replace Gift with your actual enum)
		GridPane redemptionRoot = new GridPane();
		redemptionRoot.setHgap(GAP);
		redemptionRoot.setVgap(GAP);
		redemptionRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		redemptionRoot.setAlignment(Pos.CENTER);

		Scene redemptionScene = new Scene(redemptionRoot, SHOP_SCREEN_WIDTH, SHOP_SCREEN_HEIGHT);

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
		
    	// Assuming you have a method in your PointsRewardShop class to get the purchased items
 		ArrayList<AccountsManager.ShopItem> purchasedItems = manager.getAccountByIndex(currentAccount.getAccountIndex()).getPurchasedItems();

 		VBox inventoryBox = new VBox(GAP);
 		inventoryBox.setAlignment(Pos.CENTER);
 		Label lblInventoryTitle = new Label("Your Inventory");
 		lblInventoryTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, FONT));
 		inventoryBox.getChildren().add(lblInventoryTitle);


	    btnRedeemPoints.setOnAction(event -> {
	        String selectedShopItemName = selectedItem;
	        if (selectedShopItemName != null) {
	        	AccountsManager.ShopItem selectedShopItem = manager.getShopItemByName(selectedShopItemName);
	            if (selectedShopItem != null) {
	                boolean success = manager.redeemItem(selectedShopItem, currentAccount.getAccountIndex());
	                if (success) { // Handle successful redemption (e.g., display a message)
	                	purchasedItems.add(selectedShopItem);
	                    showAlert(AlertType.INFORMATION, "Success", "Thank you for redeeming your points!");
	                    lblTrackPoints.setText("Points: " + currentAccount.getPoints());
	                    
	                } else {
	                    // Handle insufficient points (e.g., display an error message)
	                    showAlert(AlertType.ERROR, "Error", "There was a system error, you do not have enough points for a" + selectedItem + ".");
	                    lblTrackPoints.setText("Points: " + currentAccount.getPoints());
	                }
	            }
	        } else {
	            // Handle no item selected (e.g., display a message)
	            showAlert(AlertType.ERROR, "Error", "There was no item selected.");
	            lblTrackPoints.setText("Points: " + currentAccount.getPoints());
	        }
	    });
	    Image imgShopPageBackground = new Image("/images/wordleBG.png");
		BackgroundImage shopPageBackgroundImage = new BackgroundImage(imgShopPageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background shopPageBackground = new Background(shopPageBackgroundImage);
		redemptionRoot.setBackground(shopPageBackground);
	    
		return redemptionScene;
	}

	private Scene getCreateAccountScene() {
		GridPane newAccountRoot = new GridPane();
		newAccountRoot.setHgap(GAP);
		newAccountRoot.setVgap(GAP);
		newAccountRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
		newAccountRoot.setAlignment(Pos.CENTER);

		imgSymbol.setFitWidth(100); 
		imgSymbol.setFitHeight(100); 
		newAccountRoot.add(imgSymbol, 0, 0); 

		Label lblTitle = new Label();
		lblTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, LARGE_FONT)); 
																						
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

		loginPageBackground = new Background(loginPageBackgroundImage);
		newAccountRoot.setBackground(loginPageBackground);

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
		wordleRoot.add(imgSymbol, 0, 0, 2, 1); // Assuming you want it in the top-right corner (adjust column index as needed)

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
	    
	    Image imgWordlePageBackground = new Image("/images/wordleBG.png");
		BackgroundImage wordlePageBackgroundImage = new BackgroundImage(imgWordlePageBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background wordlePageBackground = new Background(wordlePageBackgroundImage);
		wordleRoot.setBackground(wordlePageBackground);

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
		
		// Check if the entered character forms a valid word
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
				String currentGuess = WordleFX.getCurrentWord(box, numFullGuesses);
			    if (WordleFX.isWordValid(currentGuess) == false) { // Display an error message or take appropriate action
			    	handleInvalidWord(numFullGuesses); 
			        showAlert(AlertType.ERROR, "Invalid Word", "The entered word, " + currentGuess + " is not a valid word!");
			        return;
			    }
				
				numFullGuesses++;
				if (wordleGame.wordClean(box, numFullGuesses - 1)) {
					showAlert(AlertType.INFORMATION, "You Win!", "The word was: " + wordleGame.getKeyword());
					win = true; 
					gameEnded = true;
					manager.incLoyaltyPoints(win, currentAccount.getAccountIndex());
					lblTrackPoints.setText("Points: " + currentAccount.getPoints());
					showAlert(AlertType.INFORMATION, "alert", "The word was: " + currentAccount.getPoints());
				} else if (WordleFX.isBoardFull(box)) {
					showAlert(AlertType.INFORMATION, "alert", "The word was: " + wordleGame.getKeyword());
					win = false; 
					gameEnded = true;
					manager.incLoyaltyPoints(win, currentAccount.getAccountIndex());
					lblTrackPoints.setText("Points: " + currentAccount.getPoints());
					showAlert(AlertType.INFORMATION, "alert", "The word was: " + currentAccount.getPoints());
				}
			}

		}
	
	}

	
	private void handleBackspace() {
	    // Handle backspace key press, e.g., clear the last filled letter
	    for (int col = LENGTH - 1; col >= 0; col--) {
	        for (int row = HEIGHT - 1; row >= 0; row--) {
	            if (box[row][col].getLetter() != Square.BLANK) {
	                if (numFullGuesses == 0 || !WordleFX.isRowFull(row, box)) {
	                    box[row][col].setLetter(Square.BLANK);
	                    return;
	                }
	            }
	        }
	    }
	}
	
	private void handleInvalidWord(int numFullGuesses) {
	    // Handle backspace key press, e.g., clear the last filled letter
	    for (int col = 0; col < LENGTH; col++) {
	    	box[numFullGuesses][col].setLetter(Square.BLANK);
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
