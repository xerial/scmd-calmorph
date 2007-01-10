package lab.cb.scmd.calmorph2;

import java.util.Vector;

public class NumberLabeling {
	
	private int _width, _height, _label_number;
	private int[] _labeled;
	private Vector<Integer> _same_labels;
	
	public NumberLabeling(int width, int size) {
		_width = width;
		_height = size / width;
		_label_number = 0;
		
		_labeled = new int[size];
        for ( int i = 0; i < _labeled.length; i++ ) { _labeled[i] = -1; }
        
        _same_labels = new Vector<Integer>();
        //for ( int i = 0; i < same_labels.size(); i++ ) { _same_labels.add(same_labels.get(i)); }
	}
	
	public int getLabelNumber() {
		return _label_number;
	}
	
	public int[] getLabeled() {
		return _labeled;
	}
	
	public Vector<Integer> getSameLabels() {
		return _same_labels;
	}
	
	public void setSameLabels(int i) {
		_same_labels.add(i);
	}
	
	/**
	 * ��l���摜��A���������ƂɃ��x�����O
	 * @param binary_image : ��l�摜
	 * @param color : ����\��boolean
	 */
    public void executeNumberLabeling(boolean[] binary_image, boolean color) {
        if ( binary_image[0] == color ) {
            _labeled[0] = _label_number;
            _same_labels.add(new Integer(_label_number++));
        }
        for ( int j = 1; j < _width; j++ ) {
            if ( binary_image[j] == color ) {
                if ( _labeled[j-1] >= 0 ) { _labeled[j] = _labeled[j-1]; }
                else {
                    _labeled[j] = _label_number;
                    _same_labels.add(new Integer(_label_number++));
                }
            }
        }
        for (int i = 1; i < _height; i++ ) {
            if ( binary_image[i * _width] == color ) {
                if ( _labeled[(i-1) * _width] >= 0 ) { _labeled[i * _width] = _labeled[(i-1) * _width]; }
                else {
                    _labeled[i * _width] = _label_number;
                    _same_labels.add(new Integer(_label_number++));
                }
            }
            for ( int j = 1; j < _width; j++ ) {
                if ( binary_image[i * _width + j] == color ) {
                    int left_label, upper_label;
                    
                    if ( _labeled[i * _width + j - 1] >= 0 ) { left_label  = smallestlabel(_same_labels, _labeled[i * _width + j - 1]); }
                    else left_label = -1;
                    
                    if ( _labeled[(i-1) * _width + j] >= 0 ) { upper_label = smallestlabel(_same_labels, _labeled[(i-1) * _width + j]); }
                    else upper_label = -1;
                    
                    if ( left_label == -1 && upper_label == -1 ) {
                        _labeled[i * _width + j] = _label_number;
                        _same_labels.add(new Integer(_label_number++));
                    } else if ( left_label == -1 ) {
                        _labeled[i * _width + j] = upper_label;
                    } else if ( upper_label == -1 ) {
                        _labeled[i * _width + j] = left_label;
                    } else if ( left_label < upper_label ) {
                        _labeled[i * _width + j] = left_label;
                        _same_labels.set(upper_label, new Integer(left_label));
                    } else {
                        _labeled[i * _width + j] = upper_label;
                        _same_labels.set(left_label, new Integer(upper_label));
                    }
                }
            }
        }
    }
    
    /**
     * ����A��������\��label�ԍ��̒��̍ŏ��l��Ԃ��B
     * �����ɁAsame_labels�x�N�^�[���A����A�������̒��̍ŏ��l�𒼐ڎ����l�ɏC���B�i�폜�j
     * @param same_labels
     * @param label_number
     * @return
     */
    public static int smallestlabel(Vector<Integer> same_labels, int label_number) {
        int result = label_number;
        Vector<Integer> temp = new Vector<Integer>();
        while ( true ) {
            if ( ((Integer)same_labels.get(result)).intValue() == result ) {
                /* same_labels�̏C���́A��ňꊇ�ōs���B
            	for ( int i = 0; i < temp.size(); i++ ) {
                    same_labels.set( ((Integer)temp.elementAt(i) ).intValue(), new Integer(result) );
                }*/
                return result;
            } else {
                temp.add( new Integer(result) );
                result = ( (Integer)same_labels.get(result) ).intValue();
            }
        }
    }
    
    /**
     * same_labels�x�N�^�[���A����A�������̒��̍ŏ��l�𒼐ڎ����l�ɏC���B
     * �����ɁAreset���label�̍ő�l��Ԃ��B
     * @param same_labels
     * @return : reset���label�̍ő�l
     */
    public static int resetSameLabelsAndGetMaxLabel(Vector<Integer> same_labels) {
    	int result = -1;
        for ( int i = 0; i < same_labels.size(); i++ ) {
            int smallest_label = smallestlabel(same_labels, i);
            if ( result < smallest_label ) { result = smallest_label; }
            same_labels.set(i, new Integer(smallest_label));
        }
        return result;
    }
}
