package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.RandomGenerator;
import ethz.ch.pp.util.Workload;

public class Main {

	public static void main(String[] args) {
		RandomGenerator dg = new RandomGenerator();
		int[] input = dg.randomArray(1024*1024);
		
		sequential(input, Workload.Type.HEAVY);
		
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
		//TODO: implement
	}
	
	public static void taskB(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskB");
		//TODO: implement
	}
	
	public static void taskC(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskC");
		//TODO: implement
	}
	
	public static void taskD(int[] input, Workload.Type wt){
		System.out.println("=====================================");
		System.out.println("TaskD");
		//TODO: implement
	}

}
