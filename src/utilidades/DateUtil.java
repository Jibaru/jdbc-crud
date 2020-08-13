
package utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    
    public static String formatDate(Date date, String formato) {
        SimpleDateFormat f = new SimpleDateFormat(formato);
        return f.format(date);
    }
    
    public static Date toDate(String d) {
        String[] fs = d.split("/");
        int dia = Integer.parseInt(fs[0]);
        int mes = Integer.parseInt(fs[1]);
        int anio = Integer.parseInt(fs[2]);

        return new Date(anio - 1900, mes - 1, dia);
    }
}
