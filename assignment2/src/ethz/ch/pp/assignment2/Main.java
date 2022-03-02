package ethz.ch.pp.assignment2;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
 		
		// TODOadjust appropriately for the required experiments
		taskA();
		
		int[] input1 = generateRandomInput(1000);
		int[] input2 = generateRandomInput(10000);
		int[] input3 = generateRandomInput(100000);
		int[] input4 = generateRandomInput(1000000);
		
		// Sequential version
		taskB(input1);
		taskB(input2);
		taskB(input3);
		taskB(input4);
		
		// Parallel version
		taskE(input1, 4);
		taskE(input2, 4);
		taskE(input3, 4);
		taskE(input4, 4);
		
		long threadOverhead = taskC();
		System.out.format("Thread overhead on current system is: %d nano-seconds\n", threadOverhead);		
	}
	
	private final static Random rnd = new Random(42);

	public static int[] generateRandomInput() {
		return generateRandomInput(rnd.nextInt(10000) + 1);
	}
	
	public static int[] generateRandomInput(int length) {	
		int[] values = new int[length];		
		for (int i = 0; i < values.length; i++) {
			values[i] = rnd.nextInt(99999) + 1;				
		}		
		return values;
	}
	
	public static int[] computePrimeFactors(int[] values) {		
		int[] factors = new int[values.length];	
		for (int i = 0; i < values.length; i++) {
			factors[i] = numPrimeFactors(values[i]);
		}		
		return factors;
	}
	
	public static int numPrimeFactors(int number) {
		int primeFactors = 0;
		int n = number;		
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
				primeFactors++;
				n /= i;
			}
		}
		return primeFactors;
	}
	
	public static class ArraySplit {
		public final int startIndex;
		public final int length;
		
		ArraySplit(int startIndex, int length) {
			this.startIndex = startIndex;
			this.length = length;
		}
	}
	
	// TaskD
	public static ArraySplit[] PartitionData(int length, int numPartitions) {
		//TODO: implement
		throw new UnsupportedOperationException();
	}
	
	public static void taskA() {
		//TODO: implement
		throw new UnsupportedOperationException();
	}
	
	public static int[] taskB(final int[] values) {
		//TODO: implement
		throw new UnsupportedOperationException();
	}
	
	// Returns overhead of creating thread in nano-seconds
	public static long taskC() {		
		//TODO: implement
		throw new UnsupportedOperationException();
	}
	
	public static int[] taskE(final int[] values, final int numThreads) {
		//TODO: implement
		throw new UnsupportedOperationException();
	}


}
