package assignment;

/**
 * @author s453512 
 * Date: 2024-01-08 
 * WordleFX.java 
 * Class that defines the characteristics of a Wordle game and also serves as a utility class that is used to enable the process of the game. 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;

public class WordleFX {
	
	// Constants 
	private static final int LENGTH = 5; // Length of the board 
	private static final int HEIGHT = 6; // Height of the board 
	
	// Field 
	private String keyword; // What the user is trying to guess 

	/**
     * Constructs a new WordleFX instance with a randomly generated keyword.
     */
	public WordleFX() {
		keyword = WordleFX.generateKeyword(); // Keyword is generated and set, therefore, whenever the class is instantiated the keywrod is set
		setKeyword(keyword); 
	}
	
	/**
     * Constructs a new WordleFX instance with a specified keyword.
     *
     * @param keyword 
     * 		The keyword to set for the game.
     * 
     * @throws 
     * 		IllegalArgumentException If the provided keyword is longer than 5 characters.
     */
	public WordleFX(String keyword) {
	    if (keyword.length() > 5) {
	        throw new IllegalArgumentException("Keyword is too long. Maximum allowed length is 5 characters.");
	    }
	    setKeyword(keyword);
	}

	/**
     * Sets the keyword for the game.
     *
     * @param keyword 
     * 		The keyword to set.
     */
	public void setKeyword(String keyword) {
		this.keyword = keyword; 
	}
	
	/**
     * Retrieves the keyword for the game.
     *
     * @return 
     * 		The keyword.
     */
	public String getKeyword() {
		return keyword; 
	}
	
	/**
     * Cleans the word (in terms of colors such as green and yellow) and updates the game board based on the user's guess.
     *
     * @param userBoard   
     * 		The game board.
     * 
     * @param numGuesses  
     * 		The number of guesses.
     * 
     * @return 
     * 		True if the word is correctly guessed, false otherwise.
     */
	public boolean wordClean(Square[][] userBoard, int numGuesses) {
		// Variables
		String wordleGuess, keyword;

		// Processing
		wordleGuess = interpretUserGuess(numGuesses, userBoard);
		if (checkWord(wordleGuess, userBoard, numGuesses)) { // If the word is already right, return true 
			return true;
		} else {
			// Get all the unique characters of the guess
			keyword = getKeyword();
			ArrayList<Character> uniqueChar = uniqueCharacters(keyword);
			
			// Processing 
			for (int col = 0; col < wordleGuess.length(); col++) {
				if (userBoard[numGuesses][col].getValue() != Square.GREEN_VALUE) { // If the value is not green check it 
					if (uniqueChar.contains(Character.toUpperCase(wordleGuess.charAt(col)))) { // In the uniqueChar list of the keyword
						userBoard[numGuesses][col].setYellow(); 
					} else {
						userBoard[numGuesses][col].setGray();
					}
				}

			}
		}
		return false;
	} // By first eliminating the green letters the algorithm can then easily check for the yellow letters in the remaining characters 

	/**
     * Checks if the user's guess matches the keyword.
     *
     * @param wordleGuess 
     * 		The user's guess.
     * 
     * @param userBoard    
     * 		The game board.
     * 
     * @param numGuesses   
     * 		The number of guesses.
     * 
     * @return 
     * 		True if the word is correctly guessed, false otherwise.
     */
	public boolean checkWord(String wordleGuess, Square[][] userBoard, int numGuesses) {
		// Processing
	    String keyword;
	    int counter = 0;
		keyword = getKeyword();
		
		// Processing 
		for (int col = 0; col < LENGTH; col++) {
			if (Character.toUpperCase(wordleGuess.charAt(col)) == Character.toUpperCase(keyword.charAt(col))) { // At the right position and character
				userBoard[numGuesses][col].setGreen(); // Sets to green 
				counter += 1; // Count to 5 for true 
			}
		}
		
		// Processing
		if (counter == 5) { // If true 
			return true; // Return true 
		} else {
			return false; // Otherwise return false 
		}
	}
	
	/**
     * Generates a random keyword from a file containing possible words.
     *
     * @return 
     * 		A randomly selected keyword.
     */
	public static String generateKeyword() {
		// Variable 
		int randNum;
		String keyword, line; 
		ArrayList<String> allWordleWords = new ArrayList<String>();
		
		// Processing (file handling)
		try {
			FileReader wordFile = new FileReader("data/wordleWords");
			BufferedReader fileReader = new BufferedReader(wordFile);

			// Looking through all the file lines 
			while ((line = fileReader.readLine()) != null) {
				allWordleWords.add(line); // Getting all the Wordle words 
			}
			fileReader.close(); // Close the reader 
 
			// Error Checking
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException a) {
			a.printStackTrace();
		}
		
		// Processing
		randNum = randomNumber(0, allWordleWords.size() - 1); // Picking a random word out of it 
		keyword = allWordleWords.get(randNum); // Setting this to the keyword 
		return keyword; // Return 
	}
	
	/**
	 * Checks if a given word is valid by searching for it in a list of valid words.
	 *
	 * @param userGuess 
	 * 		The word to be checked for validity.
	 * 
	 * @return 
	 * 		True if the word is valid, false otherwise.
	 */
	public static boolean isWordValid(String userGuess) {
		// Variable
		String line;
		ArrayList<String> allWordleWords = new ArrayList<String>();

		// Processing 
		try {
			FileReader wordFile = new FileReader("data/wordleWords");
			BufferedReader fileReader = new BufferedReader(wordFile);

			// Looking through all the file lines 
			while ((line = fileReader.readLine()) != null) {
				line = line.trim(); // Trim the line, make sure no spaces at the end 
				allWordleWords.add(line);
			}
			fileReader.close();

			// Error Checking
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException a) {
			a.printStackTrace();
		}
		
		// Creating an array to use in the findWord method 
		String[] allWords = new String[allWordleWords.size()];
	    for (int i = 0; i < allWordleWords.size(); i++) {
	    	allWords[i] = allWordleWords.get(i).toUpperCase();
	    }
	    
	    // Sort the array of usernames before the binary search 
	    Arrays.sort(allWords);
		return StringFinder.findString(allWords, userGuess, 0, allWords.length - 1); // Return the results of the binary search 
	}
	
	/**
	 * Retrieves a list of unique characters in a given keyword.
	 *
	 * @param keyword 
	 * 		The keyword to analyze.
	 * 
	 * @return A list of unique characters in the keyword.
	 */
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
		return uniqueChars; // Return the ArrayList 
	}
	
	/**
	 * Checks if a specific row on the game board is full (no blank squares).
	 *
	 * @param numGuesses 
	 * 		The number of guesses (row index).
	 * 
	 * @param userBoard  
	 * 		The game board.
	 * 
	 * @return 
	 * 		True if the row is full, false otherwise.
	 */
	public static boolean isRowFull(int numGuesses, Square[][] userBoard) {
		for (int col = 0; col < LENGTH; col++) {
			if (userBoard[numGuesses][col].getLetter() == Square.BLANK) {
				return false; 
			}
		}
		
		return true; 
	}
	
	/**
	 * Checks if the entire game board is full (all rows are full).
	 *
	 * @param userBoard 
	 * 		The game board.
	 * 
	 * @return 
	 * 		True if the board is full, false otherwise.
	 */
	public static boolean isBoardFull(Square[][] userBoard) {
		// Variable 
		int counter = 0; 
		
		// Processing 
		for (int row = 0; row < HEIGHT; row++) {
			if(isRowFull(row, userBoard)) {
				counter += 1; 
			}
		}
	
		// Checking 
		if (counter == 6) { // All six rows are full 
			return true; 
		} else {
			return false; 
		}
	}
	
	/**
	 * Interprets the user's guess from the game board.
	 *
	 * @param numGuesses  
	 * 		The number of guesses (row index).
	 * 
	 * @param wordleBoard 
	 * 		The game board.
	 * 
	 * @return 
	 * 		The interpreted word for the specified guess.
	 */
	public static String interpretUserGuess(int numGuesses, Square[][] wordleBoard) {
		String wordleWord = ""; 
		for (int col = 0; col < LENGTH; col++) {
			wordleWord += wordleBoard[numGuesses][col].getLetter(); // 
		}
		return wordleWord;
	}
	
	/**
	 * Generates a random integer between two specified values, inclusive.
	 *
	 * @param a 
	 * 		The lower bound of the range.
	 * 
	 * @param b 
	 * 		The upper bound of the range.
	 * 
	 * @return A 
	 * 		random integer within the specified range.
	 */
	public static int randomNumber(int a, int b) {
		int highNum = Math.max(a, b);
		int lowNum = Math.min(a, b);
		int range = highNum - lowNum + 1;
		return (int) (Math.random() * range) + lowNum;
	}
}