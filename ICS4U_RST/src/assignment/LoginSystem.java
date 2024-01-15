package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoginSystem {

	private ArrayList<Account> accountList;

	public LoginSystem() {

	}

	public boolean checkCredentials(String password, String username, int accountIndex) {
		String nameLine; 
		boolean validUserName, validPassword; 
		ArrayList<String> allUserNames = new ArrayList<String>(); 
		
		try {
			FileReader wordFile = new FileReader("data/accountDB");
			BufferedReader fileReader = new BufferedReader(wordFile);
			
			while ((nameLine = fileReader.readLine()) != null) {
				allUserNames.add(nameLine); 
				fileReader.readLine(); 
			}
			// Convert ArrayList<String> to Array
	        String[] userNameArray = allUserNames.toArray(new String[0]);
	        
	        validUserName = findUserName(userNameArray, username, 0, userNameArray.length); 
	        validPassword = accountList.get(accountIndex).getPassword().equals(password);

	        if (validUserName == true && validPassword == true) {
	        	return true; 
	        }
	     
			fileReader.close();
			// Error Checking
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException a) {
			a.printStackTrace();
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
