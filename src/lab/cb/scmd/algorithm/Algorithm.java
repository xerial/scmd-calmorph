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
	static public <E> Collection<E> select(Collection<E> inputCollection, Collection<E> outputCollection, UnaryPredicate<E> predicate)
	{
	    for(E element : inputCollection)
        {
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
	static public <E, F> Collection<F> transform(Collection<E> inputCollection, Collection<F> outputCollection, Transformer<E, F> transformer)
	{
        for(E element : inputCollection)
        {
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
	static public <E, F> Collection<F> selectiveTransform(Collection<E> inputCollection, Collection<F> outputCollection, SelectiveTransformer<E, F> selectiveTransformer)
	{
		for(E element : inputCollection)
		{
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
	static public <E> int count(Collection<E> inputCollection, UnaryPredicate<E> predicate)
	{
	    int count = 0;
	    for(E element : inputCollection)
	    {
	        if(predicate.isTrue(element))
	              count++;
	    }
	    return count;
	}
	
	/** inputCollection���̑S�Ă̗v�f��predicate�𖞂������ǂ����𒲂ׂ�
	 * @param inputColletion 
	 * @param predicate
	 * @return
	 */
	static public <E> boolean satisfy(Collection<E> inputCollection, UnaryPredicate<E> predicate)
	{
	    for(E element : inputCollection)
	    {
	        if(!predicate.isTrue(element))
	            return false;    
	    }
	    return true;
	}
	
	
	/** inputCollection�̒��ŁA�ŏ���predicate�𖞂������̂�Ԃ�
	 * @param <E>
	 * @param inputCollection ����
	 * @param predicate  ����
	 * @return inputCollection�̒��ŁA�ŏ���predicate�𖞂������̂�Ԃ�
	 */
	static public <E> E find(Collection<E> inputCollection, UnaryPredicate<E> predicate)
	{
	    for(E element : inputCollection)
	    {
	        if(!predicate.isTrue(element))
	            return element;
	    }
	    return null;
	}
	
	
	
	/** 2��Collection����v���邩�ǂ����𔻒肷��
	 * @param input1
	 * @param input2
	 * @param binaryPredicate �Q������v���邩�ǂ����𔻒肷��BinaryPredicate
	 * @return �^�U�l
	 */
	static public <E> boolean equal(Collection<E> input1, Collection<E> input2, BinaryPredicate<E, E> binaryPredicate)
	{
	    Iterator<E> it1 = input1.iterator();
	    Iterator<E> it2 = input2.iterator();
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
	static public <E> void foreach(Collection<E> input, Functor<E> functor) 
	{
	   for(E element : input)
	   {
	       functor.apply(element);
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