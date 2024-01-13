package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class WordleFX extends Application {

	
	//public static void wordClean(String userGuess, int numGuesses, Square[][] userBoard) {
		
	public static void wordClean(String userGuess, int numGuesses, String[][] userBoard) {
		
	
	}

	public static boolean checkWord(String userName) {
		// Processing 
		String keyword = "";
		String line; 
		ArrayList<String> allWordleWords = new ArrayList();
		
		
		
		
		// Processing 
		try {
			FileReader wordFile = new FileReader("data/wordleWords");
			BufferedReader fileReader = new BufferedReader(wordFile);
			
			while ((line = fileReader.readLine()) != null) {
				allWordleWords.add(line);
			}
			
			
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException a) {
		    a.printStackTrace();
		}
		
		if (userName.equals(keyword)) {
			return true; 
		} else { 
			return false; 
		}
		
	}
}
