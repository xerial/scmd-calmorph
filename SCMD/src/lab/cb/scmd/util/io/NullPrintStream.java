//--------------------------------------
// SCMD Project
// 
// NullPrintStream.java 
// Since:  2004/05/06
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.io;

import java.io.PrintStream;

/**  print���Ă��������s���Ȃ��N���X (verbose���b�Z�[�W�̏o�͐؂�ւ��ɕ֗�)
 * @author leo
 *
 */
public class NullPrintStream extends PrintStream
{

    /**
     * @param arg0
     */
    public NullPrintStream()
    {
        super(new NullOutputStream());
    }

   
}


//--------------------------------------
// $Log: NullPrintStream.java,v $
// Revision 1.1  2004/05/06 06:10:34  leo
// ���b�Z�[�W�o�͗p��NullPrintStream��ǉ��B
// ���v�l�v�Z�����͊����B�o�͕����͂��ꂩ��
//
//--------------------------------------