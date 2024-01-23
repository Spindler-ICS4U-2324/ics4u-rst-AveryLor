package assignment;

/**
 * @author s453512 
 * Date: 2023-11-24 
 * PointsRecorder.java 
 * Class that holds multiple accounts and assigns points to them 
 */


import java.util.ArrayList;


import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class RewardShop {
    private ArrayList<Account> accountList; // ArrayList/data structure containing all of the Account instances 
    private final String ALL_ACCOUNTS_FILE = "data/accountDB"; 
    
    public enum ShopItem {
        ITEM1("LCBO Giftcard", 10),
        ITEM2("Newspaper", 20),
        ITEM3("Costco Giftcard", 30);

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
				
				Account currentAccount = new Account(username, password);
				currentAccount.setPoints(points);
				currentAccount.setUserHasItems(userHasItems);
				currentAccount.setAccountIndex(accountList.size());
				accountList.add(currentAccount);
				
				if (userHasItems) {
					numItems = Integer.parseInt(accountReader.readLine());
					
					ArrayList<RewardShop.ShopItem> purchasedItems = new ArrayList<>();

					for (int i = 0; i < numItems; i++) {
						String currentItem = accountReader.readLine();

						RewardShop.ShopItem shopItem = RewardShop.ShopItem.valueOf(currentItem);
						purchasedItems.add(shopItem);
						
						currentAccount.setPurchasedItems(purchasedItems);

					}
				}
				
			}
			accountReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException a) {
			a.printStackTrace();
		} catch (NumberFormatException g) {
			// Using printStackTrace instead of the normal throw new
			// IllegalArgumentException
			g.printStackTrace();
			// I did this to make the application seem to flow more coherently and since
		}
	
		try (BufferedReader reader = new BufferedReader(new FileReader(ALL_ACCOUNTS_FILE))) {
	        String line;
	        int i = 0;
	        
	        while ((line = reader.readLine()) != null) {
	            String name = line;
	            String password = reader.readLine();
	            
	            Account currentAccount = new Account(name, password);
	            currentAccount.setAccountIndex(++i);
	            accountList.add(currentAccount);
	        }
	        
	        // Error Checking
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException a) {
	        a.printStackTrace();
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
     * Gets the Account instance for the given account index.
     *
     * @param accountIndex The index of the account.
     * @return The Account instance if the index is valid, or null if not found.
     */
    public Account getAccountByIndex(int accountIndex) {
        try {
            return accountList.get(accountIndex); // Return the Account instance if the index is valid
        } catch (IndexOutOfBoundsException e) {
            // Print the full stack trace of the exception
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Retrieves a ShopItem based on its name.
     *
     * @param itemName The name of the ShopItem.
     * @return The ShopItem with the specified name, or null if not found.
     */
    public ShopItem getShopItemByName(String itemName) {
        for (ShopItem shopItem : ShopItem.values()) {
            if (shopItem.getItemName().equals(itemName)) {
                return shopItem;
            }
        }
        return null; // Item not found
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