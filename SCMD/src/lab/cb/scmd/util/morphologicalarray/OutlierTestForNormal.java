//--------------------------------------
// SCMD Project
// 
// OutlierTest.java 
// Since:  2004/08/30
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.util.morphologicalarray;

/**
 * @author nakatani
 *
 */
public class OutlierTestForNormal {
	Double[] data;
	
	public OutlierTestForNormal(Double[] givenData){
		data=givenData;
		getEandSD(data);
	}
	//Double[] criticalValue;
	/**
	 * critical value �̃e�[�u�����Z�b�g����B
	 * ����n�T���v���̏ꍇ�Ɋg�����鎞�̂��߁B
	 * 
	 * @author nakatani
	 *  
	 */	
	private void setCriticalValue(){
	}
	
	/**
	 * �^����ꂽ�T���v�����ɑ΂���critical value ��Ԃ��B
	 * �Ƃ肠�����T���v�����P�Q�U���P�Q�V�̏ꍇ�����ɑΉ��B
	 * �i���ۂɂ�criticalValue�̓T���v�����P�Q�O�̎��̒l�B�{���͕�Ԃ���3.68���炢���H�j
	 * 
	 * @author nakatani
	 *  
	 */
	private double getCriticalValue(int nSample){
		/*if(nSample<126||127<nSample){
			System.err.println("error. �T���v����"+nSample+"�ɑ΂���critical value��m��܂���B");
			System.exit(-1);
		}*/
		//return 3.66;
		return 3.0902;//upper 0.001
		//return 3.7190;//upper 0.0001
	}
	
	double E;
	double SD;
	double MAX;
	double MIN;
	/**
	 * ���ϒl�ƕW���΍��Amax,min���v�Z����B
	 * 
	 * @author nakatani
	 *  
	 */
	private void getEandSD(Double[] data){
		if(data.length<2){
			System.err.println("Error in OutlierTestForNormal.getEandSD() data.length="+data.length);
			System.exit(-1);
		}
		double tmp_exp=0;
		double sum_of_squares=0;
		int n=0;
		MAX=data[0].doubleValue();
		MIN=MAX;
		for(int i=0;i<data.length;++i){
			double x=data[i].doubleValue();
			if(MAX<x)MAX=x;
			if(x<MIN)MIN=x;
			x-=tmp_exp;
			tmp_exp+=x/(double)(i+1);
			sum_of_squares+=i*x*x/(i+1);
		}
		sum_of_squares=Math.sqrt(sum_of_squares/(data.length-1));
		
		E=tmp_exp;//expectation
		SD=(sum_of_squares==0.0)?-1:sum_of_squares;//standard deviation
	}


	private double N1_testOfUpperOutlier(double x){
		//if(x<MAX)return 0.0;
		return (x-E)/SD;
	}
	private double N1_testOfLowerOutlier(double x){
		//if(MIN<x)return 0.0;
		return (E-x)/SD;
	}
	
	public boolean isUpperOutlier(double x){
		if(N1_testOfUpperOutlier(x)>=getCriticalValue(data.length))return true;
		else return false;
	}
	public boolean isLowerOutlier(double x){
		if(N1_testOfLowerOutlier(x)>=getCriticalValue(data.length))return true;
		else return false;
	}
}


//--------------------------------------
// $Log: OutlierTestForNormal.java,v $
// Revision 1.3  2004/09/18 23:10:18  nakatani
// *** empty log message ***
//
// Revision 1.2  2004/09/03 06:02:09  nakatani
// *** empty log message ***
//
// Revision 1.1  2004/08/29 20:22:38  nakatani
// MorphologicalArray�̂��߂́A����N���X�Ɖ摜�o�̓N���X�B
//
//--------------------------------------