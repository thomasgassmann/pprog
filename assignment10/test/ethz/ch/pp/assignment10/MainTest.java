package ethz.ch.pp.assignment10;

import org.junit.Assert;
import org.junit.Test;

public class MainTest {
	public final int K = 1000;
	public final int THREADS = 10;
	
	@Test
	public void checkBakeryLock() {
		checkLock(new BakeryLock(THREADS));
	}

	@Test
	public void checkTAS() {
		checkLock(new TASLock());
	}

	@Test
	public void checkTASBackoff() {
		checkLock(new TASBackoffLock());
	}
	
	public void checkLock(final Lock lock) {
		Counter counter = new Counter();
		Main.inc(lock, THREADS, K, counter);
		
		Assert.assertEquals(THREADS * K, counter.i);
	}
}
