
package modelo.dao;

import conexion.bd.Conexion;
import java.sql.*;
import java.util.ArrayList;
import modelo.bean.Categoria;

public class CategoriaDAO {
    
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static Connection cn;
    
    public static int insertar(Categoria c) { 
        
        cn = Conexion.abrir();
        
        String sql = "INSERT INTO Categoria(nombre) VALUES (?)"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, c.getNombre());
            
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
    
    public static int modificar(Categoria c) { 
        
        cn = Conexion.abrir();
        
        String sql = "UPDATE Categoria SET nombre = ? WHERE id_categoria = ?"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getId());
            
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
    
    public static int eliminar(int idCategoria) { 
        
        cn = Conexion.abrir();
        
        String sql = "DELETE FROM Categoria WHERE id_categoria = ?"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setInt(1, idCategoria);
            
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
    
    public static Categoria obtenerUno(int idCategoria) { 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT * FROM Categoria WHERE id_categoria = ?"; 
        
        Categoria cat = null;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setInt(1, idCategoria);
            
            rs = ps.executeQuery();
            
            if (rs.next()) { 

                cat = new Categoria(); 

                cat.setId(rs.getInt("id_categoria")); 
                cat.setNombre(rs.getString("nombre")); 

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
        
        return cat; 
    }
    
    public static ArrayList<Categoria> listarTodos() { 
        
        ArrayList<Categoria> categorias = new ArrayList<>(); 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT * FROM CATEGORIA"; 
        
        Categoria cat;
        
        try { 
            
            ps = cn.prepareStatement(sql); 
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                cat = new Categoria(); 

                cat.setId(rs.getInt("id_categoria")); 
                cat.setNombre(rs.getString("nombre")); 

                categorias.add(cat); 
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
        return categorias; 
    }
    
    public static ArrayList<Categoria> listarPorNombre(String nombre) {
        ArrayList<Categoria> categorias = new ArrayList<>(); 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT * FROM Categoria WHERE UPPER(nombre) LIKE UPPER(?)"; 
        
        Categoria cat;
        
        try { 
            
            ps = cn.prepareStatement(sql); 
            
            ps.setString(1, nombre + "%");
            
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                cat = new Categoria(); 

                cat.setId(rs.getInt("id_categoria")); 
                cat.setNombre(rs.getString("nombre")); 

                categorias.add(cat); 
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
        return categorias; 
    }

}
