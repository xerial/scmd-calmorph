�O����
ssh-agent�̐ݒ�����Ă����ƕ֗��ł��B
# �N���X�^�̃}�V���Ƀ��O�C�������Ƃ��ɁA
# ���������p�X���[�h�𕷂���Ȃ�����
- ���̐ݒ�
% ssh-keygen -t rsa
% cd $HOME/.ssh/
% cat id_dsa.pub > authorized_keys2
- ����ssh-agent�Ɋo��������B
% ssh-agent <your shell>
% ssh-add
�� <your shell>�̎q�v���Z�X�ŁAssh�̌����g���܂��B

- �R���p�C��

(���Ԃ�(��))
% rmic CalMorphDespatcher
% javac CalMorphClient

- �T�[�o�̎��s 

calmorph_his3server.sh �����������܂��B
rmiregistry �̈����̃p�X�ɂ́Aclass���������ꏊ���w�肵�Ă��������B
java �̈����ŁA
java.security.policy�ɂ́Ajava.policy�t�@�C���̏ꏊ���A
java.rmi.server.codebase�ɂ́Aclass�̏ꏊ���w�肵�Ă��������B
# �����Ashell�ϐ��ɂ���΂悩�����B
- 
calmorph_his3server.sh���N�� ���܂��B

�Ō�Armiregistry�ƁA�T�[�o��java���c��̂ŁA
pstree �������Ńv���Z�X�����āA��ԏ�ʂ̃v���Z�X��
kill���Ă��������B

- �N���C�A���g�̎��s
�p�X�ƃT�[�o(�f�t�H���g��cb01)��K���ɏ��������āA
���s�������N���C�A���g��
/home/sesejun/work/scmd/data/his3/calmorph_his3despatcher.sh 
���N��
