package assignment8.random;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicRandom implements RandomInterface {
    private static final long a = 25214903917L;
    private static final long c = 11;
    private AtomicLong state;
    
    public AtomicRandom(long seed) {
        state = new AtomicLong(seed);
    }

    @Override
    public int nextInt() {
    	// get the current seed value
    	long next = 0;
    	while (true) {
        	long orig = state.get();
        	// using recurrence equation to generate next seed
        	next = (a * orig + c) & (~0L >>> 16);
        	
        	if (state.compareAndSet(orig, next)) {
        		break;
        	}
    	}
    	
    	
    	return (int) (next >>> 16);
    }
}
