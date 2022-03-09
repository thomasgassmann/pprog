package ethz.ch.pp.assignment3.threads;

import ethz.ch.pp.assignment3.counters.Counter;

public abstract class ThreadCounter implements Runnable {
	Counter counter;	
	protected final int id;
	protected final int numThreads;
	protected final int numIterations;
	
	public ThreadCounter(Counter counter, int id, int numThreads, int numIterations) {
		this.counter = counter;
		this.id = id;
		this.numThreads = numThreads;
		this.numIterations = numIterations;
	}

}
