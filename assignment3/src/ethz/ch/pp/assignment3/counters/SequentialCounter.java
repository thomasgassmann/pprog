package ethz.ch.pp.assignment3.counters;

//TODO: implement
public class SequentialCounter implements Counter {
	private int _i = 0;
	
	@Override
	public void increment() {
		_i++;
	}

	@Override
	public int value() {
		return _i;		
	}
}