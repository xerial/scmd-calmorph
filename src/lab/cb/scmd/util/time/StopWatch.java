//--------------------------------------
// SCMD Project
// 
// Timer.java 
// Since:  2004/05/07
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.time;

import java.io.PrintStream;
import java.util.Date;

/** ���Ԃ��v������̂ɕ֗��ȃN���X
 * @author leo
 *
 */
public class StopWatch
{

	/**
	 * 
	 */
	public StopWatch()
	{
		reset();
	}
	
	public void reset()
	{
		_date = new Date();
		_beginTime = _date.getTime();
	}
	
	public String elapsed()
	{
		Date current = new Date();
		long currentTime = current.getTime();
		long miliSec = currentTime - _beginTime;
		double sec = (double) miliSec / 1000;
		return Double.toString(sec);
	}
	
	public void showElapsedTime(PrintStream out)
	{
		out.println("elapsed time: " + elapsed() + " sec.");
	}
	
	Date _date;
	long _beginTime; 

}


//--------------------------------------
// $Log: StopWatch.java,v $
// Revision 1.2  2004/05/07 04:37:04  leo
// StopWatch�̕\���P�ʂ��C��
//
// Revision 1.1  2004/05/07 04:30:26  leo
// ���Ԍv���p�N���X��ǉ�
//
//--------------------------------------