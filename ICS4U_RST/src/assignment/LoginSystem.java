package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LoginSystem {

	private ArrayList<Account> accountList;
	private final String ALL_ACCOUNTS_FILE = "data/accountDB"; 

	public LoginSystem() {
	    accountList = new ArrayList<>();
	    
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
	    if (findUserName(allUserNames, username, 0, allUserNames.length - 1)) {
	    	throw new IllegalArgumentException("This user name is already taken!");
	    } else {
	    	Account newAccount = new Account(username, password);
	    	accountList.add(newAccount); 
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
