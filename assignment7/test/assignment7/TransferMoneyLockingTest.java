package assignment7;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.concurrent.BrokenBarrierException;

import org.junit.Before;
import org.junit.Test;

public class TransferMoneyLockingTest {

	private static final int INITIAL_BALANCE = 10000000;
	private static final int TRANSFER_AMOUNT = 1;
	private static final int TRANSACTIONS = 10000000;
	private static final int TRANSACTIONS_CHUNK = 10000;

	private BankingSystem bs;
	private Account accountA, accountB;
	private int sum;

	@Before
	public void setUp() throws Exception {
		bs = new BankingSystem();

		accountA = new Account(0);
		accountA.setBalance(INITIAL_BALANCE);
		bs.addAccount(accountA);
		sum += accountA.getBalance();

		accountB = new Account(1);
		accountB.setBalance(INITIAL_BALANCE);
		bs.addAccount(accountB);
		sum += accountB.getBalance();
	}

	@Test(timeout=1000)
	public void testSameAccountTransfer() {
		bs.transferMoney(accountA, accountA, TRANSFER_AMOUNT);
		assertThat("Did not loose any money.", bs.totalMoneyInBank(), is(sum));
	}

	private class TransferingThread extends Thread {
		private Account from, to;

		TransferingThread(Account from, Account to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public void run() {
			for(int i=0; i<TRANSACTIONS/TRANSACTIONS_CHUNK; i++) {
				for(int j=0; j<TRANSACTIONS_CHUNK; j++) {
					bs.transferMoney(from, to, TRANSFER_AMOUNT);
				}
			}
		}
	}

	@Test(timeout=60000)
	public void testCyclicAccountTransfer() throws InterruptedException, BrokenBarrierException {
		Thread threadA = new TransferingThread(accountA, accountB);
		Thread threadB = new TransferingThread(accountB, accountA);

		threadA.start();
		threadB.start();

		threadA.join();
		threadB.join();

		assertThat("Did not loose any money.", bs.totalMoneyInBank(), is(sum));
	}

}
