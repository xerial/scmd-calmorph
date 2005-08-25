//--------------------------------------
// SCMD Project
// 
// StatisticsWithMissingValueSupport.java 
// Since:  2004/04/23
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.stat;

import java.util.HashSet;


import lab.cb.scmd.util.table.*;

/** �����l��String�𖳎��ł���悤�Ɋg������Statistics�N���X
 * StatisticsWithMissingValueSuport({"-1", "."}) �Ȃ�
 * �����l�́ADouble(-1).toString != "-1" �Ȃ̂Œ��� 
 * @author leo
 *
 */
public class StatisticsWithMissingValueSupport extends Statistics
{
	/**
	 * @param missingValueList �����l�Ƃ��Ĉ���������̃��X�g
	 */
	public StatisticsWithMissingValueSupport(String[] missingValueList) 
	{
		super();
		setMissingValues(missingValueList);
	}
	/** ���[�U�[����`����filter���T���v���ɂ�����悤�ɂȂ�
	 * @param missingValueList  �����l�Ƃ��Ĉ���������̃��X�g
	 * @param filteringStrategy filter�̎��
	 */
	public StatisticsWithMissingValueSupport(String [] missingValueList, SampleFilteringStrategy filteringStrategy)
	{
		super(filteringStrategy);		
		setMissingValues(missingValueList);
	}
	
	protected void setMissingValues(String[] missingValueList)
	{
		for(int i=0; i<missingValueList.length; i++)
			_missingValueSet.add(missingValueList[i]);		
	}
	
	protected boolean isValidAsString(Cell cell)
	{
	    boolean isValid = super.isValidAsString(cell);
	    return isValid ? !(isMissingValue(cell)) : false;
	}

	protected boolean isValidAsDouble(Cell cell)
	{
		boolean isValid = super.isValidAsDouble(cell);
		return isValid ? !(isMissingValue(cell)) : false;			
	}
	
	protected boolean isMissingValue(Cell cell)
	{
		String stringValue = cell.toString();
		return _missingValueSet.contains(stringValue);
	}
	
	HashSet _missingValueSet = new HashSet();
}


//--------------------------------------
// $Log: StatisticsWithMissingValueSupport.java,v $
// Revision 1.10  2004/06/12 13:34:09  leo
// �����l�̔�r�̏������P�D�V�̂��̂ɖ߂��܂����B
//
// Revision 1.9  2004/06/11 05:29:14  leo
// Cell�N���X��
//
// Revision 1.8  2004/05/28 19:45:46  nakatani
// assert(Double(-1) is a missing value) when missingValue=="-1"
//
// Revision 1.7  2004/05/07 03:06:20  leo
// Statistics�N���X���A�f�[�^�̃t�B���^�����O�̐헪��؂�ւ�����悤�ɕύX
//
// Revision 1.6  2004/05/06 06:10:34  leo
// ���b�Z�[�W�o�͗p��NullPrintStream��ǉ��B
// ���v�l�v�Z�����͊����B�o�͕����͂��ꂩ��
//
// Revision 1.5  2004/04/30 02:25:52  leo
// OptionParser�Ɉ����̗L�����`�F�b�N�ł���@�\��ǉ�
// setRequirementForNonOptionArgument()
//
// Revision 1.4  2004/04/27 06:40:51  leo
// util.stat package test complete
//
// Revision 1.3  2004/04/26 06:57:50  leo
// modify supports for validating double values
//
// Revision 1.2  2004/04/23 06:08:41  leo
// *** empty log message ***
//
// Revision 1.1  2004/04/23 05:56:56  leo
// temporary commit
//
//--------------------------------------