
package utilidades;

import conexion.bd.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteUtil {
    public static JasperViewer reportePedidos() {
        
        Connection cn = Conexion.abrir();
        JasperViewer vistaReporte = null;
        
        try {
        
            JasperReport reporte = null;
            // Path del reporte
            String path = "src\\reportes\\ReportePedidos.jasper";
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            JasperPrint jprint = JasperFillManager.fillReport(reporte, null, cn);
            vistaReporte = new JasperViewer(jprint, false);

        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if(cn != null) {
                    cn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return vistaReporte;
    }
}
