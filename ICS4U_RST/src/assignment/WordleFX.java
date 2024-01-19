package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class WordleFX {
	
	private static final int LENGTH = 5; 
	private static final int HEIGHT = 6; 


	public static void wordClean(String wordleGuess, Square[][] userBoard, int numGuesses) {
		if (checkWord(wordleGuess, userBoard, LENGTH)) {
		} else {
			// Get all the unique characters of the guess
			ArrayList<Character> uniqueChar = uniqueCharacters(wordleGuess);
			
			// Processing 
			for (int i = 0; i < LENGTH; i++) {
				if (userBoard[numGuesses][i].getValue() != Square.GREEN_VALUE) {
					for (int j = 0; j < uniqueChar.size(); j++) {
						if (wordleGuess.charAt(i) == uniqueChar.get(j)) {
							userBoard[numGuesses][i].setYellow(); 
						} else {
							userBoard[numGuesses][i].setGray();
						}
					}
				}
			}
		}
	}

	public static boolean checkWord(String wordleGuess, Square[][] userBoard, int numGuesses) {
		// Processing
		String keyWord = "";
		String line;
		int randNum;
		int counter = 0;
		ArrayList<String> allWordleWords = new ArrayList<String>();

		// Processing
		try {
			FileReader wordFile = new FileReader("data/wordleWords");
			BufferedReader fileReader = new BufferedReader(wordFile);

			while ((line = fileReader.readLine()) != null) {
				allWordleWords.add(line);
			}
			fileReader.close();

			// Error Checking
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException a) {
			a.printStackTrace();
		}
		// Processing
		randNum = randomNumber(0, allWordleWords.size() - 1);
		keyWord = allWordleWords.get(randNum);

		for (int i = 0; i < LENGTH; i++) {
			if (wordleGuess.charAt(i) == keyWord.charAt(i)) {
				counter += 1; 
				userBoard[i][numGuesses].setGreen();
			}
		}

		// Processing
		if (counter == 5) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ArrayList<Character> uniqueCharacters(String wordleGuess) {
		// Variables 
		ArrayList<Character> uniqueChars = new ArrayList<Character>();
		
		// Processing 
		for (int i = 0; i < wordleGuess.length(); i++) {
			char currentChar = wordleGuess.charAt(i); 
			
			// Check if the character is not in the uniqueChars list 
			if (!uniqueChars.contains(currentChar)) {
				uniqueChars.add(currentChar);
			}
		}
		return uniqueChars; 
	}
	
	public static void clearBoard(int numCol, int numRow, Square[][] userBoard) {
		for (int row = 0; row < numRow; row++) {
			for (int col = 0; col < numCol; col++) {
				userBoard[row][col] = new Square();
				userBoard[row][col].setLetter(Square.BLANK); // Initialize letter as a blank space
			}
		}
	}
	
	
	public static boolean isRowFull(int numGuesses, Square[][] wordleBoard) {
	    int counter = 0; 
		for (int col = 0; col < LENGTH; col++) {
	        if (wordleBoard[numGuesses][col].getLetter() != Square.BLANK) {
	            counter += 1; 
	        }
	    }
		if (counter == 5) {
			return true; 
		} else {
			return false;
		}
	}
	
	public static String interpretUserGuess(int row, Square[][] wordleBoard) {
		String wordleWord = ""; 
		for (int col = 0; col < LENGTH; col++) {
			wordleWord += wordleBoard[row][col].getValue();
		}
		return wordleWord;

	}
	
	/**
	 * Generates a random integer between two specified values, inclusive.
	 *
	 * @param a The lower bound of the range.
	 * @param b The upper bound of the range.
	 * @return A random integer within the specified range.
	 */
	public static int randomNumber(int a, int b) {
		int highNum = Math.max(a, b);
		int lowNum = Math.min(a, b);
		int range = highNum - lowNum + 1;
		return (int) (Math.random() * range) + lowNum;
	}
}
