package assignment14.stm;

/**
 * {@code CircularBuffer} models a generic bounded queue. This is a highly simplified version of a
 * collection like {@code ArrayBlockingQueue} in the standard Java concurrency library.
 *
 * @param <E> type parameter of the elements in the queue.
 */
public interface CircularBuffer<E> {
    /* Adds item to the queue. If full, this blocks until a slot becomes free. */
    public void put(E item);

    /* Retrieves the oldest item from the queue. If empty, this blocks until an item is present. */
    public E take();

    // @VisibleForTesting
    public boolean isEmpty();
    public boolean isFull();
}
