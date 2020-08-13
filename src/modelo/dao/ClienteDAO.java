
package modelo.dao;

import conexion.bd.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.bean.Cliente;

public class ClienteDAO {
    
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static Connection cn;
    
    public static int insertar(Cliente c) { 
        
        cn = Conexion.abrir();
        
        String sql = "INSERT INTO Cliente(nombre, ap_paterno, ap_materno, dni, telefono) VALUES (?, ?, ?, ?, ?)"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApPaterno());
            ps.setString(3, c.getApMaterno());
            ps.setString(4, c.getDni());
            ps.setString(5, c.getTelefono());
            
            resultado = ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            
            if(rs.next()) {
                c.setId(rs.getInt(1));
            }
            
        } catch (SQLException ex) { 
            System.out.println(ex.getMessage()); 
        } finally {
            try {

                if(cn != null) cn.close();
                if(ps != null) ps.close(); 
                if(rs != null) rs.close();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado; 
    }
    
    public static int modificar(Cliente c) { 
        
        cn = Conexion.abrir();
        
        String sql = "UPDATE Cliente SET nombre = ?, ap_paterno = ?, ap_materno = ?, dni = ?, telefono = ? WHERE id_cliente = ?"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApPaterno());
            ps.setString(3, c.getApMaterno());
            ps.setString(4, c.getDni());
            ps.setString(5, c.getTelefono());
            ps.setInt(6, c.getId());
            
            resultado = ps.executeUpdate();
            
        } catch (SQLException ex) { 
            System.out.println(ex.getMessage()); 
        } finally {
            try {

                if(cn != null) cn.close();
                if(ps != null) ps.close(); 
                if(rs != null) rs.close();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado; 
    }
    
    public static int eliminar(int idCliente) { 
        
        cn = Conexion.abrir();
        
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setInt(1, idCliente);
            
            resultado = ps.executeUpdate();
            
        } catch (SQLException ex) { 
            System.out.println(ex.getMessage()); 
        } finally {
            try {

                if(cn != null) cn.close();
                if(ps != null) ps.close(); 
                if(rs != null) rs.close();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado; 
    }
    
    public static Cliente obtenerUno(int idCliente) { 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT * FROM Cliente WHERE id_cliente = ?"; 
        
        Cliente cli = null;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setInt(1, idCliente);
            
            rs = ps.executeQuery();
            
            if (rs.next()) { 

                cli = new Cliente(); 

                cli.setId(rs.getInt("id_cliente")); 
                cli.setNombre(rs.getString("nombre"));
                cli.setApPaterno(rs.getString("ap_paterno"));
                cli.setApMaterno(rs.getString("ap_materno"));
                cli.setDni(rs.getString("dni"));
                cli.setTelefono(rs.getString("telefono"));
                
            } 
            
        } catch (SQLException ex) { 
            System.out.println(ex.getMessage()); 
        } finally {
            try {

                if(cn != null) cn.close();
                if(ps != null) ps.close(); 
                if(rs != null) rs.close();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return cli; 
    }
    
    public static ArrayList<Cliente> listarTodos() { 
        
        ArrayList<Cliente> clientes = new ArrayList<>(); 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT * FROM Cliente"; 
        
        Cliente cli;
        
        try { 
            
            ps = cn.prepareStatement(sql); 
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                cli = new Cliente(); 

                cli.setId(rs.getInt("id_cliente")); 
                cli.setNombre(rs.getString("nombre"));
                cli.setApPaterno(rs.getString("ap_paterno"));
                cli.setApMaterno(rs.getString("ap_materno"));
                cli.setDni(rs.getString("dni"));
                cli.setTelefono(rs.getString("telefono")); 

                clientes.add(cli); 
            } 
            
        } catch (SQLException ex) { 
            System.out.println(ex.getMessage()); 
        } finally {
            try {

                if(cn != null) cn.close();
                if(ps != null) ps.close(); 
                if(rs != null) rs.close();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return clientes; 
    }
    
    public static ArrayList<Cliente> listarPorNombreCompletoDni(String nombreComp, String dni) {
        ArrayList<Cliente> clientes = new ArrayList<>(); 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT * FROM Cliente WHERE (UPPER(nombre) LIKE UPPER(?) OR UPPER(ap_paterno) LIKE UPPER(?) OR UPPER(ap_materno) LIKE UPPER(?)) AND dni LIKE ?"; 
        
        Cliente cli;
        
        try { 
            
            ps = cn.prepareStatement(sql); 
            
            ps.setString(1, nombreComp + "%");
            ps.setString(2, nombreComp + "%");
            ps.setString(3, nombreComp + "%");
            ps.setString(4, dni + "%");
            
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                cli = new Cliente(); 

                cli.setId(rs.getInt("id_cliente")); 
                cli.setNombre(rs.getString("nombre"));
                cli.setApPaterno(rs.getString("ap_paterno"));
                cli.setApMaterno(rs.getString("ap_materno"));
                cli.setDni(rs.getString("dni"));
                cli.setTelefono(rs.getString("telefono"));  

                clientes.add(cli); 
            } 
            
        } catch (SQLException ex) { 
            System.out.println(ex.getMessage()); 
        } finally {
            try {

                if(cn != null) cn.close();
                if(ps != null) ps.close(); 
                if(rs != null) rs.close();
                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return clientes; 
    }

}
