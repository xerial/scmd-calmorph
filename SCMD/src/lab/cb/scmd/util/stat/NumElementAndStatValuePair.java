// --------------------------------------
// SCMD Project
// 
// NumElementAndStatValuePair.java
// Since: 2004/05/07
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.stat;

/**
 * �v�f�̐��ƁA�v�Z���ʂ̃y�A
 * 
 * @author leo
 */
public class NumElementAndStatValuePair
{

	public NumElementAndStatValuePair(int numElement_, double value_)
	{
		numElement = numElement_;
		value = value_;
	}
	

	public int		numElement;
	public double	value;

	/**
	 * @return Returns the numElement.
	 */
	public int getNumElement()
	{
		return numElement;
	}

	/**
	 * @return Returns the value.
	 */
	public double getValue()
	{
		return value;
	}
}

//--------------------------------------
// $Log: NumElementAndStatValuePair.java,v $
// Revision 1.1  2004/05/07 04:30:26  leo
// ���Ԍv���p�N���X��ǉ�
//
//--------------------------------------
