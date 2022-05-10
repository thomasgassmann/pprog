package assignment11.Bridge;

import java.util.concurrent.ThreadLocalRandom;

public class Truck {

	public void driveTo(Bridge bridge) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(ThreadLocalRandom.current().nextLong(100, 500));
					bridge.enterTruck();
					System.out.println("Truck just entered the bridge");
					Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
					bridge.leaveTruck();
					System.out.println("Truck just left the bridge");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
