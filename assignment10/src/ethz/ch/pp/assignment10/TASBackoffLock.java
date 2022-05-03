package ethz.ch.pp.assignment10;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class TASBackoffLock implements Lock {
	  private AtomicBoolean _state = new AtomicBoolean(false);

	    private final int MIN = 1;
	    private final int MAX = 1000;

	    @Override
	    public void acquire(int thread) {
	        Backoff backoff = null;
	        while (true) {
	            while (_state.get());
	            if (!_state.getAndSet(true)) {
	                return;
	            } else {
	                try {
	                    if (backoff == null)
	                        backoff = new Backoff(MIN, MAX);
	                    backoff.backoff();
	                } catch (InterruptedException e) {
	                }
	            }
	        }
	    }

	    @Override
	    public void release(int thread) {
	        _state.set(false);
	    }

	    private static class Backoff {
	        private Random _random = new Random();

	        private final int _min;
	        private final int _max;

	        private int _limit;

	        public Backoff(int min, int max) {
	            _min = min;
	            _max = max;
	            _limit = min;
	        }

	        public void backoff() throws InterruptedException {
	            int delay = _random.nextInt(_limit);
	            if (_limit < _max)
	                _limit *= 2;

	            Thread.sleep(delay);
	        }
	    }
}
