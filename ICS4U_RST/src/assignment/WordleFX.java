package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class WordleFX {
	
	private static final int LENGTH = 5; 


	public static void wordClean(Square[][] userBoard, int numGuesses) {
		String wordleGuess = interpretUserGuess(numGuesses, userBoard);
		if (checkWord(wordleGuess, userBoard, numGuesses)) {
		} else {
			// Get all the unique characters of the guess
			ArrayList<Character> uniqueChar = uniqueCharacters(wordleGuess);

			// Convert the ArrayList of characters to an array of characters
			Character[] uniqueCharArray = uniqueChar.toArray(new Character[uniqueChar.size()]);
			for (int i = 0; i < uniqueCharArray.length; i++) {
				uniqueCharArray[i] = uniqueChar.get(i); 
			}
			
			
			// Processing 
//			for (int col = 0; col < LENGTH; col++) {
//				if (userBoard[numGuesses][col].getValue() != Square.GREEN_VALUE) {
//					
//					for (int j = 0; j < uniqueChar.size(); j++) {
//						if (wordleGuess.charAt(col) == uniqueChar.get(j)) {
//							userBoard[numGuesses][col].setYellow(); 
//						} else {
//							userBoard[numGuesses][col].setGray();
//						}
//					}
//				}
//			}
			
			for (int col = 0; col < LENGTH; col++) {
			    if (userBoard[numGuesses][col].getValue() != Square.GREEN_VALUE) {
			        boolean isInWord = false;
			        for (int j = 0; j < uniqueCharArray.length; j++) {
			            if (wordleGuess.charAt(col) == uniqueCharArray[j]) {
			                isInWord = true;
			                break;
			            }
			        }
			        if (isInWord) {
			            for (int j = 0; j < uniqueChar.size(); j++) {
			                if (wordleGuess.charAt(col) == uniqueChar.get(j)) {
			                    userBoard[numGuesses][col].setYellow(); 
			                    break;
			                }
			            }
			        } else {
			            userBoard[numGuesses][col].setGray();
			        }
			    }
			}
		}
	}

	public static boolean checkWord(String wordleGuess, Square[][] userBoard, int numGuesses) {
		// Processing
		String keyword = "";
		int counter = 0;
		
		//keyword = generateKeyWord(); 

		keyword = "plane"; 
		
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
	
	public static String generateKeyWord() {
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
	
	public static boolean isRowFull(int numGuesses, Square[][] userBoard) {
		int counter = 0; 
		for (int col = 0; col < LENGTH; col++) {
			if (userBoard[numGuesses][col].getLetter() != Square.BLANK) {
				counter += 1; 
			}
		}
		
		if (counter == 5) {
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
