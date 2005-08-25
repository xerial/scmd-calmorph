//--------------------------------------
// SCMD Project
// 
// XMLOutputterTest.java 
// Since:  2004/05/04
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.xml;

import java.io.*;

import junit.framework.TestCase;


/**
 * @author leo
 *
 */
public class XMLOutputterTest extends TestCase
{
    public void testReplaceToPredefinedEntities()
    {
        TextContentFilter filter = new HTMLFilter();
        String input = "'aj&lkjasdfjk< asdaf'<>\"";
        String transformed = filter.filter(input);
        assertEquals("&apos;aj&amp;lkjasdfjk&lt; asdaf&apos;&lt;&gt;&quot;", transformed);
        
    }
    
	public void testXMLOutputter()
	{
		XMLOutputter xmlout = new XMLOutputter(); // System.out�ɏo��
		
		try
		{
		    xmlout.startTag("sample");
		    xmlout.startTag("item", new XMLAttribute("id", "0").add("name", "sampleitem"));		    
		    xmlout.textContent("element content");		    
		    xmlout.closeTag();
		    xmlout.closeTag();
		    xmlout.endOutput();  // ���Y��̃^�O������΁A�S�ĕ���
		    
		}
		catch(InvalidXMLException e)
		{
		    fail(e.getMessage());
		}
	}
	
	public void testXMLFileOut() throws IOException
	{
	    File xmlfile = new File("__samplefile.xml");	    
	    XMLOutputter xmlout = new XMLOutputter(new FileOutputStream(xmlfile));
	    try
	    {
	        xmlout.startTag("roottag");
	        xmlout.startTag("item", new XMLAttribute("id", "10"));
	        xmlout.closeTag();
	        xmlout.endOutput();
	        
	        BufferedReader fin = new BufferedReader(new FileReader(xmlfile));
	        String line;
	        while((line = fin.readLine()) != null)
	        {
	            System.out.println(line);	
	        }
	        
	    }
	    catch(InvalidXMLException e)
	    {
	        fail(e.getMessage());
	    }
	    finally
	    {
	        xmlout.closeStream();   // file�����
	    }
	}
	
	public void testDTDOutputTest() throws IOException, InvalidXMLException
	{
	    XMLOutputter xmlout = new XMLOutputter();
	    xmlout.setDTDDeclaration(new DTDDeclaration("lab.cb.scmd", "lab.cb.scmd.dtd"));
	    xmlout.startTag("lab.cb.scmd").
	    	startTag("photo", new XMLAttribute("id", "104")).
	    	selfCloseTag("cell", new XMLAttribute("id", "1234").add("max", "110")).
	    	closeTag().
	    closeTag();
	    xmlout.endOutput();
	}
	
	public void testFilter()  throws InvalidXMLException
	{
	    XMLOutputter xmlout = new XMLOutputter();
	    xmlout.startTag("lab.cb.scmd", new XMLAttribute("cdata", "<![CDATA[ 'a&a']]>"));
	    xmlout.textContent("<![CDATA[ 'asdf'&dafa'\"'asfa']]>");
	    xmlout.closeTag();
	    xmlout.endOutput();
	}
	
}


//--------------------------------------
// $Log: XMLOutputterTest.java,v $
// Revision 1.11  2004/08/26 08:47:53  leo
// *** empty log message ***
//
// Revision 1.10  2004/08/07 12:30:11  leo
// Filter��؂�ւ�����悤�ɂ��܂���
//
// Revision 1.9  2004/07/22 14:16:38  leo
// DTD�錾�͈��
//
// Revision 1.8  2004/07/22 13:23:47  leo
// DTD�錾�𕡐��Ăׂ�悤�ɕύX
//
// Revision 1.7  2004/07/21 02:51:56  leo
// AllTests��lab.cb.scmd.util.xml��ǉ�
//
// Revision 1.6  2004/07/13 08:04:31  leo
// �኱�C��
//
// Revision 1.5  2004/07/12 08:00:25  leo
// &, <, >, ", '��entity�Q�Ƃɒu������悤�ɉ���
//
// Revision 1.4  2004/07/12 07:26:52  leo
// XMLOutputter�̏C��
//
// Revision 1.3  2004/07/08 08:20:02  leo
// XMLOutputter�̃e�X�g�R�[�h�������Ȃ���
//
// Revision 1.2  2004/05/05 15:56:48  leo
// A1B, C_data.xls�v�Z������ǉ�
//
// Revision 1.1  2004/05/03 17:02:35  leo
// XMLOutputter�������n�߂܂����B�e�X�g�R�[�h�͖�����
//
//--------------------------------------