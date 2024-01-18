package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoginSystem {

	private ArrayList<Account> accountList;

	public LoginSystem() {
		accountList = new ArrayList<Account>();
	    ArrayList<String> names = new ArrayList<>();
	    ArrayList<String> passwords = new ArrayList<>();
	    
	    try {
	        BufferedReader reader = new BufferedReader(new FileReader("data/accountDB"));
	        String line;
	        
	        while ((line = reader.readLine()) != null) {
	            names.add(line);
	            passwords.add(reader.readLine());
	        }
	        
	        reader.close();
	        
	        // Error Checking
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException a) {
	        a.printStackTrace();
	    }
	    
	    for (int i = 0; i < names.size(); i++) {
	        Account currentAccount = new Account(names.get(i), passwords.get(i));
	        currentAccount.setAccountIndex(i + 1);
	        accountList.add(currentAccount);
	    }
	}

	public boolean checkCredentials(String password, String username) {
	    for (Account account : accountList) {
	        if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
	            return true;
	        }
	    }
	    return false;
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
