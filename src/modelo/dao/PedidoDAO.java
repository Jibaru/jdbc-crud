
package modelo.dao;

import conexion.bd.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import modelo.bean.Articulo;
import modelo.bean.Categoria;
import modelo.bean.Cliente;
import modelo.bean.Pedido;

public class PedidoDAO {
    
    private static PreparedStatement ps;
    private static ResultSet rs;
    private static Connection cn;
    
    public static int insertar(Pedido p) { 
        
        cn = Conexion.abrir();
        
        String sql = "INSERT INTO Pedido(fecha_pedido, descripcion, id_cliente, id_articulo) VALUES (?, ?, ?, ?)"; 
        
        int resultado = 0;
        
        try {
            
            ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setDate(1, new Date(p.getFechaPedido().getTime()));
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getCliente().getId());
            ps.setInt(4, p.getArticulo().getId());
            
            resultado = ps.executeUpdate();
            
            if(rs.next()) {
                p.setId(rs.getInt(1));
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
    
    public static Pedido obtenerUno(int idPedido) { 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT p.*, " +
                    "c.nombre AS nombre_cli, " +
                    "c.ap_paterno AS ap_paterno_cli, " +
                    "c.ap_materno AS ap_materno_cli, " +
                    "c.dni AS dni_cli, " +
                    "c.telefono AS telefono_cli, " +
                    "a.nombre AS nombre_art, " +
                    "a.precio AS precio_art, " +
                    "a.descripcion AS descripcion_art, " +
                    "a.imagen AS imagen_art, " +
                    "cat.id_categoria, " +
                    "cat.nombre AS nombre_cat " +
                    "FROM Pedido p JOIN Cliente c ON p.id_cliente = c.id_cliente " +
                    "JOIN Articulo a ON p.id_articulo = a.id_articulo " +
                    "JOIN categoria cat ON a.id_categoria = cat.id_categoria " + 
                    "WHERE id_pedido = ?";
        
        Pedido ped = null;
        
        try {
            
            ps = cn.prepareStatement(sql);
            
            ps.setInt(1, idPedido);
            
            rs = ps.executeQuery();
            
            if (rs.next()) { 

                ped = new Pedido();
                
                Cliente cli = new Cliente();
                Categoria cat = new Categoria();
                Articulo art = new Articulo();
                
                cli.setId(rs.getInt("id_cliente")); 
                cli.setNombre(rs.getString("nombre_cli"));
                cli.setApPaterno(rs.getString("ap_paterno_cli"));
                cli.setApMaterno(rs.getString("ap_materno_cli"));
                cli.setDni(rs.getString("dni_cli"));
                cli.setTelefono(rs.getString("telefono_cli"));
                
                cat.setId(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre_cat"));

                art.setId(rs.getInt("id_articulo")); 
                art.setNombre(rs.getString("nombre_art"));
                art.setPrecio(rs.getDouble("precio_art"));
                art.setDescripcion(rs.getString("descripcion_art"));
                art.setImagen(rs.getBlob("imagen_art"));
                art.setCategoria(cat);
                
                ped.setId(rs.getInt("id_pedido"));
                ped.setFechaPedido(rs.getDate("fecha_pedido")); 
                ped.setDescripcion(rs.getString("descripcion"));
                ped.setCliente(cli);
                ped.setArticulo(art);
                
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
        
        return ped; 
    }
    
    public static int eliminar(int idPedido) { 
        
        cn = Conexion.abrir();
        
        String sql = "DELETE FROM Pedido WHERE id_pedido = ?"; 
        
        int resultado = 0;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setInt(1, idPedido);
            
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
    
    public static ArrayList<Pedido> listarTodos() { 
        
        ArrayList<Pedido> pedidos = new ArrayList<>(); 
        
        cn = Conexion.abrir();
        
        String sql = "SELECT p.*, " +
                    "c.nombre AS nombre_cli, " +
                    "c.ap_paterno AS ap_paterno_cli, " +
                    "c.ap_materno AS ap_materno_cli, " +
                    "c.dni AS dni_cli, " +
                    "c.telefono AS telefono_cli, " +
                    "a.nombre AS nombre_art, " +
                    "a.precio AS precio_art, " +
                    "a.descripcion AS descripcion_art, " +
                    "a.imagen AS imagen_art, " +
                    "cat.id_categoria, " +
                    "cat.nombre AS nombre_cat " +
                    "FROM Pedido p JOIN Cliente c ON p.id_cliente = c.id_cliente " +
                    "JOIN Articulo a ON p.id_articulo = a.id_articulo " +
                    "JOIN categoria cat ON a.id_categoria = cat.id_categoria " + 
                    "ORDER BY p.fecha_pedido ASC"; 
        
        Pedido ped;
        
        try { 
            
            ps = cn.prepareStatement(sql); 
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                ped = new Pedido();
                
                Cliente cli = new Cliente();
                Categoria cat = new Categoria();
                Articulo art = new Articulo();
                
                cli.setId(rs.getInt("id_cliente")); 
                cli.setNombre(rs.getString("nombre_cli"));
                cli.setApPaterno(rs.getString("ap_paterno_cli"));
                cli.setApMaterno(rs.getString("ap_materno_cli"));
                cli.setDni(rs.getString("dni_cli"));
                cli.setTelefono(rs.getString("telefono_cli"));
                
                cat.setId(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre_cat"));

                art.setId(rs.getInt("id_articulo")); 
                art.setNombre(rs.getString("nombre_art"));
                art.setPrecio(rs.getDouble("precio_art"));
                art.setDescripcion(rs.getString("descripcion_art"));
                art.setImagen(rs.getBlob("imagen_art"));
                art.setCategoria(cat);
                
                ped.setId(rs.getInt("id_pedido"));
                ped.setFechaPedido(rs.getDate("fecha_pedido")); 
                ped.setDescripcion(rs.getString("descripcion"));
                ped.setCliente(cli);
                ped.setArticulo(art);

                pedidos.add(ped); 
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
        return pedidos; 
    }
    
    public static ArrayList<Pedido> listarPorFecha(java.util.Date inferior, java.util.Date superior) { 
        
        ArrayList<Pedido> pedidos = new ArrayList<>(); 
        System.out.println(inferior);
        System.out.println(superior);
        cn = Conexion.abrir();
        
        String sql = "SELECT p.*, " +
                    "c.nombre AS nombre_cli, " +
                    "c.ap_paterno AS ap_paterno_cli, " +
                    "c.ap_materno AS ap_materno_cli, " +
                    "c.dni AS dni_cli, " +
                    "c.telefono AS telefono_cli, " +
                    "a.nombre AS nombre_art, " +
                    "a.precio AS precio_art, " +
                    "a.descripcion AS descripcion_art, " +
                    "a.imagen AS imagen_art, " +
                    "cat.id_categoria, " +
                    "cat.nombre AS nombre_cat " +
                    "FROM Pedido p JOIN Cliente c ON p.id_cliente = c.id_cliente " +
                    "JOIN Articulo a ON p.id_articulo = a.id_articulo " +
                    "JOIN categoria cat ON a.id_categoria = cat.id_categoria "+ 
                    "WHERE p.fecha_pedido >= ? AND " + 
                    "p.fecha_pedido <= ? " +
                    "ORDER BY p.fecha_pedido ASC"; 
        
        Pedido ped;
        
        try { 
            
            ps = cn.prepareStatement(sql);
            
            ps.setDate(1, new Date(inferior.getTime()));
            ps.setDate(2, new Date(superior.getTime()));
            
            rs = ps.executeQuery(); 

            while (rs.next()) { 

                ped = new Pedido();
                
                Cliente cli = new Cliente();
                Categoria cat = new Categoria();
                Articulo art = new Articulo();
                
                cli.setId(rs.getInt("id_cliente")); 
                cli.setNombre(rs.getString("nombre_cli"));
                cli.setApPaterno(rs.getString("ap_paterno_cli"));
                cli.setApMaterno(rs.getString("ap_materno_cli"));
                cli.setDni(rs.getString("dni_cli"));
                cli.setTelefono(rs.getString("telefono_cli"));
                
                cat.setId(rs.getInt("id_categoria"));
                cat.setNombre(rs.getString("nombre_cat"));

                art.setId(rs.getInt("id_articulo")); 
                art.setNombre(rs.getString("nombre_art"));
                art.setPrecio(rs.getDouble("precio_art"));
                art.setDescripcion(rs.getString("descripcion_art"));
                art.setImagen(rs.getBlob("imagen_art"));
                art.setCategoria(cat);

                ped.setId(rs.getInt("id_pedido"));
                ped.setFechaPedido(rs.getDate("fecha_pedido")); 
                ped.setDescripcion(rs.getString("descripcion"));
                ped.setCliente(cli);
                ped.setArticulo(art);

                pedidos.add(ped); 
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
        return pedidos; 
    }
    
    public static String obtenerEnTexto(Pedido p){
        Cliente cli = p.getCliente();
        Articulo art = p.getArticulo();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String msg = 
            "****************************************"
            +"\nDatos cliente:"
            +"\n****************************************"
            +"\nNombres: " + cli.getNombre()
            +"\nApellidos: " + cli.getApPaterno() + " " + cli.getApMaterno()
            +"\nDNI: " + cli.getDni()
            +"\nTeléfono: " + cli.getTelefono()
            +"\n\n****************************************"
            +"\nArtículo pedido"
            +"\n****************************************"
            +"\nID: " + art.getId()
            +"\nNombre: "+ art.getNombre()
            +"\n\n****************************************"
            +"\nDatos de pedido"
            +"\n****************************************"
            +"\nFecha de pedido:"+p.getId()
            +"\nFecha de pedido:"+formato.format(p.getFechaPedido());
        
        return msg.replaceAll("\n", System.getProperty("line.separator"));
    }
}
