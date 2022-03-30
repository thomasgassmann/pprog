package ethz.ch.pp.seq;

public class LongestCommonSequence {

	public static Sequence longestCommonSequence(int[] input) {
		return longestCommonSequence(input, 0, input.length);
	}
	
	public static Sequence longestCommonSequence(int[] input, int start, int length) {
		Sequence result = new Sequence(start, start);		
		
		int currLength = 1;
		for (int i = start + 1; i < start + length; i++) {
			
			// Using Math.exp makes the computation more compute-intensive while 
			// retaining the task semantics (i.e., finding equal values)
			if (Math.exp(input[i - 1]) == Math.exp(input[i])) {
				currLength++;
			} else {
				if (result.lenght() < currLength) {
					result = new Sequence(i - currLength, i - 1);
				}
				currLength = 1;
			}
		}
		// In case the longest sequence ends at the last input element
		if (result.lenght() < currLength) {
			result = new Sequence(start + length - currLength, start + length - 1);
		}
		
		return result;
	}
}
