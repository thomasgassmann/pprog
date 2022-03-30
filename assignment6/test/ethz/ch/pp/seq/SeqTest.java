package ethz.ch.pp.seq;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import ethz.ch.pp.util.RandomGenerator;

public class SeqTest {

	  @Test
	  public void testSeqSmall() {
	    Assert.assertEquals(new Sequence(2, 3), LongestCommonSequence.longestCommonSequence(new int[]{1, 2, 3, 3, 2}));
	    Assert.assertEquals(new Sequence(0, 0), LongestCommonSequence.longestCommonSequence(new int[]{1, 2, 3, 2}));
	    Assert.assertEquals(new Sequence(0, 0), LongestCommonSequence.longestCommonSequence(new int[]{1}));
	    Assert.assertEquals(new Sequence(0, 2), LongestCommonSequence.longestCommonSequence(new int[]{1, 1, 1}));
	    Assert.assertEquals(new Sequence(0, 5), LongestCommonSequence.longestCommonSequence(new int[]{1, 1, 1, 1, 1, 1}));
	  }

	  @Test
	  public void testSeqRand() {
		  RandomGenerator gen = new RandomGenerator();
		  for (int j = 0; j < 20; j++) {
			  int[] input = gen.randomArray(20);
			  Sequence result = LongestCommonSequence.longestCommonSequence(input);

			  System.out.println("Sequence: " + result.startIndex + " - " + result.endIndex);
			  for (int i = result.startIndex; i <= result.endIndex; i++) {
				  Assert.assertEquals(input[result.startIndex], input[i]);
			  }
		  }
	  }

	  @Test
	  public void testMultiSmall() {
	    Assert.assertEquals(new Sequence(2, 3), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 2, 3, 3, 2}, 4));
	    Assert.assertEquals(new Sequence(0, 0), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 2, 3, 2}, 4));
	    Assert.assertEquals(new Sequence(0, 0), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1}, 4));
	    Assert.assertEquals(new Sequence(0, 2), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 1, 1}, 4));
	    Assert.assertEquals(new Sequence(0, 5), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 1, 1, 1, 1, 1}, 4));
	  }

	  @Test
	  public void testCompare() {
		  RandomGenerator gen = new RandomGenerator();
		  for (int j = 0; j < 1000; j++) {
			  int[] input = gen.randomArray(20);
			  Assert.assertEquals(
					  Arrays.toString(input),
					  LongestCommonSequence.longestCommonSequence(input),
					  LongestCommonSequenceMulti.longestCommonSequence(input, 4)
					  );
		  }
	  }
	  
}
