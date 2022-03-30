package ethz.ch.pp.seq;

import java.util.concurrent.RecursiveTask;

public class LongestCommonSequenceMulti extends RecursiveTask<Sequence> {
	
	private static final long serialVersionUID = 4179716026313995745L;
		
	public static Sequence longestCommonSequence(int[] input, int numThreads) {
		// TODO Implement
		return new Sequence(0, 0);
	}

	@Override
	protected Sequence compute() {
		// TODO Implement		
		return new Sequence(0, 0);		
	}
}
