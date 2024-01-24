package assignment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.io.FileNotFoundException;


/**
 * @author s453512 
 * Date: 2024-01-08 
 * AccountInformation.java 
 * Utility class that saves all the information to a file of the user's choice once the program has finished. 
 */

public class AccountInformation { // Utility class, the sole method is static (hence why there is no constructor) 

	/**
	 * Saves all account information to a specified file.
	 *
	 * @param file        
	 * 		The path of the file to which the information will be saved.
	 * 
	 * @param accountInfo 
	 * 		The list of accounts whose information needs to be saved.
	 * 
	 * @throws FileNotFoundException 
	 * 		If the specified file cannot be found or created.
	 * 
	 * @throws IOException           
	 * 		If an I/O error occurs while writing to the file.
	 */
	public static void saveAllAccounts(String file, ArrayList<Account> accountList) {
		// Variables
		String username, password; // Names
		ArrayList<AccountsManager.ShopItem> purchasedItems;
		boolean userHasItems;
		int points;

		try {
			FileWriter fileWriter = new FileWriter(file); // Initialize FileWriter to write to the file
			PrintWriter filePrinter = new PrintWriter(fileWriter); // Initialize FileReader to read from the file

			for (Account temp : accountList) { // Goes through all the accounts within the data structure (ArrayList)
				// Gets all the pertinent information as described in the variables above
				// through gettors
				username = temp.getUsername();
				password = temp.getPassword();
				points = temp.getPoints();
				userHasItems = temp.getHasUserItems();
				filePrinter.println(username);
				filePrinter.println(password);
				filePrinter.println(points);
				filePrinter.println(userHasItems);
				// Prints all the information into the text file

				if (userHasItems) {
					purchasedItems = temp.getPurchasedItems();
					filePrinter.println(purchasedItems.size());
					for (int i = 0; i < purchasedItems.size(); i++) {
						filePrinter.println(purchasedItems.get(i));
					}
				}
			}
			fileWriter.close(); // Closes at the end
		} catch (FileNotFoundException e) { // If the file is not found
			e.printStackTrace();
		} catch (IOException a) { // Handles the IOException
			a.printStackTrace();
		} 
	}
	
	
}