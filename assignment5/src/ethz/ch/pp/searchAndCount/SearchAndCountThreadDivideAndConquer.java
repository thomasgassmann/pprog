package ethz.ch.pp.searchAndCount;

import java.util.concurrent.atomic.AtomicInteger;

import ethz.ch.pp.util.Workload;

public class SearchAndCountThreadDivideAndConquer {
	
	private final int[] _input;
	private final int _cutOff;
	private final Workload.Type _type;
	private final int _maxThreads;
	// count main thread, otherwise 0
	private final AtomicInteger _currentThreads = new AtomicInteger(1);
	
	public SearchAndCountThreadDivideAndConquer(int[] input, int cutOff, Workload.Type type, int maxThreads) {
		_cutOff = cutOff;
		_input = input;
		_type = type;
		_maxThreads = maxThreads;
	}
	
	public static int countNoAppearances(int[] input, Workload.Type wt, int cutOff, int numThreads) {
		var q = new SearchAndCountThreadDivideAndConquer(input, cutOff, wt, numThreads);
		return q.count(0, input.length - 1);
	}
	
	private int count(int from, int to) {
		if (to - from <= _cutOff) {
			return calc(_input, _type, from, to);
		}
		
		int s = (to - from) / 2;
		int l = 0;
		int r = 0;
		Fut lfut = null;
		Fut rfut = null;
		if (_currentThreads.get() < _maxThreads) {
			lfut = calcSpawn(from, from + s);
		} else {
			l = calc(_input, _type, from, from + s);
		}
		
		if (_currentThreads.get() < _maxThreads) {
			rfut = calcSpawn(from + s + 1, to);
		} else {
			r = calc(_input, _type, from + s + 1, to);
		}

		try {
			if (lfut != null) {
				lfut.t.join();
				l = lfut.value;
			}
			if (rfut != null) {
				rfut.t.join();
				r = rfut.value;
			}
		} catch (InterruptedException e) {
		}
		
		return l + r;
	}
	
	private static class Fut {
		int value;
		Thread t;
	}
	
	private Fut calcSpawn(final int from, final int to) {
		_currentThreads.incrementAndGet();
		var f = new Fut();
		final int[] values = _input;
		f.t = new Thread() {
			@Override
			public void run() {
				f.value = calc(values, _type, from, to);
				_currentThreads.decrementAndGet();
			}
		};
		f.t.start();
		return f;
	}
	
	private static int calc(int[] input, Workload.Type type, int l, int r) {
		int c = 0;
		for (int i = l; i <= r; i++) {
			if (Workload.doWork(input[i], type)) {
				c++;
			}
		}
		
		return c;
	}

}