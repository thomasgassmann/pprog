package ethz.ch.pp.assignment3;

import org.junit.Assert;
import org.junit.Test;

import ethz.ch.pp.assignment3.Main;
import ethz.ch.pp.assignment3.counters.AtomicCounter;
import ethz.ch.pp.assignment3.counters.Counter;
import ethz.ch.pp.assignment3.counters.SequentialCounter;
import ethz.ch.pp.assignment3.counters.SynchronizedCounter;
import ethz.ch.pp.assignment3.threads.ThreadCounterFactory.ThreadCounterType;


public class MainTest {

	@Test
	public void testSequentialCounter() {
		Counter counter = new SequentialCounter();
		Main.count(counter, 1, ThreadCounterType.NATIVE, 100000);
		Assert.assertEquals(100000, counter.value());
	}
	
	@Test
	public void testAtomicCounter() {
		Counter counter = new AtomicCounter();
		Main.count(counter, 4, ThreadCounterType.NATIVE, 100000);
		Assert.assertEquals(100000 * 4, counter.value());
	}
	
	@Test
	public void testSynchronizedCounter() {
		Counter counter = new SynchronizedCounter();
		Main.count(counter, 4, ThreadCounterType.NATIVE, 100000);
		Assert.assertEquals(100000 * 4, counter.value());
	}
	
}
