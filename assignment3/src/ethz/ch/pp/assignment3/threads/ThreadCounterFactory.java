package ethz.ch.pp.assignment3.threads;

import ethz.ch.pp.assignment3.counters.Counter;

public class ThreadCounterFactory {
	
	public enum ThreadCounterType {
	    NATIVE,
	    FAIR
	}
	
	public static ThreadCounter make(ThreadCounterType type, Counter counter, int id, int numThreads, int numIterations) {
		switch (type) {
		case NATIVE:
			return new NativeThreadCounter(counter, id, numThreads, numIterations);
		case FAIR:
			return new FairThreadCounter(counter, id, numThreads, numIterations);
		default:
			throw new UnsupportedOperationException();
		}
	}
}
