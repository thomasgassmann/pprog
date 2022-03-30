package ethz.ch.pp.util;

import java.util.Random;

public final class RandomGenerator {
	
	private final Random rnd = new Random(0);
	
    /**
     * Generate array of random integers of given size.
     *
     * @param size  Length of the array to generate
     */
	public final int[] randomArray(int size) {
		final int[] result = new int[size];
		for (int i = 0; i < result.length; i++) {
			result[i] = this.rnd.nextInt(100);
		}
		return result;
	}

}
