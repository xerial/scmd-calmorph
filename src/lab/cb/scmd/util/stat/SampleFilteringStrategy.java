//--------------------------------------
// SCMD Project
// 
// SampleFilteringStrategy.java 
// Since:  2004/05/07
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.stat;

import java.util.Collection;
import lab.cb.scmd.util.table.TableIterator;


/** �e�[�u����H���ē�����Sample�̏W���ɁA�ǂ̂悤�Ƀt�B���^�[�������邩
 * ��`���邽�߂�strategy (StrategyPattern)
 * setStatClass�ŁA�X�̐헪���g��Statistics�N���X���`���Ă����K�v������
 * @author leo
 *
 */
abstract public class SampleFilteringStrategy
{
	/** 
	 * @param statClass ���̐헪������Statistics�N���X
	 */
	public void setStatClass(Statistics statClass)
	{
		_statClass = statClass;
	}
	
	abstract public Collection filter(TableIterator ti);
	
	/**����strategy���g���Ă���Statistics�N���X�{�̂̋@�\���g�����߂̃N���X
	 * isValidCell�̔���ȂǁB
	 * @return
	 */
	protected Statistics getStatClass() { return _statClass; }	
	
	Statistics _statClass;
}


//--------------------------------------
// $Log: SampleFilteringStrategy.java,v $
// Revision 1.1  2004/05/07 03:06:20  leo
// Statistics�N���X���A�f�[�^�̃t�B���^�����O�̐헪��؂�ւ�����悤�ɕύX
//
//--------------------------------------