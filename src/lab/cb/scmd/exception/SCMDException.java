//------------------------------------
// SCMD Project
//  
// SCMDException.java 
// Since:  2004/04/16
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------
package lab.cb.scmd.exception;

import java.io.PrintStream;

/**
 * SCMD Project�ł̃R�[�h�Ŏg����O�N���X�̃x�[�X�B
 * ���̃N���X��extends���āA�D�݂̗�O��lab.cb.scmd.exception�p�b�P�[�W���ɍ쐬���Ă��������B
 * @author leo
 */
public class SCMDException extends Exception {

	/**
	 * 
	 */
	public SCMDException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public SCMDException(String arg0) {
		super(arg0);
	}

	/** �ϒ��̈������Ƃ�constructor. ���b�Z�[�W�Ԃɂ̓X�y�[�X������
	 * @param message 
	 * @TODO �ϒ������̎g�����𒲂ׂĂ������
	 */
//	public SCMDException(String message1, String... message)
//	{
//	    super(message1 + )
//	}
//	
//	static protected String concatinateStrings(Object... s)
//	{
//	    StringBuffer buffer = new StringBuffer();
//	    for))
//	    buffer.append(s);
//	    return buffer.toString();
//	}

	
	/**
	 * @param arg0
	 */
	public SCMDException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SCMDException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	/** ��O���b�Z�[�W���o�͂���
	 * @param outputStream �o�͐� (System.err�Ȃ�)
	 */
	public void what(PrintStream outputStream)
	{
		String m = this.getMessage();
		if(m != null)
		{
			outputStream.println(m);
		}
	}
	
	/** ��O���b�Z�[�W��System.err�ɏo�͂���
	 */
	public void what()
	{
		what(System.err);
	}

}


//--------------------------------------
// $Log: SCMDException.java,v $
// Revision 1.2  2004/05/05 16:27:50  leo
// �R�}���h���C����parse���̃G���[(LackOfArgumentException)��ǉ��B
// SCMDException�ɁA�G���[�\���̊ȕ։��̂���what()���\�b�h��ǉ��B
//
// Revision 1.1  2004/04/22 04:08:46  leo
// first ship for /home/lab.cb.scmd/CVS
//
// Revision 1.1  2004/04/22 02:53:31  leo
// first ship of SCMDProject
//
// Revision 1.2  2004/04/22 02:30:15  leo
// grouping complete
//
// Revision 1.1  2004/04/19 09:20:44  leo
// first ship
//
// Revision 1.1  2004/04/16 09:28:46  leo
// add exception class & table class
//
//--------------------------------------