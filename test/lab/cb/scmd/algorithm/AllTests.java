//--------------------------------------
// SCMD Project
// 
// AllTests.java 
// Since:  2004/06/24
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.algorithm;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author leo
 *
 */
public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for lab.cb.scmd.algorithm");
		//$JUnit-BEGIN$
		suite.addTestSuite(AlgorithmTest.class);
		suite.addTestSuite(ComparisonPredicateTest.class);
		suite.addTestSuite(RegexPredicateTest.class);
		//$JUnit-END$
		return suite;
	}
}


//--------------------------------------
// $Log: AllTests.java,v $
// Revision 1.3  2004/06/24 02:28:41  leo
// ���K�\���p��Predicate�̃e�X�g��ǉ�
//
// Revision 1.2  2004/06/24 02:09:14  leo
// ComparisonPredicate�̊���
//
// Revision 1.1  2004/06/23 16:31:58  leo
// Collection����̂��߂�lab.cb.scmd.algorithm�p�b�P�[�W��ǉ�
//
//--------------------------------------