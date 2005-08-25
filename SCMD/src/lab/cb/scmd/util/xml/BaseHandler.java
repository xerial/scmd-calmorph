//--------------------------------------
// SCMDProject
// 
// BaseHandler.java 
// Since: 2004/07/22
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.xml;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * characters�C�x���g�́Abuffer�̐؂�ڂɂ��������ۂɁA �K�������A������string��^����킯�ł͂Ȃ��̂ŁA
 * characters�C�x���g��sequence���Ȃ������ʂ��AtextContent()�Ƃ��ĕԂ��N���X�B
 * 
 * [[NOTICE]]: ���̃N���X���g���ꍇ�ɂ́A�K��constructor, startDobument, startElement, endElement,
 * characters events�ŁAsuper(..)���ĂԂ���
 * 
 * @author leo
 *  
 */
public class BaseHandler extends DefaultHandler
{

    /**
     *  
     */
    public BaseHandler()
    {
        super();
    }

    public void characters(char[] ch, int start, int length) throws SAXException
    {
        String currentContent = (String) _contentStack.pop();
        currentContent += new String(ch, start, length);
        _contentStack.push(currentContent);
    }

    public void textContent(String content) throws SAXException
    {
    // implement this (deafult: do nothing)
    }

    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        String content = ((String) _contentStack.pop()).trim();
        if(content.length() > 0)
            textContent(content);
        }

    public void startDocument() throws SAXException
    {
        _contentStack.clear();
        _contentStack.push(new String(""));
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException
    {
        _contentStack.push(new String(""));
    }

    Stack _contentStack = new Stack();
}

//--------------------------------------
// $Log: BaseHandler.java,v $
// Revision 1.1  2004/07/22 07:10:09  leo
// first ship
//
//--------------------------------------
