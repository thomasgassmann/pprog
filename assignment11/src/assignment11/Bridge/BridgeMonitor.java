package assignment11.Bridge;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BridgeMonitor extends Bridge {
	// TODO use this object as a monitor
	// you might find that you need some additional variables.
	private final Object monitor = new Object();
	private int numCars = 0;
	private int numTrucks = 0;

	public void enterCar() throws InterruptedException {
		//TODO implement rules for car entry
		synchronized (monitor) {
			while (numCars >= 3 || numTrucks != 0) {
				monitor.wait();
			}
			
			numCars++;
		}
	}

	public void leaveCar() {
		//TODO implement rules for car leave
		synchronized (monitor) {
			numCars--;
			monitor.notifyAll();
		}
	}

	public void enterTruck() throws InterruptedException {
		//TODO implement rules for truck entry - similar to car entry
		synchronized (monitor) {
			while (numCars != 0 || numTrucks != 0) {
				monitor.wait();
			}
			
			numTrucks++;
		}
	}

	public void leaveTruck() {
		//TODO implement rules for car leave - similar to car leave
		synchronized (monitor) {
			numTrucks--;
			monitor.notifyAll();
		}
	}

	public static void main(String[] args) {
		Random r = new Random();
		BridgeMonitor b = new BridgeMonitor();
		for (int i = 0; i < 100; ++i) {
			if (r.nextBoolean()) {
				(new Car()).driveTo(b);
			} else {
				(new Truck()).driveTo(b);
			}
		}
	}

}
