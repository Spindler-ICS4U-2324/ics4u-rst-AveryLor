package assignment;

import javafx.application.Application;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class NYTimesFX extends Application {

	private static final int GAP = 15;
	private static final int FONT = 20;
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 500;
	private LoginSystem loginCheck = new LoginSystem();
	private Square[][] box;
	private Stage myStage; 
	
	
	// JavaFX elements
	private Label lblTitle, lblUsername, lblPassword;
	private TextField txtUsername, txtPassword; 
	private Button btnSubmit; 
	
	@Override
	public void start(Stage myStage) throws Exception {
		
		this.myStage = myStage;
		
		GridPane root = new GridPane();
		root.setHgap(GAP);
		root.setVgap(GAP);
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.CENTER);
		
		lblTitle = new Label();
		lblTitle.setFont(Font.font(FONT));
		lblTitle.setText("Login Page");
		root.add(lblTitle, 0, 0, 1, 1);
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
		
		lblUsername = new Label(); 
		lblUsername.setFont(Font.font(FONT));
		lblUsername.setText("Username:");
		root.add(lblUsername, 0, 1, 1, 1);
		GridPane.setHalignment(lblUsername, HPos.LEFT);
		
		lblPassword = new Label(); 
		lblPassword.setFont(Font.font(FONT));
		lblPassword.setText("Password:");
		root.add(lblPassword, 0, 2, 1, 1);
		GridPane.setHalignment(lblPassword,  HPos.LEFT);
		
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
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

	    return new Scene(homeRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
	}
	
	private Scene getWordleScene() {
		GridPane wordleRoot = new GridPane();
	    wordleRoot.setHgap(GAP);
	    wordleRoot.setVgap(GAP);
	    wordleRoot.setPadding(new Insets(GAP, GAP, GAP, GAP));
	    wordleRoot.setAlignment(Pos.CENTER);

	    Label lblHome = new Label();
	    lblHome.setFont(Font.font(FONT));
	    lblHome.setText("Wordle");
	    wordleRoot.add(lblHome, 0, 0, 1, 1);
	    GridPane.setHalignment(lblHome, HPos.LEFT);
	    
	    Button btnReturnHome = new Button(); 
	    btnReturnHome.setFont(Font.font(FONT));
	    btnReturnHome.setText("Return Home");
	    btnReturnHome.setOnAction(event -> myStage.setScene(getHomeScene()));
	    wordleRoot.add(btnReturnHome, 0, 0, 1, 10);
	    
	    box = new Square[3][3];
		for (int row = 0; row <= 2; row++) {
			for (int col = 0; col <= 2; col++) {
				box[row][col] = new Square();
				wordleRoot.add(box[row][col], col, row);
			}
		}
		
		Label lblResult = new Label();
		lblResult.setFont(Font.font(FONT));
		wordleRoot.add(lblResult, 0, 3, 3, 1);
		GridPane.setHalignment(lblResult, HPos.CENTER);


		
	    
	    return new Scene(wordleRoot, SCREEN_WIDTH, SCREEN_HEIGHT);
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
		

		
		
	

	
	
	
	


