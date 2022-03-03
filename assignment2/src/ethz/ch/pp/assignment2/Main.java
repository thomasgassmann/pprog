package ethz.ch.pp.assignment2;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class Main {

	public static double measure(Function<Integer, int[]> fn) {
		long start = System.nanoTime();
		fn.apply(0);
		
		return (System.nanoTime() - start) / 1.0e6;
	}
	
	public static void main(String[] args) {
		
		PartitionData(1000, 128);
		
		// TODOadjust appropriately for the required experiments
		taskA();
		
		final int[] input1 = generateRandomInput(1000);
		final int[] input2 = generateRandomInput(10000);
		final int[] input3 = generateRandomInput(100000);
		final int[] input4 = generateRandomInput(1000000);
		
 		for (int i = 1; i <= 128; i *= 2) {
 			final int c = i;
 			System.out.println("Using " + i + " threads");
 			System.out.println("1000: Took " + measure(a -> taskE(input1, c)));
 			System.out.println("10000: Took " + measure(a -> taskE(input2, c)));
 			System.out.println("100000: Took " + measure(a -> taskE(input3, c)));
 			System.out.println("1000000: Took " + measure(a -> taskE(input4, c)));
 		}
		

		// Sequential version
		computePrimeFactorsOnMainThread(input1);
		computePrimeFactorsOnMainThread(input2);
		computePrimeFactorsOnMainThread(input3);
		computePrimeFactorsOnMainThread(input4);
		taskB(input1);
		taskB(input2);
		taskB(input3);
		taskB(input4);

		long threadOverhead = taskC();
		System.out.format("Thread overhead on current system is: %d nano-seconds\n", threadOverhead);
		
		taskCVariance();
		
		// Parallel version
		taskE(input1, 4);
		taskE(input2, 4);
		taskE(input3, 4);
		taskE(input4, 4);	
	}
	
	public static void taskCVariance() {
		int numIterations = 1000;
		long[] ns = new long[numIterations];
		for (int i = 0; i < 1000; i++) {
			ns[i] = taskC();
		}
		
		System.out.println("Took " + average(ns) + " on average");
		System.out.println("Variance " + variance(ns));
	}
	
	private static double variance(long[] values) {
		var mean = average(values);
		double s = 0;
		for (int i = 0; i < values.length; i++) {
			s += Math.pow(values[i] - mean, 2);
		}
		
		return s / values.length;
	}
	
	private static double average(long[] values) {
		long sum = 0;
		for (int i = 0; i < values.length; i++)
			sum += values[i];
		return sum / values.length;
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
	
	/**
	 * we split them evenly, but if the input was sorted, this 
	 * might lead to bad results, since the upper values take longer 
	 * to process
	 * we could avoid this if we split them by mod numPartitions
	 * but it's fine to split them evenly in this case
	 * @param length
	 * @param numPartitions
	 * @return
	 */
	public static ArraySplit[] PartitionData(int length, int numPartitions) {		
		ArraySplit[] res = new ArraySplit[numPartitions];
		
		int current = 0;
		int size = length / numPartitions;
		for (int i = 0; i < numPartitions - 1; i++) {
			res[i] = new ArraySplit(current, size);
			current += size;
		}
		
		res[res.length - 1] = new ArraySplit(current, length - current);
		return res;
	}
	
	public static void computePrimeFactorsOnMainThread(final int[] values) {
		long start = System.nanoTime();
		
		int[] res = computePrimeFactors(values);
		
		var elapsed = (System.nanoTime() - start) / 1.0e6;
		System.out.println("Sequential Took: " + elapsed + "ms");
	}
	
	public static void taskA() {
		System.out.println("Main Thread Name: " + Thread.currentThread().getName());
		var t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello Thread!");
				System.out.println("Child Thread Name: " + Thread.currentThread().getName());
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// nothing to be expected, java has forced my hand
			e.printStackTrace();
		}
	}
	
	public static int[] taskB(final int[] values) {
		long start = System.nanoTime();
		var r = new Runnable() {
			private int[] res;
			
			@Override
			public void run() {
				this.res = computePrimeFactors(values);
			}
		};
		var t = new Thread(r);
		t.start();
		try {
			t.join();
			var elapsed = (System.nanoTime() - start) / 1.0e6;
			System.out.println("Parallel Took: " + elapsed + "ms");
			return r.res;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Returns overhead of creating thread in nano-seconds
	public static long taskC() {		
		long start = System.nanoTime();
		
		var t = new Thread() {
			@Override
			public void run() {
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return System.nanoTime() - start;
	}
	
	public static int[] taskE(final int[] values, final int numThreads) {
		var partitions = PartitionData(values.length, numThreads);
		final int[] res = new int[values.length];
		
		var threads = new ArrayList<Thread>();
		for (int i = 0; i < partitions.length; i++) {
			final ArraySplit part = partitions[i];
			var t = new Thread() {
				@Override
				public void run() {
					for (int j = part.startIndex; j < part.startIndex + part.length; j++) {
						res[j] = numPrimeFactors(values[j]);
					}
				}
			};
			t.start();
			threads.add(t);
		}
		
		for (var thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}


}
