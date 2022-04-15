package assignment8.random;

public class RandomWorker implements Runnable {

    private static final int N_RANDOMS = 1000000;
    private RandomInterface rng;
    private long sum;

    RandomWorker(RandomInterface rng) {
        this.rng = rng;
        this.sum = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < N_RANDOMS; i++) {
            sum += rng.nextInt();
        }
    }

    public long getSum() {
        return sum;
    }
}
