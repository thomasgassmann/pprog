package ethz.ch.pp.searchAndCount;

import ethz.ch.pp.util.Workload;

public class SearchAndCountSeqDivideAndConquer {
	
	public static int countNoAppearances(int[] input, Workload.Type wt) {
		return count(input, wt, 0, input.length - 1);
	}
	
	public static int count(int[] input, Workload.Type type, int from, int to) {
		if (to - from <= 2) {
			int c = 0;
			for (int i = from; i <= to; i++) {
				if (Workload.doWork(input[i], type)) {
					c++;
				}
			}
			
			return c;
		}

		int s = (to - from) / 2;
		int l = count(input, type, from, from + s);
		int r = count(input, type, from + s + 1, to);
		
		return l + r;
	}

}