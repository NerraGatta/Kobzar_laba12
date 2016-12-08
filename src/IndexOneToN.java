import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Created by nera_gatta on 31.10.2016.
 */
public class IndexOneToN implements Serializable, IndexBase {
    private static final long serialWersionUID = 1L;
    private TreeMap<String, long[]> map;

    public IndexOneToN() {
         map = new TreeMap<>();
    }

    public String[] getKeys (Comparator<String> comp) {
        String[] result = map.keySet().toArray( new String[0]);
        Arrays.sort(result, comp);
        return result;
    }

    public void put (String key, long value) {
        long[] arr = map.get(key);
        arr = (arr != null) ? Index.InsertValue(arr, value): new long[]{value};
        map.put(key, arr);
    }

    public  void put (String keys, String keyDel, long value) {
        /*StringTokenizer st = new StringTokenizer(keys, keyDel);
        int num = st.countTokens();

        for (int i = 0; i < num; ++i) {
            String key = st.nextToken();
            key = key.trim();
            this.put(key, value);
        }*/
        String[] splitKeys = keys.split(keyDel);
        for ( String str: splitKeys) {
            String key = str;
            key = key.trim();
            put(key, value);
        }
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public long[] get(String key) {
        return map.get(key);
    }
}
