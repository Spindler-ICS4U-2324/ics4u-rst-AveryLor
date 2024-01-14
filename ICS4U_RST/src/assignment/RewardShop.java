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
    private FileReader accountFile; // Used to access the file 
    private BufferedReader accountReader; // Used to read from the file 
    private ArrayList<Account> accountList; // ArrayList/data structure containing all of the Account instances 
    private static final int BONUS_FIELD = 5000; 

    private ShopItems rewardItems;

    public enum ShopItems {
      GiftCard, Coffee, Newspaper
    }
    
    
    /**
     * Constructor. Initializes an ArrayList that holds all of the accounts.  
     */
    public RewardShop() {
        accountList = new ArrayList<Account>();
    }

    /**
     * This method checks the data file and creates the necessary accounts described within the file
     * to create persistence.
     *
     * @param file
     *        String of the file path
     *
     * @throws FileNotFoundException
     *         If the file cannot be found tell the user 
     *         
     * @throws IOException
     *         If an I/O error occurs while reading the file tell the user 
     * 
     * @throws NumberFormatException
     *         If there is an error parsing a number from the file tlel the user
     */
    public void savedAccountsProcessing(String file) {
        // Variable
        String userPassword, lastName;
        int accountIndex, numWeeks;

        // Try and catch loop
        try {
            accountFile = new FileReader(file);
            accountReader = new BufferedReader(accountFile);

            // Processing
            while (accountReader.readLine() != null) { // The first line is blank due to the while loop 
                accountIndex = Integer.parseInt(accountReader.readLine()); // Second line is the account index 
                userPassword = accountReader.readLine(); // Third is the first name 
                lastName = accountReader.readLine(); // Fourth is the last name 
                numWeeks = Integer.parseInt(accountReader.readLine()); // Fifth is the number of weeks that were payed 

                // Variables
                double[] weeklySpendingLog = new double[numWeeks];

                // Processing
                for (int i = 0; i < numWeeks; i++) { // And loop through the rest of the weeks 
                    weeklySpendingLog[i] = Double.parseDouble(accountReader.readLine());
                }
                //addCompleteAccount(userPassword, lastName, accountIndex, weeklySpendingLog); // Use the addCompleteAccount method afterwards to create persistence 
            }
            accountFile.close();
        } catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException a) {
		    a.printStackTrace();
        } catch (NumberFormatException g) { // Using printStackTrace instead of the normal throw new IllegalArgumentException
            g.printStackTrace(); // I did this to make the application seem to flow more coherently and since this is more of a technical error within how  
            // the user utilizes the object rather than their input I decided to print this error directly to the console rather than a custom message. 
            // Furthermore, e.printStackTrace() will provide detailed information about where the exception occured which I believe will be far more useful in this case. 
        }
    }

    
    /**
     * Removes an account from the data structure.
     *
     * @param accountIndex 
     * 		The index of the account to be removed
     * 
     * @throws IllegalArgumentException
     * 		If the user enters an account index that is not associated with a current 
     * 		account index then force the reprompt. 
     * 		
     */
    public void removeAccount(int accountIndex) {
    	if (accountIndex > accountList.size() || accountIndex < 1) { // If the account to be removed is not an actual account then force reprompt for valid data 
    		throw new IllegalArgumentException("Enter a valid account index.");
    	} else {
    		accountList.remove(accountIndex - 1); // The account index is one greater than the actual index 
    		for (Account temp : accountList) { // Once one account is removed, change the account indexes of the other accounts 
    			if(accountIndex < temp.getAccountIndex()) { // For all accounts greater than that position
    				temp.setAccountIndex(temp.getAccountIndex() - 1); //Reduce its index by 1
    			}
    		}
    	}
    }
    
    public void incLoyaltyPoints(boolean win, int accountIndex) {
    	Account currentAccount = accountList.get(accountIndex);
    	if (win == true) {
    		currentAccount.setPoints(currentAccount.getPoints() + 100);
    	} else {
    		currentAccount.setPoints(currentAccount.getPoints() + 50);
    	}
    }
    


}