package ethz.ch.pp.searchAndCount;

import org.junit.Assert;
import org.junit.Test;

import ethz.ch.pp.searchAndCount.SearchAndCountSeq;
import ethz.ch.pp.util.RandomGenerator;
import ethz.ch.pp.util.Workload;

public class SearchAndCountTest {

	@Test
	public void test100HEAVYrandom() {
		RandomGenerator dg = new RandomGenerator();
		int[] testNumbers = dg.randomArray(100);
		int resSingle = SearchAndCountSeq.countNoAppearances(testNumbers, Workload.Type.HEAVY);
		int resMultiple = SearchAndCountThreadDivideAndConquer.countNoAppearances(
				testNumbers, Workload.Type.HEAVY, 2, 2);
		
		Assert.assertEquals(resSingle, resMultiple);
	}
	
	@Test
	public void test100HEAVYrandomSeq() {
		RandomGenerator dg = new RandomGenerator();
		int[] testNumbers = dg.randomArray(100);
		int resSingle = SearchAndCountSeq.countNoAppearances(testNumbers, Workload.Type.HEAVY);
		int resMultiple = SearchAndCountSeqDivideAndConquer.countNoAppearances(
				testNumbers,Workload.Type.HEAVY);
		
		Assert.assertEquals(resSingle, resMultiple);
	}
	
	@Test
	public void test100Lightrandom() {
		RandomGenerator dg = new RandomGenerator();
		int[] testNumbers = dg.randomArray(100);
		int resSingle = SearchAndCountSeq.countNoAppearances(testNumbers, Workload.Type.LIGHT);
		int resMultiple = SearchAndCountThreadDivideAndConquer.countNoAppearances(
				testNumbers, Workload.Type.LIGHT, 2, 2);

		Assert.assertEquals(resSingle, resMultiple);
	}

	@Test
	public void test1000000HEAVYrandom() {
		RandomGenerator dg = new RandomGenerator();
		int[] testNumbers = dg.randomArray(100000);
		int resSingle = SearchAndCountSeq.countNoAppearances(testNumbers, Workload.Type.HEAVY);
		int resMultiple = SearchAndCountThreadDivideAndConquer.countNoAppearances(
				testNumbers, Workload.Type.HEAVY, 10000, 4);

		Assert.assertEquals(resSingle, resMultiple);
	}

	@Test
	public void test1000000Lightrandom() {
		RandomGenerator dg = new RandomGenerator();
		int[] testNumbers = dg.randomArray(100000);
		int resSingle = SearchAndCountSeq.countNoAppearances(testNumbers, Workload.Type.LIGHT);
		int resMultiple = SearchAndCountThreadDivideAndConquer.countNoAppearances(
				testNumbers, Workload.Type.LIGHT, 100, 2);
		
		Assert.assertEquals(resSingle, resMultiple);
	}

}
