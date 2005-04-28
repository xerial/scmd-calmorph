// --------------------------------------
// SCMD Project
// 
// CalcGroupStat.java
// Since: 2004/04/27
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------
package lab.cb.scmd.autoanalysis.grouping;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import lab.cb.scmd.util.cui.*;
import lab.cb.scmd.util.io.NullPrintStream;
import lab.cb.scmd.util.stat.EliminateOnePercentOfBothSidesStrategy;
import lab.cb.scmd.util.stat.NumElementAndStatValuePair;
import lab.cb.scmd.util.stat.Statistics;
import lab.cb.scmd.util.stat.StatisticsWithMissingValueSupport;
import lab.cb.scmd.util.table.*;
import lab.cb.scmd.util.time.StopWatch;
import lab.cb.scmd.exception.SCMDException;

/**
 * �O���[�v��{A, A1B, C}�̓��v�l�����v�Z����N���X
 * 
 * @author leo
 *  
 */
public class CalcGroupStat implements TableFileName
{

	OptionParser		_parser				= new OptionParser();
	String				_missingValue[]		= {"-1","-1.0","Infinity","NaN"};
	Statistics			_stat				= new StatisticsWithMissingValueSupport(_missingValue);
	PrintStream			_log				= new NullPrintStream();
	boolean				_eliminateSample	= false;
	boolean				_shortVariables		= false;

	// option IDs
	final static int	OPT_HELP			= 0;
	final static int	OPT_VERBOSE			= 1;
	final static int	OPT_BASEDIR			= 2;
	//final static int	OPT_OLD_FORMAT		= 3;
	final static int	OPT_ELIMINATE		= 4;
	final static int	OPT_SHORTVARIABLES	= 5;

	String				_baseDirName		= ".";
	boolean				_isVerbose			= false;

	public CalcGroupStat()
	{}

	public CalcGroupStat(String baseDirName)
	{
		_baseDirName = baseDirName;
	}

	/**
	 * CV�p�����[�^�����擾����
	 * 
	 * @param parameterName
	 *            CV�p�����[�^���ւ̕ϊ����̃p�����[�^�� regex =
	 *            ([A-Za-z]{1})([0-9][0-9-]+_(A|A1B|C))
	 * @return CV�p�����[�^�� ([A-Za-z]{1})CV([0-9][0-9-]+_(A|A1B|C))
	 * @throws SCMDException
	 */
	protected String getCVParameterName(String parameterName) throws SCMDException
	{
		Pattern p = Pattern.compile("([A-Za-z]{1})([0-9][0-9-]*_(A|A1B|C))");
		Matcher m = p.matcher(parameterName);
		if(m.matches())
			return m.group(1) + "CV" + m.group(2);
		else
			throw new SCMDException("cannot translate given parameter name " + parameterName + " to CV name");
	}

	/**
	 * ���v�l�̌v�Z���ʂ��i�[����R���e�i
	 * 
	 * @author leo
	 *  
	 */
	class OutputResult
	{

		OutputResult(HashMap resultHash, HashMap numValidSampleHash)
		{
			_resultHash = resultHash;
			_numValidSampleHash = numValidSampleHash;
		}

		HashMap	_resultHash;
		HashMap	_numValidSampleHash;

		/**
		 * @return Returns the _numValidSampleHash.
		 */
		public HashMap get_numValidSampleHash()
		{
			return _numValidSampleHash;
		}

		/**
		 * @return Returns the _resultHash.
		 */
		public HashMap get_resultHash()
		{
			return _resultHash;
		}
	}

	/**
	 * �f�B���N�g���̒���A, A1B, C�t�H���_����T�����A��������orf_{A, A1B, C}.xls�t�@�C������A���v�l���v�Z
	 * 
	 * @throws SCMDException
	 */
	public void loopForEachDirectory() throws SCMDException, IOException
	{
		StopWatch globalTime = new StopWatch();
		
		File inputDir = new File(_baseDirName);
		if(!inputDir.isDirectory())
			throw new SCMDException("base directory: " + _baseDirName + " doesn't exist");

		File[] groupDir = new File[GROUP_NAME.length];
		for (int i = 0; i < GROUP_NAME.length; i++)
		{
			String groupDirName = _baseDirName + File.separator + GROUP_NAME[i];
			groupDir[i] = new File(groupDirName);
			if(!groupDir[i].isDirectory())
				throw new SCMDException("directory:" + groupDirName + " doesn't exist");
		}

		// output file
		PrintWriter resultOut[] = new PrintWriter[GROUP_NAME.length];
		PrintWriter sampleNumOut[] = new PrintWriter[GROUP_NAME.length];
		if(_eliminateSample)
		{
			_log.println("eliminating 1% of both sides of samples");
		}
		String suffix = _eliminateSample ? "_elim" : ""; // ������؂�ꍇ�̓t�@�C������ς���
		for (int i = 0; i < resultOut.length; i++)
		{
			String resultFile = GROUP_NAME[i] + suffix + ".xls";
			String resultNumFile = GROUP_NAME[i] + "_num" + suffix + ".xls";
			resultOut[i] = new PrintWriter(new FileWriter(resultFile));
			sampleNumOut[i] = new PrintWriter(new FileWriter(resultNumFile));
			_log.println("[GROUP " + GROUP_NAME[i] + "]");
			_log.println("output file:    \t" + resultFile);
			_log.println("sapmle num file: \t" + resultNumFile);
		}

		// output parameter labels
		if( _shortVariables ) {
			outputLabel_A = outputLabel_SRT_A;
			outputLabel_A1B = outputLabel_SRT_A1B;
			outputLabel_C = outputLabel_SRT_C;
		} else {
			outputLabel_A = outputLabel_ALL_A;
			outputLabel_A1B = outputLabel_ALL_A1B;
			outputLabel_C = outputLabel_ALL_C;
		}
		outputLabel(resultOut[GROUP_A], outputLabel_A);
		outputLabel(resultOut[GROUP_A1B], outputLabel_A1B);
		outputLabel(resultOut[GROUP_C], outputLabel_C);

		outputLabel(sampleNumOut[GROUP_A], outputNumLabel_A);
		outputLabel(sampleNumOut[GROUP_A1B], outputNumLabel_A1B);
		outputLabel(sampleNumOut[GROUP_C], outputNumLabel_C);

		for (int i = 0; i < groupDir.length; i++)
		{
			StopWatch groupTime = new StopWatch();
			_log.println("Entering the directory: " + groupDir[i]);
			File[] fileList = groupDir[i].listFiles();
			for (int j = 0; j < fileList.length; j++)
			{
				if(!fileList[j].isDirectory())
					continue;

				// directory������ORF��؂�o��
				String dirName = fileList[j].getName();
				int groupNameSuffixPosition = dirName.lastIndexOf("_" + GROUP_NAME[i]);
				if(groupNameSuffixPosition == -1)
					continue; // ORF_{GROUP_NAME}�̌`���ł͂Ȃ��B
				String orfName = dirName.substring(0, groupNameSuffixPosition);

				String inputTableFile = orfName + GROUP_FILE_SUFFIX[i];
				_log.print(" reading \t" + inputTableFile + "               \r");

				switch (i)
				{
					case GROUP_A :
						calc_A(new File(fileList[j], orfName + GROUP_FILE_SUFFIX[GROUP_A]), orfName, resultOut[GROUP_A], sampleNumOut[GROUP_A]);
						break;
					case GROUP_A1B :
						calc_A1B(new File(fileList[j], orfName + GROUP_FILE_SUFFIX[GROUP_A1B] ), orfName,
								resultOut[GROUP_A1B], sampleNumOut[GROUP_A1B]);
						break;
					case GROUP_C :
						calc_C(new File(fileList[j], orfName + GROUP_FILE_SUFFIX[GROUP_C]), orfName, resultOut[GROUP_C], sampleNumOut[GROUP_C]);
						break;
				}				
			}
			resultOut[i].close();
			sampleNumOut[i].close();			
			_log.println();
			groupTime.showElapsedTime(_log);
		}
		_log.println("completed");
		globalTime.showElapsedTime(_log);
	}

	void outputLabel(PrintWriter out, String[] label)
	{
		if(label.length < 1)
			return;
		out.print(label[0]);
		for (int i = 1; i < label.length; i++)
			out.print("\t" + label[i]);
		out.println();
	}
	void outputResult(String orfName, PrintWriter out, String[] label, HashMap resultHash)
	{
		out.print(orfName);
		for (int i = 1; i < label.length; i++) {
			out.print("\t" + resultHash.get(label[i]));
		}
		out.println();
	}

	String[]	outputLabel_A;
	String[]	outputLabel_SRT_A		= {
			"Stage_A", "C11-1_A", "C12-1_A", "C13_A", "C103_A", "C104_A", "C115_A", "C126_A", "C127_A", "CCV11-1_A",
			"CCV115_A", "A7-1_A", "A8-1_A", "A101_A", "A105_A", "A106_A", "A113_A", "A120_A", "A121_A", "A122_A",
			"A123_A", "ACV101_A", "D14-1_A", "D15-1_A", "D16-1_A", "D17-1_A", "D102_A", "D105_A", "D117_A", "D127_A",
			"D135_A", "D147_A", "D148_A", "D154_A", "D155_A", "D173_A", "D176_A", "D179_A", "D182_A", "D188_A",
			"D191_A", "D194_A", "DCV14-1_A", "DCV105_A", "DCV147_A", "DCV182_A"};

	String[]	outputLabel_ALL_A		= {
			"Stage_A", "C11-1_A", "C12-1_A", "C13_A", "C103_A", "C104_A", "C115_A", "C126_A", "C127_A",
			"A7-1_A", "A8-1_A", "A101_A", "A105_A", "A106_A", "A113_A", "A120_A", "A121_A", "A122_A",
			"A123_A", "D14-1_A", "D15-1_A", "D16-1_A", "D17-1_A", "D102_A", "D105_A", "D117_A", "D127_A",
			"D135_A", "D147_A", "D148_A", "D154_A", "D155_A", "D173_A", "D176_A", "D179_A", "D182_A", "D188_A",
			"D191_A", "D194_A",
			"CCV11-1_A", "CCV12-1_A", "CCV13_A", "CCV103_A", "CCV104_A", "CCV115_A", "CCV126_A", "CCV127_A",
			"ACV7-1_A", "ACV8-1_A", "ACV101_A", "ACV120_A", "ACV121_A", "ACV122_A",
			"ACV123_A", "DCV14-1_A", "DCV15-1_A", "DCV16-1_A", "DCV17-1_A", "DCV102_A", "DCV105_A", "DCV117_A", "DCV127_A",
			"DCV135_A", "DCV147_A", "DCV148_A", "DCV154_A", "DCV155_A", "DCV173_A", "DCV176_A", "DCV179_A", "DCV182_A", "DCV188_A",
			"DCV191_A", "DCV194_A"};


	// unification of parameter names
	// "D17-1,D17-3_A1B" => "D17-3_A1B"
	// "D160,D161_A1B" => "D161_A1B"
	// "D164,D165_A1B" => "D165_A1B" 
	// "D171,D172_A1B" => "D172_A1B"
	// "D188,D190_A1B" => "D190_A1B"
	String[]	outputLabel_A1B;
	String[]	outputLabel_SRT_A1B		= {
			"Stage_A1B", "C11-1_A1B", "C11-2_A1B", "C12-1_A1B", "C12-2_A1B", "C13_A1B", "C101_A1B", "C102_A1B",
			"C103_A1B", "C104_A1B", "C105_A1B", "C106_A1B", "C107_A1B", "C108_A1B", "C109_A1B", "C110_A1B", "C111_A1B",
			"C112_A1B", "C113_A1B", "C114_A1B", "C115_A1B", "C116_A1B", "C117_A1B", "C118_A1B", "C123_A1B", "C124_A1B",
			"C125_A1B", "C126_A1B", "C127_A1B", "C128_A1B", "CCV11-1_A1B", "CCV11-2_A1B", "CCV105_A1B", "CCV106_A1B",
			"CCV109_A1B", "CCV114_A1B", "CCV115_A1B", "CCV118_A1B", "A7-1_A1B", "A7-2_A1B", "A8-1_A1B", "A8-2_A1B",
			"A9_A1B", "A101_A1B", "A102_A1B", "A103_A1B", "A104_A1B", "A120_A1B", "A121_A1B", 
			"A122_A1B", "A123_A1B", "ACV101_A1B", "ACV102_A1B",
			"ACV103_A1B", "ACV104_A1B", "D14-3_A1B", "D15-3_A1B", "D16-3_A1B", "D17-3_A1B", "D104_A1B",
			"D107_A1B", "D110_A1B", "D114_A1B", "D118_A1B", "D126_A1B", "D129_A1B", "D132_A1B", "D136_A1B", "D142_A1B",
			"D143_A1B", "D145_A1B", "D147_A1B", "D148_A1B", "D152_A1B", "D154_A1B", "D155_A1B", "D161_A1B",
			"D165_A1B", "D169_A1B", "D170_A1B", "D172_A1B", "D175_A1B", "D178_A1B", "D181_A1B", "D184_A1B",
			"D190_A1B", "D193_A1B", "D196_A1B", "DCV14-3_A1B", "DCV107_A1B", "DCV114_A1B", "DCV147_A1B",
			"DCV184_A1B"			};

	String[]	outputLabel_ALL_A1B		= {
			"Stage_A1B", "C11-1_A1B", "C11-2_A1B", "C12-1_A1B", "C12-2_A1B", "C13_A1B", "C101_A1B", "C102_A1B",
			"C103_A1B", "C104_A1B", "C105_A1B", "C106_A1B", "C107_A1B", "C108_A1B", "C109_A1B", "C110_A1B", "C111_A1B",
			"C112_A1B", "C113_A1B", "C114_A1B", "C115_A1B", "C116_A1B", "C117_A1B", "C118_A1B", "C123_A1B", "C124_A1B",
			"C125_A1B", "C126_A1B", "C127_A1B", "C128_A1B", "A7-1_A1B", "A7-2_A1B", "A8-1_A1B", "A8-2_A1B",
			"A9_A1B", "A101_A1B", "A102_A1B", "A103_A1B", "A104_A1B", "A107_A1B", "A108_A1B", "A109_A1B", "A110_A1B",
			"A112_A1B", "A113_A1B", "A120_A1B", "A121_A1B", "A122_A1B", "A123_A1B", 
			"D14-3_A1B", "D15-3_A1B", "D16-3_A1B", "D17-3_A1B", "D104_A1B",
			"D107_A1B", "D110_A1B", "D114_A1B", "D118_A1B", "D126_A1B", "D129_A1B", "D132_A1B", "D136_A1B", "D142_A1B",
			"D143_A1B", "D145_A1B", "D147_A1B", "D148_A1B", "D152_A1B", "D154_A1B", "D155_A1B", "D161_A1B",
			"D165_A1B", "D169_A1B", "D170_A1B", "D172_A1B", "D175_A1B", "D178_A1B", "D181_A1B", "D184_A1B",
			"D190_A1B", "D193_A1B", "D196_A1B",
			"CCV11-1_A1B", "CCV11-2_A1B", "CCV12-1_A1B", "CCV12-2_A1B", "CCV13_A1B", "CCV101_A1B", "CCV102_A1B",
			"CCV103_A1B", "CCV104_A1B", "CCV105_A1B", "CCV106_A1B", "CCV107_A1B", "CCV108_A1B", "CCV109_A1B", "CCV110_A1B", "CCV111_A1B",
			"CCV112_A1B", "CCV113_A1B", "CCV114_A1B", "CCV115_A1B", "CCV116_A1B", "CCV117_A1B", "CCV118_A1B", "CCV126_A1B", "CCV127_A1B", 
			"CCV128_A1B", "ACV7-1_A1B", "ACV7-2_A1B", "ACV8-1_A1B", "ACV8-2_A1B",
			"ACV9_A1B", "ACV101_A1B", "ACV102_A1B", "ACV103_A1B", "ACV104_A1B", 
			"ACV120_A1B", "ACV121_A1B", "ACV122_A1B", "ACV123_A1B", 
			"DCV14-3_A1B", "DCV15-3_A1B", "DCV16-3_A1B", "DCV17-3_A1B", "DCV104_A1B",
			"DCV107_A1B", "DCV110_A1B", "DCV114_A1B", "DCV118_A1B", "DCV126_A1B", "DCV129_A1B", "DCV132_A1B", "DCV136_A1B", "DCV142_A1B",
			"DCV143_A1B", "DCV145_A1B", "DCV147_A1B", "DCV148_A1B", "DCV152_A1B", "DCV154_A1B", "DCV155_A1B", "DCV161_A1B",
			"DCV165_A1B", "DCV169_A1B", "DCV170_A1B", "DCV172_A1B", "DCV175_A1B", "DCV178_A1B", "DCV181_A1B", "DCV184_A1B",
			"DCV190_A1B", "DCV193_A1B", "DCV196_A1B" };

	String[]	outputLabel_C;
	String[]	outputLabel_SRT_C		= {
			"Stage_C", "C11-1_C", "C11-2_C", "C12-1_C", "C12-2_C", "C13_C", "C101_C", "C102_C", "C103_C", "C104_C",
			"C105_C", "C106_C", "C107_C", "C108_C", "C109_C", "C110_C", "C111_C", "C112_C", "C113_C", "C114_C",
			"C115_C", "C116_C", "C117_C", "C118_C", "C123_C", "C124_C", "C125_C", "C126_C", "C127_C", "C128_C",
			"CCV11-1_C", "CCV11-2_C", "CCV105_C", "CCV106_C", "CCV109_C", "CCV114_C", "CCV115_C", "CCV118_C", "A7-1_C",
			"A7-2_C", "A8-1_C", "A8-2_C", "A9_C", "A101_C", "A102_C", "A103_C", "A104_C", "A107_C", "A108_C", "A109_C",
			"A110_C", "A112_C", "A113_C", "A120_C", "A121_C", "A122_C", "A123_C", "ACV101_C", "ACV102_C", "ACV103_C",
			"ACV104_C", "D14-1_C", "D14-2_C", "D14-3_C", "D15-1_C", "D15-2_C", "D15-3_C", "D16-1_C", "D16-2_C",
			"D16-3_C", "D17-1_C", "D17-2_C", "D103_C", "D106_C", "D108_C", "D109_C", "D112_C", "D113_C", "D116_C",
			"D117_C", "D119_C", "D121_C", "D123_C", "D125_C", "D128_C", "D130_C", "D131_C", "D134_C", "D135_C",
			"D137_C", "D139_C", "D141_C", "D143_C", "D144_C", "D145_C", "D146_C", "D147_C", "D148_C", "D149_C",
			"D150_C", "D151_C", "D152_C", "D153_C", "D154_C", "D155_C", "D156_C", "D157_C", "D158_C", "D159_C",
			"D162_C", "D163_C", "D166_C", "D167_C", "D169_C", "D170_C", "D173_C", "D174_C", "D176_C", "D177_C",
			"D179_C", "D180_C", "D182_C", "D183_C", "D185_C", "D186_C", "D188_C", "D189_C", "D191_C", "D192_C",
			"D193_C", "D194_C", "D195_C", "D196_C", "D197_C", "D198_C", "DCV14-1_C", "DCV14-2_C", "DCV14-3_C",
			"DCV106_C", "DCV112_C", "DCV113_C", "DCV116_C", "DCV123_C", "DCV147_C", "DCV149_C", "DCV182_C", "DCV183_C"};

	String[]	outputLabel_ALL_C		= {
			"Stage_C", "C11-1_C", "C11-2_C", "C12-1_C", "C12-2_C", "C13_C", "C101_C", "C102_C", "C103_C", "C104_C",
			"C105_C", "C106_C", "C107_C", "C108_C", "C109_C", "C110_C", "C111_C", "C112_C", "C113_C", "C114_C",
			"C115_C", "C116_C", "C117_C", "C118_C", "C123_C", "C124_C", "C125_C", "C126_C", "C127_C", "C128_C", "A7-1_C",
			"A7-2_C", "A8-1_C", "A8-2_C", "A9_C", "A101_C", "A102_C", "A103_C", "A104_C", "A107_C", "A108_C", "A109_C",
			"A110_C", "A112_C", "A113_C", "A120_C", "A121_C", "A122_C", "A123_C", 
			"D14-1_C", "D14-2_C", "D14-3_C", "D15-1_C", "D15-2_C", "D15-3_C", "D16-1_C", "D16-2_C",
			"D16-3_C", "D17-1_C", "D17-2_C", "D103_C", "D106_C", "D108_C", "D109_C", "D112_C", "D113_C", "D116_C",
			"D117_C", "D119_C", "D121_C", "D123_C", "D125_C", "D128_C", "D130_C", "D131_C", "D134_C", "D135_C",
			"D137_C", "D139_C", "D141_C", "D143_C", "D144_C", "D145_C", "D146_C", "D147_C", "D148_C", "D149_C",
			"D150_C", "D151_C", "D152_C", "D153_C", "D154_C", "D155_C", "D156_C", "D157_C", "D158_C", "D159_C",
			"D162_C", "D163_C", "D166_C", "D167_C", "D169_C", "D170_C", "D173_C", "D174_C", "D176_C", "D177_C",
			"D179_C", "D180_C", "D182_C", "D183_C", "D185_C", "D186_C", "D188_C", "D189_C", "D191_C", "D192_C",
			"D193_C", "D194_C", "D195_C", "D196_C", "D197_C", "D198_C",
			"CCV11-1_C", "CCV11-2_C", "CCV12-1_C", "CCV12-2_C", "CCV13_C", "CCV101_C", "CCV102_C", "CCV103_C", "CCV104_C",
			"CCV105_C", "CCV106_C", "CCV107_C", "CCV108_C", "CCV109_C", "CCV110_C", "CCV111_C", "CCV112_C", "CCV113_C", "CCV114_C",
			"CCV115_C", "CCV116_C", "CCV117_C", "CCV118_C", "CCV126_C", "CCV127_C", "CCV128_C", 
			"ACV7-1_C", "ACV7-2_C", "ACV8-1_C", "ACV8-2_C", "ACV9_C", "ACV101_C", "ACV102_C", "ACV103_C", "ACV104_C", 
			"ACV120_C", "ACV121_C", "ACV122_C", "ACV123_C", 
			"DCV14-1_C", "DCV14-2_C", "DCV14-3_C", "DCV15-1_C", "DCV15-2_C", "DCV15-3_C", "DCV16-1_C", "DCV16-2_C",
			"DCV16-3_C", "DCV17-1_C", "DCV17-2_C", "DCV103_C", "DCV106_C", "DCV108_C", "DCV109_C", "DCV112_C", "DCV113_C", "DCV116_C",
			"DCV117_C", "DCV119_C", "DCV121_C", "DCV123_C", "DCV125_C", "DCV128_C", "DCV130_C", "DCV131_C", "DCV134_C", "DCV135_C",
			"DCV137_C", "DCV139_C", "DCV141_C", "DCV143_C", "DCV144_C", "DCV145_C", "DCV146_C", "DCV147_C", "DCV148_C", "DCV149_C",
			"DCV150_C", "DCV151_C", "DCV152_C", "DCV153_C", "DCV154_C", "DCV155_C", "DCV156_C", "DCV157_C", "DCV158_C", "DCV159_C",
			"DCV162_C", "DCV163_C", "DCV166_C", "DCV167_C", "DCV169_C", "DCV170_C", "DCV173_C", "DCV174_C", "DCV176_C", "DCV177_C",
			"DCV179_C", "DCV180_C", "DCV182_C", "DCV183_C", "DCV185_C", "DCV186_C", "DCV188_C", "DCV189_C", "DCV191_C", "DCV192_C",
			"DCV193_C", "DCV194_C", "DCV195_C", "DCV196_C", "DCV197_C", "DCV198_C"};

	String[]	outputNumLabel_A	= {
			"Stage_A", "C11-1_A", "C12-1_A", "C13_A", "C103_A", "C104_A", "C115_A", "C126_A", "C127_A", "A7-1_A",
			"A8-1_A", "A101_A", "A120_A", "A121_A", "A122_A", "A123_A", "D14-1_A", "D15-1_A", "D16-1_A", "D17-1_A",
			"D102_A", "D105_A", "D117_A", "D127_A", "D135_A", "D147_A", "D148_A", "D154_A", "D155_A", "D173_A",
			"D176_A", "D179_A", "D182_A", "D188_A", "D191_A", "D194_A"};

	String[]	outputNumLabel_A1B	= {
			"Stage_A1B", "C11-1_A1B", "C11-2_A1B", "C12-1_A1B", "C12-2_A1B", "C13_A1B", "C101_A1B", "C102_A1B",
			"C103_A1B", "C104_A1B", "C105_A1B", "C106_A1B", "C107_A1B", "C108_A1B", "C109_A1B", "C110_A1B", "C111_A1B",
			"C112_A1B", "C113_A1B", "C114_A1B", "C115_A1B", "C116_A1B", "C117_A1B", "C118_A1B", "C126_A1B", "C127_A1B",
			"C128_A1B", "A7-1_A1B", "A7-2_A1B", "A8-1_A1B", "A8-2_A1B", "A9_A1B", "A101_A1B", "A102_A1B", "A103_A1B",
			"A104_A1B", "A120_A1B", "A121_A1B", "A122_A1B", "A123_A1B", "D14-3_A1B", "D15-3_A1B", "D16-3_A1B",
			"D17-3_A1B", "D104_A1B", "D107_A1B", "D110_A1B", "D114_A1B", "D118_A1B", "D126_A1B", "D129_A1B",
			"D132_A1B", "D136_A1B", "D142_A1B", "D143_A1B", "D145_A1B", "D147_A1B", "D148_A1B", "D152_A1B", "D154_A1B",
			"D155_A1B", "D161_A1B", "D165_A1B", "D169_A1B", "D170_A1B", "D172_A1B", "D175_A1B",
			"D178_A1B", "D181_A1B", "D184_A1B", "D190_A1B", "D193_A1B", "D196_A1B"};

	String[]	outputNumLabel_C	= {
			"Stage_C", "C11-1_C", "C11-2_C", "C12-1_C", "C12-2_C", "C13_C", "C101_C", "C102_C", "C103_C", "C104_C",
			"C105_C", "C106_C", "C107_C", "C108_C", "C109_C", "C110_C", "C111_C", "C112_C", "C113_C", "C114_C",
			"C115_C", "C116_C", "C117_C", "C118_C", "C123_C", "C124_C", "C125_C", "C126_C", "C127_C", "C128_C", "A7-1_C",
			"A7-2_C", "A8-1_C", "A8-2_C", "A9_C", "A101_C", "A102_C", "A103_C", "A104_C", "A107_C", "A108_C", "A109_C",
			"A110_C", "A112_C", "A113_C", "A120_C", "A121_C", "A122_C", "A123_C", 
			"D14-1_C", "D14-2_C", "D14-3_C", "D15-1_C", "D15-2_C", "D15-3_C", "D16-1_C", "D16-2_C",
			"D16-3_C", "D17-1_C", "D17-2_C", "D103_C", "D106_C", "D108_C", "D109_C", "D112_C", "D113_C", "D116_C",
			"D117_C", "D119_C", "D121_C", "D123_C", "D125_C", "D128_C", "D130_C", "D131_C", "D134_C", "D135_C",
			"D137_C", "D139_C", "D141_C", "D143_C", "D144_C", "D145_C", "D146_C", "D147_C", "D148_C", "D149_C",
			"D150_C", "D151_C", "D152_C", "D153_C", "D154_C", "D155_C", "D156_C", "D157_C", "D158_C", "D159_C",
			"D162_C", "D163_C", "D166_C", "D167_C", "D169_C", "D170_C", "D173_C", "D174_C", "D176_C", "D177_C",
			"D179_C", "D180_C", "D182_C", "D183_C", "D185_C", "D186_C", "D188_C", "D189_C", "D191_C", "D192_C",
			"D193_C", "D194_C", "D195_C", "D196_C", "D197_C", "D198_C"};

	void calc_A(File tableFile, String orfName, PrintWriter out, PrintWriter numOut) throws SCMDException
	{
		FlatTable table_A = new FlatTable(tableFile);
		//String[] targetedParameterOfCV_A = {"C11-1_A", "C115_A", "A101_A",
		// "D14-1_A", "D105_A", "D147_A", "D182_A"};
		OutputResult result_A = calcOldFormat("A", table_A, outputLabel_A);

		String[] groupTypePattern_A = {"A", "B", "N"};
		String[] groupCountParameter_A = {"A105_A", "A106_A", "A113_A"};
		HashMap groupCount_A = countRatio(groupTypePattern_A, table_A, "Agroup");
		for (int i = 0; i < groupCountParameter_A.length; i++) 
			result_A.get_resultHash().put(groupCountParameter_A[i], groupCount_A.get(groupTypePattern_A[i]));

		// output result
		outputResult(orfName, out, outputLabel_A, result_A.get_resultHash());
		outputResult(orfName, numOut, outputNumLabel_A, result_A.get_numValidSampleHash());
	}

	void calc_A1B(File tableFile, String orfName, PrintWriter out, PrintWriter numOut) throws SCMDException
	{
		FlatTable table_A1B = new FlatTable(tableFile);
		//		String[] targetParameterOfCV_A1B = {
		//				"C11-1_A1B", "C11-2_A1B", "C105_A1B", "C106_A1B", "C109_A1B",
		// "C114_A1B", "C115_A1B", "C118_A1B",
		//				"A101_A1B", "A102_A1B", "A103_A1B", "A104_A1B", "D14-3_A1B",
		// "D107_A1B", "D114_A1B", "D147_A1B",
		//				"D184_A1B"};
		OutputResult result_A1B = calcOldFormat("A1B", table_A1B, outputLabel_A1B );
		String[] groupTypePattern_A1B = {"api", "iso", "E", "F", "api|iso", "N"};
		String[] groupCountParameter_A1B = {"A107_A1B", "A108_A1B", "A109_A1B", "A110_A1B", "A112_A1B", "A113_A1B"};
		HashMap groupCount_A1B = countRatio(groupTypePattern_A1B, table_A1B, "Agroup");
		for (int i = 0; i < groupCountParameter_A1B.length; i++)
			result_A1B.get_resultHash().put(groupCountParameter_A1B[i], groupCount_A1B.get(groupTypePattern_A1B[i]));

		String[] groupTypePattern2_A1B = {"small", "medium", "large"};
		String[] groupCountParameter2_A1B = {"C123_A1B", "C124_A1B", "C125_A1B"};
		HashMap groupCount2_A1B = countRatio(groupTypePattern2_A1B, table_A1B, "Cgroup");
		for (int i = 0; i < groupCountParameter2_A1B.length; i++)
			result_A1B.get_resultHash().put(groupCountParameter2_A1B[i], groupCount2_A1B.get(groupTypePattern2_A1B[i]));

		// output result
		outputResult(orfName, out, outputLabel_A1B, result_A1B.get_resultHash());
		outputResult(orfName, numOut, outputNumLabel_A1B, result_A1B.get_numValidSampleHash());

	}

	void calc_C(File tableFile, String orfName, PrintWriter out, PrintWriter numOut) throws SCMDException
	{
		FlatTable table_C = new FlatTable(tableFile);
		//		String[] targetParameterOfCV_C = {
		//				"C11-1_A1B", "C11-2_A1B", "C105_A1B", "C106_A1B", "C109_A1B",
		// "C114_A1B", "C115_A1B", "C118_A1B",
		//				"A101_A1B", "A102_A1B", "A103_A1B", "A104_A1B", "D14-3_A1B",
		// "D107_A1B", "D114_A1B", "D147_A1B",
		//				"D184_A1B"};
		OutputResult result_C = calcOldFormat("C", table_C, outputLabel_C);

		String[] groupTypePattern_C = {"api", "iso", "E", "F", "api|iso", "N"};
		String[] groupCountParameter_C = {"A107_C", "A108_C", "A109_C", "A110_C", "A112_C", "A113_C"};
		HashMap groupCount_C = countRatio(groupTypePattern_C, table_C, "Agroup");
		for (int i = 0; i < groupCountParameter_C.length; i++)
			result_C.get_resultHash().put(groupCountParameter_C[i], groupCount_C.get(groupTypePattern_C[i]));

		String[] groupTypePattern2_C = {"small", "medium", "large"};
		String[] groupCountParameter2_C = {"C123_C", "C124_C", "C125_C"};
		HashMap groupCount2_C = countRatio(groupTypePattern2_C, table_C, "Cgroup");
		for (int i = 0; i < groupCountParameter2_C.length; i++)
			result_C.get_resultHash().put(groupCountParameter2_C[i], groupCount2_C.get(groupTypePattern2_C[i]));

		// output result
		outputResult(orfName, out, outputLabel_C, result_C.get_resultHash());
		outputResult(orfName, numOut, outputNumLabel_C, result_C.get_numValidSampleHash());
	}

	/**
	 * groupTypePattern�Ŏw�肳���p�^�[�����A�e�[�u���̂���P�񒆂ɂǂꂾ���̊������邩���v�Z����
	 * 
	 * @param groupTypePattern
	 *            �J�E���g����p�^�[���̔z��
	 * @param inputTable
	 *            �J�E���g�������܂ރe�[�u��
	 * @param targetColumnLabel
	 *            count����A�e�[�u�����̃p�����[�^��
	 * @return
	 */
	private HashMap countRatio(String[] groupTypePattern, FlatTable inputTable, String targetColumnLabel)
	{
		// �o�����镶���񂲂ƂɃJ�E���g����i���̂Ƃ���groupTypePattern�ƈ�v���邩�͔��肵�Ȃ��j
		HashMap countResult = new HashMap();
		for (TableIterator ti = inputTable.getVerticalIterator(targetColumnLabel); ti.hasNext();)
		{
			String group = ((Cell) ti.nextCell()).toString();
			Integer count = (Integer) countResult.get(group);
			if(count != null)
				countResult.put(group, new Integer(count.intValue() + 1));
			else
				countResult.put(group, new Integer(1));
		}

		// patternCount - ���ʂ̏o�͐�
		HashMap patternCount = new HashMap();
		for (int i = 0; i < groupTypePattern.length; i++)
			patternCount.put(groupTypePattern[i], new Integer(0)); // ������

		// countResult ���̃p�^�[�����ǂ�groupTypePattern���̗v�f�Ɉ�v���邩�𒲂ׁA
		// �e�p�^�[���̏o���񐔂��ApatternCount�ɑ������
		Set entrySet = countResult.keySet();
		for (Iterator ei = entrySet.iterator(); ei.hasNext();)
		{
			String keyElem = (String) ei.next();
			for (int i = 0; i < groupTypePattern.length; i++)
			{
				if(keyElem.matches(groupTypePattern[i]))
				{
					String matchedPattern = groupTypePattern[i];
					int countToAdd = ((Integer) countResult.get(keyElem)).intValue(); // keyElem�̏o����
					int prevCount = ((Integer) patternCount.get(matchedPattern)).intValue(); // ���Ɍv�コ��Ă���J�E���g
					patternCount.put(matchedPattern, new Integer(prevCount + countToAdd));
				}
			}
		}

		// �����̌v�Z
		int numValidCell = _stat.countValidStringCell(inputTable.getVerticalIterator(targetColumnLabel));
		if(numValidCell < 1)
			numValidCell = 1;  // 0�Ŋ���Ȃ��悤�ɂ���
		for (int i = 0; i < groupTypePattern.length; i++)
		{
			int count = ((Integer) patternCount.get(groupTypePattern[i])).intValue();
			patternCount.put(groupTypePattern[i], new Double((double) count / numValidCell));
		}
		return patternCount;
	}

	//    String trimDoubleQuotation(String str)
	//    {
	//        if(str.startsWith("\""))
	//        {
	//            if(str.endsWith("\""))
	//                return str.substring(1, str.length() - 1);
	//            else
	//                return str;
	//        }
	//        else
	//            return str;
	//    }

	/**
	 * @param groupName
	 *            A, A1B, C�̂ǂꂩ���w��
	 * @param inputTable
	 *            �e�[�u��
	 * @param outputLabel
	 *            �o�͂���p�����[�^��
	 * @return OutputResult(�p�����[�^��->�v�Z���ʒl�̃n�b�V���A�p�����[�^��->�L���v�f�̃J�E���g �̃n�b�V��)
	 * @throws SCMDException
	 */
	protected OutputResult calcOldFormat(String groupName, FlatTable inputTable, String[] outputLabel)
	throws SCMDException
	{
		// A_data.xls �̃f�[�^�̏o��
		HashMap resultHash = new HashMap(); // �o�͌��ʂ�����R���e�i
		HashMap numValidSampleMap = new HashMap(); // �v�Z�Ɏg��ꂽ�T���v����������R���e�i
		
		// create a list of targeted parameter of calcurating averages and
		// counting the number of samples.
		Vector labelListOfOriginalTable = inputTable.getColLabelList();
		Vector parameter = new Vector();
		String regex = "[\"]?[^_]+_" + groupName + "[\"]?";
		for (Iterator vi = labelListOfOriginalTable.iterator(); vi.hasNext();)
		{
			String label = (String) vi.next();
			if(label.matches(regex)) // ������"_" + groupName���������̂����o��
				parameter.add(label);
			else
			{
				//   _log.println("parameter " + label + " is removed");
			}
		}

		// calc average
		for (Iterator vi = parameter.iterator(); vi.hasNext();)
		{
			String paramName = (String) vi.next();
			NumElementAndStatValuePair result = _stat.calcMeanAndNumSample(inputTable.getVerticalIterator(paramName));			
			resultHash.put(paramName, new Double(result.getValue()));
			numValidSampleMap.put(paramName, new Integer(result.getNumElement()));
		}

		LinkedList cvParameterList = new LinkedList();
		// outputLabel���ŁACV�̊܂܂�Ă�����̂����o��
		Pattern cvPattern = Pattern.compile("([A-Za-z]{1})CV([0-9][0-9-]*_(A|A1B|C))");
		for (int i = 0; i < outputLabel.length; i++)
		{
			Matcher m = cvPattern.matcher(outputLabel[i]);
			if(m.matches())
				cvParameterList.add(new String(m.group(1) + m.group(2))); // remove
			// CV
			// substring
		}
		// calc CV
		for (Iterator vi = cvParameterList.iterator(); vi.hasNext();)
		{
			String targetParameter = (String) vi.next();
			resultHash.put(getCVParameterName(targetParameter), new Double(_stat.calcCV(inputTable
					.getVerticalIterator(targetParameter))));
		}

		return new OutputResult(resultHash, numValidSampleMap);
	}

	/**
	 * @param args
	 * @return ��͂���ORF�̃f�B���N�g��
	 * @throws SCMDException
	 */
	public void setupByArguments(String[] args) throws SCMDException
	{
		setupOptionParser();

		_parser.getContext(args);

		//		Iterator argIterator = _parser.getArgumentList().iterator();
		//		if(!argIterator.hasNext())
		//			throw new SCMDException("no input directory is spcecified");

		if(_parser.isSet(OPT_HELP))
			printUsage(0);

		if(_parser.isSet(OPT_BASEDIR))
			_baseDirName = new String(_parser.getValue(OPT_BASEDIR));
		if(_parser.isSet(OPT_VERBOSE))
		{
			_log = System.out;
		}
		if(_parser.isSet(OPT_ELIMINATE))
		{
			// ���[�̂P����؂�헪�ɐ؂�ւ���
			_stat = new StatisticsWithMissingValueSupport(_missingValue, new EliminateOnePercentOfBothSidesStrategy());
			_eliminateSample = true;
		}
		if(_parser.isSet(OPT_SHORTVARIABLES))
		{
			// �o�͌��ʂ�CV�l���܂߂Ȃ�
			_shortVariables = true;
		}

		//return (String) argIterator.next();
	}

	void setupOptionParser() throws SCMDException
	{
		_parser.setOption(new Option(OPT_HELP, "h", "help", "diaplay help message"));
		_parser.setOption(new Option(OPT_VERBOSE, "v", "verbose", "display verbose messages"));
		_parser.setOption(new OptionWithArgument(OPT_BASEDIR, "b", "basedir", "DIR",
				"set input directory base (default = current directory)"));
		//		_parser.setOption(new Option(OPT_OLD_FORMAT, "", "oldformat",
		//				"output in the old format ({A, A1B, C}_data.xls, _datanum.xls)"));
		_parser.setOption(new Option(OPT_ELIMINATE, "e", "eliminate", "eliminates 1% of both sides of samples"));
		_parser.setOption(new Option(OPT_SHORTVARIABLES, "s", "short", "only output basic parameters"));
		//_parser.setRequirementForNonOptionArgument();
	}

	public void printUsage(int exitCode)
	{
		System.out.println("Usage: CalcGroupStat [option]");
		System.out.println(_parser.createHelpMessage());
		System.exit(exitCode);
	}

	public static void main(String[] args)
	{
		CalcGroupStat c = new CalcGroupStat();
		try
		{
			c.setupByArguments(args);
			c.loopForEachDirectory();
		}
		catch (SCMDException e)
		{
			e.what();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}
}
//--------------------------------------
// $Log: CalcGroupStat.java,v $
// Revision 1.22  2004/09/01 01:50:50  sesejun
// *** empty log message ***
//
// Revision 1.21  2004/09/01 01:34:00  sesejun
// outputLabel_ALL_C �Ƀ��x���̔������������̂�ǉ�
//
// Revision 1.20  2004/08/01 08:19:36  leo
// BasicTable��hasRowLabel��ǉ�
// XMLOutputter�ŁAjava.io.writer���g����悤�ɕύX
// �iJSP��writer��java.io.Writer�̔h���N���X�̂���)
//
// Revision 1.19  2004/07/27 05:18:12  leo
// TableIterator�̔�����
//
// Revision 1.18  2004/07/06 04:37:26  nakatani
// "Infinity","NaN"��missing_value�ɒǉ��B���̕��ϒl��Infinity�ƂȂ�̂�h�����߁B
//
// Revision 1.17  2004/07/05 09:08:51  sesejun
// group�p�����[�^��CV�l���v�Z���Ă��܂��Ă����̂�
// �����B
//
// Revision 1.16  2004/06/23 08:16:24  sesejun
// �\�Ȃ��ׂẴp�����[�^�ŁACV�l���v�Z����悤�ɕύX�B
// �]���̃p�����[�^�́A-s �Ōv�Z���邱�Ƃ��ł���B
//
// Revision 1.15  2004/06/01 08:08:25  nakatani
// �o�̓t�@�C������ύX�i_data ���폜�A_datanum --> _num�j�@
//
// Revision 1.14  2004/05/31 23:28:02  nakatani
// missing value ��"-1.0"��ǉ�
//
// Revision 1.13  2004/05/07 04:30:26  leo
// ���Ԍv���p�N���X��ǉ�
//
// Revision 1.12  2004/05/07 03:06:20  leo
// Statistics�N���X���A�f�[�^�̃t�B���^�����O�̐헪��؂�ւ�����悤�ɕύX
//
// Revision 1.11 2004/05/06 09:27:32 leo
// FlatTable�Ń��x����ǂݍ��񂾂Ƃ��Adouble quotation����菜���悤�ɂ����B
// Excel�}�N�����琶�����ꂽ�t�@�C���́A���܂�double quoation���Ńp�����[�^�����o�͂���邽��
//
// Revision 1.10 2004/05/06 08:05:41 leo
// CV�l�̌v�Z���̃o�O�C��
//
// Revision 1.9 2004/05/06 06:10:34 leo
// ���b�Z�[�W�o�͗p��NullPrintStream��ǉ��B
// ���v�l�v�Z�����͊����B�o�͕����͂��ꂩ��
//
// Revision 1.8 2004/05/05 16:27:50 leo
// �R�}���h���C����parse���̃G���[(LackOfArgumentException)��ǉ��B
// SCMDException�ɁA�G���[�\���̊ȕ։��̂���what()���\�b�h��ǉ��B
//
// Revision 1.7 2004/05/05 15:56:48 leo
// A1B, C_data.xls�v�Z������ǉ�
//
// Revision 1.6 2004/05/04 15:11:32 leo
// A_data.xls�̌v�Z������ǉ� �i�v�e�X�g�j
//
// Revision 1.5 2004/05/03 15:59:46 leo
// fomatting
//
// Revision 1.4 2004/04/30 06:00:55 leo
// temporary commit
//
// Revision 1.3 2004/04/30 02:25:52 leo
// OptionParser�Ɉ����̗L�����`�F�b�N�ł���@�\��ǉ�
// setRequirementForNonOptionArgument()
//
// Revision 1.2 2004/04/27 16:01:08 leo
// �O���[�v���̃t�@�C�������ATableFileName class�ɒ��o
//
// Revision 1.1 2004/04/27 09:02:03 leo
// *** empty log message ***
//
//--------------------------------------
