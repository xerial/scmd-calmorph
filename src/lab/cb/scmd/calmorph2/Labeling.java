package lab.cb.scmd.calmorph2;

import java.util.Vector;

public class Labeling {
	
	private int _width, _height, _size, _size_threshold;
	private boolean _corner_cut;
	
	private Vector<Integer>[] _constituent_pixels_of_each_label;
	private boolean[] _label_validity;
	
	public Labeling(int width, int size, int size_threshold, boolean corner_cut) {
		_width = width;
		_height = size / width;
		_size = size;
		_size_threshold = size_threshold;
		_corner_cut = corner_cut;
	}
    
	/**
	 * �A���������Ƃɔԍ������x�����O
	 * @param binary_image
	 * @param color
	 * @return : label�ԍ����ƂɁA���̑S�\��pixel��o�^����Vector�A��v�f�Ɏ��z���Ԃ��B
	 */
	public Vector<Integer>[] label(boolean[] binary_image, boolean color) {
        NumberLabeling num_label = new NumberLabeling(_width, _size);
        num_label.executeNumberLabeling(binary_image, color);
        
        int max_label = num_label.resetSameLabelsAndGetMaxLabel();
        setConstituentPixelsOfEachLabel(max_label, num_label);
        
        int number_of_valid_labels = eliminateLabelsOnImageCornerAndSmallLabels(max_label);
        return getConstituentPixelsOfValidLabels(number_of_valid_labels, max_label);
    }
	
	/**
	 * label�ԍ����ƂɁA���̑S�\��pixel��o�^�B
	 * �o�^�� : Vector<Integer>[] _constituent_pixels_of_each_label
	 * @param max_label
	 * @param num_label
	 */
	protected void setConstituentPixelsOfEachLabel(int max_label, NumberLabeling num_label) {
		_constituent_pixels_of_each_label = new Vector[max_label + 1];
        for ( int i = 0; i < max_label + 1; i++ ) { _constituent_pixels_of_each_label[i] = new Vector<Integer>(); }
        for ( int i = 0; i < _size; i++ ) {
            if ( num_label.getLabeled()[i] >= 0 ) {
            	_constituent_pixels_of_each_label[( (Integer)(num_label.getSameLabels().get( num_label.getLabeled()[i] )) ).intValue()].add(new Integer(i));
            }
        }
	}
	
	/**
	 * label�ԍ����ƂɁA���̑S�\��pixel��o�^�B�ivalid labels only version�j
	 * @param number_of_valid_labels
	 * @param max_label
	 * @return
	 */
	protected Vector<Integer>[] getConstituentPixelsOfValidLabels(int number_of_valid_labels, int max_label) {
		Vector<Integer>[] result = new Vector[number_of_valid_labels];
        int index = 0;
        
        for ( int i = 0; i < result.length; i++ ) {
            result[i] = new Vector<Integer>();
            while ( index <= max_label ) {
                if ( _label_validity[index] ) { break; }
                index++;
            }
            if ( index <= max_label ) {
                for ( int p : _constituent_pixels_of_each_label[index] ) { result[i].add(p); }
                index++;
            } else { break; }
        }
        
        return result;
	}
	
	/**
	 * ���x�����O���ꂽ�A�������̓��A�摜�̉��ɐڂ��Ă�����̂ƁA�T�C�Y��臒l�ȉ��̂��̂��폜����B
	 * @param max_label
	 * @return : �c���� �i�摜�̉��ɐڂ��Ă��炸�A�T�C�Y��臒l�ȏ�́j ���x�����O���ꂽ�A�������̐�
	 */
	protected int eliminateLabelsOnImageCornerAndSmallLabels(int max_label) {
		int number_of_valid_labels = 0;
        _label_validity = new boolean[max_label + 1];
        
        for ( int i = 0 ; i < max_label + 1; i++ ) {
            if ( _constituent_pixels_of_each_label[i].size() > _size_threshold ) {
                if ( !_corner_cut ) {
                    _label_validity[i] = true;
                    number_of_valid_labels++;
                } else {
                    if ( !eliminateLabelsOnImageCorner(i) ) { number_of_valid_labels++; }
                }
            } else { _label_validity[i] = false; }
        }
        return number_of_valid_labels;
	}
	
	/**
	 * ���郉�x�����O���ꂽ�A���������摜�̉��ɐڂ��Ă���ꍇ�A���̘A�����������O����B
	 * _label_validity[i] = false �Ƃ���B
	 * @param i
	 * @return : ���O����ꍇ�ATRUE��Ԃ��B
	 */
	protected boolean eliminateLabelsOnImageCorner(int i) {
		_label_validity[i] = true;
        for ( int p : _constituent_pixels_of_each_label[i] ) {
            if ( onImageCorner(p) ) {
                _label_validity[i] = false;
                return true;
            }
        }
        return false;
	}
	
	/**
	 * ���郉�x�����O���ꂽ�A���������A�摜�̉��ɐڂ��Ă���ꍇTRUE��Ԃ��B
	 * @param p
	 * @return
	 */
	protected boolean onImageCorner(int p) {
		if ( p < _width || p >= _width * (_height - 1) || p % _width <= 0 || p % _width >= _width - 1 ) { return true; }
		else { return false; }
	}
}
