package ethz.ch.pp.searchAndCount;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import ethz.ch.pp.util.Workload;

class CountTask implements Callable<Integer> {
	private int[] _input;
	private int _from;
	private int _to;
	private Workload.Type _type;
	
	public CountTask(int[] input, int from, int to, Workload.Type type) {
		_from = from;
		_input = input;
		_type = type;
		_to = to;
	}

	@Override
	public Integer call() throws Exception {
		int c = 0;
		for (int i = _from; i <= _to; i++) {
			if (Workload.doWork(_input[i], _type)) {
				c++;
			}
		}
		
		return c;
	}
}

public class SearchAndCountExecutorService {
	private final int[] _input;
	private final int _cutOff;
	private final Workload.Type _type;
	private ExecutorService _ex;
	private int _maxThreads;
	
	public SearchAndCountExecutorService(int[] input, int cutOff, Workload.Type type, int maxThreads) {
		_cutOff = cutOff;
		_input = input;
		_type = type;
		_ex = Executors.newFixedThreadPool(maxThreads);
		_maxThreads = maxThreads;
	}
	
	public static int countNoAppearances(int[] input, Workload.Type wt, int cutOff, int numThreads) {
		var q = new SearchAndCountExecutorService(input, cutOff, wt, numThreads);
		try {
			return q.count();
		} catch (Exception ex) {
			// this is ugly
			return 0;
		}
	}
	
	private int count() throws Exception {
		if (_input.length <= _cutOff) {
			return new CountTask(_input, 0, _input.length, _type).call();
		}

		var size = _input.length / _maxThreads;
		int current = 0;
		ArrayList<Future<Integer>> futures = new ArrayList<>();
		for (int i = 0; i < _maxThreads; i++) {
			int l = i == _maxThreads - 1 ? _input.length - i * size : size;
			var res = _ex.submit(new CountTask(_input, current, current + l, _type));
			current += l + 1;
			futures.add(res);
		}
		
		_ex.awaitTermination(10, TimeUnit.SECONDS);
		int t = 0;
		for (var future : futures) {
			t += future.get();
		}
		
		return t;
	}

}