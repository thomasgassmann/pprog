package assignment7;

public class Account implements Comparable<Account> {

	private final int id;
	private int balance;

	/**
	 * @param id Id of the account. You should make sure to set this is unique over all accounts.
	 */
	public Account(int id) {
		this.id = id;
		this.balance = 0;
	}

	/**
	 * @return Balance of the account.
	 */
	public int getBalance() {
		return balance;
	}

	/**
	 * Set a new balance for this account.
	 * 
	 * @param balance The new balance. Must not be negative.
	 */
	public void setBalance(int balance) {
		if (balance < 0) {
			throw new IllegalArgumentException("Negative balance.");
		}
		this.balance = balance;
	}

	/**
	 * @return Id of the account. You should make sure this is unique over all accounts.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Implementation of Comparable interface. This allows a list of Accounts
	 * to be sorted using Collections.sort()
	 * 
	 * @return A negative integer, zero, or a positive integer if
	 * this account is less than, equal to, or greater than the other account.
	 */
	@Override
	public int compareTo(Account other) {
		return (id < other.getId() ? -1 : (id == other.getId() ? 0 : 1));
	}

}
