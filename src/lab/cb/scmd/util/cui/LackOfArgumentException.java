//--------------------------------------
// SCMD Project
// 
// LackOfArgumentException.java 
// Since:  2004/05/06
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.cui;

import lab.cb.scmd.exception.SCMDException;


/** �R�}���h���C������������Ȃ��Ƃ��ɔ�������Exception
 * @author leo
 *
 */
public class LackOfArgumentException extends SCMDException
{

	/**
	 * 
	 */
	public LackOfArgumentException()
	{
		super();

	}

	/**
	 * @param arg0
	 */
	public LackOfArgumentException(String arg0)
	{
		super(arg0);

	}

	/**
	 * @param arg0
	 */
	public LackOfArgumentException(Throwable arg0)
	{
		super(arg0);

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public LackOfArgumentException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);

	}

}


//--------------------------------------
// $Log: LackOfArgumentException.java,v $
// Revision 1.1  2004/05/05 16:27:50  leo
// �R�}���h���C����parse���̃G���[(LackOfArgumentException)��ǉ��B
// SCMDException�ɁA�G���[�\���̊ȕ։��̂���what()���\�b�h��ǉ��B
//
//--------------------------------------