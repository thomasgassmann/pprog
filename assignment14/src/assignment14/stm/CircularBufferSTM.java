package assignment14.stm;

import scala.concurrent.stm.Ref;
import scala.concurrent.stm.TArray;
import scala.concurrent.stm.japi.STM;

import java.util.concurrent.Callable;

/**
 * This class implements a {@link assignment14.stm.CircularBuffer} using software-transactional memory (more
 * specifically using ScalaSTM [http://nbronson.github.io/scala-stm/]).
 */
public class CircularBufferSTM<E> implements CircularBuffer<E> {
	private Ref.View<Integer> _head = STM.newRef(0);
	private Ref.View<Integer> _tail = STM.newRef(0);
	private Ref.View<Integer> _count = STM.newRef(0);
	private TArray.View<E> _items = null;
	
	CircularBufferSTM(int capacity) {
        // TODO: implement: create TArray of size capacity.
		_items = STM.newTArray(capacity);
    }

    public void put(final E item) {
        // TODO: implement: add item to the queue, otherwise block until a slot is free.
    	STM.atomic(new Runnable() {
    		@Override
    		public void run() {
    			if (isFull()) {
    				STM.retry();
    			}
    			
    			_items.update(_head.get(), item);
    			_head.set(inc(_head.get()));
    			STM.increment(_count, 1);
    		}
    	});
    }

    public E take() {
        // TODO: implement: return the oldest item or block until an item is present.
    	return STM.atomic(new Callable<E>() {
    		@Override
    		public E call() {
    			if (isEmpty()) {
    				STM.retry();
    			}
    			
    			var item = _items.refViews().apply(_tail.get()).get();
    			_items.update(_tail.get(), null);
    			_tail.set(inc(_tail.get()));
    			STM.increment(_count, -1);
    			return item;
    		}
    	});
    }

    public boolean isEmpty() {
        // TODO: implement: helper method which can be used above.
    	return STM.atomic(new Callable<Boolean>() {
    		@Override
    		public Boolean call() {
    			return _count.get() == 0;
    		}
    	});
    }

    public boolean isFull() {
        // TODO: implement: helper method which can be used above.
    	return STM.atomic(new Callable<Boolean>() {
    		@Override
    		public Boolean call() {
    			return _count.get() == _items.length();
    		}
    	});
    }
    
    private int inc(int i) {
    	return (i + 1) % _items.length();
    }
}
