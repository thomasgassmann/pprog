package ethz.ch.pp.mergeSort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import ethz.ch.pp.util.RandomGenerator;

public class MergeSortTest {
  
  @Test
  public void test100random() {
	RandomGenerator dg = new RandomGenerator();    
    int[] inputSeq = dg.randomArray(10000);
    int[] inputMulti = new int[inputSeq.length];
    int[] ref = new int[inputSeq.length];
    System.arraycopy(inputSeq, 0, inputMulti, 0, inputSeq.length);
    System.arraycopy(inputSeq, 0, ref, 0, inputSeq.length);
        
    int[] resMulti = MergeSortSingle.sort(inputSeq);
    int[] resSingle = MergeSortMulti.sort(inputMulti, Runtime.getRuntime().availableProcessors());
    
    Arrays.sort(ref);
    Assert.assertArrayEquals(resSingle, resMulti);
    Assert.assertArrayEquals(ref, resMulti);
    
  }
  
  @Test
  public void testBigRandom() {
	RandomGenerator dg = new RandomGenerator();    
    int[] inputSeq = dg.randomArray(1024 * 1024);
    int[] inputMulti = new int[inputSeq.length];
    int[] ref = new int[inputSeq.length];
    System.arraycopy(inputSeq, 0, inputMulti, 0, inputSeq.length);
    System.arraycopy(inputSeq, 0, ref, 0, inputSeq.length);
      
    int[] resMulti = MergeSortSingle.sort(inputSeq);
    int[] resSingle = MergeSortMulti.sort(inputMulti, Runtime.getRuntime().availableProcessors());
        
    
    Arrays.sort(ref);
    Assert.assertArrayEquals(resSingle, resMulti);
    Assert.assertArrayEquals(ref, resMulti);
    
  }
  
}
