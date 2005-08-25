//--------------------------------------
// SCMD Project
// 
// OptionGroup.java 
// Since:  2004/04/22
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.cui;

import java.util.*;

import lab.cb.scmd.exception.SCMDException;

/**
 * @author leo
 *
 */
public class OptionGroup extends OptionComposite
{
	/**
	 * @param groupName �I�v�V�����O���[�v�̖��O
	 */
	public OptionGroup(String groupName)
	{
		super();
		_groupName = groupName;
	}
	/**
	 * @param groupName �I�v�V�����O���[�v�̖��O
	 * @param isExclusive true�ɃZ�b�g���ꂽ�ꍇ�A���̃O���[�v���̃I�v�V�����͔r���I�ɂȂ�i���̃O���[�v�̃I�v�V�����Ɠ����Ɏg�p�ł��Ȃ��Ȃ�)
	 */
	public OptionGroup(String groupName, boolean isExclusive)
	{
		super();
		_groupName = groupName;
		_isExclusive = isExclusive;
	}
	
	public boolean isGroup() { return true; }
	public String getGroupName() { return _groupName; }

	
	public boolean isExclusive()
	{ return _isExclusive; }

	public String createHelpMessage()
	{
		OptionDescriptionContainer container = new OptionDescriptionContainer();
		collectOptionDescriptions(container);
		return container.toString();
	}
	public void collectOptionDescriptions(OptionDescriptionContainer container)
	{
		if(!_groupName.equals(""))	
			container.addDescription("[" + _groupName + "]");
		for (Iterator li = _optionList.iterator(); li.hasNext();)
		{
			OptionComposite component = (OptionComposite) li.next();
			component.collectOptionDescriptions(container);
		}
	}
	/**
	 * �O���[�v���ɃI�v�V������ǉ�
	 * @param option 	�ǉ�����I�v�V����
	 */
	public OptionGroup add(OptionComposite option)
	{
		_optionList.add(option);
		return this;
	}
	
	public void putOptionsInTheGroup(TreeMap optionMap, TreeMap optionID2GroupMap) throws SCMDException
	{ 
		for (Iterator oi = _optionList.iterator(); oi.hasNext();)
		{
			Option element = (Option) oi.next();
			int optionID = element.getOptionID();
			if(optionMap.get(new Integer(optionID)) != null)
				throw new SCMDException("duplilcate option id: " + optionID);
			optionMap.put(new Integer(element.getOptionID()), element);
			optionID2GroupMap.put(new Integer(element.getOptionID()), _groupName);
		}		
	}

	String _groupName;
	boolean _isExclusive = false;
	LinkedList _optionList = new LinkedList();
	/* (non-Javadoc)
	 * @see lab.cb.scmd.util.cui.OptionComposite#findByLongOptionName(java.lang.String)
	 */
	public Option findByLongOptionName(String longOption)
	{
		Option opt = null;
		for(Iterator li = _optionList.iterator(); li.hasNext();)
		{
			OptionComposite component = (OptionComposite) li.next();
			opt = component.findByLongOptionName(longOption);
			if(opt != null)
				break;
		}
		return opt;
	}
	/* (non-Javadoc)
	 * @see lab.cb.scmd.util.cui.OptionComposite#findByShortOptionName(java.lang.String)
	 */
	public Option findByShortOptionName(String shortOption)
	{
		Option opt = null;
		for(Iterator li = _optionList.iterator(); li.hasNext();)
		{
			OptionComposite component = (OptionComposite) li.next();
			opt = component.findByShortOptionName(shortOption);
			if(opt != null)
				break;
		}
		return opt;
	}
}


//--------------------------------------
// $Log: OptionGroup.java,v $
// Revision 1.3  2004/07/07 15:04:22  leo
// Ant�Ŏ����R���p�C���A�e�X�g���s���L�q
//
// Revision 1.2  2004/06/11 08:51:27  leo
// option ��exclusive �ȈقȂ�group�ɑ�������̂��A
// �����ɃZ�b�g�����Ƃ��ɗ�O���o����悤�ɂ���
//
// Revision 1.1  2004/04/22 04:08:46  leo
// first ship for /home/lab.cb.scmd/CVS
//
// Revision 1.1  2004/04/22 02:53:31  leo
// first ship of SCMDProject
//
// Revision 1.1  2004/04/22 02:30:15  leo
// grouping complete
//
//--------------------------------------