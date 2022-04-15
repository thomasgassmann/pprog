package assignment8.random;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicRandom implements RandomInterface {
    private static final long a = 25214903917L;
    private static final long c = 11;
    private long state;

    public AtomicRandom(long seed) {
        state = seed;
    }

    @Override
    public int nextInt() {
    	// get the current seed value
    	long orig = state;
    	// using recurrence equation to generate next seed
    	long next = (a * orig + c) & (~0L >>> 16);
    	// store the updated seed
    	 state = next;
         return (int) (next >>> 16);
    }
}
