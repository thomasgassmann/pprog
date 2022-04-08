package ethz.ch.pp.seq;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class LongestCommonSequenceMulti extends RecursiveTask<Sequence> {

    private static final long serialVersionUID = 4179716026313995745L;
    private static final int CUTOFF = 250;

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
        if (_to - _from <= CUTOFF) {
        	Sequence seq = LongestCommonSequence.longestCommonSequence(_input, _from, _to - _from + 1);
        	int rOffset = -1, lOffset = -1;
        	for (int i = _from; i < _input.length && _input[_from] == _input[i]; i++)
        		lOffset++;
        	for (int i = _to; i >= 0 && _input[_to] == _input[i]; i--)
        		rOffset--;
        	return build(seq.startIndex, seq.endIndex, lOffset, rOffset);
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

        int lOffset = leftSeq.leftOffset;
        int rOffset = rightSeq.rightOffset;

        Sequence combined = null;
        if (leftSeq.endIndex + 1 == rightSeq.startIndex && 
        		Math.exp(_input[leftSeq.endIndex]) == Math.exp(_input[rightSeq.startIndex])) {
            combined = new Sequence(leftSeq.startIndex, rightSeq.endIndex);
        } else if (Math.exp(_input[li]) == Math.exp(_input[ri])) {
            combined = new Sequence(li - leftSeq.rightOffset, ri + rightSeq.leftOffset);
        }

        if (combined != null) {
            if (combined.endIndex == _to) {
                rOffset = combined.lenght() - 1;
            }

            if (combined.startIndex == _from) {
                lOffset = combined.lenght() - 1;
            }
        }

        Sequence max = max(leftSeq, combined, rightSeq);
        return build(max.startIndex, max.endIndex, lOffset, rOffset);
    }

    private Sequence max(Sequence a, Sequence b, Sequence c) {
        return max(a, max(b, c));
    }

    private Sequence max(Sequence a, Sequence b) {
        if (a == null) {
            return b;
        }

        if (b == null) {
            return a;
        }

        return b.lenght() > a.lenght() ? b : a;
    }

    private Sequence build(int from, int to, int leftOffset, int rightOffset) {
        Sequence s = new Sequence(from, to);
        s.leftOffset = leftOffset;
        s.rightOffset = rightOffset;
        return s;
    }
}
