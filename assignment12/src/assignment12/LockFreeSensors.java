package assignment12;

import java.util.concurrent.atomic.AtomicReference;

class LockFreeSensors implements Sensors {

    LockFreeSensors()
    {
    }

    // store data and timestamp
    // if and only if data stored previously is older (lower timestamp)
    public void update(long timestamp, double[] data)
    {
    		// IMPLEMENT ME
    	
    		// make this lock-free
    		// don't try (a variant) double compare and swap
    		// hint: make use of the SensorData class (why?)
    }

    // pre: val != null
    // pre: val.length matches length of data written via update
    // if no data has been written previously, return 0
    // otherwise return current timestamp and fill current data to array passed as val
    public long get(double val[])
    {
		// IMPLEMENT ME
    	
        return 0;
    }

}
