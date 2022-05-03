package ethz.ch.pp.assignment10;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class BakeryLock implements Lock {
    private AtomicIntegerArray _flag;
    private AtomicIntegerArray _label;
    private final int _n;

    public BakeryLock(int n) {
        this._n = n;
        _flag = new AtomicIntegerArray(n);
        _label = new AtomicIntegerArray(n);
    }

    @Override
    public void acquire(int me) {
        _flag.set(me, 1);
        _label.set(me, maxLabel() + 1);
        while (conflict(me));
    }

    @Override
    public void release(int me) {
        _flag.set(me, 0);
    }

    private boolean conflict(int me) {
        for (int i = 0; i < _n; i++) {
            if (i != me && _flag.get(i) != 0) {
                int diff = _label.get(i) - _label.get(me);
                if (diff < 0 || diff == 0 && i < me) {
                    // give way if other number is smaller than ours
                    // or if it is the same and their index is smaller
                    return true;
                }
            }
        }

        return false;
    }

    private int maxLabel() {
        int max = _label.get(0);
        for (int i = 1; i < _n;i++) {
            max = Math.max(max, _label.get(i));
        }

        return max;
    }
}
