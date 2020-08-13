
package modelo.dao;

import java.util.ArrayList;
import modelo.bean.*;

public class Prueba {
    
    // Se prueban los DAO
    public static void main(String[] args) {
        
        ArrayList<Articulo> lista = ArticuloDAO.listarTodos();
        
        for(var l:lista) {
            System.out.println(l);
        }
        
    }
    
}
