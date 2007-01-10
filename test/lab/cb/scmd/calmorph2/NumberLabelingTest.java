package lab.cb.scmd.calmorph2;

import junit.framework.TestCase;

public class NumberLabelingTest extends TestCase {
	
	private static final boolean t = true;
	private static final boolean F = false;
	
	public void testExecuteNumberLabeling() {
		boolean[] points = {F, F, F, F, t, F,
				            F, F, t, F, t, F,
				            t, F, t, F, t, F,
				            t, t, t, t, t, F,
				            F, F, F, F, F, F,
				            t, t, F, F, t, t};
		NumberLabeling nl = new NumberLabeling(6, 36);
		nl.executeNumberLabeling(points, true);
		
		assertEquals(5, nl.getLabelNumber());
		assertEquals(0, nl.getSameLabels().get(0).intValue());
		assertEquals(0, nl.getSameLabels().get(1).intValue());
		assertEquals(1, nl.getSameLabels().get(2).intValue());
		assertEquals(3, nl.getSameLabels().get(3).intValue());
		assertEquals(4, nl.getSameLabels().get(4).intValue());
	}

	public void testSmallestlabel() {
		NumberLabeling nl = new NumberLabeling(6, 36);
		
		nl.setSameLabels(0);
		nl.setSameLabels(0);
		nl.setSameLabels(1);
		nl.setSameLabels(3);
		nl.setSameLabels(4);
		
		assertEquals(0, NumberLabeling.smallestlabel(nl.getSameLabels(), 0));
		assertEquals(0, NumberLabeling.smallestlabel(nl.getSameLabels(), 1));
		assertEquals(0, NumberLabeling.smallestlabel(nl.getSameLabels(), 2));
		assertEquals(3, NumberLabeling.smallestlabel(nl.getSameLabels(), 3));
		assertEquals(4, NumberLabeling.smallestlabel(nl.getSameLabels(), 4));
		
		assertEquals(0, nl.getSameLabels().get(0).intValue());
		assertEquals(0, nl.getSameLabels().get(1).intValue());
		assertEquals(1, nl.getSameLabels().get(2).intValue()); // �폜�����ɉe��
		assertEquals(3, nl.getSameLabels().get(3).intValue());
		assertEquals(4, nl.getSameLabels().get(4).intValue());
	}
	
	public void testResetSameLabelsAndGetMaxLabel() {
		NumberLabeling nl = new NumberLabeling(6, 36);
		
		nl.setSameLabels(0);
		nl.setSameLabels(0);
		nl.setSameLabels(1);
		nl.setSameLabels(3);
		nl.setSameLabels(4);
		nl.setSameLabels(4);
		nl.setSameLabels(5);
		
		assertEquals(4, NumberLabeling.resetSameLabelsAndGetMaxLabel(nl.getSameLabels()));
		
		assertEquals(0, nl.getSameLabels().get(0).intValue());
		assertEquals(0, nl.getSameLabels().get(1).intValue());
		assertEquals(0, nl.getSameLabels().get(2).intValue());
		assertEquals(3, nl.getSameLabels().get(3).intValue());
		assertEquals(4, nl.getSameLabels().get(4).intValue());
		assertEquals(4, nl.getSameLabels().get(5).intValue());
		assertEquals(4, nl.getSameLabels().get(6).intValue());
	}
	
}
