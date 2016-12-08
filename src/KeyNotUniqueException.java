/**
 * Created by nera_gatta on 31.10.2016.
 */
public class KeyNotUniqueException extends Exception{
    private static final long serialVersionUID = 1L;

    public KeyNotUniqueException (String key) {
        super (new String("Key is not unique" + key));
    }
}
