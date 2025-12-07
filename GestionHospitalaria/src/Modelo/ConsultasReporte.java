package Modelo;

import java.sql.*;

/**
 * Clase que maneja las consultas de reportes a la base de datos
 * @author mms24
 */
public class ConsultasReporte extends Conexion {
    
    /**
     * Obtiene la cantidad de citas por estado (sin filtro de fechas)
     * @param modelo objeto que almacenará las estadísticas
     * @return true si se obtuvieron los datos correctamente
     */
    public boolean obtenerEstadisticas(Reporte modelo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT " +
                     "SUM(CASE WHEN estado = 'Realizada' THEN 1 ELSE 0 END) as atendidas, " +
                     "SUM(CASE WHEN estado = 'Cancelada' THEN 1 ELSE 0 END) as canceladas, " +
                     "SUM(CASE WHEN estado = 'Ausente' THEN 1 ELSE 0 END) as ausentes " +
                     "FROM cita";
        
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                modelo.setAtendidas(rs.getInt("atendidas"));
                modelo.setCanceladas(rs.getInt("canceladas"));
                modelo.setAusentes(rs.getInt("ausentes"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
    
    /**
     * Obtiene la cantidad de citas por estado con filtro de fechas
     * @param modelo objeto que almacenará las estadísticas y tiene las fechas
     * @return true si se obtuvieron los datos correctamente
     */
    public boolean obtenerEstadisticasConFiltro(Reporte modelo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        
        String sql = "SELECT " +
                     "SUM(CASE WHEN estado = 'Realizada' THEN 1 ELSE 0 END) as atendidas, " +
                     "SUM(CASE WHEN estado = 'Cancelada' THEN 1 ELSE 0 END) as canceladas, " +
                     "SUM(CASE WHEN estado = 'Ausente' THEN 1 ELSE 0 END) as ausentes " +
                     "FROM cita " +
                     "WHERE DATE(fecha_hora) BETWEEN ? AND ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, modelo.getFechaInicio());
            ps.setString(2, modelo.getFechaFin());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                modelo.setAtendidas(rs.getInt("atendidas"));
                modelo.setCanceladas(rs.getInt("canceladas"));
                modelo.setAusentes(rs.getInt("ausentes"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
