package assignment11.Bridge;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BridgeCondition extends Bridge {

	//TODO use this given lock and create conditions form it
	//you might find that you need some additional variables
	final Lock bridgeLock = new ReentrantLock();
	private volatile int numCars = 0;
	private volatile int numTrucks = 0;
	final Condition carLeave = bridgeLock.newCondition();
	final Condition truckLeave = bridgeLock.newCondition();

	public void enterCar() throws InterruptedException {
		//TODO implement rules for car entry
		bridgeLock.lock();
		try {
			while (numCars >= 3 || numTrucks > 0) {
				if (numCars >= 3) {
					carLeave.await();
				} else {
					truckLeave.await();
				}
			}
			
			numCars++;
		} finally {
			bridgeLock.unlock();
		}
	}

	public void leaveCar() {
		//TODO implement rules for car leave
		bridgeLock.lock();
		try {
			numCars--;
			carLeave.signalAll();
		} finally {
			bridgeLock.unlock();
		}
	}

	public void enterTruck() throws InterruptedException {
		//TODO implement rules for truck entry - similar to car entry
		bridgeLock.lock();
		try {
			while (numCars > 0 || numTrucks > 0) {
				if (numCars > 0) {
					carLeave.await();
				} else {
					truckLeave.await();
				}
			}
			
			numTrucks++;
		} finally {
			bridgeLock.unlock();
		}
	}

	public void leaveTruck() {
		//TODO implement rules for car leave - similar to car leave
		bridgeLock.lock();
		try {
			numTrucks--;
			truckLeave.signalAll();
		} finally {
			bridgeLock.unlock();
		}
	}

	public static void main(String[] args) {
		Random r = new Random();
		BridgeCondition b = new BridgeCondition();
		for (int i = 0; i < 100; ++i) {
			if (r.nextBoolean()) {
				(new Car()).driveTo(b);
			} else {
				(new Truck()).driveTo(b);
			}
		}
	}

}
