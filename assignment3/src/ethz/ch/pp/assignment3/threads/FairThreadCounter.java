package ethz.ch.pp.assignment3.threads;

import ethz.ch.pp.assignment3.counters.Counter;

public class FairThreadCounter extends ThreadCounter {
		
	public FairThreadCounter(Counter counter, int id, int numThreads, int numIterations) {
		super(counter, id, numThreads, numIterations);
	}

	@Override
	public void run() {
		//TODO: implement
		throw new UnsupportedOperationException();
	}

}
