package assignment12;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class LockedSensors implements Sensors {
	volatile long time = 0;
	volatile double data[];
	private ReadWriteLock _lock = new ReentrantReadWriteLock(true);

	LockedSensors() {
		time = 0;
	}

	// store data and timestamp
	// if and only if data stored previously is older (lower timestamp)
	public void update(long timestamp, double[] data) {
		try {
			_lock.writeLock().lock();
			if (timestamp > time) {
				if (this.data == null) {
					this.data = new double[data.length];
				}

				time = timestamp;
				for (int i = 0; i < data.length; ++i) {
					this.data[i] = data[i];
				}
			}
		} finally {
			_lock.writeLock().unlock();
		}
	}

	// pre: val != null
	// pre: val.length matches length of data written via update
	// if no data has been written previously, return 0
	// otherwise return current timestamp and fill current data to array passed as
	// val
	public long get(double val[]) {
		// ADAPT ME I'M NOT THREADSAFE
		if (time == 0) {
			return 0;
		} else {
			try {
				_lock.readLock().lock();
				for (int i = 0; i < data.length; ++i)
					val[i] = data[i];
				return time;
			} finally {
				_lock.readLock().unlock();
			}
		}
	}
}
