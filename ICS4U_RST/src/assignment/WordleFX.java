package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class WordleFX {
	
	private static final int LENGTH = 5; 
	private static final int HEIGHT = 6; 
	private String keyword; 

	public WordleFX() {
		keyword = WordleFX.generateKeyword();
		setKeyword(keyword); 
	}
	
	public WordleFX(String keyword) {
	    if (keyword.length() > 5) {
	        throw new IllegalArgumentException("Keyword is too long. Maximum allowed length is 5 characters.");
	    }
	    setKeyword(keyword);
	}

	
	public void setKeyword(String keyword) {
		this.keyword = keyword; 
	}
	
	public String getKeyword() {
		return keyword; 
	}
	
	
	public boolean wordClean(Square[][] userBoard, int numGuesses) {
		// Variables
		String wordleGuess, keyword;

		// Processing
		wordleGuess = interpretUserGuess(numGuesses, userBoard);
		if (checkWord(wordleGuess, userBoard, numGuesses)) {
			return true;
		} else {
			// Get all the unique characters of the guess
			keyword = getKeyword();
			
			ArrayList<Character> uniqueChar = uniqueCharacters(keyword);
			for (int col = 0; col < wordleGuess.length(); col++) {
				if (userBoard[numGuesses][col].getValue() != Square.GREEN_VALUE) {
					if (uniqueChar.contains(Character.toUpperCase(wordleGuess.charAt(col)))) {
						userBoard[numGuesses][col].setYellow(); 
					} else {
						userBoard[numGuesses][col].setGray();
					}
				}

			}
		}
		return false;
	}


	public boolean checkWord(String wordleGuess, Square[][] userBoard, int numGuesses) {
		// Processing=
	    String keyword;
	    int counter = 0;

		keyword = getKeyword();
		
		for (int col = 0; col < LENGTH; col++) {
			if (Character.toUpperCase(wordleGuess.charAt(col)) == Character.toUpperCase(keyword.charAt(col))) {
				userBoard[numGuesses][col].setGreen();
				counter += 1; 

			}
		}

		// Processing
		if (counter == 5) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String generateKeyword() {
		int randNum;
		String keyword, line; 
		
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
		keyword = allWordleWords.get(randNum);
		
		return keyword; 
	}
	
	public static ArrayList<Character> uniqueCharacters(String keyword) {
		// Variables 
		ArrayList<Character> uniqueChars = new ArrayList<Character>();
		
		// Processing 
		for (int i = 0; i < keyword.length(); i++) {
			char currentChar = keyword.charAt(i); 
			
			// Check if the character is not in the uniqueChars list 
			if (!uniqueChars.contains(currentChar)) {
				uniqueChars.add(Character.toUpperCase(currentChar));
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
	
	public static boolean isRowFull(int numGuesses, Square[][] userBoard) {
		for (int col = 0; col < LENGTH; col++) {
			if (userBoard[numGuesses][col].getLetter() == Square.BLANK) {
				return false; 
			}
		}
		
		return true; 
	}
	
	public static boolean isBoardFull(Square[][] userBoard) {
		int counter = 0; 
		
		for (int row = 0; row < HEIGHT; row++) {
			if(isRowFull(row, userBoard)) {
				counter += 1; 
			}
		}
		
		if (counter == 6) {
			return true; 
		} else {
			return false; 
		}
	}
	
	

	
	public static String interpretUserGuess(int numGuesses, Square[][] wordleBoard) {
		String wordleWord = ""; 
		for (int col = 0; col < LENGTH; col++) {
			wordleWord += wordleBoard[numGuesses][col].getLetter();
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
