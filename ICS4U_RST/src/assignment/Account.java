package assignment;

/**
 * @author s453512 
 * Date: 2023-11-24 
 * Account.java 
 * Class that defines the characteristics of an account 
 */


public class Account {

	// Variables 
	private String username; // First name of the account 
	private String userPassword;  // Last name of the account 
	private int accountIndex; // When instantiated each account is given an index. The PointsRecorder handles the assignment of accountIndex. 
	private int userPoints; 
	//private ArrayList<ShopItems>;
	//private ShopItems[];
	
	 /**
     * Default constructor. Initializes an account with default values.
     */
	public Account() {
		// Basic constructor, sets all the necessary data fields to a value. 
		username = "";
		userPassword = "";
	}
	
	/**
     * Overloaded Constructor. Initializes an account with the given first name, last name, and number of weeks.
     *
     * @param userName 
     * 		The first name of the account holder.
     * 
     * @param userPassword  
     * 		The last name of the account holder.
     * 
     * @param numWeek   
     * 		The number of weeks.
     */
	public Account(String userName, String userPassword) {
		this.username = userName; 
		this.userPassword = userPassword;
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
 	
	public void setPoints(int userPoints) {
		this.userPoints = userPoints;
	}
	
	public int getPoints() {
		return userPoints; 
	}
	
	public void purchaseItem(String itemName) {
		
	}
}