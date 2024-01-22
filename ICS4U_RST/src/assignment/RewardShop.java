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
        ITEM1("Item 1", 10),
        ITEM2("Item 2", 20),
        ITEM3("Item 3", 30);

        private final String itemName;
        private final int points;

        ShopItem(String itemName, int points) {
            this.itemName = itemName;
            this.points = points;
        }

        public String getItemName() {
            return itemName;
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

	
    public void loadAllAccounts(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String username = line;
                String password = reader.readLine();
                int numItems = Integer.parseInt(reader.readLine());
                
                ArrayList<RewardShop.ShopItem> shopItems = new ArrayList<>();
                
                for (int i = 0; i < numItems; i++) {
                	String currentItem = reader.readLine();
                	RewardShop.ShopItem shopItem = RewardShop.ShopItem.valueOf(currentItem);
                	shopItems.add(shopItem);
                }
                
                

                // Create an Account instance and add it to the accountList
                Account account = new Account(username, password);
                account.setPoints(numItems);

                // Convert purchasedItems array to ArrayList
                ArrayList<RewardShop.ShopItem> purchasedItemsList = new ArrayList<>();
                for (String item : purchasedItems) {
                    purchasedItemsList.add(ShopItem.valueOf(item)); // Assuming ShopItem is an enum
                }
                account.setPurchasedItems(purchasedItemsList);

                accountList.add(account);
            }
        }
    }
    
    


    public boolean redeemItem(ShopItem item, int accountIndex) {
        Account currentAccount = accountList.get(accountIndex);
        int requiredPoints = item.getPoints();

        if (currentAccount.getPoints() >= requiredPoints) {
            currentAccount.setPoints(currentAccount.getPoints() - requiredPoints);
            currentAccount.purchaseItem(item);
            return true; // Successful redemption
        } else {
            return false; // Insufficient points for redemption
        }
    }

    public void incLoyaltyPoints(boolean win, int accountIndex) {
        Account currentAccount = accountList.get(accountIndex);
        if (win) {
            currentAccount.setPoints(currentAccount.getPoints() + 100);
        } else {
            currentAccount.setPoints(currentAccount.getPoints() + 50);
        }
    }
}