package assignment11.Database;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class User {

	private String name;
	
	
	public User(String name) {
		this.name = name;
	}

	public Thread doDatabaseThings(Database db) {
		User u = this;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(ThreadLocalRandom.current().nextLong(100, 500));
					db.login(u);
					System.out.printf("User %s is doing database things\n", u.getName());
					Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
					db.logout(u);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		return t;
	}

	public Thread backupCurrentSession(CyclicBarrier b1, CyclicBarrier b2) {
		User u = this;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(ThreadLocalRandom.current().nextLong(100, 500));
					System.out.printf("User %s finished backup part A\n", u.getName());
					b1.await();
					Thread.sleep(ThreadLocalRandom.current().nextLong(100, 500));
					System.out.printf("User %s finished backup part B\n", u.getName());
					b2.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		return t;
	}

	public Thread backupCurrentSession(MyBarrier b1, MyBarrier b2) {
		User u = this;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(ThreadLocalRandom.current().nextLong(100, 500));
					System.out.printf("User %s finished backup part A\n", u.getName());
					b1.await();
					Thread.sleep(ThreadLocalRandom.current().nextLong(100, 500));
					System.out.printf("User %s finished backup part B\n", u.getName());
					b2.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		return t;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
