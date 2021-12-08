package cr.ac.cenfotec.autosE.dao;

import cr.ac.cenfotec.autosE.domian.Inventario;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class H2InventarioDao {

    /**
     * Método:              addInvetario
     * Descripción:         Método que permite cargar inventario
     * @param jdbc          Instancia de JdbcTemplate
     * @param inventario    Variable de tipo Objeto que representa un inventario
     */
    public void addInvetario(JdbcTemplate jdbc, Inventario inventario){
        String query = "INSERT INTO TBL_SOLICITUDES (modelo, pasajeros, precio, cantidad)" +
                " VALUES ('" + inventario.getModelo() + "', " + inventario.getPasajeros()
                + ", "  + inventario.getPrecio() + ", "  + inventario.getCantidad() + ")";
        jdbc.execute(query);
    }

    /**
     * Método:              getAllInvetarios
     * Descripción:         Método que permite retorna una lista de objetos
     *                      de tipo Inventario
     * @param jdbc          Instancia de JdbcTemplate
     * @return              Lista de objetos de tipo inventario,que representa la tabla
     *                      de inventario
     */
    public List<Inventario> getAllInvetarios(JdbcTemplate jdbc) {
        String query = "SELECT * FROM TBL_INVENTARIO";
        List<Inventario> inventarios = jdbc.query(query,
                (rs, rowNum) ->
                        new Inventario(
                                rs.getLong("id"),
                                rs.getString("modelo"),
                                rs.getInt("pasajeros"),
                                rs.getInt("precio"),
                                rs.getInt("cantidad")
                        ));
        return inventarios;
    }

    /**
     * Método:              getInvetarioByModelo
     * Descripción:         Método que permite retorna una lista de objetos
     *                      del Inventario con un modelo determinado
     * @param jdbc          Instancia de JdbcTemplate
     * @param tipoVehiculo  Variable de tipo String que representa el modelo
     *                      vehículo
     * @return              Lista de objetos de tipo Inventario
     */
    public List<Inventario> getInvetarioByModelo(JdbcTemplate jdbc, String tipoVehiculo) {
        String query = "SELECT * FROM TBL_INVENTARIO WHERE MODELO = '" + tipoVehiculo + "'";
        List<Inventario> inventarios = jdbc.query(query,
                (rs, rowNum) ->
                        new Inventario(
                                rs.getLong("id"),
                                rs.getString("modelo"),
                                rs.getInt("pasajeros"),
                                rs.getInt("precio"),
                                rs.getInt("cantidad")
                        ));
        return inventarios;
    }

    /**
     * Método:              updateInventarioById
     * Descripción:         Método que permite actulizar la cantidad de un inventario
     *                      utilizando su id
     * @param jdbc          Instancia de JdbcTemplate
     * @param id            Variable de tipo Long que representa el id del inventario
     * @param cant          Variable de tipo int que representa la cantidad de inventario
     */
    public void updateInventarioById(JdbcTemplate jdbc, Long id, int cant){
        String sql = "UPDATE TBL_INVENTARIO SET CANTIDAD = " + cant + " WHERE id = " + id;
        jdbc.execute(sql);
    }
}
