package ethz.ch.pp.seq;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class LongestCommonSequenceMulti extends RecursiveTask<Sequence> {
	
	private static final long serialVersionUID = 4179716026313995745L;
		
	private int[] _input;
	private int _from;
	private int _to;
	
	public LongestCommonSequenceMulti(int[] input, int from, int to) {
		_input = input;
		_from = from;
		_to = to;
	}
	
	public static Sequence longestCommonSequence(int[] input, int numThreads) {
		ForkJoinPool p = new ForkJoinPool(numThreads);
		return p.invoke(new LongestCommonSequenceMulti(input, 0, input.length - 1));
	}

	@Override
	protected Sequence compute() {
		if (_to - _from <= 1) {
			int l = _to == _from ? 0 : 1;
			return _input[_to] == _input[_from] ? build(_from, _to, l, l) : build(_from, _from, 0, 0);
		}
		
		int size = (_to - _from) / 2;
		int li = _from + size;
		int ri = _from + size + 1;
		var left = new LongestCommonSequenceMulti(_input, _from, li);
		var right = new LongestCommonSequenceMulti(_input, ri, _to);
		
		left.fork();
		right.fork();
		
		Sequence leftSeq = left.join();
		Sequence rightSeq = right.join();
		
		Sequence combined = new Sequence(_from, _from);
		if (leftSeq.endIndex + 1 == rightSeq.startIndex && _input[leftSeq.endIndex] == _input[rightSeq.startIndex]) {
			combined = build(leftSeq.startIndex, rightSeq.endIndex, 0, 0);
		} else if (_input[li] == _input[ri]) {
			combined = build(li - leftSeq.rightOffset, ri + rightSeq.leftOffset, 0, 0);
		}
		
		Sequence max = max(leftSeq, combined, rightSeq);
		
		int lOffset = leftSeq.leftOffset;
		int rOffset = rightSeq.rightOffset;
		if (_from + lOffset + 1 == _to - rOffset && _input[_from + lOffset + 1] == _input[_to - rOffset]) {
			lOffset += 1 + rOffset;
			rOffset += 1 + lOffset;
		}
		
		return build(max.startIndex, max.endIndex, lOffset, rOffset);
	}
	
	private Sequence max(Sequence a, Sequence b, Sequence c) {
		return max(a, max(b, c));
	}
	
	private Sequence max(Sequence a, Sequence b) {
		return b.lenght() > a.lenght() ? b : a;
	}
	
	private Sequence build(int from, int to, int leftOffset, int rightOffset) {
		Sequence s = new Sequence(from, to);
		s.leftOffset = leftOffset;
		s.rightOffset = rightOffset;
		return s;
	}
}
