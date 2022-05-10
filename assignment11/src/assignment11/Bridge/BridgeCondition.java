package assignment11.Bridge;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BridgeCondition extends Bridge {

	//TODO use this given lock and create conditions form it
	//you might find that you need some additional variables
	final Lock bridgeLock = new ReentrantLock();

	public void enterCar() throws InterruptedException {
		bridgeLock.lock();
		//TODO implement rules for car entry
		bridgeLock.unlock();
	}

	public void leaveCar() {
		//TODO implement rules for car leave
	}

	public void enterTruck() throws InterruptedException {
		//TODO implement rules for truck entry - similar to car entry
	}

	public void leaveTruck() {
		//TODO implement rules for car leave - similar to car leave
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
