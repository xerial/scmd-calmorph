//--------------------------------------
// SCMD Project
// 
// OptionComposite.java 
// Since:  2004/04/22
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.cui;

import java.util.*;
import java.io.*;

/**
 * Composite Pattern�̃x�[�X�N���X
 * �O���[�v�����ꂽ�I�v�V����(OptionGroup)��A�P��̃I�v�V����(Option)���A
 * ���ꂼ��t�@�C���V�X�e���̃f�B���N�g���A�t�H���_�̂悤�Ɉ����A�ċA�I��
 * ���g�����ǂ�悤�ɂ���B 
 * @author leo
 *  
 */
public abstract class OptionComposite
{
	/**
	 * help���b�Z�[�W�p�̏o�͂��W�߂�
	 * @param container 
	 */
	abstract public void collectOptionDescriptions(OptionDescriptionContainer container);
	abstract public Option findByLongOptionName(String longOption);
	abstract public Option findByShortOptionName(String shortOption);
	
	abstract public boolean isGroup();
	/**
	 * �w���v���b�Z�[�W���i�[���A�t�H�[�}�b�g���ďo�͂���N���X
	 * @author leo
	 *
	 */
	class OptionDescriptionContainer
	{

		public void addDescription(String shortOptionColumn, String longOptionColumn, String descriptionColumn)
		{
			String column[] = new String[3];
			column[0] = shortOptionColumn;
			column[1] = longOptionColumn;
			column[2] = descriptionColumn;
			_columnList.add(column);
		}
		public void addDescription(String groupName)
		{
			String singleColumn[] = new String[1];
			singleColumn[0] = groupName;
			_columnList.add(singleColumn);
		}

		public String toString()
		{
			// calculate necessary width for each column
			int width[] = { 0, 0, 0 };
			for (Iterator ci = _columnList.iterator(); ci.hasNext();)
			{
				String[] line = (String[]) ci.next();
				if (line.length != 3)
					continue; // single line 
				for (int i = 0; i < line.length; i++)
					width[i] = width[i] < line[i].length() ? line[i].length() : width[i];
			}
			for(int i=0; i<width.length; i++)
				width[i]++;
			// print each options
			StringWriter strWriter = new StringWriter();
			PrintWriter out = new PrintWriter(strWriter);
			for (Iterator ci = _columnList.iterator(); ci.hasNext();)
			{
				String[] line = (String[]) ci.next();
				if(line.length == 1)
					out.print(line[0]);  // group name
				else
				{
					out.print(" "); /// left margin
					for (int i = 0; i < line.length; i++)
					{
						out.print(line[i]);
						int numSpace = width[i] - line[i].length();
						for (int j = 0; j < numSpace; j++)
							out.print(" ");
					}	
				}
				out.println();
			}
			return strWriter.toString();
		}

		LinkedList _columnList = new LinkedList();
	}

}



//--------------------------------------
// $Log: OptionComposite.java,v $
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