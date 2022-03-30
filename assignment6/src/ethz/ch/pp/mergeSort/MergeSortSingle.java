package ethz.ch.pp.mergeSort;

import ethz.ch.pp.util.ArrayUtils;

public class MergeSortSingle {

	private MergeSortSingle() {}

	public static int[] sort(int[] input) {
		return new MergeSortSingle().sort(input, 0, input.length);
	}

	public int[] sort(int[] input, int start, int length) {

		int[] result = new int[length];

		if (length == 1) {

			/*
			 * Simple case: input is only 1 number
			 */
			result[0] = input[start];

		} else if (length == 2) {

			/*
			 * Base case: input is two numbers. Return them in the correct 
			 * order. 
			 */
			if (input[start] > input[start + 1]) {
				result[0] = input[start + 1];
				result[1] = input[start];
			} else {
				result[0] = input[start];
				result[1] = input[start + 1];
			}

		} else if (length > 2) {

			/*
			 * Generic case: input is more than two numbers.
			 */
			int halfSize = (length) / 2;

			/*
			 * Sort first half
			 */
			int[] firstHalf = 
					this.sort(input, start, halfSize);

			/*
			 * Sort second half 
			 */
			int[] secondHalf = 
					this.sort(input, start + halfSize, length - halfSize);

			/*
			 * Merge the results
			 */
			ArrayUtils.merge(firstHalf, secondHalf, result);
		}

		return result;

	}
}
