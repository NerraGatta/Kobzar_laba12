import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;

public class Bills {
    
    private static void delete_backup() {
        new File(MainForm.getFilenameBackPath()).delete();
        new File(MainForm.getFilenameIdxBackPath()).delete();
    }
    
    static void delete_file() {
        delete_backup();
        new File(MainForm.getFilenamePath()).renameTo(new File(MainForm.getFilenameBackPath()));
        new File(MainForm.getFilenameIdxPath()).renameTo(new File(MainForm.getFilenameIdxBackPath()));
    }
    
    private static void backup() {
        delete_backup();
        new File(MainForm.getFilenamePath()).renameTo(new File(MainForm.getFilenameBackPath()));
        new File(MainForm.getFilenameIdxPath()).renameTo(new File(MainForm.getFilenameIdxBackPath()));
    }
    
    static boolean delete_file(String mode, String key)
            throws ClassNotFoundException, IOException, KeyNotUniqueException, IllegalArgumentException {
        if (mode.equals("owner")) {
            if (key.split(" ").length != 3) {
                throw new IllegalArgumentException("Invalid data!");
            }
        }
        
        if (key.isEmpty()) {
            throw new IllegalArgumentException("You forgot to write a key!");
        }
        
        long[] poss = null;
        try (Index idx = Index.load(MainForm.getFilenameIdxPath())) {
            IndexBase pidx = indexByArg(mode, idx);
            if (pidx == null) {
                return false;
            }
            if (!pidx.contains(key)) {
                System.err.println("Key not found: " + key); //throws exception
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
    
    private static void printRecordSorted(JTextPane textPane, RandomAccessFile raf, String key, IndexBase pidx) throws ClassNotFoundException,
            IOException, BadLocationException {
        long[] poss = pidx.get(key);
        for (long pos : poss) {
            String text ="*** Key: " + key + " points to";
            printRecord(raf, pos, textPane, text);
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
    
    static void printFile(JTextPane textPane) throws IOException, ClassNotFoundException, BadLocationException {
        long pos;
        int rec = 0;
        
        String text = null;
        try (RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            while ((pos = raf.getFilePointer()) < raf.length()) {
                text = "#" + (++rec);
                printRecord(raf, pos, textPane, text);
            }
        }
    
        if (text == null) {
            throw new IOException("File is empty!");
        }
    }
    
    private static IndexBase indexByArg(String arg, Index idx) {
        IndexBase pidx = null;
        if (arg.equals("BuildingNumber")) {
            pidx = idx.numberBuildings;
        } else if (arg.equals("ApartmentNumber")) {
            pidx = idx.numberFlats;
        } else if (arg.equals("Owner")) {
            pidx = idx.FIO;
        } else if (arg.equals("PaymentDate")) {
            pidx = idx.dates;
        } else {
            System.err.println("Invalid index specified: " + arg);
        }
        return pidx;
    }
    
    static boolean printFileSorted (JTextPane textPane, String mode, Boolean reverse) throws BadLocationException,
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
    
    static boolean findByKey (JTextPane textPane, String mode, String key)
            throws  IOException, ClassNotFoundException, IllegalArgumentException, BadLocationException{
        if (mode.equals("f")) {
            if(key.split(" ").length != 3) {
                throw new IllegalArgumentException("Invalid key!");
            }
        }
        
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            IndexBase pidx = indexByArg(mode, idx);
            if (!pidx.contains(key)) {
                throw new IllegalArgumentException("Key not found: " + key);
            }
            printRecordSorted(textPane, raf, key, pidx);
        }
        return true;
    }
    
    static boolean findByKey (JTextPane textPane, String mode, String key, Comparator<String> comparator)
            throws ClassNotFoundException, IOException, IllegalArgumentException, BadLocationException {
        if (mode.equals("f")) {
            if(key.split(" ").length != 3) {
                throw new IllegalArgumentException("Invalid key!");
            }
        }
        
        try (Index idx = Index.load(MainForm.getFilenameIdxPath());
             RandomAccessFile raf = new RandomAccessFile(MainForm.getFilenamePath(), "rw")) {
            IndexBase pidx = indexByArg(mode, idx);
            if (!pidx.contains(key)) {
                throw new IllegalArgumentException("Key not found: " + key);
            }
            String[] keys = pidx.getKeys(comparator);
            for (String keyInKeys : keys) {
                if (keyInKeys.equals(key)) {
                    break;
                }
                printRecordSorted(textPane, raf, keyInKeys, pidx);
            }
        }
        return true;
    }
}
