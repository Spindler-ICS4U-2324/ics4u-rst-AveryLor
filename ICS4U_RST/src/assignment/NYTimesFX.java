package assignment;

import java.awt.TextField;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NYTimesFX extends Application {

	private static final int GAP = 15;
	private static final int FONT = 20;
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 500;
	private Square[][] box;
	
	// JavaFX elements
	private Label lblTitle, lblUsername, lblPassword;
	private TextField txtUsername, txtPassword; 
	
	@Override
	public void start(Stage myStage) throws Exception {
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
		
		// Page 2 display
		/*
		GridPane root = new GridPane();
		root.setHgap(GAP);
		root.setVgap(GAP);
		root.setPadding(new Insets(GAP, GAP, GAP, GAP));
		root.setAlignment(Pos.CENTER);
		
		box = new Square[3][3];
		for (int row = 0; row <= 2; row++) {
			for (int col = 0; col <= 2; col++) {
				box[row][col] = new Square();
				root.add(box[row][col], col, row);
			}
		}
		
		lblResult = new Label();
		lblResult.setFont(Font.font(FONT));
		root.add(lblResult, 0, 3, 3, 1);
		GridPane.setHalignment(lblResult, HPos.CENTER);

		Button btnReset = new Button("Reset");
		btnReset.setFont(Font.font(FONT));
		root.add(btnReset, 0, 3, 2, 3);
		btnReset.setOnAction(event -> resetGame());
		
		Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		myStage.setTitle("NYTimesFX");
		myStage.setScene(scene);
		myStage.show();
		
		
	}
	
	private void resetGame() {
		for (int row = 0; row <= 2; row++) {
			for (int col = 0; col <= 2; col++) {
				box[row][col].clear();
			}
		}
		lblResult.setText("");
	}
	
	
	
	


}

*/