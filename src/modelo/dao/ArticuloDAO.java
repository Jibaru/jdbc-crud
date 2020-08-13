
package modelo.dao;

import conexion.bd.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.bean.Articulo;
import modelo.bean.Categoria;

public class ArticuloDAO {
    
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static Connection cn;
    
    public static int insertar(Articulo a) { 
        
        cn = Conexion.abrir();
        
        String sql = "INSERT INTO Articulo(nombre, precio, descripcion, imagen, id_categoria) VALUES (?, ?, ?, ?, ?)"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, a.getNombre());
            ps.setDouble(2, a.getPrecio());
            ps.setString(3, a.getDescripcion());
            ps.setBlob(4, a.getImagen());
            ps.setInt(5, a.getCategoria().getId());
            
            resultado = ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            
            if(rs.next()) {
                a.setId(rs.getInt(1));
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
    
    public static int modificar(Articulo a) { 
        
        cn = Conexion.abrir();
        
        String sql = "UPDATE Articulo SET nombre = ?, precio = ?, descripcion = ?, imagen = ?, id_categoria = ? WHERE id_articulo = ?"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setString(1, a.getNombre());
            ps.setDouble(2, a.getPrecio());
            ps.setString(3, a.getDescripcion());
            ps.setBlob(4, a.getImagen());
            ps.setInt(5, a.getCategoria().getId());
            ps.setInt(6, a.getId());
            
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
    
    public static int eliminar(int idArticulo) { 
        
        cn = Conexion.abrir();
        
        String sql = "DELETE FROM Articulo WHERE id_articulo = ?"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setInt(1, idArticulo);
            
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
    
    public static Articulo obtenerUno(int idArticulo) { 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT a.*, c.nombre AS nombre_cat FROM Articulo a JOIN Categoria c ON a.id_categoria = c.id_categoria WHERE a.id_articulo = ?"; 
        
        Articulo art = null;
        
        try {
            
            ps = cn.prepareStatement(sql);
            
            ps.setInt(1, idArticulo);
            
            rs = ps.executeQuery();
            
            if (rs.next()) { 

                art = new Articulo();
                
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre_cat"));

                art.setId(rs.getInt("id_articulo")); 
                art.setNombre(rs.getString("nombre"));
                art.setPrecio(rs.getDouble("precio"));
                art.setDescripcion(rs.getString("descripcion"));
                art.setImagen(rs.getBlob("imagen"));
                art.setCategoria(cat);
                
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
        
        return art; 
    }
    
    public static ArrayList<Articulo> listarTodos() { 
        
        ArrayList<Articulo> articulos = new ArrayList<>(); 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT a.*, c.nombre AS nombre_cat FROM Articulo a JOIN Categoria c ON a.id_categoria = c.id_categoria"; 
        
        Articulo art;
        
        try { 
            
            ps = cn.prepareStatement(sql); 
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                art = new Articulo();
                
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre_cat"));

                art.setId(rs.getInt("id_articulo")); 
                art.setNombre(rs.getString("nombre"));
                art.setPrecio(rs.getDouble("precio"));
                art.setDescripcion(rs.getString("descripcion"));
                art.setImagen(rs.getBlob("imagen"));
                art.setCategoria(cat);
                
                articulos.add(art);
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
        
        return articulos; 
    }
    
    public static ArrayList<Articulo> listarPorNombrePrecio(String nombre, double precio) { 
        
        ArrayList<Articulo> articulos = new ArrayList<>(); 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT a.*, c.nombre AS nombre_cat FROM Articulo a JOIN Categoria c ON a.id_categoria = c.id_categoria WHERE UPPER(a.nombre) LIKE UPPER(?) AND a.precio = ?"; 
        
        Articulo art;
        
        try { 
            
            ps = cn.prepareStatement(sql); 
            
            ps.setString(1, nombre + "%");
            ps.setDouble(2, precio);
            
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                art = new Articulo();
                
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre_cat"));

                art.setId(rs.getInt("id_articulo")); 
                art.setNombre(rs.getString("nombre"));
                art.setPrecio(rs.getDouble("precio"));
                art.setDescripcion(rs.getString("descripcion"));
                //art.setImagen(ImagenUtil.inputStreamAFile(rs.getBinaryStream("imagen")));
                art.setImagen(rs.getBlob("imagen"));
                art.setCategoria(cat);
                
                articulos.add(art);
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
        
        return articulos; 
    }
    
    public static ArrayList<Articulo> listarPorNombre(String nombre) { 
        
        ArrayList<Articulo> articulos = new ArrayList<>(); 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT a.*, c.nombre AS nombre_cat FROM Articulo a JOIN Categoria c ON a.id_categoria = c.id_categoria WHERE UPPER(a.nombre) LIKE UPPER(?)"; 
        
        Articulo art;
        
        try { 
            
            ps = cn.prepareStatement(sql); 
            
            ps.setString(1, nombre + "%");
            
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                art = new Articulo();
                
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre_cat"));

                art.setId(rs.getInt("id_articulo")); 
                art.setNombre(rs.getString("nombre"));
                art.setPrecio(rs.getDouble("precio"));
                art.setDescripcion(rs.getString("descripcion"));
                //art.setImagen(ImagenUtil.inputStreamAFile(rs.getBinaryStream("imagen")));
                art.setImagen(rs.getBlob("imagen"));
                art.setCategoria(cat);
                
                articulos.add(art);
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
        
        return articulos; 
    }
}
