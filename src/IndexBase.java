import java.util.Comparator;

/**
 * Created by nera_gatta on 31.10.2016.
 */
public interface IndexBase {

    String[] getKeys(Comparator<String> comp);

    void put (String key, long value);

    boolean contains (String key);

    long[] get (String key);
}
