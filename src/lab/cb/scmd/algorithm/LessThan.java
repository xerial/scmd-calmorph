//--------------------------------------
// SCMD Project
// 
// LessThan.java 
// Since:  2004/06/24
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.algorithm;

/** input < comparisonTarget �Ȃ� isTrue() = true �ƂȂ�
 * @author leo
 *
 */
public class LessThan extends ComparisonPredicate {
	
	public LessThan(Comparable comparisonTarget)
	{
		super(comparisonTarget);
	}
	/* (non-Javadoc)
	 * @see lab.cb.scmd.algorithm.UnaryPredicate#isTrue(java.lang.Object)
	 */
	public boolean isTrue(Object input) {
		return getComparisonTarget().compareTo(input) > 0;	
	}
}


//--------------------------------------
// $Log: LessThan.java,v $
// Revision 1.2  2004/06/24 02:09:14  leo
// ComparisonPredicate�̊���
//
// Revision 1.1  2004/06/23 16:31:58  leo
// Collection����̂��߂�lab.cb.scmd.algorithm�p�b�P�[�W��ǉ�
//
//--------------------------------------