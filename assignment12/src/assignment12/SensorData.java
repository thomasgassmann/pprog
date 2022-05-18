package assignment12;

// think how this class can become useful
class SensorData {
    final double[] data;
    final long timestamp;

    SensorData(long t, double[] d) {
        timestamp = t;
        data = new double[d.length];
        for (int i = 0; i < d.length; ++i) {
            data[i] = d[i];
        }
    }

    double[] getValues() {
        return data;
    }

    long getTimestamp() {
        return timestamp;
    }
}
