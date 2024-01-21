package assignment;

/**
 * @author s453512 
 * Date: 2023-11-24 
 * PointsRecorder.java 
 * Class that holds multiple accounts and assigns points to them 
 */


import java.util.ArrayList;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class RewardShop {
    private ArrayList<Account> accountList; // ArrayList/data structure containing all of the Account instances 

    
    public enum ShopItem {
        GIFTCARD(10), NEWSPAPER(20), COFFEE(3);

        private final int points;

        ShopItem(int points) {
            this.points = points;
        }

        public int getPoints() {
            return points;
        }
    }
    
    /**
     * Constructor. Initializes an ArrayList that holds all of the accounts.  
     */
    public RewardShop() {
        accountList = new ArrayList<Account>();
    }

    /**
	 * Saves all account information to a specified file.
	 *
	 * @param file        The path of the file to which the information will be
	 *                    saved.
	 * 
	 * @param accountInfo The list of accounts whose information needs to be saved.
	 * 
	 * @throws FileNotFoundException 
	 * 		If the specified file cannot be found or created.
	 * 
	 * @throws IOException           
	 * 		If an I/O error occurs while writing to the file.
	 */
	public void saveAllAccounts(String file) {
		// Variables
		String username, password; // Names 

		try {
			FileWriter fileWriter = new FileWriter(file); // Initialize FileWriter to write to the file
			PrintWriter filePrinter = new PrintWriter(fileWriter); // Initialize FileReader to read from the file

			for (Account temp : accountList) { // Goes through all the accounts within the data structure (ArrayList) 
				// Gets all the pertinent information as described in the variables above through gettors 
				username = temp.getUsername();
				password = temp.getPassword();
				
				// Prints all the information into the text file 
				filePrinter.println(username);
				filePrinter.println(password);

			}
			fileWriter.close(); // Closes at the end 
		} catch (FileNotFoundException e) { // If the file is not found
			e.printStackTrace();
		} catch (IOException a) { // Handles the IOException
			a.printStackTrace();
		} // Again used the .printStackTrace() for the same reasons outlined in the PointsRecorder class 
	}

	
	public void loadAllAccounts(String file) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String username = line;
                String password = reader.readLine();
                int points = Integer.parseInt(reader.readLine());
                String[] purchasedItems = reader.readLine().split(",");

                // Create an Account instance and add it to the accountList
                Account account = new Account(username, password);
                account.setPoints(points);
                for (String item : purchasedItems) {
                    account.purchaseItem(item);
                }
                accountList.add(account);
            }
        }
    }

	
	
	public boolean redeemItem(ShopItem item, int accountIndex) {
        Account currentAccount = accountList.get(accountIndex);
        int requiredPoints = item.getPoints();

        if (currentAccount.getPoints() >= requiredPoints) {
            currentAccount.setPoints(currentAccount.getPoints() - requiredPoints);
            currentAccount.purchaseItem(item.name()); // Assuming the item name is the same as the enum constant
            return true; // Successful redemption
        } else {
            return false; // Insufficient points for redemption
        }
    }
	
    
    public void incLoyaltyPoints(boolean win, int accountIndex) {
    	Account currentAccount = accountList.get(accountIndex);
    	if (win == true) {
    		currentAccount.setPoints(currentAccount.getPoints() + 100);
    	} else {
    		currentAccount.setPoints(currentAccount.getPoints() + 50);
    	}
    }
    


}