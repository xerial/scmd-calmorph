//--------------------------------------
// SCMD Project
// 
// Algorithm.java 
// Since:  2004/06/23
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

package lab.cb.scmd.algorithm;

import java.util.*;


/** Collection�ɉ��H�������邽�߂̃A���S���Y���Q (C++��STL��)
 * @author leo
 *
 */
public class Algorithm 
{
	/** inputCollection�̂����Apredicate.isTrue() �������������݂̂̂� outputCollection�ɒǉ�����
	 * @param inputCollection
	 * @param outputCollection
	 * @param predicate
	 * @return
	 */
	static public Collection select(Collection inputCollection, Collection outputCollection, UnaryPredicate predicate)
	{
		for(Iterator it = inputCollection.iterator(); it.hasNext(); )
		{
			Object element = it.next();
			if(predicate.isTrue(element))
				outputCollection.add(element);
		}
		return outputCollection;
	}
	
	/** inputCollection�̗v�f���ꂼ��ɁAtransformer.transform() ��K�p���āA���ʂ�outputCollection�ɒǉ�����
	 * @param inputCollection
	 * @param outputCollection
	 * @param transformer
	 * @return
	 */
	static public Collection transform(Collection inputCollection, Collection outputCollection, Transformer transformer)
	{
		for(Iterator it = inputCollection.iterator(); it.hasNext(); )
		{
			Object element = it.next();
			outputCollection.add(transformer.transform(element));
		}		
		return outputCollection;
	}
	
	/** inputCollection�̗v�f�̂����AselectiveTransformer.isTrue() �𖞂������̂ɁAselectiveTransformer.transform()��K�p���āA
	 * ���ʂ�outputCollection�ɒǉ�����
	 * @param inputCollection
	 * @param outputCollection
	 * @param selectiveTransformer
	 * @return
	 */
	static public Collection selectiveTransform(Collection inputCollection, Collection outputCollection, SelectiveTransformer selectiveTransformer)
	{
		for(Iterator it = inputCollection.iterator(); it.hasNext(); )
		{
			Object element = it.next();
			if(selectiveTransformer.isTrue(element))
				outputCollection.add(selectiveTransformer.transform(element));
		}
		return outputCollection;		
	}
	
	
	/** inputCollection���ŁApredicate�𖞂������̂̌����J�E���g����
	 * @param inputCollection
	 * @param predicate
	 * @return predicate�𖞂����v�f�̐�
	 */
	static public int count(Collection inputCollection, UnaryPredicate predicate)
	{
	    int count = 0;
	    for(Iterator it = inputCollection.iterator(); it.hasNext(); )
	    {
	        if(predicate.isTrue(it.next()))
	              count++;
	    }
	    return count;
	}
	
	/** inputCollection���̑S�Ă̗v�f��predicate�𖞂������ǂ����𒲂ׂ�
	 * @param inputColletion 
	 * @param predicate
	 * @return
	 */
	static public boolean satisfy(Collection inputColletion, UnaryPredicate predicate)
	{
	    for(Iterator it = inputColletion.iterator(); it.hasNext(); )
	    {
	        if(!predicate.isTrue(it.next()))
	            return false;    
	    }
	    return true;
	}
	
	
	static public Object find(Collection inputCollection, UnaryPredicate predicate)
	{
	    for(Iterator it = inputCollection.iterator(); it.hasNext(); )
	    {
	        Object elem = it.next();
	        if(!predicate.isTrue(elem))
	            return elem;
	    }
	    return null;
	}
	
	
	
	/** 2��Collection����v���邩�ǂ����𔻒肷��
	 * @param input1
	 * @param input2
	 * @param binaryPredicate �Q������v���邩�ǂ����𔻒肷��BinaryPredicate
	 * @return �^�U�l
	 */
	static public boolean equal(Collection input1, Collection input2, BinaryPredicate binaryPredicate)
	{
	    Iterator it1 = input1.iterator();
	    Iterator it2 = input2.iterator();
	    for( ; it1.hasNext(); )
	    {
	        if(!it2.hasNext())
	            return false;
	        
	        if(!binaryPredicate.isTrue(it1.next(), it2.next()))
	            return false;
	    }
	    if(it2.hasNext())
	        return false;
	    else
	        return true;
	}
	
	static public boolean equal(Collection input, Object[] comparisonTarget)
	{
	    if(input.size() != comparisonTarget.length)
	        return false;
	    Iterator it = input.iterator();
	    for(int i=0; i<comparisonTarget.length; i++){
	        if(!it.hasNext())
	            return false;
	        if(!comparisonTarget[i].equals(it.next()))
	            return false;
	    }
	    return true;
	}

	
	static public boolean equal(Collection input, int[] comparisonTarget)
	{
	    if(input.size() != comparisonTarget.length)
	        return false;
	    
	    Iterator it = input.iterator();
	    for(int i=0; i<comparisonTarget.length; i++){
	        if(!it.hasNext())
	            return false;
	        int intVal = ((Integer) it.next()).intValue();

	        if(intVal != comparisonTarget[i])
	            return false;
	    }
	    return true;
	}
	
	static public boolean equal(Collection input, double[] comparisonTarget)
	{
	    if(input.size() != comparisonTarget.length)
	        return false;
	    
	    Iterator it = input.iterator();
	    for(int i=0; i<comparisonTarget.length; i++){
	        if(!it.hasNext())
	            return false;
	        double doubleVal = ((Double) it.next()).doubleValue();

	        if(doubleVal != comparisonTarget[i])
	            return false;
	    }
	    return true;
	}


	/** input�̊e�v�f�ɑ΂��A functor.apply��K�p����
	 * @param input
	 * @param functor
	 */
	static public void foreach(Collection input, Functor functor) 
	{
	   Iterator it = input.iterator();
	   for( ; it.hasNext(); )
	   {
	       functor.apply(it.next());
	   }
	}
	
	
	/** �^����ꂽCollection�ɁAadd ���\�b�h�ŁA�z�񂩂珉���l����͂��Ă���
	 * @param targetCollection �����l����͂���collection 
	 * @param initialValue �����l�̔z��
	 * @return targetCollection
	 */
	static public Collection initializeCollection(Collection targetCollection, int[] initialValue)
	{
	    for(int i=0; i<initialValue.length; i++)
	    {
	        targetCollection.add(new Integer(initialValue[i]));
	    }
	    return targetCollection;
	}

	static public Collection initializeCollection(Collection targetCollection, double[] initialValue)
	{
	    for(int i=0; i<initialValue.length; i++)
	    {
	        targetCollection.add(new Double(initialValue[i]));
	    }
	    return targetCollection;
	}

	/**
	 * @param targetCollection
	 * @param initialValue
	 * @return
	 */
	static public Collection initializeCollection(Collection targetCollection, String[] initialValue)
	{
	    for(int i=0; i<initialValue.length; i++)
	    {
	        targetCollection.add(initialValue[i]);
	    }
	    return targetCollection;
	}
}


//--------------------------------------
// $Log: Algorithm.java,v $
// Revision 1.6  2004/07/22 07:09:38  leo
// satisfy, find��ǉ�
//
// Revision 1.5  2004/06/24 03:45:22  leo
// Algorithm�ɁAcount, equal,��ǉ�
//
// Revision 1.4  2004/06/24 02:28:41  leo
// ���K�\���p��Predicate�̃e�X�g��ǉ�
//
// Revision 1.3  2004/06/24 02:09:14  leo
// ComparisonPredicate�̊���
//
// Revision 1.2  2004/06/24 01:47:35  leo
// Collection��initializer��ǉ�
//
// Revision 1.1  2004/06/23 16:31:58  leo
// Collection����̂��߂�lab.cb.scmd.algorithm�p�b�P�[�W��ǉ�
//
//--------------------------------------