//--------------------------------------
// SCMD Project
// 
// NullOutputStream.java 
// Since:  2004/05/06
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.io;

import java.io.IOException;
import java.io.OutputStream;

/** NullPrintStream�Ŏg�p�B �����o�͂��Ȃ��N���X
 * @author leo
 *
 */
public class NullOutputStream extends OutputStream
{

    /**
     * 
     */
    public NullOutputStream()
    {
        super();
        // do nothing
    }

    /* (non-Javadoc)
     * @see java.io.OutputStream#write(int)
     */
    public void write(int arg0) throws IOException
    {
        // do nothing
    }

}


//--------------------------------------
// $Log: NullOutputStream.java,v $
// Revision 1.1  2004/05/06 06:10:34  leo
// ���b�Z�[�W�o�͗p��NullPrintStream��ǉ��B
// ���v�l�v�Z�����͊����B�o�͕����͂��ꂩ��
//
//--------------------------------------