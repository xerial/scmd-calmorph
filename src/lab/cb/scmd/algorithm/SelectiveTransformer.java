//--------------------------------------
// SCMD Project
// 
// SelectiveTransformer.java 
// Since:  2004/06/24
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.algorithm;

/** isTrue = true�ƂȂ���͂ɑ΂��Ă̂݁Atransform���s���������Ɏg��
 * �܂��A���̍ہAisTrue�Ōv�Z�������ʂ��ė��p����transform�����s�ł���悤�ɂ��邽�߂̃N���X
 * @author leo
 *
 */
public interface SelectiveTransformer extends UnaryPredicate, Transformer 
{
	/* (non-Javadoc)
	 * @see lab.cb.scmd.algorithm.UnaryPredicate#isTrue(java.lang.Object)
	 */
	public boolean isTrue(Object target);
	/* (non-Javadoc)
	 * @see lab.cb.scmd.algorithm.Transformer#transform(java.lang.Object)
	 */
	public Object transform(Object object);
}


//--------------------------------------
// $Log: SelectiveTransformer.java,v $
// Revision 1.2  2004/06/24 01:15:25  leo
// abstract class����Ainterface�ɕύX
//
// Revision 1.1  2004/06/23 16:31:58  leo
// Collection����̂��߂�lab.cb.scmd.algorithm�p�b�P�[�W��ǉ�
//
//--------------------------------------