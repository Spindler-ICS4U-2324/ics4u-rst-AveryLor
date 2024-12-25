package assignment;

/**
 * @author s453512 
 * Date: 2024-01-08 
 * Account.java 
 * Class that defines the characteristics of an account 
 */


import java.util.ArrayList;

public class Account {

	// Variables 
	private String username; // First name of the account 
	private String userPassword;  // Last name of the account 
	private int accountIndex; // When instantiated each account is given an index. The PointsRecorder handles the assignment of accountIndex. 
	private int userPoints; // User points 
	private boolean userHasItems; // Does the user actually have items (used for checking files)
	private ArrayList<AccountsManager.ShopItem> purchasedItems;
	
	 /**
     * Default constructor. Initializes an account with default values.
     */
	public Account() {
		// Basic constructor, sets all the necessary data fields to a value. 
		username = "";
		userPassword = "";
		purchasedItems = new ArrayList<AccountsManager.ShopItem>();
	}
	
	/**
     * Overloaded Constructor. Initializes an account with the given first name, last name, and number of weeks.
     *
     * @param username 
     * 		The first name of the account holder.
     * 
     * @param userPassword  
     * 		The last name of the account holder.
     * 
     * @param numWeek   
     * 		The number of weeks.
     */
	public Account(String username, String userPassword) {
		this.username = username; 
		this.userPassword = userPassword;
		purchasedItems = new ArrayList<AccountsManager.ShopItem>();
	}
	
	/**
     * Sets the userName of the account holder.
     */
	public void setUsername(String userName) {
		this.username = userName; 
	}
	
	/**
     * Gets the first name of the account holder.
     *
     * @return 
     * 		The first name.
     */
	public String getUsername() {
		return username;
	}
	
	/**
     * Sets the last name of the account holder. 
     */
	public void setPassword(String userPassword) {
		this.userPassword = userPassword; 
	}
	
	/**
     * Gets the last name of the account holder.
     *
     * @return 
     * 		The last name.
     */
	public String getPassword() {
		return userPassword; 
	}
	
	/**
     * Sets the account index.
     *
     * @param accountIndex
     * 		The account index.
     */
	public void setAccountIndex(int accountIndex) {
		this.accountIndex = accountIndex; 
	}
	
	/**
     * Gets the account index.
     *
     * @return 
     * 		The account index.
     */
	public int getAccountIndex() {
		return accountIndex; 
	}
 	
	/**
	 * Sets the points for the user.
	 *
	 * @param userPoints The points to set for the user.
	 */
	public void setPoints(int userPoints) {
		this.userPoints = userPoints;
	}
	
	/**
	 * Retrieves the current points of the user.
	 *
	 * @return The current points of the user.
	 */
	public int getPoints() {
		return userPoints; 
	}

	/**
     * Sets whether the user has items.
     *
     * @param userHasItems The value indicating whether the user has items.
     */
    public void setUserHasItems(boolean userHasItems) {
        this.userHasItems = userHasItems;
    }

    /**
     * Checks whether the user has items.
     *
     * @return true if the user has items, false otherwise.
     */
    public boolean getHasUserItems() {
        return userHasItems;
    }
	

    /**
     * Gets the list of purchased items for the account.
     *
     * @return The list of purchased items.
     */
    public ArrayList<AccountsManager.ShopItem> getPurchasedItems() {
        return purchasedItems;
    }

	/**
	 * Purchase the specified item and add it to the shopItems ArrayList.
	 *
	 * @param item The item to be purchased.
	 * 
	 */
	public void purchaseItem(AccountsManager.ShopItem item) {
		// Add the item to the shopItems ArrayList
		purchasedItems.add(item);
	}
    
    /**
     * Set the list of purchased items for the account.
     *
     * @param purchasedItemsList 
     * 		The list of purchased items.
     */
    public void setPurchasedItems(ArrayList<AccountsManager.ShopItem> purchasedItemsList) {
        this.purchasedItems = purchasedItemsList;
    }
}