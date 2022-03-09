package ethz.ch.pp.assignment3.counters;

import java.util.concurrent.atomic.AtomicInteger;

//TODO: implement
public class AtomicCounter implements Counter {
	private AtomicInteger _i = new AtomicInteger();
	
	@Override
	public void increment() {
		_i.addAndGet(1);
	}

	@Override
	public int value() {
		return _i.intValue();
	}

}