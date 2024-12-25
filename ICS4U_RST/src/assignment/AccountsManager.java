package assignment;

/**
 * @author s453512 
 * Date: 2024-01-08 
 * AccountInformation.java 
 * Class that holds a list of Account's and manages their credentials, points and shop items 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AccountsManager {

	// Constants
	private static final int WIN_REWARD = 100; 
	private static final int LOOSE_REWARD = 50; 
	private static final int BASE_POINTS = 0; 
	private static final int LBCO_COST = 2000; 
	private static final int NEWSPAPER_COST = 200; 
	private static final int COFFEE_COST = 100; 
	private static final int COSTCO_COST = 1000; 
	private static final String LCBO_GIFTCARD = "LCBO Giftcard";
	private static final String NEWSPAPER = "Newspaper";
	private static final String COSTCO_GIFTCARD = "Costco Giftcard";
	private static final String COFFEE = "Coffee";
	
	// Field 
	private ArrayList<Account> accountList;

	/**
     * Enumeration representing shop items with their names and corresponding points.
     */
	public enum ShopItem {

		// Items within the item shop
		ITEM1(LCBO_GIFTCARD, LBCO_COST), ITEM2(NEWSPAPER, NEWSPAPER_COST), ITEM3(COSTCO_GIFTCARD, COSTCO_COST), ITEM4(COFFEE, COFFEE_COST);

		// ShopItem type fields 
		private String itemName; // They have a name 
		private int points; // And a cost number of points 
		
		/**
		 * Constructs a new ShopItem with the specified item name and points.
		 *
		 * @param itemName 
		 * 		The name of the shop item.
		 * 
		 * @param points   
		 * 		The number of points required to redeem this item.
		 */
		ShopItem(String itemName, int points) {
			this.itemName = itemName;
			this.points = points;
		}

		/**
         * Gets the name of the shop item.
         *
         * @return The name of the shop item.
         */
		public String getItemName() {
			return itemName;
		}

		/**
         * Gets the points required for the shop item.
         *
         * @return The points required for the shop item.
         */
		public int getPoints() {
			return points;
		}
	}

	/**
     * Constructs an AccountsManager with an empty account list.
     */
	public AccountsManager() {
		accountList = new ArrayList<Account>();
	}

	/**
     * Loads user accounts from the specified file.
     *
     * @param file 
     * 		The file containing user account information.
     * 
     * @throws IOException 
     * 		If an I/O error occurs.
     */
	public void loadAllAccounts(String file) throws IOException {
		try {
			// Variables 
			int numItems, points;
			String line, username, password;
			boolean userHasItems;

			// Instantiating
			FileReader accountFile = new FileReader(file);
			BufferedReader accountReader = new BufferedReader(accountFile);

			// Reading all lines of the file 
			while ((line = accountReader.readLine()) != null) {
				username = line;
				password = accountReader.readLine();
				points = Integer.parseInt(accountReader.readLine()); 
				userHasItems = Boolean.parseBoolean(accountReader.readLine());

				// Create an instance of a class with the overloaded constructor (easier to set 
				Account currentAccount = new Account(username, password); // username and password 
				currentAccount.setPoints(points); // Set points 
				currentAccount.setUserHasItems(userHasItems); // Set if they have any shop items  
				currentAccount.setAccountIndex(accountList.size()); // And the index 
				accountList.add(currentAccount);
				
				// If they do have shop items, add them to the list 
				if (userHasItems) {
					numItems = Integer.parseInt(accountReader.readLine());
					ArrayList<AccountsManager.ShopItem> purchasedItems = new ArrayList<>();
					
					// Looping through the entire item list that the user has 
					for (int i = 0; i < numItems; i++) {
						String currentItem = accountReader.readLine();
						AccountsManager.ShopItem shopItem = AccountsManager.ShopItem.valueOf(currentItem);
						purchasedItems.add(shopItem);
						currentAccount.setPurchasedItems(purchasedItems); // Setting it to the current Account 
					}
				}
			}
			accountReader.close(); // Following this, close the file 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException a) {
			a.printStackTrace();
		} catch (NumberFormatException g) {
			g.printStackTrace();
		}
	}

	/**
     * Sets the account list.
     *
     * @param accountList The ArrayList of accounts to set.
     */
    public void setAccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }
    
    /**
     * Gets the account list.
     *
     * @return The ArrayList of accounts.
     */
    public ArrayList<Account> getAccountList() {
        return accountList;
    }
    
    /**
     * Checks the provided credentials against existing accounts.
     *
     * @param password 
     * 		The password to check.
     * 
     * @param username 
     * 		The username to check.
     * 
     * @return 
     * 		True if the credentials are valid, false otherwise.
     */
	public boolean checkCredentials(String password, String username) {
	    for (Account account : accountList) { // Basic linear-esque search to checkCredentials 
	        if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
	            return true; // If both the password and username check out 
	        }
	    }
	    return false; // Otherwise, return false 
	}
	
	/**
     * Creates a new account with the provided username and password.
     *
     * @param username 
     * 		The username for the new account.
     * 
     * @param password 
     * 		The password for the new account.
     * 
     * @throws IllegalArgumentException 
     * 		If the provided username is already taken.
     */
	public void createNewAccount(String username, String password) {
	   // Converting the accountList ArrayList into a useable array
		String[] allUserNames = new String[accountList.size()];
	    for (int i = 0; i < accountList.size(); i++) {
	        allUserNames[i] = accountList.get(i).getUsername();
	    }
	    
	    // Sort the array of usernames before beginning the binary search 
	    Arrays.sort(allUserNames);
	    if (StringFinder.findString(allUserNames, username, 0, allUserNames.length - 1)) { // The username is already taken 
	    	throw new IllegalArgumentException("This user name is already taken!");
	    } else {
	    	// Creating the Account if the username is not already taken 
	    	Account newAccount = new Account(username, password);
	    	accountList.add(newAccount); // Adding it to the list 
	    	newAccount.setAccountIndex(accountList.size() - 1); // Setting its index, 1 less since index starts from 0 
	    	newAccount.setUserHasItems(false); // Setting 
	    	newAccount.setPoints(BASE_POINTS); // Points start at zero 
	    }
		
	}
	
	/**
     * Finds the account index based on the provided username.
     *
     * @param 
     * 		username The username to search for.
     * 
     * @return 
     * 		The account index if the username is found, or -1 if not found.
     */
    public int findAccountIndexByUsername(String username) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUsername().equals(username)) {
                return i; // Return the index if the username is found
            }
        }
        return -1; // Return -1 if the username is not found
    }
    
    /**
     * Gets the Account instance for the given account index.
     *
     * @param accountIndex 
     * 		The index of the account.
     * 
     * @return 
     * 		The Account instance if the index is valid, or null if not found.
     */
    public Account getAccountByIndex(int accountIndex) {
        try {
            return accountList.get(accountIndex); // Return the Account instance if the index is valid
        } catch (IndexOutOfBoundsException e) { // Print the full stack trace of the exception
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Retrieves a ShopItem based on its name.
     *
     * @param itemName 
     * 		The name of the ShopItem.
     * 
     * @return 
     * 		The ShopItem with the specified name, or null if not found.
     */
    public ShopItem getShopItemByName(String itemName) {
        for (ShopItem shopItem : ShopItem.values()) { // Linear like search to get the shopItem name 
            if (shopItem.getItemName().equals(itemName)) {
                return shopItem;
            }
        }
        return null; // Item not found
    }

    /**
     * Redeems a shop item for the specified account if the account has enough points.
     *
     * @param item         
     * 		The shop item to redeem.
     * 
     * @param 
     * 		accountIndex The index of the account attempting the redemption.
     * 
     * @return
     * 		True if the redemption is successful, false if the account has insufficient points.
     */
    public boolean redeemItem(ShopItem item, int accountIndex) {
        // Variable Assignment
    	Account currentAccount = accountList.get(accountIndex);
        int requiredPoints = item.getPoints();

        // Processing 
        if (currentAccount.getPoints() >= requiredPoints) { // Checking if they have enough points 
            currentAccount.setPoints(currentAccount.getPoints() - requiredPoints);
            return true; // Successful redemption
        } else {
            return false; // Insufficient points for redemption
        }
    }

    /**
     * Increases the loyalty points for the specified account based on the game outcome.
     *
     * @param win          
     * 		True if the player won the game, false if they lost.
     * 
     * @param accountIndex 
     * 		The index of the account to update.
     */
    public void incLoyaltyPoints(boolean win, int accountIndex) {
        Account currentAccount = accountList.get(accountIndex);
        if (win) { // If they win they get 100 points 
            currentAccount.setPoints(currentAccount.getPoints() + WIN_REWARD);
        } else { // If they loose they get 50 points
            currentAccount.setPoints(currentAccount.getPoints() + LOOSE_REWARD);
        }
    }
}