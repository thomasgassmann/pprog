package assignment12;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;

public class SensorsTest {

    class ThreadTest {
        private Thread thread;
        private Exception exception;

        public ThreadTest(final Runnable runnable) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.run();
                    } catch (Exception e) {
                        exception = e;
                    }
                }
            });

        }

        public void start() {
            thread.start();
        }

        public void interrupt() {
            thread.interrupt();
        }

        public void join() throws InterruptedException {
            thread.join();
        }

        public void joinWithException() throws Exception {
            thread.join();
            if (exceptionOccured()) {
                throw exception;
            }
        }

        public void assertNoException() throws InterruptedException {
            assertFalse(exceptionOccured());
        }

        public Thread getThread() {
            return thread;
        }

        public boolean exceptionOccured() {
            return exception != null;
        }

        public Exception getException() {
            return exception;
        }
    }

	@Test(timeout=8000)
	public void testLockedSensors() throws InterruptedException {
        long seed = 11L;
        int num_of_sensors = 10;
        int num_of_monitors = 50;
        int num_of_readings = 10000;
        testSensors(new LockedSensors(), num_of_sensors, num_of_monitors, num_of_readings, seed);
	}

    @Test(timeout=8000)
    public void testLockFreeSensors() throws InterruptedException {
        long seed = 11L;
        int num_of_sensors = 10;
        int num_of_monitors = 50;
        int num_of_readings = 10000;
        testSensors(new LockFreeSensors(), num_of_sensors, num_of_monitors, num_of_readings, seed);
    }

    public void testSensors(Sensors sensors, int num_of_sensors, int num_of_monitors,
                                       int num_of_readings, long seed) throws InterruptedException {
        Random random = new Random();
        random.setSeed(seed);

        ThreadTest[] sensorTasks = new ThreadTest[num_of_sensors];
        ThreadTest[] monitorTasks = new ThreadTest[num_of_monitors];

        for (int i = 0; i < sensorTasks.length; ++i) {
            long local_seed = random.nextLong();
            sensorTasks[i] = new ThreadTest(Main.createSensorRunnable(sensors, num_of_readings, local_seed));
            sensorTasks[i].start();
        }
        for (int i = 0; i < monitorTasks.length; ++i) {
            monitorTasks[i] = new ThreadTest(Main.createMonitorThread(sensors));
            monitorTasks[i].start();
        }

        for (ThreadTest sensorTask : sensorTasks) {
            sensorTask.join();
            sensorTask.assertNoException();
        }

        for (ThreadTest monitorTask : monitorTasks) {
            monitorTask.interrupt();
            monitorTask.join();
            monitorTask.assertNoException();
        }
    }

}
