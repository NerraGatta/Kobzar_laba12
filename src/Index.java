import java.io.*;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by nera_gatta on 31.10.2016.
 */
public class Index implements Serializable, Closeable {
    private static final long serialVersionUID = 1L;
    IndexOneToN numberBuildings;
    IndexOneToN numberFlats;
    IndexOneToOne FIO;
    IndexOneToN dates;
    private transient String filename = null;

    public Index(){
        numberBuildings = new IndexOneToN();
        numberFlats = new IndexOneToN();
        FIO = new IndexOneToOne();
        dates = new IndexOneToN();
    }

    public static long[] InsertValue( long[] arr, long value) {
        int length = (arr == null) ? 0 : arr.length;
        long[] result = new long[length + 1];

        for (int i=0; i<length; ++i) {
            result[i] = arr[i];
        }

        result[length] = value;
        return result;
    }

    public void test (Bill bill) throws KeyNotUniqueException {
        assert bill != null;

        if (FIO.contains(bill.getFIO())) {
            throw new KeyNotUniqueException(bill.getFIO());
        }
    }

    public void put (Bill bill, long value) throws KeyNotUniqueException {
        test(bill);
        numberBuildings.put(Integer.toString(bill.getNumberBuilding()), value);
        numberFlats.put(Integer.toString(bill.getNumberFlat()), value);
        FIO.put(bill.getFIO(), value);
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dates.put((bill.getDateOfPayment()).format(dtf), value);
    }

    public void save (String name) {
        filename = name;
    }

    public void saveAs ( String name ) throws IOException {
        FileOutputStream file = new FileOutputStream( name );
        try (ZipOutputStream zos  = new ZipOutputStream(file)) {
            zos.putNextEntry( new ZipEntry(Buffer.zipEntryName));
            zos.setLevel(ZipOutputStream.DEFLATED);
            try(ObjectOutputStream oos = new ObjectOutputStream(zos)) {
                oos.writeObject(this);
                oos.flush();
                zos.closeEntry();
                zos.flush();
            }
        }
    }

    public static Index load (String name) throws IOException, ClassNotFoundException {
        Index obj = null;
        try {
            FileInputStream file = new FileInputStream(name);
            try (ZipInputStream zis = new ZipInputStream(file)) {
                ZipEntry zen = zis.getNextEntry();
                if (!zen.getName().equals(Buffer.zipEntryName)) {
                    throw new IOException("Invalid block format");
                }
                try (ObjectInputStream ois = new ObjectInputStream(zis)) {
                    obj = (Index) ois.readObject();
                }
            }
        }
        catch (FileNotFoundException e) {
            obj = new Index();
        }
        if (obj != null) {
            obj.save(name);
        }
        return obj;
    }

    public void close() throws IOException {
        saveAs( filename );
    }

}
