package assignment11.Database;
import org.junit.Test;

import assignment11.Database.DatabaseCustom;
import assignment11.Database.User;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class DatabaseTest {
		private static final int ACCOUNTS = 30;
		private static ByteArrayOutputStream baos;
		private static PrintStream ps;
		private static DatabaseCustom db;
		private static User[] users;

		public void setUp() {
			assertTrue("Running on a multicore machine.", Runtime.getRuntime().availableProcessors() > 1);
			// Create a stream to hold the output
		    baos = new ByteArrayOutputStream();
		    ps = new PrintStream(baos);
		    // Tell Java to use your special stream
		    System.setOut(ps);
		    db = new DatabaseCustom();
		    users = new User[ACCOUNTS];
		}

		private void runDatabaseThings() throws InterruptedException, BrokenBarrierException {
			Thread t[] = new Thread[ACCOUNTS];
			
			for (int i = 0; i < ACCOUNTS; ++i) {
				users[i] = new User("User"+i);
				t[i] = users[i].doDatabaseThings(db);
			}
			for (int i = 0; i < ACCOUNTS; ++i) {
				t[i].join();
			}
			
		
			
			String out = baos.toString();
			assertTrue(!out.contains("user count: 11"));
		}
		
		private void runBackupThings() throws InterruptedException, BrokenBarrierException {
			Thread t[] = new Thread[ACCOUNTS];
			
			for (int i = 0; i < ACCOUNTS; ++i) {
				users[i] = new User("User"+i);
				t[i] = users[i].doDatabaseThings(db);
			}
			
			Thread.sleep(600);
			// Backup happens at a random time for the threads
			db.backup();
			
			for (int i = 0; i < ACCOUNTS; ++i) {
				t[i].join();
			}
			
			String out = baos.toString();
			
			while (out.lastIndexOf("Backup done") == -1)
				Thread.sleep(600);
			
			int backupDone = out.lastIndexOf("Backup done");
			int finidhedAlast = out.lastIndexOf("finished backup part A");
			int finidhedBfirst = out.indexOf("finished backup part B");
			int finidhedBlast = out.lastIndexOf("finished backup part B");
			
			
			
			assertTrue(finidhedAlast < finidhedBfirst && finidhedBlast < backupDone);
		}
		
		@Test
		public void testSemaphoreParallel() throws InterruptedException, BrokenBarrierException {
			System.out.println("Parallel using N Threads.");
			setUp();
			runDatabaseThings();
		}
		
		@Test
		public void testBarrierParallel() throws InterruptedException, BrokenBarrierException {
			System.out.println("Parallel using N Threads.");
			setUp();
			runBackupThings();
		}
}
