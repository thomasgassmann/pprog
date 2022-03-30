package ethz.ch.pp.seq;

import ethz.ch.pp.util.RandomGenerator;

public class Main {

	public static void main(String[] args) {
	    RandomGenerator dg = new RandomGenerator();
	    int[] input = dg.randomArray(1024*1024*5);
	    
		System.out.println("warm-up cache for more precise measurements");
		taskBaseline(input);
	    
		taskBaseline(input);
		for (int numThreads = 2; numThreads < 8; numThreads++) {
			taskParallel(input, numThreads);
		}
	}
	
	public static void taskBaseline(int[] input){
		long t0 = System.currentTimeMillis();
		Sequence result = null;
		for (int i = 0; i < 5; i++) {
			result = LongestCommonSequence.longestCommonSequence(input);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ") LongestCommonSequence takes " + ((t1 - t0)/5) + " msec");
		System.out.println("\tresult: " + result);
		
	}
	
	public static void taskParallel(int[] input, int numThreads){
		long t0 = System.currentTimeMillis();
		Sequence result = null;
		for (int i = 0; i < 5; i++) {
			result = LongestCommonSequenceMulti.longestCommonSequence(input, numThreads);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ") LongestCommonSequenceMulti with " + numThreads + " threads takes " + ((t1 - t0)/5) + " msec");
		System.out.println("\tresult: " + result);
	}

}

