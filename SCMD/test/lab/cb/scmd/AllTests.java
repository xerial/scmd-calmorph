//--------------------------------------
// SCMD Project
// 
// AllTests.java 
// Since:  2004/06/11
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd;

import lab.cb.scmd.exception.SCMDException;
import lab.cb.scmd.util.cui.*;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author leo
 * SCMD Project��Test Suite 
 * ���s���ɂ́Aworkfolder���Aworkfolder/test�Ɏw�肷�邱��
 */
public class AllTests
{

    public static Test suite()
    {
        TestSuite suite = new TestSuite("SCMD Test Suites");
        //$JUnit-BEGIN$
        suite.addTest(lab.cb.scmd.algorithm.AllTests.suite());
        suite.addTest(lab.cb.scmd.autoanalysis.grouping.AllTests.suite());
        suite.addTest(lab.cb.scmd.util.AllTests.suite());
        //$JUnit-END$
        return suite;
    }

    public static void main(String[] args)
    {
        final int OPT_HELP = 0;
        final int OPT_SWINGTEST = 1;
        final int OPT_AWTTEST = 2;
        try
        {
            OptionParser parser = new OptionParser();
        
            parser.addOptionGroup((new OptionGroup("swing option", true)).
                    add(new Option(OPT_SWINGTEST, "s", "swing", "run Swing TestRunner")));
            
            parser.addOptionGroup((new OptionGroup("awt option", true)).
                    add(new Option(OPT_AWTTEST, "a", "awt", "run AWT TestRunner")));

            parser.getContext(args);
            
            if(parser.isSet(OPT_SWINGTEST))
            {
                junit.swingui.TestRunner.run(AllTests.class);
                return;
            }
            if(parser.isSet(OPT_AWTTEST))
            {
                junit.awtui.TestRunner.run(AllTests.class);
                return;
            }
            junit.textui.TestRunner.run(AllTests.class);           
        }
        catch(SCMDException e)
        {
            e.what(System.err);            
        }            
    }

}


//--------------------------------------
// $Log: AllTests.java,v $
// Revision 1.4  2004/07/08 08:24:30  leo
// TestSuite�̍\����������
//
// Revision 1.3  2004/07/07 15:04:22  leo
// Ant�Ŏ����R���p�C���A�e�X�g���s���L�q
//
// Revision 1.2  2004/06/23 16:31:58  leo
// Collection����̂��߂�lab.cb.scmd.algorithm�p�b�P�[�W��ǉ�
//
// Revision 1.1  2004/06/11 06:23:45  leo
// TestSuite���쐬
//
//--------------------------------------