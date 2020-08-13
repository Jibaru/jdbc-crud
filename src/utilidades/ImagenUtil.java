
package utilidades;

import java.sql.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.sql.rowset.serial.SerialBlob;

public class ImagenUtil {
    
    public static Image blobAImage(Blob blob) {
        
        BufferedImage image = null;
            
        try {
            InputStream in = blob.getBinaryStream();  
            image = ImageIO.read(in);
        } catch(IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return image;
    }
    
    public static ImageIcon imageAImageIcon(Image imagen, int ancho, int alto) {
        imagen = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon icono = new ImageIcon(imagen);
        return icono;
    }
    
    public static ImageIcon blobAImageIcon(Blob blob, int ancho, int alto) {
        Image imagen = blobAImage(blob);
        ImageIcon icono = imageAImageIcon(imagen, ancho, alto);
        return icono;
    }
    
    public static Blob fileABlob(File file) {
        
        Blob blob = null;
        try {
            byte[] bytes = obtenerBytesDeFile(file);

            blob = new SerialBlob(bytes);
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return blob;
    }
    
    private static byte[] obtenerBytesDeFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            throw new IOException("El archivo es muy grande");
        }

        byte[] bytes = new byte[(int)length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("No se pudo leer el archivo completamente: "+file.getName());
        }

        is.close();
        return bytes;
    }
}
