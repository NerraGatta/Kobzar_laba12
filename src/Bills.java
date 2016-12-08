import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by nera_gatta on 26.10.2016.
 */
public class Bills {
    static boolean optionZip = true;
    
    public static void main(String[] args) {
        try {
            if (args.length >= 1) {
                if (args[0].equals("-?") || args[0].equals("-h")) {
                    System.out.println(
                            "Syntax:\n" +
                                    "\t-a  [file [encoding]]       - append data\n" +
                                    "\t-az [file [encoding]]       - append data, compress every record\n" +
                                    "\t-d                          - clear all data\n" +
                                    "\t-dk  {n-b|n-f|f|d} key      - clear data by key\n" +
                                    "\t-p                          - print data unsorted\n" +
                                    "\t-ps  {n-b|n-f|f|d}          - print data sorted by number of building/number of flat/FIO/ payment date\n" +
                                    "\t-psr {n-b|n-f|f|d}          - print data reverse sorted by number of building/number of flat/FIO/ payment date\n" +
                                    "\t-f   {n-b|n-f|f|d} key      - find record by key\n" +
                                    "\t-fr  {n-b|n-f|f|d} key      - find records > key\n" +
                                    "\t-fl  {n-b|n-f|f|d} key      - find records < key\n" +
                                    "\t-?, -h                      - command line syntax\n"
                    );
                } else if (args[0].compareTo(("-a")) == 0) {
                    // Append file with new object from System.in
                    // -a [file [encoding]]
                    append_file(args, false);
                } else if (args[0].equals("-az")) {
                    // Append file with compressed new object from System.in
                    // -az [file [encoding]]
                    append_file(args, true);
                } else if (args[0].compareTo("-p") == 0) {
                    //Prints data file
                    printFile();
                } else if (args[0].equals("-ps")) {
                    // Prints data file sorted by key
                    if (!printFile(args, false)) {
                        System.exit(1);
                    }
                } else if (args[0].equals("-psr")) {
                    // Prints data file reverse-sorted by key
                    if (!printFile(args, true)) {
                        System.exit(1);
                    }
                } else if (args[0].compareTo("-d") == 0) {
                    // delete files
                    if (args.length != 1) {
                        System.err.println("Invalid number of arguments");
                        System.exit(1);
                        ;
                    }
                    delete_file();
                } else if (args[0].equals("-dk")) {
                    // Delete records by key
                    if (!delete_file(args)) {
                        System.exit(1);
                    }
                } else if (args[0].equals("-f")) {
                    // Find record(s) by key
                    if (!findByKey(args)) {
                        System.exit(1);
                    }
                } else if (args[0].equals("-fr")) {
                    // Find record(s) by key large then key
                    if (!findByKey(args, new KeyCompReverse())) {
                        System.exit(1);
                    }
                } else if (args[0].equals("-fl")) {
                    // Find record(s) by key less then key
                    if (!findByKey(args, new KeyComp())) {
                        System.exit(1);
                    }
                } else {
                    System.err.println("Option is not realised: " + args[0]);
                    System.exit(1);
                }
            } else {
                System.err.println("Bills: Nothing to do! Enter -? for options");
            }
        } catch (Exception e) {
            System.err.println("Run/time error: " + e);
            System.exit(1);
        }
        System.out.println("Bills are finished...");
        System.exit(0);
    }
    
    //input file encoding
    private static String encoding = "Cp866";
    private static PrintStream billOut = System.out;
    
    //private static Scanner fin = new Scanner( System.in );
    
    //private static BufferedReader br = new BufferedReader( new InputStreamReader (System.in));
    
    static Bill read_bill(Scanner fin) throws IOException {
        return Bill.nextRead(fin, billOut) ? Bill.read(fin, billOut) : null;
    }
    
    private static void delete_backup() {
        new File(MainForm.getFilenameBackPath()).delete();
        new File(MainForm.getFilenameIdxBackPath()).delete();
    }
    
    static void delete_file() {
        delete_backup();
        new File(MainForm.getFilenamePath()).delete();
        new File(MainForm.getFilenameIdxPath()).delete();
    }
    
    private static void backup() {
        delete_backup();
        new File(MainForm.getFilenamePath()).renameTo(new File(MainForm.getFilenameBackPath()));
        new File(MainForm.getFilenameIdxPath()).renameTo(new File(MainForm.getFilenameIdxBackPath()));
    }
    
    static boolean delete_file(String arg, String key) throws ClassNotFoundException, IOException, KeyNotUniqueException {
        long[] poss = null;
        try (Index idx = Index.load(MainForm.getFilenameIdxPath())) {
            IndexBase pidx = indexByArg(arg, idx);
            if (pidx == null) {
                return false;
            }
            if (!pidx.contains(key)) {
                System.err.println("Key not found: " + key);
                return false;
            }
            poss = pidx.get(key);
        }
        backup();
        Arrays.sort(poss);
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile fileBack = new RandomAccessFile(MainForm.getFilenameBackPath(), "rw");
             RandomAccessFile file = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            boolean[] wasZipped = new boolean[]{false};
            long pos;
            while ((pos = fileBack.getFilePointer()) < fileBack.length()) {
                Bill bill = (Bill) Buffer.readObject(fileBack, pos, wasZipped);
                if (Arrays.binarySearch(poss, pos) < 0) { // if not found in deleted
                    long ptr = Buffer.writeObject(file, bill, wasZipped[0]);
                    idx.put(bill, ptr);
                }
                
            }
        }
        return true;
        
    }
    
    static boolean delete_file(String[] args)
            throws ClassNotFoundException, IOException, KeyNotUniqueException {
        //-dk  {i|a|n} key      - clear data by key
        if (args.length != 3) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        
        long[] poss = null;
        try (Index idx = Index.load(MainForm.getFilenameIdxPath())) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (pidx == null) {
                return false;
            }
            if (!pidx.contains(args[2])) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            poss = pidx.get(args[2]);
        }
        backup();
        Arrays.sort(poss);
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile fileBack = new RandomAccessFile(MainForm.getFilenameBackPath(), "rw");
             RandomAccessFile file = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            boolean[] wasZipped = new boolean[]{false};
            long pos;
            while ((pos = fileBack.getFilePointer()) < fileBack.length()) {
                Bill bill = (Bill) Buffer.readObject(fileBack, pos, wasZipped);
                if (Arrays.binarySearch(poss, pos) < 0) { // if not found in deleted
                    long ptr = Buffer.writeObject(file, bill, wasZipped[0]);
                    idx.put(bill, ptr);
                }
                
            }
        }
        return true;
    }
    
    static void append_file(Boolean zipped, Bill bill) throws IOException, ClassNotFoundException, KeyNotUniqueException {
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            
            if (bill == null) {
                return;
            }
            
            idx.test(bill);
            long pos = Buffer.writeObject(raf, bill, zipped);
            idx.put(bill, pos);
        }
    }
    
    static void append_file(String[] args, Boolean zipped)
            throws IOException, ClassNotFoundException,
            KeyNotUniqueException {
        if (args.length >= 2) {
            FileInputStream stdin = new FileInputStream(args[1]);
            System.setIn(stdin);
            if (args.length == 3) {
                encoding = args[2];
            }
            // hide output:
            billOut = new PrintStream("null");
        }
        append_file(zipped);
    }
    
    static void append_file(Boolean zipped)
            throws FileNotFoundException, IOException, ClassNotFoundException,
            KeyNotUniqueException {
        Scanner fin = new Scanner(System.in, encoding);
        billOut.println("Enter bill data: ");
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            for (; ; ) {
                Bill bill = read_bill(fin);
                if (bill == null)
                    break;
                idx.test(bill);
                long pos = Buffer.writeObject(raf, bill, zipped);
                idx.put(bill, pos);
            }
        }
    }
    
    private static void printRecord(RandomAccessFile raf, long pos)
            throws ClassNotFoundException, IOException {
        boolean[] wasZipped = new boolean[]{false};
        Bill bill = (Bill) Buffer.readObject(raf, pos, wasZipped);
        if (wasZipped[0] == true) {
            System.out.print(" compressed");
        }
        System.out.println(" record at position " + pos + ": \n" + bill);
    }
    
    private static void printRecord(RandomAccessFile raf, String key, IndexBase pidx)
            throws ClassNotFoundException, IOException {
        long[] poss = pidx.get(key);
        for (long pos : poss) {
            System.out.print("*** Key: " + key + " points to");
            printRecord(raf, pos);
        }
    }
    
    private static void printRecordSorted(JTextPane textPane, RandomAccessFile raf, String key, IndexBase pidx) throws ClassNotFoundException,
            IOException, BadLocationException {
        long[] poss = pidx.get(key);
        for (long pos : poss) {
            appendString(textPane,"*** Key: " + key + " points to");
            printRecord(raf, pos);
        }
    }
    
    private static void printRecord(RandomAccessFile raf, long pos, JTextPane textPane, String text) throws
            IOException, ClassNotFoundException, BadLocationException {
        boolean[] wasZipped = new boolean[]{false};
        Bill bill = (Bill) Buffer.readObject(raf, pos, wasZipped);
        if (wasZipped[0] == true) {
            text += " compressed";
        }
        text += " record at position " + pos + ": \n" + bill;
        
        appendString(textPane, text + "\n");
        //textPane.setText(text+"\n");
    }
    
    static void appendString(JTextPane textPane, String str) throws BadLocationException {
        StyledDocument styledDocument = textPane.getStyledDocument();
        styledDocument.insertString(styledDocument.getLength(), str, null);
    }
    
    static void printFile()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        long pos;
        int rec = 0;
        try (RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            while ((pos = raf.getFilePointer()) < raf.length()) {
                System.out.print("#" + (++rec));
                printRecord(raf, pos);
            }
            System.out.flush();
        }
    }
    
    static void printFile(JTextPane textPane) throws IOException, ClassNotFoundException, BadLocationException {
        long pos;
        int rec = 0;
        try (RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            while ((pos = raf.getFilePointer()) < raf.length()) {
                String text = "#" + (++rec);
                printRecord(raf, pos, textPane, text);
            }
        }
    }
    
    private static IndexBase indexByArg(String arg, Index idx) {
        IndexBase pidx = null;
        if (arg.equals("n-b")) {
            pidx = idx.numberBuildings;
        } else if (arg.equals("n-f")) {
            pidx = idx.numberFlats;
        } else if (arg.equals("f")) {
            pidx = idx.FIO;
        } else if (arg.equals("d")) {
            pidx = idx.dates;
        } else {
            System.err.println("Invalid index specified: " + arg);
        }
        return pidx;
    }
    
    private static boolean printFileSorted (JTextPane textPane, String mode, Boolean reverse) throws BadLocationException,
                IOException, ClassNotFoundException {
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            IndexBase pidx = indexByArg(mode, idx);
            if (pidx == null) {
                return false;
            }
            String[] keys =
                    pidx.getKeys(reverse ? new KeyCompReverse() : new KeyComp());
            for (String key : keys) {
                printRecordSorted(textPane, raf, key, pidx);
            }
        }
        return true;
        
    }
    
    static boolean printFile(String[] args, boolean reverse)
            throws ClassNotFoundException, IOException {
        if (args.length != 2) {
            System.err.println("Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (pidx == null) {
                return false;
            }
            String[] keys =
                    pidx.getKeys(reverse ? new KeyCompReverse() : new KeyComp());
            for (String key : keys) {
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }
    
    static boolean findByKey(String[] args)
            throws ClassNotFoundException, IOException {
        if (args.length != 3) {
            if (args[1].equals("f")) {
                for (int i = 3; i < args.length; i++) {
                    args[2] += " " + args[i];
                }
            } else {
                System.err.println("Invalid number of arguments");
                return false;
            }
        }
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (!pidx.contains(args[2])) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            printRecord(raf, args[2], pidx);
        }
        return true;
    }
    
    static boolean findByKey(String[] args, Comparator<String> comp)
            throws ClassNotFoundException, IOException {
        if (args.length != 3) {
            if (args[1].equals("f")) {
                for (int i = 3; i < args.length; i++) {
                    args[2] += " " + args[i];
                }
            } else {
                System.err.println("Invalid number of arguments");
                return false;
            }
        }
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (!pidx.contains(args[2])) {
                System.err.println("Key not found: " + args[2]);
                return false;
            }
            String[] keys = pidx.getKeys(comp);
            for (String key : keys) {
                if (key.equals(args[2])) {
                    break;
                }
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }
}
