package cr.ac.cenfotec.autosE.dao;

import cr.ac.cenfotec.autosE.domian.Solicitud;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class H2SolicitudDao {
    /**
     * Método:              crearSolicitud
     * Descripción:         Método que permite crear o una solicitud
     * @param jdbc          Instancia de JdbcTemplate
     * @param solicitud     Objeto de tipo Solicitud
     */
    public void crearSolicitud(JdbcTemplate jdbc, Solicitud solicitud){
        String query = "INSERT INTO TBL_SOLICITUDES (IDUSUARIO, IDINVENTARIO, MONTO, STATUS)" +
                " VALUES (" + solicitud.getIdUsuario() + ", " + solicitud.getIdInventario()
                + ", " + solicitud.getMonto() + ", "  + solicitud.isStatus() + ")";
        jdbc.execute(query);
    }

    /**
     * Método:              getSolicitudesPendiente
     * Descripción:         Método que retornar la lista de solicitudes pendientes que
     *                      no son del usuario
     * @param jdbc          Instancia de JdbcTemplate
     * @param idUser        Variable de tipo Long que representa el id del usuario
     * @param idInv         Variable de tipo Long que represente el id del inventario
     * @return              Lista de objetos de tipo solicitud
     */
    public List<Solicitud> getSolicitudesPendiente(JdbcTemplate jdbc, Long idUser, Long idInv){
        String query = "SELECT * FROM TBL_SOLICITUDES WHERE STATUS = false AND IDUSUARIO != " + idUser
                + " AND IDINVENTARIO = " + idInv;
        List<Solicitud> solicitudes = jdbc.query(query,
                (rs, rowNum) ->
                        new Solicitud(
                                rs.getLong("id"),
                                rs.getLong("idUsuario"),
                                rs.getLong("idInventario"),
                                rs.getLong("monto"),
                                rs.getBoolean("status")
                        ));
        return solicitudes;
    }

    /**
     * Método:              getSolicitudPendienteByIdUser
     * Descripción:         Método que retornar la lista de solicitudes pendientes que
     *                      son del usuario
     * @param jdbc          Instancia de JdbcTemplate
     * @param idUser        Variable de tipo Long que representa el id del usuario
     * @param idInv         Variable de tipo Long que represente el id del inventario
     * @return              Lista de objetos de tipo solicitud
     */
    public List<Solicitud> getSolicitudPendienteByIdUser(JdbcTemplate jdbc, Long idUser, Long idInv) {
        String query = "SELECT * FROM TBL_SOLICITUDES WHERE STATUS = false AND IDUSUARIO = " + idUser
                + " AND IDINVENTARIO = " + idInv;
        List<Solicitud> solicitudes = jdbc.query(query,
                (rs, rowNum) ->
                        new Solicitud(
                                rs.getLong("id"),
                                rs.getLong("idUsuario"),
                                rs.getLong("idInventario"),
                                rs.getLong("monto"),
                                rs.getBoolean("status")
                        ));
        return solicitudes;
    }
    /**
     * Método:              updateSolicitudById
     * Descripción:         Método que perite actualizar una solicitud pendiente
     *                      cambiando su estado a true para un id definido
     * @param jdbc          Instancia de JdbcTemplate
     * @param id            Variable de tpo Long, que representa el id del usuario
     */
    public void updateSolicitudById(JdbcTemplate jdbc, boolean status, Long id) {
        String sql = "UPDATE TBL_SOLICITUDES SET STATUS = " + status + " WHERE id = " + id;
        jdbc.execute(sql);
    }
}
