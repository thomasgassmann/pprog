package ethz.ch.pp.assignment10;

public interface Lock {
	void acquire(int me);
	void release(int me);
}
