package ethz.ch.pp.assignment3.threads;

public class Turn {
	private int _turn;
	private int _count;
	
	public Turn(int count) {
		_count = count;
	}
	
	public synchronized void next() {
		_turn = (_turn + 1) % _count;
	}
	
	public boolean isTurn(int i) {
		return i == _turn;
	}
}
