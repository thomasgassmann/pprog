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
    CircularBufferSTM(int capacity) {
        // TODO: implement: create TArray of size capacity.
    }

    public void put(final E item) {
        // TODO: implement: add item to the queue, otherwise block until a slot is free.
    }

    public E take() {
        // TODO: implement: return the oldest item or block until an item is present.
        return null;
    }

    public boolean isEmpty() {
        // TODO: implement: helper method which can be used above.
        return false;
    }

    public boolean isFull() {
        // TODO: implement: helper method which can be used above.
        return false;
    }
}
