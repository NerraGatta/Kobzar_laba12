import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Created by nera_gatta on 31.10.2016.
 */
public class IndexOneToOne implements Serializable, IndexBase {
    private static final long serialVersionUID = 1L;
    private TreeMap<String, Long> map;

    public IndexOneToOne() {
        map = new TreeMap<>();
    }

    public String[] getKeys (Comparator<String> comp) {
        String[] result = map.keySet().toArray(new String[0]);
        Arrays.sort(result, comp);
        return result;
    }

    public void put (String key, long value) {
        map.put(key, value); // new Long(value)
    }

    public boolean contains (String key) {
        return map.containsKey(key);
    }

    public long[] get (String key) {
        long pos = this.map.get(key); //((Long)this.map.get(key)).longValue()
        return new long[]{pos};
    }
}
