//--------------------------------------
// SCMDProject
// 
// HTMLFilter.java 
// Since: 2004/08/07
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author leo
 *  
 */
public class HTMLFilter implements TextContentFilter
{
    Pattern cdataPattern = Pattern.compile("<!\\[CDATA\\[([^\\]]*)\\]\\]>");
    /**
     *  
     */
    public HTMLFilter()
    {
    }

    /**
     * &, <, >, ", ' �̕������entitiy�Q�Ƃɕϊ�����
     * 
     * @param content
     *            �ϊ����镶����
     * @return
     */
    public String filter(String content) {
        
        Matcher m = cdataPattern.matcher(content);
        if(m.matches())
        {
            // CDATA section�̒��g��Ԃ�
            return m.group(1);
        }
        
        StringBuffer substituedStringBuffer = new StringBuffer(content.length());
        for (int i = 0; i < content.length(); i++)
        {
            char c = content.charAt(i);
            switch (c)
            {
            case '<':
                substituedStringBuffer.append("&lt;");
                break;
            case '>':
                substituedStringBuffer.append("&gt;");
                break;
            case '"':
                substituedStringBuffer.append("&quot;");
                break;
            case '\'':
                substituedStringBuffer.append("&apos;");
                break;
            case '&':
                substituedStringBuffer.append("&amp;");
                break;
            default:
                substituedStringBuffer.append(c);
            }
        }
        return substituedStringBuffer.toString();
    }

}

//--------------------------------------
// $Log: HTMLFilter.java,v $
// Revision 1.2  2004/08/26 04:28:13  leo
// CDATA�̏�����ǉ�
//
// Revision 1.1  2004/08/07 12:30:11  leo
// Filter��؂�ւ�����悤�ɂ��܂���
//
//--------------------------------------
