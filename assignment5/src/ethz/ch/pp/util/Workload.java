package ethz.ch.pp.util;

public class Workload {

	public enum Type {
		HEAVY, LIGHT,
	}

	public static boolean doWork(int n, Type w) {
		return (w == Type.HEAVY) ? isPrime(n) : isNonZero(n);
	}

	public static boolean isPrime(int n) {
		int sqrt = (int) Math.sqrt(n);	    
		for (int i = 2; i <= sqrt; i++) {
			if (n % i == 0)
				return false;
		}

		return true;
	}

	public static boolean isNonZero(int n) {
		return (n != 0);
	}
}
