package assignment;

/**
 * @author s453512 
 * Date: 2024-01-08 
 * StringFinder.java 
 * Utility class that is used to find a string in an array using the findString(String[], String, int, int) method
 */

public class StringFinder {

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
	public static boolean findString(String[] allWords, String word, int left, int right) {
		if (left > right) {
			return false; // element not found
		}

		int middle = (left + right) / 2;

		if (allWords[middle].equals(word)) { // element was found!
			return true;
		}

		if (allWords[middle].compareTo(word) > 0) {
			return findString(allWords, word, left, middle - 1); // check the left side
		} else {
			return findString(allWords, word, middle + 1, right); // check the right side
		}
	}
	
}	
