//--------------------------------------
// SCMD Project
// 
// ComparisonPredicate.java 
// Since:  2004/06/24
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.algorithm;

/** Comparable�̎���(Integer, Double, etc.) ��^���āA
 * ���Ƃ��΁A Integer �� Integer���m�̔�r���s�����߂� functor 
 * @author leo
 *
 */
public abstract class ComparisonPredicate implements UnaryPredicate
{
	public ComparisonPredicate(Comparable comparisonTarget)
	{
		_comparisonTarget = comparisonTarget;
	}
	protected Comparable getComparisonTarget() { return _comparisonTarget; }
	
	Comparable _comparisonTarget;
}


//--------------------------------------
// $Log: ComparisonPredicate.java,v $
// Revision 1.1  2004/06/24 02:09:14  leo
// ComparisonPredicate�̊���
//
// Revision 1.2  2004/06/24 01:47:06  leo
// �R�����g��ǉ�
//
// Revision 1.1  2004/06/23 16:31:58  leo
// Collection����̂��߂�lab.cb.scmd.algorithm�p�b�P�[�W��ǉ�
//
//--------------------------------------