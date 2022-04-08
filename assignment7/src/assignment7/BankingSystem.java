package assignment7;

import java.util.ArrayList;
import java.util.List;

public class BankingSystem {

	protected List<Account> accountList;

	/**
	 * Initializes the BankingSystem:
	 * accountList is empty and totalMoneyInBank() should return 0.
	 */
	public BankingSystem() {
		setAccountList(new ArrayList<Account>());
	}

	/**
	 * Transfers Money from one account to another.
	 * 
	 * @param from Account to transfer money from.
	 * @param to Account to transfer money to.
	 * @param amount Amount to transfer.
	 * @return True if Money was transferred successfully.
	 *         False if there was not enough balance in from.
	 */
	public boolean transferMoney(Account from, Account to, int amount) {	
		if (from.getBalance() < amount) {
			return false;
		} else {
			from.setBalance(from.getBalance() - amount);
			to.setBalance(to.getBalance() + amount);
		}
		//System.out.println(from.getId());
		return true;
	}

	/**
	 * Returns the sum of a given list of accounts.
	 * 
	 * @fixme Tends to return wrong results :-(
	 */
	public int sumAccounts(List<Account> accounts) {
		int sum = 0;

		for (Account a : accounts) {
			synchronized(a) {
				sum += a.getBalance();
			}
		}

		return sum;		
	}

	/**
	 * Calculates the total amount of money in the bank at any point in time.
	 * @return The total amount of money.
	 * 
	 * @fixme Tends to return wrong results :-(
	 */
	public int totalMoneyInBank() {
		return sumAccounts(getAccountList());
	}

	/**
	 * Adds a new account to the bank.
	 * The account needs to have a positive balance to be added to the system.
	 * 
	 * @param a New account
	 * @return True if account was added successfully.
	 *         False if account could not be added to the system 
	 *         (ie., account did not have enough balance).
	 */
	public boolean addAccount(Account a) {
		if (a.getBalance() >= 0) {
			getAccountList().add(a);
			return true;
		}
		else {
			return false;
		}

	}

	protected List<Account> getAccountList() {
		return accountList;
	}

	protected void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

}
