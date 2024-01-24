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
	
	private static final int LENGTH = 5; // Length of the board 
	private static final int HEIGHT = 6; // Height of the board 
	private String keyword; // What the user is trying to guess 

	/**
     * Constructs a new WordleFX instance with a randomly generated keyword.
     */
	public WordleFX() {
		keyword = WordleFX.generateKeyword(); // Keyword is generated and set 
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
	
	/**
     * Generates a random keyword from a file containing possible words.
     *
     * @return 
     * 		A randomly selected keyword.
     */
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

		// Processing
		ArrayList<String> allWordleWords = new ArrayList<String>();

		try {
			FileReader wordFile = new FileReader("data/wordleWords");
			BufferedReader fileReader = new BufferedReader(wordFile);

			while ((line = fileReader.readLine()) != null) {
				line = line.trim();
				allWordleWords.add(line);
			}
			fileReader.close();

			// Error Checking
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException a) {
			a.printStackTrace();
		}
		
		String[] allWords = new String[allWordleWords.size()];
	    for (int i = 0; i < allWordleWords.size(); i++) {
	    	allWords[i] = allWordleWords.get(i).toUpperCase();
	    }
	    // Sort the array of usernames
	    Arrays.sort(allWords);
	    
	    // Return 
		return findWord(allWords, userGuess, 0, allWords.length - 1);
	}
	
	/**
	 * Retrieves the current word from the game board for a specific guess.
	 *
	 * @param box      
	 * 		The game board.
	 * 
	 * @param numGuess 
	 * 		The number of guesses.
	 * 
	 * @return 
	 * 		The current word for the specified guess.
	 */
	public static String getCurrentWord(Square[][] box, int numGuess) {
		StringBuilder wordBuilder = new StringBuilder();
		for (int col = 0; col < LENGTH; col++) {
			wordBuilder.append(box[numGuess][col].getLetter());
		}
		return wordBuilder.toString();
	}
	
	/**
	 * Finds a word in an array of words using a binary search algorithm.
	 *
	 * @param allWords 
	 * 		The array of words.
	 * 
	 * @param word     
	 * 		The word to search for.
	 * 
	 * @param left     
	 * 		The left index of the search range.
	 * 
	 * @param right    
	 * 		The right index of the search range.
	 * 
	 * @return 
	 * 		True if the word is found, false otherwise.
	 */
	public static boolean findWord(String[] allWords, String word, int left, int right) {
		if (left > right) {
			return false; // element not found
		}

		int middle = (left + right) / 2;

		if (allWords[middle].equals(word)) { // element was found!
			return true;
		}

		if (allWords[middle].compareTo(word) > 0) {
			return findWord(allWords, word, left, middle - 1); // check the left side
		} else {
			return findWord(allWords, word, middle + 1, right); // check the right side
		}
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
		return uniqueChars; 
	}
	
	/**
	 * Checks if a specific row on the game board is full (no blank squares).
	 *
	 * @param numGuesses 
	 * 		The number of guesses (row index).
	 * @param userBoard  
	 * 		The game board.
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
	 * @return 
	 * 		True if the board is full, false otherwise.
	 */
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
	
	/**
	 * Interprets the user's guess from the game board.
	 *
	 * @param numGuesses  
	 * 		The number of guesses (row index).
	 * @param wordleBoard 
	 * 		The game board.
	 * @return 
	 * 		The interpreted word for the specified guess.
	 */
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