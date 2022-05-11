package assignment11.Database;

public class MySemaphore {

	private volatile int count;

	public MySemaphore(int maxCount) {
		//TODO initialize count suitably
		count = maxCount;
	}

	public synchronized void acquire() throws InterruptedException {
		//TODO implment suitable monitor and implement semaphore acquisition
		while (count == 0) {
			wait();
		}
		
		count--;
	}

	public synchronized void release() {
		//TODO implment suitable monitor and implement semaphore release
		count++;
		notify();
	}

}
