package ethz.ch.pp.util;

public class ArrayUtils {

	/**
	 * Merges two elements from two ordered array into a single ordered array. 
	 * That is, output = sorted(in1 + in2)
	 *
	 * @param  in1  ordered array
	 * @param  in2  ordered array
	 * @param  output array to write ordered elements from in1 and in2 
	 */
	public static void merge(int[] in1, int[] in2, int[] output) {	

		int in1Position = 0;
		int in2Position = 0;
		int outputPosition = 0;

		while (in1Position < in1.length && in2Position < in2.length) {
			if (in1[in1Position] < in2[in2Position]) {
				output[outputPosition++] = in1[in1Position++];
			} else {
				output[outputPosition++] = in2[in2Position++];
			}
		}

		while (in1Position < in1.length) {
			output[outputPosition++] = in1[in1Position++];
		}

		while (in2Position < in2.length) {
			output[outputPosition++] = in2[in2Position++];
		}
	}
}
