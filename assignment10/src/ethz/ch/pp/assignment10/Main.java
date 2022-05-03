package ethz.ch.pp.assignment10;

import org.junit.Assert;

public class Main {

	public static void main(String[] args) {
 		System.out.println("Assignment 10");
 		
 		for (int i = 2; i <= 10; i++) {
 			BakeryLock bakeryLock = new BakeryLock(i);
 			TASLock tasLock = new TASLock();
 			TASBackoffLock tasBackoffLock = new TASBackoffLock();
 			
 			for (int j = 500; j <= 50000; j *= 10) {
 				System.out.println("============================");
 				System.out.println("Checking with " + i + " threads and " + j + " iterations");
 				time(bakeryLock, i, j, "Bakery Lock");
 				time(tasLock, i, j, "TASLock");
 				time(tasBackoffLock, i, j, "TASLock (backoff)");
 			}
 		}
	}
	
	public static void time(final Lock lock, int threadCount, int iterations, String name) {
		Counter counter = new Counter();

		long start = System.nanoTime();
		inc(lock, threadCount, iterations, counter);
		long total = (System.nanoTime() - start) / 1_000_000;
		
		assert counter.i == threadCount * iterations;
			
		System.out.println("Took " + total + "ms for lock " + name);
	}
	
	private static class Container {
		public long lastPrint = System.nanoTime();
		public boolean didPrint = false;
	}
	
	public static void inc(final Lock lock, int threadCount, int iterations, final Counter counter) {
		Thread[] threads = new Thread[threadCount];

		final Container c = new Container();
		for (int i = 0; i < threadCount; i++) {
			final int currentThread = i;
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < iterations; j++) {
						lock.acquire(currentThread);
						counter.i++;
						if (System.nanoTime() - c.lastPrint > Math.pow(10, 9)) {
							c.lastPrint = System.nanoTime();
							c.didPrint = true;
							System.out.print("i: " + counter.i + " ");
						}
						
						lock.release(currentThread);
					}
				}
			});
			threads[i].start();
		}
		
		for (int i = 0; i < threadCount; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				assert false;
			}
		}

		if (c.didPrint) {
			System.out.println();
		}
	}
}
