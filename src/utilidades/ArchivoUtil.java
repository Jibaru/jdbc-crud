
package utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ArchivoUtil {
    
    public static void descargarArchivo(String contenido){
        JFileChooser seleccionar = new JFileChooser();
        File archivo = null;
        if(seleccionar.showDialog(null, "Guardar") == JFileChooser.APPROVE_OPTION){
            archivo = seleccionar.getSelectedFile();
            if(archivo.getName().endsWith("txt")){
                try {
                    FileWriter archivoNuevo = new FileWriter(archivo);
                    PrintWriter escribir = new PrintWriter(archivoNuevo);
                    escribir.write(contenido);
                    archivoNuevo.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }else{
                JOptionPane.showMessageDialog(null, "El archivo tiene que estar en formate .txt");
                ArchivoUtil.descargarArchivo(contenido);
            }
        }    
    }
}
