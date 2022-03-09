package ethz.ch.pp.assignment3;

import java.util.ArrayList;
import java.util.List;

import ethz.ch.pp.assignment3.counters.AtomicCounter;
import ethz.ch.pp.assignment3.counters.Counter;
import ethz.ch.pp.assignment3.counters.SequentialCounter;
import ethz.ch.pp.assignment3.counters.SynchronizedCounter;
import ethz.ch.pp.assignment3.threads.NativeThreadCounter;
import ethz.ch.pp.assignment3.threads.ThreadCounterFactory;
import ethz.ch.pp.assignment3.threads.ThreadCounterFactory.ThreadCounterType;
import ethz.ch.pp.assignment3.threads.Turn;


public class Main {

	public static void count(final Counter counter, int numThreads, ThreadCounterType type, int numInterations) {
		List<Thread> threads = new ArrayList<Thread>();
		var turn = new Turn(numThreads);
		for (int i = 0; i < numThreads; i++) {
			threads.add(new Thread(ThreadCounterFactory.make(type, counter, i, numThreads, numInterations, turn)));
		}

		for (int i = 0; i < numThreads; i++) {
			threads.get(i).start();
		}
		
		for (int i = 0; i < numThreads; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
//		taskASequential();
//		taskAParallel();
//		taskB();
//		taskD();
//		taskE();
//		taskF();
		taskG();
	}
	
	public static void taskASequential(){
		Counter counter = new SequentialCounter();
		count(counter, 1, ThreadCounterType.NATIVE, 100000);
		System.out.println("Counter: " + counter.value());
	}

	public static void taskAParallel(){
		Counter counter = new SequentialCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100000);
		System.out.println("Counter: " + counter.value());
	}
	
	public static void taskB(){
		Counter counter = new SynchronizedCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100000);
		System.out.println("Counter: " + counter.value());
	}
	
	public static void taskD(){
		Counter counter = new SequentialCounter(); //TODO: which type of counter can we use here?
		count(counter, 2, ThreadCounterType.FAIR, 100000);
		System.out.println("Counter: " + counter.value());
	}
	
	public static void taskE(){
		Counter counter = new AtomicCounter();
		count(counter, 4, ThreadCounterType.NATIVE, 100000);
		System.out.println("Counter: " + counter.value());
	}
	
	public static void taskF() {
		var start = System.nanoTime();
		
		Counter synchron = new SynchronizedCounter();
		count(synchron, 4, ThreadCounterType.NATIVE, 100000);
		
		System.out.println("Took synchronized: " + (System.nanoTime() - start) / 1000);
		start = System.nanoTime();
		
		Counter atomic = new AtomicCounter();
		count(atomic, 4, ThreadCounterType.NATIVE, 100000);

		System.out.println("Took atomic: " + (System.nanoTime() - start) / 1000);
	}
	
	public static void taskG() {
		final Counter counter = new AtomicCounter();
		var observer = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {	
					try {
						Thread.sleep(1);
						System.out.println("Current progress: " + counter.value());
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		});
		observer.start();

		var numThreads = 4;
		var threads = new ArrayList<Thread>();
		for (int i = 0; i < numThreads; i++) {
			threads.add(new Thread(new NativeThreadCounter(counter, i, numThreads, 100_000)));
			threads.get(i).start();
		}
		
		for (var thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		observer.interrupt();
		try {
			observer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
