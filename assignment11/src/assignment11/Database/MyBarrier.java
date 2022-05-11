package assignment11.Database;

public class MyBarrier {

	private final int count;
	private volatile int current = 0;
	private volatile boolean isDraining = false;
	
	MyBarrier(int n){
		//TODO find suitable variables for the barrier and initialize them
		count = n;
    }

	synchronized void await() {
		//TODO implement the barrier await using Monitors
		try {
			while (isDraining) {
				wait();
			}
			
			current++;
			while (count > current && !isDraining) {
				wait();
			}
			
			if (current == count) {
				isDraining = true;
				notifyAll();
			}
	
			current--;
			if (current == 0) {
				isDraining = false;
				notifyAll();
			}
		} catch (InterruptedException ex) {
		}
	}
}
