//--------------------------------------
// SCMDProject
// 
// FileUtil.java 
// Since: 2004/07/13
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.io;

import java.io.*;


/**
 * @author leo
 *
 */
public class FileUtil
{

    /**
     * 
     */
    static public void testExistence(File file) throws FileNotFoundException
    {
        if(!file.exists())
            throw new FileNotFoundException("file:" + file + " doesn't exist");            
    }
    
    static public void testExistence(String filePath) throws FileNotFoundException
    {
        testExistence(new File(filePath));
    }
}


//--------------------------------------
// $Log: FileUtil.java,v $
// Revision 1.3  2004/07/17 08:07:39  leo
// InvalidPathException����菜���܂���
//
// Revision 1.2  2004/07/14 03:20:37  leo
// ���\�b�h��ǉ�
//
// Revision 1.1  2004/07/13 08:07:20  leo
// �v���Z�X���N�����ăR�}���h���C�������s����c�[���𓱓�
//
//--------------------------------------