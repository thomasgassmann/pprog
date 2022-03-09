package ethz.ch.pp.assignment3.threads;

import ethz.ch.pp.assignment3.counters.Counter;

public class FairThreadCounter extends ThreadCounter {
		
	private Turn _turn;
	
	public FairThreadCounter(Counter counter, int id, int numThreads, int numIterations, Turn turn) {
		super(counter, id, numThreads, numIterations);
		_turn = turn;
	}

	@Override
	public void run() {
		for (int i = 0; i < numIterations; i++) {
			synchronized (counter) {
				while (!_turn.isTurn(id)) {
					try {
						counter.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				counter.increment();
				_turn.next();
				
				counter.notifyAll();
			}
		}
	}

}
