//--------------------------------------
//SCMD Project
//
//CountNumCells.java
//Since: 2004/04/27
//
//$URL: http://phenome.gi.k.u-tokyo.ac.jp/devel/svn/phenome/trunk/SCMD/src/lab/cb/scmd/autoanalysis/grouping/CalcGroupStat.java $ 
//$LastChangedBy: leo $ 
//--------------------------------------

package lab.cb.scmd.autoanalysis.grouping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import lab.cb.scmd.exception.SCMDException;
import lab.cb.scmd.util.cui.Option;
import lab.cb.scmd.util.cui.OptionParser;
import lab.cb.scmd.util.cui.OptionWithArgument;
import lab.cb.scmd.util.io.NullPrintStream;
import lab.cb.scmd.util.stat.EliminateOnePercentOfBothSidesStrategy;
import lab.cb.scmd.util.stat.StatisticsWithMissingValueSupport;
import lab.cb.scmd.util.time.StopWatch;

/**
 * @author sesejun
 * 
 * Complex以外のCellの数を数えるクラス
 */
public class CountNumCells {

    OptionParser        _parser             = new OptionParser();
    PrintStream         _log                = new NullPrintStream();

    // option IDs
    final static int    OPT_HELP            = 0;
    final static int    OPT_VERBOSE         = 1;
    final static int    OPT_BASEDIR         = 2;
    final static int    OPT_OUTFILE         = 3;

    String              _baseDirName        = ".";
    String              _outputFileName     = "cellNum.xls";
    boolean             _isVerbose          = false;

    public CountNumCells() {
        
    }
    
    public CountNumCells(String baseDirName) {
        _baseDirName = baseDirName;
    }

    public void loopForEachDirectory() throws SCMDException, IOException
    {
        StopWatch globalTime = new StopWatch();
        
        File inputDir = new File(_baseDirName);
        if(!inputDir.isDirectory())
            throw new SCMDException("base directory: " + _baseDirName + " doesn't exist");

        // output file
        PrintStream resultOut = new PrintStream(new FileOutputStream(_outputFileName));
        _log.println("output file:    \t" + _outputFileName);

        // output parameter labels
        //outputLabel(resultOut, new String[] {"orf", "count"});
        resultOut.println("orf\tcount");

        File[] fileList = inputDir.listFiles();
        for (int j = 0; j < fileList.length; j++)
        {
            if(!fileList[j].isDirectory())
                    continue;

            // directory名からORFを切り出す
            String orfName = fileList[j].getName();
            File orfFile = new File(orfName);
            
            String inputTableFile = _baseDirName + File.separator + orfName + File.separator + orfName + ".xls";
            _log.print(" reading \t" + inputTableFile + "               \r");
            File file = new File(inputTableFile);
            if(!file.exists()) {
                _log.println("\nskip this file because of no existance");
                continue;
            }
            int count = calc_count(inputTableFile);
            
            resultOut.println(orfName + "\t" + count);
//            System.out.println(count);
        }
        resultOut.close();
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
    
    int calc_count(String inputTableFile) throws SCMDException {
        CalMorphTable table = new CalMorphTable(inputTableFile);
        int cgroupIdx = table.getColIndex("Cgroup");
        int numcount = 0;
        for(int row = 0; row < table.getRowSize(); row++ ) {
            if( !table.getCell(row, cgroupIdx).toString().equals("complex") ) {
                numcount++;
            }
        }
        return numcount;
    }

    void setupOptionParser() throws SCMDException
    {
        _parser.setOption(new Option(OPT_HELP, "h", "help", "diaplay help message"));
        _parser.setOption(new Option(OPT_VERBOSE, "v", "verbose", "display verbose messages"));
        _parser.setOption(new OptionWithArgument(OPT_BASEDIR, "b", "basedir", "DIR",
                "set input directory base (default = current directory)"));
        _parser.setOption(new OptionWithArgument(OPT_OUTFILE, "o", "output", "FILE", "set output file (default = cellNum.xls)"));
    }
    
    public void setupByArguments(String[] args) throws SCMDException
    {
        setupOptionParser();
        _parser.getContext(args);
        if(_parser.isSet(OPT_HELP))
            printUsage(0);

        if(_parser.isSet(OPT_BASEDIR))
            _baseDirName = new String(_parser.getValue(OPT_BASEDIR));
        if(_parser.isSet(OPT_OUTFILE))
            _outputFileName = new String(_parser.getValue(OPT_OUTFILE));
        if(_parser.isSet(OPT_VERBOSE))
        {
            _log = System.out;
        }
    }

    void printUsage(int exitCode)
    {
        System.out.println("Usage: > java -jar CountNumCells.jar [options] orf_directory");
        System.out.println(_parser.createHelpMessage());
        System.exit(exitCode);
    }
    
    public static void main(String[] args) {
        CountNumCells cells = new CountNumCells();
        try {
            cells.setupByArguments(args);
            cells.loopForEachDirectory();
        } catch (SCMDException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
