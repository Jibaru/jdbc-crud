
package conexion.bd;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    
    private static String url = "jdbc:mysql://localhost/bd_pedido_ejemplo";
    private static String usuario = "root";
    private static String clave = "root";
    private static Connection conn;
    
    public static Connection abrir() {
        
        try {
            // Registrar driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // Abrir conexión
            conn = DriverManager.getConnection(url, usuario, clave);
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return conn;
        
    }
}
