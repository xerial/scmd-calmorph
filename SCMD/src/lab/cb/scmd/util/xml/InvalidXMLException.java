//--------------------------------------
// SCMD Project
// 
// InvalidXMLException.java 
// Since:  2004/05/04
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.xml;



/** �s����XML���o�͂��ꂽ�Ƃ��ɔ��������O
 * @author leo
 *
 */
public class InvalidXMLException extends XMLException
{

	/**
	 * 
	 */
	public InvalidXMLException()
	{
		super();

	}

	/**
	 * @param arg0
	 */
	public InvalidXMLException(String arg0)
	{
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public InvalidXMLException(Throwable arg0)
	{
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InvalidXMLException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

}


//--------------------------------------
// $Log: InvalidXMLException.java,v $
// Revision 1.1  2004/05/03 17:02:35  leo
// XMLOutputter�������n�߂܂����B�e�X�g�R�[�h�͖�����
//
//--------------------------------------