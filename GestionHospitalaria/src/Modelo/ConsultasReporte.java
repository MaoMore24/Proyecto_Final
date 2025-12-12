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
                     "SUM(CASE WHEN UPPER(estado) = 'REALIZADA' THEN 1 ELSE 0 END) as atendidas, " +
                     "SUM(CASE WHEN UPPER(estado) = 'CANCELADA' THEN 1 ELSE 0 END) as canceladas, " +
                     "SUM(CASE WHEN UPPER(estado) = 'AUSENTE' THEN 1 ELSE 0 END) as ausentes " +
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
                     "SUM(CASE WHEN UPPER(estado) = 'REALIZADA' THEN 1 ELSE 0 END) as atendidas, " +
                     "SUM(CASE WHEN UPPER(estado) = 'CANCELADA' THEN 1 ELSE 0 END) as canceladas, " +
                     "SUM(CASE WHEN UPPER(estado) = 'AUSENTE' THEN 1 ELSE 0 END) as ausentes " +
                     "FROM cita " +
                     "WHERE DATE(fecha_hora) BETWEEN ? AND ?";
        
        try {
            ps = con.prepareStatement(sql);
            // Convertir String YYYY-MM-DD a java.sql.Date para mayor seguridad
            ps.setDate(1, java.sql.Date.valueOf(modelo.getFechaInicio()));
            ps.setDate(2, java.sql.Date.valueOf(modelo.getFechaFin()));
            
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
