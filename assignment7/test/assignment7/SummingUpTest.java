package assignment7;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Test;

import assignment7.Account;
import assignment7.BankingSystem;

public class SummingUpTest {

	private static final int TRANSFER_AMOUNT = 1;
	private static final int INITIAL_BALANCE = 100000;
	private static final int ACCOUNTS = 10000;
	private static final int TRANSACTIONS = 1000000;

	private BankingSystem bs;
	private int initialSum, lastSum;
	private List<Account> accounts;
	private List<Thread> threads;

	private AtomicLong remainingTransactions;

	@Before
	public void setUp() throws Exception {
		int noOfCores = Runtime.getRuntime().availableProcessors();
		assertTrue("Running on a multicore machine.", noOfCores > 1);

		bs = new BankingSystem();
		accounts = new ArrayList<Account>();
		for (int i=0; i<ACCOUNTS; i++) {
			Account a = new Account(i);
			a.setBalance(INITIAL_BALANCE);
			initialSum += INITIAL_BALANCE;
			accounts.add(a);
		}

		// Add the accounts shuffled.
		List<Account> shuffled = new ArrayList<Account> (accounts);
		Collections.shuffle(shuffled);
		for (Account a : shuffled) bs.addAccount(a);

		remainingTransactions = new AtomicLong(noOfCores*TRANSACTIONS);

		// one transfer thread for each core
		threads = new ArrayList<Thread>(noOfCores+1);
		for (int i=0; i < noOfCores; i++) {
			threads.add(new Thread() {
				@Override
				public void run() {
					Random generator = new Random();
					while(remainingTransactions.decrementAndGet() > 0) {
						Account from = accounts.get(generator.nextInt(ACCOUNTS));
						Account to = accounts.get(generator.nextInt(ACCOUNTS));
						bs.transferMoney(from, to, TRANSFER_AMOUNT);
					}
				}
			});
		}

		// additional revisor thread which sums stuff up
		threads.add(new Thread() {
			@Override
			public void run() {

				while(remainingTransactions.decrementAndGet() > 0) {
					lastSum = bs.totalMoneyInBank();
					if (lastSum != initialSum) {
						// mismatch found, finish early
						remainingTransactions.set(0);
					}
				}
			}
		});
	}

	@Test(timeout=15000) // Timeout in case of deadlock
	public void testSum() throws InterruptedException {
		for (Thread t : threads) {
			t.start();
		}

		for (Thread t : threads) {
			t.join();
		}

		assertThat("Concurrent summing up was incorrect", lastSum, is(initialSum));
	}
}
