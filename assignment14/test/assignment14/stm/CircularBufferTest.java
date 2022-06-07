package assignment14.stm;

import assignment14.util.BlockingCall;
import org.junit.Test;

import static assignment14.util.BlockingCall.Assert.assertBlocks;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * It is surprisingly difficult to write meaningful tests which stress the implementation.
 * We do not have fine-grained control over the threads to manipulate their interleaving.
 * Further, it would be useful to verify that Refs are not accessed from outside an atomic
 * block. For this reason, we just do single-threaded tests as a basic sanity check.
 *
 * (If you have any good ideas of how to do multi-threaded testing please let us know!)
 */
public class CircularBufferTest {
    @Test
    public void testPutThenTake() {
        CircularBuffer<String> buffer = new CircularBufferSTM<String>(1);
        assertTrue(buffer.isEmpty());
        buffer.put("foo");
        assertTrue(buffer.isFull());
        assertEquals("foo", buffer.take());
        assertTrue(buffer.isEmpty());
    }

    @Test
    public void testTakeAndPutActIndependently() {
        CircularBuffer<Integer> buffer = new CircularBufferSTM<Integer>(2);
        buffer.put(1);
        buffer.put(2);
        assertEquals(1, (int) buffer.take());
        buffer.put(3);
        assertTrue(buffer.isFull());

        // Validate that both 2 and 3 are present and appear in the correct order.
        //
        // If the queue is implemented with just one counter (rather than a separate put
        // counter + take counter) then these operations might interact.  For example,
        // adding object 3 could overwrite object 2.
        assertEquals(2, (int) buffer.take());
        assertEquals(3, (int) buffer.take());
        assertTrue(buffer.isEmpty());
    }

    @Test
    public void testTakeInSameOrderAsPut() {
        int N = 100;
        CircularBuffer<Integer> buffer = new CircularBufferSTM<Integer>(N);
        for (int i = 0; i < N; i++) {
            buffer.put(i);
        }
        assertTrue(buffer.isFull());
        for (int i = 0; i < N; i++) {
            assertEquals(i, (int) buffer.take());
        }
        assertTrue(buffer.isEmpty());
    }

    @Test(timeout = 1000L)
    public void testTakeEmptyBlocks() {
        assertBlocks(new BlockingCall() {
            @Override
            public void call() throws InterruptedException {
                CircularBuffer<Integer> buffer = new CircularBufferSTM<Integer>(1);
                assertTrue(buffer.isEmpty());
                buffer.take();
            }
        });
    }

    @Test(timeout = 1000L)
    public void testPutFullBlocks() {
        assertBlocks(new BlockingCall() {
            @Override
            public void call() throws InterruptedException {
                CircularBuffer<Integer> buffer = new CircularBufferSTM<Integer>(1);
                buffer.put(123);
                assertTrue(buffer.isFull());
                buffer.put(456);
            }
        });
    }
}
