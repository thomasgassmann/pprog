package assignment7;

import java.util.ArrayList;
import java.util.Comparator;
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
		try {
			from.getLock().lock();
			if (from.getBalance() < amount) {
				return false;
			} 
		} finally {
			from.getLock().unlock();
		}
		
		// lock smaller account id first
		try {
			if (from.getId() > to.getId()) {
				to.getLock().lock();
				from.getLock().lock();
			} else {
				from.getLock().lock();
				to.getLock().lock();
			}
			
			to.setBalance(to.getBalance() + amount);
			from.setBalance(from.getBalance() - amount);
		} finally {
			from.getLock().unlock();
			to.getLock().unlock();
		}

		return true;
	}

	/**
	 * Returns the sum of a given list of accounts.
	 * 
	 * @fixme Tends to return wrong results :-(
	 */
	public int sumAccounts(List<Account> accounts) {
		int sum = 0;
		var sortedList = new ArrayList<>(accounts);
		sortedList.sort(new Comparator<Account>() {
			@Override
			public int compare(Account o1, Account o2) {
				return o1.getId() - o2.getId();
			}
		});
		
		try {
			for (Account a : sortedList) {
				a.getLock().lock();
				sum += a.getBalance();
			}
		} finally {
			for (Account a : sortedList) {
				a.getLock().unlock();
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
		} else {
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
