package ethz.ch.pp.mergeSort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import ethz.ch.pp.util.ArrayUtils;

public class MergeSortMulti extends RecursiveTask<int[]> {

	private static final long serialVersionUID = 1531647254971804196L;
	
	private int[] _values;
	private int _start;
	private int _length;
	
	public MergeSortMulti(int[] values, int start, int length) {
		_values = values;
		_start = start;
		_length = length;
	}

	public static int[] sort(int[] input, int numThreads) {
		ForkJoinPool f = new ForkJoinPool();
		int[] res = f.invoke(new MergeSortMulti(input, 0, input.length));
		return res;
	}

	@Override
	protected int[] compute() {
		int[] result = new int[_length];

		if (_length == 1) {
			result[0] = _values[_start];
		} else if (_length == 2) {
			if (_values[_start] > _values[_start + 1]) {
				result[0] = _values[_start + 1];
				result[1] = _values[_start];
			} else {
				result[0] = _values[_start];
				result[1] = _values[_start + 1];
			}

		} else if (_length > 2) {

			int halfSize = (_length) / 2;

			MergeSortMulti left = new MergeSortMulti(_values, _start, halfSize);
			MergeSortMulti right = new MergeSortMulti(_values, _start + halfSize, _length - halfSize);
			left.fork();
			right.fork();
			
			int[] firstHalf = left.join();
			int[] secondHalf = right.join();
			
			ArrayUtils.merge(firstHalf, secondHalf, result);
		}

		return result;
	}

}
