package assignment12;

import java.util.Random;

class Main {

    public static Runnable createSensorRunnable(final Sensors sensors, final int num_of_readings, final long seed) {
        return new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                random.setSeed(seed);
                double[] val = new double[2];
                for (int i = 0; i < num_of_readings; ++i) {
                    // Generate some random reading and store a consistency value for checking later on.
                	long time = System.nanoTime();
                    val[0] = random.nextDouble();
                    val[1] = - val[0] + time;
                    // Update sensor data with values and timestamp
                    sensors.update(time, val);
                }
            }
        };
    }

    public static Thread createSensorThread(Sensors sensors, int num_of_readings, long seed) {
        return new Thread(createSensorRunnable(sensors, num_of_readings, seed));
    }

    public static Runnable createMonitorRunnable(final Sensors sensors) {
        return new Runnable() {
            @Override
            public void run() {
                // Poll for first sensor data
            	long last;
                // Poll for the first valid timestamp
                double val[] = new double[2];
                do {
                    last = sensors.get(val);
                } while (last == 0);
                // We run the loop until the thread is signalled to stop
                while (!Thread.currentThread().isInterrupted()) {
                    long time = sensors.get(val);
                    if (time < last) {
                        // Report error if retrieved timestamp is older than a previously seen one
                        System.out.println(time + " < " + last);
                        throw new RuntimeException("Sensors timestamp was older than previous data timestamp");
                    }
                    if (val[1] != -val[0] + time) {
                        // Report error if data is inconsistent
                        System.out.println(val[1] + " != " + (-val[0]+time));
                        throw new RuntimeException("Sensors data inconsistency");
                    }
                    // Check if timestamps are valid
                    assert(time >= last);
                    // Check if values are consistent
                    assert(val[1] == -val[0] + time);
                    last = time;
                }
            }
        };
    }

    public static Thread createMonitorThread(Sensors sensors) {
        return new Thread(createMonitorRunnable(sensors));
    }

    public static void measureTimings(Sensors sensors, int num_of_sensors, int num_of_monitors,
                                      int num_of_readings, long seed) throws InterruptedException {
        Random random = new Random();
        random.setSeed(seed);

        Thread[] sensorTasks = new Thread[num_of_sensors];
        Thread[] monitorTasks = new Thread[num_of_monitors];

        // Record start time for timing measurement
        long time_start = System.nanoTime();

        // Generate and start all sensor threads
        for (int i = 0; i < sensorTasks.length; ++i) {
            // Generate local seed for sensor thread for reproducability (ThreadLocalRandom is not seedable).
            long local_seed = random.nextLong();
            sensorTasks[i] = createSensorThread(sensors, num_of_readings, local_seed);
            sensorTasks[i].start();
        }
        // Generate and start all monitor threads
        for (int i = 0; i < monitorTasks.length; ++i) {
            monitorTasks[i] = createMonitorThread(sensors);
            monitorTasks[i].start();
        }

        // Wait for all sensor tasks to finish
        for (Thread sensorTask : sensorTasks) {
            sensorTask.join();
        }

        // Record end time for timing measurement
        long time_end = System.nanoTime();

        // Stop all monitor threads and wait for them to finish
        for (Thread monitorTask : monitorTasks) {
            monitorTask.interrupt();
            monitorTask.join();
        }

        // Record end time with monitor cleanup for completion
        long time_end_with_cleanup = System.nanoTime();

        // Output measured timings in seconds
        double time_elapsed = (time_end - time_start) * 1e-9;
        System.out.println("Elapsed time without monitor cleanup: " + time_elapsed + "s");
        double time_elapsed_with_cleanup = (time_end_with_cleanup - time_start) * 1e-9;
        System.out.println("Elapsed time with monitor cleanup: " + time_elapsed_with_cleanup + "s");
    }

    public static void main(String[] args) throws InterruptedException {
        // Define fixed random seed for reproducability
        long seed = 11L;
        // Number of readings from each sensor
        int num_of_readings = 50000;

        // In each of the following blocks we measure timings of different numbers of sensors and monitors
        // with the locked and lock-free sensors implementation.

        int num_of_sensors = 5;
        int num_of_monitors = 20;
        System.out.println("Measuring runtime for locked sensors:");
        measureTimings(new LockedSensors(), num_of_sensors, num_of_monitors, num_of_readings, seed);
        System.out.println("Measuring runtime for lock-free sensors:");
        measureTimings(new LockFreeSensors(), num_of_sensors, num_of_monitors, num_of_readings, seed);

        // This could take a while
        num_of_sensors = 5;
        num_of_monitors = 100;
        System.out.println("Measuring runtime for locked sensors with many monitors:");
        measureTimings(new LockedSensors(), num_of_sensors, num_of_monitors, num_of_readings, seed);
        System.out.println("Measuring runtime for lock-free sensors with many monitors:");
        measureTimings(new LockFreeSensors(), num_of_sensors, num_of_monitors, num_of_readings, seed);

        num_of_sensors = 200;
        num_of_monitors = 20;
        System.out.println("Measuring runtime for locked sensors with many sensors:");
        measureTimings(new LockedSensors(), num_of_sensors, num_of_monitors, num_of_readings, seed);
        System.out.println("Measuring runtime for lock-free sensors with many sensors:");
        measureTimings(new LockFreeSensors(), num_of_sensors, num_of_monitors, num_of_readings, seed);
    }

}
