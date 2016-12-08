import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Created by nera_gatta on 31.10.2016.
 */
public class KeyCompReverse implements Comparator<String>{

    public int compare (String str1, String str2) {
        try {
            Integer i1 = Integer.parseInt(str1);
            Integer i2 = Integer.parseInt(str2);

            return  i2.compareTo(i1);
        }
        catch ( Exception eInt ){
            try {
                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate ld1 = LocalDate.parse(str1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate ld2 = LocalDate.parse(str2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                return  ld2.compareTo(ld1);
            }
            catch ( Exception eLD) {
                return str2.compareTo(str1);
            }
        }
    }

}
