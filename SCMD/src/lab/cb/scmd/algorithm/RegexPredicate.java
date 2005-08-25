//--------------------------------------
// SCMD Project
// 
// RegexPredicate.java 
// Since:  2004/06/24
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.algorithm;

import java.util.regex.*;

/** ���K�\���Ƀ}�b�`������̂Ȃ�Atrue�Ɣ��肷��
 * �����p�^�[�����J��Ԃ��g�p����Ƃ��A���̃N���X���g���ƁA
 * ���x���p�^�[���̃R���p�C�������Ȃ��Ă���
 * �܂��A���K�\����match�̌���(getMatcher()�Ŏ擾)���g���ĂȂ�炩�̏o�͂𓾂����Ȃ�Atransform��override����Ɨǂ�
 * @author leo
 *
 */
public class RegexPredicate implements SelectiveTransformer
{	
	public RegexPredicate(String regularExpression)
	{
		_regexPattern = Pattern.compile(regularExpression);
	}	
	
	public boolean isTrue(Object input)
	{
		_patternMatcher = _regexPattern.matcher((String) input);
		return _patternMatcher.matches();
	}
	
	public Object transform(Object input)
	{
	    return input;
	}
	
	public Matcher getMatcher() 
	{
		return _patternMatcher;
	}
		
	protected Pattern _regexPattern;
	protected Matcher _patternMatcher = null;
}


//--------------------------------------
// $Log: RegexPredicate.java,v $
// Revision 1.2  2004/06/24 01:16:01  leo
// UnaryPredicate�̎�������ASelectiveTransformer�̎����ɕύX
//
// Revision 1.1  2004/06/23 16:31:58  leo
// Collection����̂��߂�lab.cb.scmd.algorithm�p�b�P�[�W��ǉ�
//
//--------------------------------------