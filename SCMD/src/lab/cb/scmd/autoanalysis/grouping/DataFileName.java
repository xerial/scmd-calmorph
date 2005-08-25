//--------------------------------------
// SCMDProject
// 
// DataFileName.java 
// Since:  2004/06/29
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.autoanalysis.grouping;

import java.io.File;

/**
 * @author leo
 *
 */
public interface DataFileName
{
	//�L���p�����[�^�[���o�v���O�����iValidParameters.java�j�Ŏg�p�����t�@�C���̒�`
	String SEP = File.separator;
	String GROUP_FILE_NAME[] =
		{
			"A_elim.xls",
			"A1B_elim.xls",
			"C_elim.xls",
			"actin_data.xls",
			"actin_SD.xls",
			"conA_data.xls",
			"conA_SD.xls",
			"dapi_data.xls",
			"dapi_SD.xls" 
		};
	//���̃t�@�C����S�ĊJ���ăf�[�^���W�߂�B
	String VERSATILE = "versatile.xls";
	String NUM_FILE_NAME[] =
		{ 
			"A_num_elim.xls", 
			"A1B_num_elim.xls", 
			"C_num_elim.xls" 
		};
	String OUTPUT_FILE_NAME = "valid.xls"; //�L���p�����[�^�[�������W�߂��f�[�^�t�@�C���B
	String PARAMETER_LIST = "valid_parameters.xls"; //�L���p�����[�^�[�̃��X�g�B�itab��؂�j
}


//--------------------------------------
// $Log: DataFileName.java,v $
// Revision 1.7  2004/09/03 06:02:53  nakatani
// versatile.xls��ǉ��B�זE���𐔂��邽�߁B
//
// Revision 1.6  2004/08/29 21:30:20  nakatani
// _SDdata.xls --> _SD.xls
//
// Revision 1.5  2004/08/27 14:31:07  nakatani
// *** empty log message ***
//
// Revision 1.4  2004/08/27 12:45:20  nakatani
// versatile.xls, versatile_SD.xls ��ǂݍ��܂Ȃ��悤�ɕύX�B
//
// Revision 1.3  2004/08/08 12:18:24  nakatani
// NUM_FILE_NAME[]��ǉ��B
//
// Revision 1.2  2004/07/28 00:43:40  nakatani
// �t�@�C�����̕ύX�Bdata.xls--> versatile.xls�ȂǁB
//
// Revision 1.1  2004/06/29 01:31:37  leo
// �f�[�^file�����`����interface��\�ɏo���܂���
//
//--------------------------------------