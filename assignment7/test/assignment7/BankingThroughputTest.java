package assignment7;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BankingThroughputTest {

	private static final int TRANSFER_AMOUNT = 1;
	private static final int INITIAL_BALANCE = 10000;
	private static final int ACCOUNTS = 100000;
	private static final int TRANSACTIONS = 20000000;

	private BankingSystem bs;
	private int sum;
	private List<Account> accounts;
	private List<Thread> threads;
	private int transactions;

	public void setUp(int actors, final int individualTransactions) {
		assertTrue("Running on a multicore machine.", Runtime.getRuntime().availableProcessors() > 1);
		this.transactions = individualTransactions*actors;

		bs = new BankingSystem();
		accounts = new ArrayList<Account>();
		for (int i=0; i<ACCOUNTS; i++) {
			Account a = new Account(i);
			a.setBalance(INITIAL_BALANCE);
			sum += INITIAL_BALANCE;
			accounts.add(a);

			bs.addAccount(a);
		}

		threads = new ArrayList<Thread>();
		for (int i=0; i < actors; i++) {
			threads.add(new Thread() {
				@Override
				public void run() {
					Random generator = new Random();
					for (int i=0; i < individualTransactions; i++) {
						Account from = accounts.get(generator.nextInt(ACCOUNTS));
						Account to = accounts.get(generator.nextInt(ACCOUNTS));
						bs.transferMoney(from, to, TRANSFER_AMOUNT);
					}
				}
			});
		}
	}

	private void runTransactions() throws InterruptedException {
		for (Thread t : threads) {
			t.start();
		}

		long start = System.nanoTime();
		for (Thread t : threads) {
			t.join();
		}
		long end = System.nanoTime();

		assertThat("Did not loose any money.", bs.totalMoneyInBank(), is(sum));

		double total_transactions = (double) this.transactions;
		double total_time_seconds = (double) (end-start)/ 1_000_000_000.0;
		double transactions_per_sec = total_transactions/total_time_seconds;

		System.out.printf("Completed %d transactions in %.3f sec: %.4e transactions/sec\n",
				this.transactions, total_time_seconds, transactions_per_sec);
	}

	@Test
	public void testTransactionThroughputSingle() throws InterruptedException {
		System.out.println("Sequential using 1 Threads.");
		setUp(1, Runtime.getRuntime().availableProcessors()*TRANSACTIONS);
		runTransactions();
	}

	@Test
	public void testTransactionThroughputParallel() throws InterruptedException {
		System.out.println("Parallel using " + Runtime.getRuntime().availableProcessors() + " Threads.");
		setUp(Runtime.getRuntime().availableProcessors(), TRANSACTIONS);
		runTransactions();
	}
}
