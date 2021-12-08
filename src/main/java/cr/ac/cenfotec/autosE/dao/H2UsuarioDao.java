package cr.ac.cenfotec.autosE.dao;

import cr.ac.cenfotec.autosE.domian.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class H2UsuarioDao {

    /**
     * Método:              getSolicitudesPendiente
     * Descripción:         Método que retornar la lista de solicitudes pendientes que
     *                      no son del usuario
     * @param jdbc
     * @param usuario
     */
    public void createPersona(JdbcTemplate jdbc, Usuario usuario){
        jdbc.update("INSERT into TBL_USUARIOS (cedula, name, lastName1, lastName2, phone, mail)" +
                        "values (?,?,?,?,?,?,?)",
                usuario.getCedula(),
                usuario.getName(),
                usuario.getLastName1(),
                usuario.getLastName2(),
                usuario.getPhone(),
                usuario.getMail());
    }
    /**
     * Método:              addUsuario
     * Descripción:         Método que insertar un usuario en la base de datos
     * @param jdbc          Instancia de JdbcTemplate
     * @param usuario       Objeto de tipo Usuario, que es el que se cargará a la bd
     */
    public void addUsuario(JdbcTemplate jdbc, Usuario usuario){
        String query = "INSERT INTO TBL_USUARIOS (cedula, name, lastName1, lastName2, phone, mail)" +
                " VALUES ('" + usuario.getCedula() + "', '" + usuario.getName() + "', '" + usuario.getLastName1()
                + "', '" + usuario.getLastName2()  + "', '" + usuario.getPhone() + "', '" + usuario.getMail()
                + "')";
        jdbc.execute(query);
    }

    /**
     * Método:              getUsuario
     * Descripción:         Método que permite retornar la lista de usuarios de la base de datos
     * @param jdbc          Instancia de JdbcTemplate
     * @return              Lista de objetos de tipo Usuario
     */
    public List<Usuario> getUsuario(JdbcTemplate jdbc){
        String query = "SELECT * FROM TBL_USUARIOS";
        List<Usuario> usuarios = jdbc.query(query,
                (rs, rowNum) ->
                        new Usuario(
                                rs.getLong("id"),
                                rs.getString("cedula"),
                                rs.getString("name"),
                                rs.getString("lastName1"),
                                rs.getString("lastName2"),
                                rs.getString("phone"),
                                rs.getString("mail")
                        ));
        return usuarios;
    }

    /**
     * Método:              getUserById
     * Descripción:         Método que retornar un usuario utilizado su id
     *                      de la base de datos
     * @param jdbc          Instancia de JdbcTemplate
     * @param id            Variable de tipo Long que representa el id del usuario
     * @return              Objeto de tipo usuario
     */
    public Usuario getUserById(JdbcTemplate jdbc, Long id) {
        String sql = "SELECT * FROM TBL_USUARIOS WHERE id = ?";
        Usuario usuarios = jdbc.queryForObject(sql, new Object[]{id} , (rs, rowNum) ->
                new Usuario(
                        rs.getLong("id"),
                        rs.getString("cedula"),
                        rs.getString("name"),
                        rs.getString("lastName1"),
                        rs.getString("lastName2"),
                        rs.getString("phone"),
                        rs.getString("mail")
                ));
        return usuarios;
    }

    /**
     * Método:              getUsuarioById
     * Descripción:         Método que retornar un usuario utilizado su id
     *                      de la base de datos
     * @param jdbc          Instancia de JdbcTemplate
     * @param id            Variable de tipo Long que representa el id del usuario
     * @return              Objeto de tipo usuario
     */
    public List<Usuario> getUsuarioById(JdbcTemplate jdbc, Long id) {
        String sql = "SELECT * FROM TBL_USUARIOS WHERE ID = " + id;
        List<Usuario> usuarios = jdbc.query(sql, (rs, rowNum) ->
                new Usuario(
                        rs.getLong("id"),
                        rs.getString("cedula"),
                        rs.getString("name"),
                        rs.getString("lastName1"),
                        rs.getString("lastName2"),
                        rs.getString("phone"),
                        rs.getString("mail")
                ));
        return usuarios ;
    }
    /**
     * Método:              getPersonaByCedula
     * Descripción:         Método que retorna una lista de usuarios utilizado su cedula
     *                      de la base de datos
     * @param jdbc          Instancia de JdbcTemplate
     * @param cedula        Variable de tipo String que representa la cédula del usuario
     * @return              Lista de objetos de tipo Usuario
     */
    public List<Usuario> getPersonaByCedula(JdbcTemplate jdbc, String cedula) {
        String sql = "SELECT * FROM TBL_USUARIOS WHERE CEDULA = " + "'" + cedula + "'";
        List<Usuario> usuarios = jdbc.query(sql, (rs, rowNum) ->
                new Usuario(
                        rs.getLong("id"),
                        rs.getString("cedula"),
                        rs.getString("name"),
                        rs.getString("lastName1"),
                        rs.getString("lastName2"),
                        rs.getString("phone"),
                        rs.getString("mail")
                ));
        return usuarios ;
    }

    /**
     * Método:              updateStatusUsuario
     * Descripción:         Método permite modificar el status a true utilizando
     *                      la cédula del usuario
     * @param jdbc          Instancia de JdbcTemplate
     * @param usuario       Objeto de tipo Usuario, que es el que se cargará a la bd
     */
    public void updateStatusUsuario(JdbcTemplate jdbc, Usuario usuario){
        String sql = "UPDATE TBL_USUARIOS SET STATUS = TRUE WHERE cedula = " + usuario.getCedula();
        jdbc.execute(sql);
    }

    /**
     * Método:              updateUsuario
     * Descripción:         Método permite modificar la información de un usuario utilizando
     *                      el id del usuario
     * @param jdbc          Instancia de JdbcTemplate
     * @param usuario       Objeto de tipo Usuario, que es el que se cargará a la bd
     */
    public void updateUsuario(JdbcTemplate jdbc, Usuario usuario){
        String sql = "UPDATE TBL_USUARIOS SET CEDULA = '" + usuario.getCedula() + "', NAME = '"
                + usuario.getName() + "', LASTNAME1 = '" + usuario.getLastName1() + "', LASTNAME2 = '"
                + usuario.getLastName2() + "', PHONE = ' " + usuario.getPhone() + "', MAIL = ' "
                + usuario.getMail() + "' WHERE id = " + usuario.getId();
        jdbc.execute(sql);
    }
}
