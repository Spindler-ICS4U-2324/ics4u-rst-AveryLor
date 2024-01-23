package assignment;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class LoginSystem {

	private ArrayList<Account> accountList;

	public LoginSystem() {
	    accountList = new ArrayList<>();
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
			g.printStackTrace();
			// I did this to make the application seem to flow more coherently and since
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
    

	public boolean checkCredentials(String password, String username) {
	    for (Account account : accountList) {
	        if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	
	public void createNewAccount(String username, String password) {
	    // Assuming accountList is an ArrayList<String>
	    String[] allUserNames = new String[accountList.size()];
	    for (int i = 0; i < accountList.size(); i++) {
	        allUserNames[i] = accountList.get(i).getUsername();
	    }
	    // Sort the array of usernames
	    Arrays.sort(allUserNames);
	    if (findUserName(allUserNames, username, 0, allUserNames.length - 1)) {
	    	throw new IllegalArgumentException("This user name is already taken!");
	    } else {
	    	Account newAccount = new Account(username, password);
	    	accountList.add(newAccount); 
	    	newAccount.setAccountIndex(accountList.size());
	    	newAccount.setUserHasItems(false);
	    	newAccount.setPoints(0);
	    	
	    }
		
	}
	
	/**
     * Finds the account index based on the provided username.
     *
     * @param username The username to search for.
     * @return The account index if the username is found, or -1 if not found.
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
	

	public boolean findUserName(String[] allUserNames, String searchName, int left, int right) {
		if (left > right) {
			return false; // element not found
		}

		int middle = (left + right) / 2;

		if (allUserNames[middle].equals(searchName)) { // element was found!
			return true;
		}

		if (allUserNames[middle].compareTo(searchName) > 0) {
			return findUserName(allUserNames, searchName, left, middle - 1); // check the left side
		} else {
			return findUserName(allUserNames, searchName, middle + 1, right); // check the right side
		}
	}
	
	
	

}
