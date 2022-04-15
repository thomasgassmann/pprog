package assignment8.random;

import org.junit.Assert;

public class Main {

    private static final int MAX_THREADS = 16;

    public static void main(String[] args) throws InterruptedException {
        RandomInterface lockedRng = new LockedRandom(123L);
        RandomInterface atomicRng = new AtomicRandom(123L);
        for (int i = 2; i <= MAX_THREADS; i*=2) {
            long lockAnswer = doSum(lockedRng, i);
            long atomicAnswer = doSum(atomicRng, i);
            Assert.assertEquals(atomicAnswer, lockAnswer);
        }
    }

    public static long doSum(RandomInterface rnd, int nThreads) throws InterruptedException {
        // set up
        RandomWorker[] workers = new RandomWorker[nThreads];
        Thread[] threads = new Thread[nThreads];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new RandomWorker(rnd);
            threads[i] = new Thread(workers[i]);
        }
        // do the computation
        long begin = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("[%s] Took %d ms", rnd.getClass().getSimpleName(), (end - begin)));
        // get result of the computation
        long sum = 0L;
        for (RandomWorker w : workers) {
            sum += w.getSum();
        }
        System.out.println(String.format("[%s] Got %d", rnd.getClass().getSimpleName(), sum));
        return sum;
    }
}
