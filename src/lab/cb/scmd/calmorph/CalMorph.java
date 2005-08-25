package lab.cb.scmd.calmorph;

//------------------------------------
// SCMD Project
//  
// CalMorph.java 
// Since:  2004/04/16
//
// $URL$ 
// $LastChangedBy$ 
//--------------------------------------

import java.io.File;
import java.util.Vector;

class CalMorph {
    boolean outstate,objectsave,outimage,subdir;
    int maximage,objectload;
    String name,path,outdir,xmldir;

    public CalMorph() {
    }
   
    public static void main(String[] args) {
        CalMorph cm = new CalMorph();

        //	�R�}���h���C���w���v��\������
        if(args.length == 1) {
            System.err.println("CalMorph");
            System.err.println("gui usage: java CalMorph");
            System.err.println("cui usage: java CalMorph [OPTION] inputdir outputdir xmldir");
            System.err.println("OPTION:");
            System.err.println("  --outstate");
            System.err.println("  --objectsave");
            System.err.println("  --objectload=value");
            System.err.println("  --subdir");	//	�T�u�f�B���N�g��
            System.exit(1);
        //	GUI�ŋN������
        } else if(args.length == 0) {//����0��GUI�N��
            GUIFrame gui = new GUIFrame();
            gui.setVisible(true);
        //	�p�����[�^�G���[
		} else if(args[args.length-3].substring(0,1).equals("-")) {
			System.err.println("directory name \""+args[args.length-3]+"\" is not allowed");
			System.exit(1);
		//	�p�����[�^�G���[
		} else if(args[args.length-2].substring(0,1).equals("-")) {
			System.err.println("directory name \""+args[args.length-2]+"\" is not allowed");
			System.exit(1);
		//	�p�����[�^�G���[
		} else if(args[args.length-1].substring(0,1).equals("-")) {
			System.err.println("directory name \""+args[args.length-1]+"\" is not allowed");
			System.exit(1);
		//	CUI�ŋN������
		} else {
			cm.CUI(args);
		}
	}
    
    public void CUI(String args[]) {
        File in=null,out=null,xml=null;

    	//option�̃f�t�H���g�l
		this.maximage = 200;
		this.outstate = false;
		this.objectsave = false;
		this.subdir = false;
		this.objectload = -1;
		for(int i=0;i<args.length-3;i++) {
			this.optionSearch(args[i]);
		}
		
		in = new File(args[args.length-3]);
		out = new File(args[args.length-2]);
		xml = new File(args[args.length-1]);
		if(in.exists()) {
			this.name = in.getName();
			this.path = in.getAbsolutePath()+"/"+in.getName();
		} else {
			System.err.println("error:"+in.getName()+" not exist");
			System.exit(1);
		}
		if(out.exists()) {
			this.outdir = out.getAbsolutePath();
		} else {
			out.mkdir();
			this.outdir = out.getAbsolutePath();
		}
		if(xml.exists()) {
			this.xmldir = xml.getAbsolutePath();
		} else {
			xml.mkdir();
			this.xmldir = xml.getAbsolutePath();
		}
			
		//	�w�肳�ꂽinputdir�̂Ȃ��̃f�B���N�g����Ώۂɂ���
		if(subdir) {		
			File infiles[] = in.listFiles();
			Vector<File> dirs = new Vector<File>();
			for(int i=0;i<infiles.length;i++) {
				if(infiles[i].isDirectory() && !infiles[i].isHidden()){
					System.out.println(infiles[i].getAbsolutePath());
					dirs.add(infiles[i]);
				}
			}
	        for(int i=0;i<dirs.size();i++) {
	    		//	.jpg�̐��𐔂���
	    		String ls[] = dirs.get(i).list();
	    		for (int j = 0; j < ls.length; j++)
	    		{
	    			if(ls[j].substring(ls[j].length()-4,ls[j].length()).equals(".jpg")){
	    				int k=ls[j].length()-5;
	    				while(ls[j].charAt(k) != 'A' && ls[j].charAt(k) != 'C' && ls[j].charAt(k) != 'D' && k>0) k--;
	    				if(k>0){
	    					int l = Integer.parseInt(ls[j].substring(k+1,ls[j].length()-4));
	    					if(l>this.maximage) this.maximage = l;
	    				}
	    			}
	    		}

	    		//	�f�B���N�g����
	        	String name = dirs.get(i).getName();
	        	//	�f�B���N�g���p�X
	            String path = dirs.get(i).getAbsolutePath()+"/"+dirs.get(i).getName();
	            //	�v�Z����
	            DisruptantProcess dp = new DisruptantProcess(name,path,outdir,null,maximage,objectsave,objectload,null,outstate,true,true,outimage,true);
				if(i==0) dp.setPrintFile("GUI");
	            dp.process();
	        }
		} else {
			//	.jpg�̐��𐔂���
			String ls[] = in.list();
			for (int i = 0; i < ls.length; i++)
			{
				if(ls[i].substring(ls[i].length()-4,ls[i].length()).equals(".jpg")){
					int j=ls[i].length()-5;
					while(ls[i].charAt(j) != 'A' && ls[i].charAt(j) != 'C' && ls[i].charAt(j) != 'D' && j>0) j--;
					if(j>0){
						int k = Integer.parseInt(ls[i].substring(j+1,ls[i].length()-4));
						if(k>this.maximage) this.maximage = k;
					}
				}
			}

			//	�v�Z����
			DisruptantProcess dp = new DisruptantProcess(name,path,outdir,xmldir,maximage,objectsave,objectload,null,outstate,true,true,outimage,true);
			dp.setPrintFile("CUI");
			dp.process();
		}
    }

	public void optionSearch(String option) {
		if(option.length() == 10 && option.substring(0,10).equals("--outstate")) {
			outstate=true;
			System.err.println("outstate");
		} else if(option.length() == 12 && option.substring(0,12).equals("--objectsave")) {
			objectsave=true;
			System.err.println("objectsave");
		} else if(option.length() >= 13 && option.substring(0,13).equals("--objectload=")) {
			objectload = Integer.parseInt(option.substring(13));
			System.err.println("objectload="+objectload);
		} else if(option.length() == 10 && option.substring(0,10).equals("--outimage")) {
			outimage=true;
			System.err.println("outimage");
		} else if(option.length() == 8 && option.substring(0,8).equals("--subdir")) {
			subdir=true;
			System.err.println("subdir");
		} else {
			System.err.println("Unrecognized option:"+option);
			System.exit(1);
		}
	}
}

//--------------------------------------
//$Log: CalMorph.java,v $
//Revision 1.6  2004/09/06 13:44:06  sesejun
//CalMorph�̂����炭������1_0�̃\�[�X
//
//Revision 1.5  2004/07/20 01:44:22  sesejun
//���p���Ă��Ȃ�import���폜
//
//Revision 1.4  2004/07/01 10:23:29  sesejun
//�\�����ƌv�Z����������x����
//
//Revision 1.3  2004/06/30 17:14:21  sesejun
//�v�Z�����Əo�͕����̕����B
//�G���[��f���ۂ�
//
//Revision 1.2  2004/06/25 01:42:05  sesejun
//CalMorph_version_1.0�����֕ύX
//�ꕔ�A�ύX�����ɂ��ǂ��Ă��܂��Ă���\����
//�C��������A�ēx�ύX���Ă��������B
//