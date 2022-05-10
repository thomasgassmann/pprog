package assignment11.Database;

import java.util.concurrent.BrokenBarrierException;

public class DatabaseCustom extends Database {

	private MySemaphore semaphore = new MySemaphore(MAX_USERS);

	@Override
	public void login(User u) throws InterruptedException {
		semaphore.acquire();
		synchronized (this) {
			activeUsers.add(u);
			assert 0 <= activeUsers.size() && activeUsers.size() <= MAX_USERS;
			System.out.printf("User %s logged in - user count: %d\n", u.getName(), activeUsers.size());
		}
	}

	@Override
	public synchronized void logout(User u) throws InterruptedException {
		activeUsers.remove(u);
		assert 0 <= activeUsers.size() && activeUsers.size() <= MAX_USERS;
		System.out.printf("User %s logged out - user count: %d\n", u.getName(), activeUsers.size());
		semaphore.release();
	}

	@Override
	public synchronized void backup() throws InterruptedException, BrokenBarrierException {
		System.out.println("\n=== \nStarting backup\n=== \n");
		if (activeUsers.size() >= 1) {
			MyBarrier b1 = new MyBarrier(activeUsers.size());
			MyBarrier b2 = new MyBarrier(activeUsers.size() + 1);
			for (User u : activeUsers) {
				u.backupCurrentSession(b1, b2);
			}
			b2.await();
		}
		System.out.println("Running backup on server");
		System.out.println("\n=== \nBackup done\n=== \n");
	}

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		DatabaseCustom db = new DatabaseCustom();
		for (int i = 0; i < 20; ++i) {
			(new User(i + "")).doDatabaseThings(db);
		}

		Thread.sleep(600);
		// Backup happens at a random time for the threads
		db.backup();
	}

}
