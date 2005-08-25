//--------------------------------------
// SCMD Project
// 
// EliminateOnePercentOfBothSidesStrategy.java 
// Since:  2004/05/07
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.stat;

import java.util.*;

import lab.cb.scmd.util.table.Cell;
import lab.cb.scmd.util.table.TableIterator;


/** �T���v�����\�[�g���āA����1%�������i1%���P�����̏ꍇ�́A�P�ɌJ��グ�j�����R���N�V������
 * �Ԃ��헪
 * @author leo
 *
 */
public class EliminateOnePercentOfBothSidesStrategy extends SampleFilteringStrategy
{

	/* (non-Javadoc)
	 * @see lab.cb.scmd.util.stat.SampleFilteringStrategy#filter(lab.cb.scmd.util.table.TableIterator)
	 */
	public Collection filter(TableIterator ti)
	{
		LinkedList list = new LinkedList();
		for(; ti.hasNext(); )
		{
			Cell c = (Cell) ti.nextCell();
			if(!getStatClass().isValidAsDouble(c))
				continue;			
			list.add(new Double(c.doubleValue()));
		}
		Collections.sort(list);
		
		double onePercent = list.size() * 0.01;
		int numEliminates = (int) Math.floor(onePercent);  
		numEliminates = numEliminates < 1 ? 1 :  numEliminates;   // �P�����Ȃ�P�ɌJ�グ
		
		// list�̗��[���珜��
		for(int i=0; i<numEliminates && list.size() > 2; i++)
		{
			list.removeFirst();
			list.removeLast();
		}
		
		return list;
	}

}


//--------------------------------------
// $Log: EliminateOnePercentOfBothSidesStrategy.java,v $
// Revision 1.2  2004/07/27 05:18:12  leo
// TableIterator�̔�����
//
// Revision 1.1  2004/05/07 03:06:20  leo
// Statistics�N���X���A�f�[�^�̃t�B���^�����O�̐헪��؂�ւ�����悤�ɕύX
//
//--------------------------------------