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
		String line; 
		
		try {
			FileReader wordFile = new FileReader("data/accountDB");
			BufferedReader fileReader = new BufferedReader(wordFile);
			
			while (line = fileReader.readLine() != null) {
				
			}
			
			// Error Checking
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException a) {
			a.printStackTrace();
		}
		
		return true;
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
