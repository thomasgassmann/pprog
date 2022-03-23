package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.RandomGenerator;
import ethz.ch.pp.util.Workload;

public class Main {

	public static void main(String[] args) {
		RandomGenerator dg = new RandomGenerator();
		int[] input = dg.randomArray(1024*1024);
		
		sequential(input, Workload.Type.HEAVY);
		
		taskD(input, Workload.Type.HEAVY);
		taskA(input, Workload.Type.HEAVY);
		taskB(input, Workload.Type.HEAVY);		
		taskC(input, Workload.Type.HEAVY);		
	}

	public static void sequential(int[] input, Workload.Type wt){
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			SearchAndCountSeq.countNoAppearances(input, wt);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ",workload=" + wt + ") SearchAndCountSeq takes "
				+ ((t1 - t0)/5) + " msec");
	}
	
	public static void taskA(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskA");
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			SearchAndCountSeqDivideAndConquer.countNoAppearances(input, wt);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ",workload=" + wt + ") SearchAndCountSeqDivideAndConquer takes "
				+ ((t1 - t0)/5) + " msec");
	}
	
	public static void taskB(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskB");
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			SearchAndCountThreadDivideAndConquer.countNoAppearances(input, wt, 1, 10);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ",workload=" + wt + ") SearchAndCountThreadDivideAndConquer takes "
				+ ((t1 - t0)/5) + " msec");
	}
	
	public static void taskC(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskC");
		RandomGenerator dg = new RandomGenerator();
		int[] i = dg.randomArray(100000);
		long bestHeavy = Integer.MAX_VALUE;
		int bestT = 0;
		int bestC = 0;
		for (int t = 2; t <= 8; t += 2) {
			for (int c = 5000; c <= 100000 / t; c += 5000) {
				long t0 = System.nanoTime();
				SearchAndCountThreadDivideAndConquer.countNoAppearances(i, Workload.Type.LIGHT, 1, 10);
				long t1 = System.nanoTime();
				System.out.println("For (inputsize=" + i.length + ",workload=" + Workload.Type.LIGHT + ") threads: " + t +", cutoff: " + c +" takes "
						+ ((t1 - t0)) + " ns");

				t0 = System.nanoTime();
				SearchAndCountThreadDivideAndConquer.countNoAppearances(i, Workload.Type.HEAVY, 1, 10);
				t1 = System.nanoTime();
				System.out.println("For (inputsize=" + i.length + ",workload=" + Workload.Type.HEAVY + ") threads: " + t +", cutoff: " + c +" takes "
						+ ((t1 - t0)) + " ns");
				if (t1 - t0 < bestHeavy) {
					bestHeavy = t1 - t0;
					bestT = t;
					bestC = c;
				}
			}
		}
		
		System.out.println("Best threads: " + bestT + " cutoff: " + bestC);
	}
	
	public static void taskD(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskD");
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			var a = SearchAndCountSeq.countNoAppearances(input, wt);
			var b = SearchAndCountThreadDivideAndConquer.countNoAppearances(input, wt, 1, 10);
			System.out.println("a: "+ a + " b: " + b);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ",workload=" + wt + ") SearchAndCountThreadDivideAndConquer takes "
				+ ((t1 - t0)/5) + " msec");
	}

}
