package ethz.ch.pp.assignment10;

import java.util.concurrent.atomic.AtomicBoolean;

public class TASLock implements Lock {

    private AtomicBoolean _state = new AtomicBoolean(false);

    @Override
    public void acquire(int thread) {
        while (_state.getAndSet(true));
    }

    @Override
    public void release(int thread) {
        _state.set(false);
    }
}
