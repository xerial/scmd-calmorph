//--------------------------------------
// SCMD Project
// 
// TableIterator.java 
// Since:  2004/04/23
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.table;

import java.util.Iterator;

/**
 * @author leo
 *
 */
public interface TableIterator extends Cloneable, Iterator
{
	public Cell nextCell();
	public Object clone();
	
	public int row();
	public int col();
}


//--------------------------------------
// $Log: TableIterator.java,v $
// Revision 1.5  2004/08/10 10:44:31  leo
// CellShapeStat�̒ǉ�
//
// Revision 1.4  2004/08/02 09:55:42  leo
// *** empty log message ***
//
// Revision 1.3  2004/07/27 05:18:12  leo
// TableIterator�̔�����
//
// Revision 1.2  2004/04/23 05:56:56  leo
// temporary commit
//
// Revision 1.1  2004/04/23 04:44:38  leo
// add stat/table utilities
//
//--------------------------------------