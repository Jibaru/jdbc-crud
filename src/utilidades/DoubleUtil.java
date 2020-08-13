
package utilidades;

public class DoubleUtil {
    
    public static Double parseString(String s) {
        Double dob;
        
        if(s.isBlank() || s.isEmpty()) {
            System.out.println("El string está en blanco");
            return null;
        }
        
        try {
            dob = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return dob;
    }
    
}
