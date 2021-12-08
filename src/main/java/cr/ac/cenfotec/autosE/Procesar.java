package cr.ac.cenfotec.autosE;


import cr.ac.cenfotec.autosE.dao.H2InventarioDao;
import cr.ac.cenfotec.autosE.dao.H2SolicitudDao;
import cr.ac.cenfotec.autosE.dao.H2UsuarioDao;
import cr.ac.cenfotec.autosE.domian.Inventario;
import cr.ac.cenfotec.autosE.domian.Solicitud;
import cr.ac.cenfotec.autosE.domian.Usuario;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.inject.Named;
import java.util.List;

@Named
public class Procesar implements JavaDelegate {
    H2InventarioDao inventarioDao = new H2InventarioDao();
    H2UsuarioDao usuarioDao = new H2UsuarioDao();
    H2SolicitudDao solicitudDao = new H2SolicitudDao();
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        /**
         * Variables de los formularios
         */
        String cedula = (String) delegateExecution.getVariable("cedula");
        String name = (String) delegateExecution.getVariable("name");
        String lastName1 = (String) delegateExecution.getVariable("lastName1");
        String lastName2 = (String) delegateExecution.getVariable("lastName2");
        String phone = (String) delegateExecution.getVariable("phone");
        String mail = (String) delegateExecution.getVariable("mail");
        String tipoVehiculo = (String) delegateExecution.getVariable("tipoVehiculo");
        String montoMono = (String) delegateExecution.getVariable("montoMono");
        String montoSedan = (String) delegateExecution.getVariable("montoSedan");
        String montoSubMini = (String) delegateExecution.getVariable("montoSubMini");
        String montoSubPlus = (String) delegateExecution.getVariable("montoSubPlus");
        String montoHeavySub = (String) delegateExecution.getVariable("montoHeavySub");

        /**
         * Variables de conversión
         */
        float montoPagado = 0;
        float diferencia = 0;
        boolean status = false;
        float valorMono = 10000;
        float valorSedan = 25000;
        float valorSubMini = 38000;
        float valorSubPlus = 45000;
        float valorHeavy = 58000;
        float valorArticulo = 0;
        Long idInv = Long.valueOf(0);

        /**
         * queries a la basa de datos
         */
        Usuario usuario = new Usuario(cedula, name, lastName1, lastName2, phone, mail);
        List<Usuario> usuarioBase = usuarioDao.getPersonaByCedula(jdbcTemplate, cedula);
        List<Inventario> inventarios = inventarioDao.getInvetarioByModelo(jdbcTemplate, tipoVehiculo);

        /**
         * Define el código del inventario
         */
        if (tipoVehiculo.equals("monoPlaza")) {
            idInv = Long.valueOf(1);
        } else if (tipoVehiculo.equals("sedan")) {
            idInv = Long.valueOf(2);
        } else if (tipoVehiculo.equals("subMini")) {
            idInv = Long.valueOf(3);
        } else if (tipoVehiculo.equals("subPlus")) {
            idInv = Long.valueOf(4);
        } else if (tipoVehiculo.equals("heavySub")) {
            idInv = Long.valueOf(5);
        }

        Solicitud solicitud = new Solicitud();
        tramitarUsuario(usuario, usuarioBase);
        Long idUser = getIdUsuarioByCedula(cedula);
        usuario.setId(idUser);
        solicitud.setIdUsuario(idUser);

        List<Solicitud> solicitudes = solicitudDao.getSolicitudesPendiente(jdbcTemplate,
                (Long)usuario.getId(), idInv);
        /**
         * Se estableces las variables float
         */
        if (montoMono != null) {
            montoPagado = Float.parseFloat(montoMono);
        }
        if (montoSedan != null) {
            montoPagado = Float.parseFloat(montoSedan);
        }
        if (montoSubMini != null) {
            montoPagado = Float.parseFloat(montoSubMini);
        }
        if (montoSubPlus != null) {
            montoPagado = Float.parseFloat(montoSubPlus);
        }
        if (montoHeavySub != null) {
            montoPagado = Float.parseFloat(montoHeavySub);
        }
        solicitud.setMonto(montoPagado);


        /**
         * Mensajes para el usuario en caso que no haya aportado el monto establecido
         */
        String msj1 = "Estimado " + usuario.toString() + " El proceso no puedo tramitarse ya que el monto \n" +
                "entregado es menor al valor del vehículo.\n" +
                "Monto del proceso: ";
        String msj2 = "Estimado " + usuario.toString() + " El proceso no puedo tramitarse ya que el monto \n" +
                "entregado es menor al valor mínimo del proceso.\n" +
                "Monto mínimo del proceso: ";

        /**
         * Mono Plaza
         */
        if (tipoVehiculo.equals("monoPlaza") && montoPagado >= valorMono) {
            valorArticulo = valorMono;
            procesarVenta(montoPagado, valorArticulo, solicitud, inventarios, usuario, idInv);
        } else if (tipoVehiculo.equals("monoPlaza")) {
            diferencia = valorMono - montoPagado;
            msj1 = "Venta de vehículo Mono Plaza\n "
                    + msj1 + valorMono
                    + " diferencia: "
                    + diferencia;
            printMsj(msj1);
        }
        /**
         * Sedano.
         */
        if (tipoVehiculo.equals("sedan") && montoPagado >= valorSedan) {
            valorArticulo = valorSedan;
            procesarVenta(montoPagado, valorArticulo, solicitud, inventarios, usuario, idInv);
        } else if (tipoVehiculo.equals("sedan")){
            diferencia = valorSedan - montoPagado;
            msj1 = "Venta de vehículo Sedán\n "
                    + msj1 + valorSedan
                    + " diferencia: "
                    + diferencia;
            printMsj(msj1);
        }

        /**
         * Suburban Mini
         */
        if (tipoVehiculo.equals("subMini") && montoPagado >= valorSubMini) {
            procesarSubasta(solicitud, usuario, idInv, solicitudes, inventarios, montoPagado, tipoVehiculo);
        } else if (tipoVehiculo.equals("subMini")) {
            diferencia = valorSubMini - montoPagado;
            msj2 = "Proceso de subasta de Suburban Mini\n "
                    + msj2 + valorSubMini
                    + " diferencia: "
                    + diferencia;
            printMsj(msj2);
        }

        /**
         * Suburban Plus
         */
        if (tipoVehiculo.equals("subPlus") && montoPagado >= valorSubPlus) {
            procesarSubasta(solicitud, usuario, idInv, solicitudes, inventarios, montoPagado, tipoVehiculo);
        } else if (tipoVehiculo.equals("subPlus")){
            diferencia = valorSubPlus - montoPagado;
            msj2 = "Proceso de subasta de Suburban Plus\n "
                    + msj2 + valorSubPlus
                    + " diferencia: "
                    +diferencia;
            printMsj(msj2);
        }

        /**
         * Heavy Suburban
         */
        if (tipoVehiculo.equals("heavySub") && montoPagado >= valorHeavy) {
            procesarSubasta(solicitud, usuario, idInv, solicitudes, inventarios, montoPagado, tipoVehiculo);
        } else if (tipoVehiculo.equals("heavySub")){
            diferencia = valorHeavy - montoPagado;
            msj2 = "Proceso de subasta de Heavy Suburban\n "
                    + msj2 + valorHeavy
                    + " diferencia: "
                    +diferencia;
            printMsj(msj2);
        }

    }

    /**
     * Método:              tramitarUsuario
     * Descripción:         Método que permite crear o actualizar el usuario. Si
     *                      no existe en la base lo crea, y si existe lo actualiza
     * @param usuario       Objeto de tipo usuario que representa al usuario que llena el formulario
     * @param usuarioBase   Lista de Objetos de tipo usuerio que representa al usuario de la base de datos.
     */
    public void tramitarUsuario(Usuario usuario, List<Usuario> usuarioBase) {
        if (usuarioBase.isEmpty()) {
            usuarioDao.addUsuario(jdbcTemplate, usuario);
        } else {
            usuario.setId(usuarioBase.get(0).getId());
            usuarioDao.updateUsuario(jdbcTemplate, usuario);
        }
    }

    /**
     * Método:              updateInventario
     * Descripción:         Método que actualizar la cantidad de inventaria
     * @param inventario    Objeto de tipo Inventario
     */
    public void updateInventario(Inventario inventario){
        int cantidad = inventario.getCantidad() - 1;
        inventarioDao.updateInventarioById(jdbcTemplate, inventario.getId(), cantidad);
    }

    /**
     * Método:              getIdUsuarioByCedula
     * Descripción:         Método que permite obtener el id del usuario
     * @param cedula        variable de tipo string que representa la cédula del usario
     * @return              variable de tipo Long que representa el id del usuario
     */
    public Long getIdUsuarioByCedula(String cedula) {
        return usuarioDao.getPersonaByCedula(jdbcTemplate, cedula).get(0).getId();
    }
    /**
     * Método:              getIdUsuarioById
     * Descripción:         Método que permite obtener el id del usuario
     * @param id            variable de tipo Long que representa el id del usario
     * @return              variable de tipo Long que representa el id del usuario
     */
    public Usuario getIdUsuarioById(Long id) {
        return usuarioDao.getUsuarioById(jdbcTemplate, id).get(0);
    }

    /**
     * Método:              procesarVenta
     * Descripción:         Método que permite procesar los vehículos que son de venta inmediata.
     *                      verifica si
     * @param montoPagado
     * @param valorArticulo
     * @param solicitud
     * @param inventarios
     * @param usuario
     */
    public void procesarVenta(float montoPagado,
                              float valorArticulo,
                              Solicitud solicitud,
                              List<Inventario> inventarios,
                              Usuario usuario,
                              Long idInv){
        String vehiculo = "";
        if (idInv == 1) {
            vehiculo = "Mono Plaza";
        } else {
            vehiculo = "Sedán";
        }
        String msj3 = "Estimado " + usuario.toString() + "\nLa venta del vehículo " + vehiculo +
                " se completó satisfactoriamente.\nEn los próximos días nos estaremos" +
                " comunicando con usted para hacer la entrega respectiva.";
        String msj4 = "Estimado " + usuario.toString() + "\nLa venta del vehículo " + vehiculo
                + " se completó satisfactoriamente.\nFavor pasar a retirar el vehículo";
        float diferencia = 0;
        diferencia = montoPagado - valorArticulo;
        solicitud.setIdInventario(Long.valueOf(idInv));
        if (inventarios.get(0).getCantidad() == 0) {
            solicitud.setStatus(false);
            solicitud.setMonto(montoPagado);
            solicitudDao.crearSolicitud(jdbcTemplate, solicitud);
            msj3 += "\nSu cambio es de " + diferencia;
            printMsj(msj3);
        } else {
            updateInventario(inventarios.get(0));
            solicitud.setStatus(true);
            solicitudDao.crearSolicitud(jdbcTemplate, solicitud);
            msj4 += "\nSu cambio es de " + diferencia;
            printMsj(msj4);
        }
    }
    public void procesarSubasta(Solicitud solicitud,
                                Usuario usuario,
                                Long idInv,
                                List<Solicitud> solicitudes,
                                List<Inventario> inventarios,
                                float montoPagado,
                                String tipoVehiculo){
        String vehiculo = "";
        if (idInv == 3) {
            vehiculo = "Suburvan Mini";
        } else if (idInv == 4) {
            vehiculo = "Suburban Plus";
        } else {
            vehiculo = "Heavy Suburban";
        }
        String msj5 = "Proceso de subasta de " + vehiculo + "\nEstimado "
                + usuario.toString()
                + "\nSu solicitud ha sido procesada satisfactoriamente" +
                "\noportunamente le estaremos comunicando el resultado.";
        String msj6 = "Proceso de subasta de " + vehiculo + "\n";
        String msj7 = "Proceso de subasta de " + vehiculo + "\n";
        if (tipoVehiculo.equals("monoPlaza")) {
            idInv = Long.valueOf(1);
        } else if (tipoVehiculo.equals("sedan")) {
            idInv = Long.valueOf(2);
        } else if (tipoVehiculo.equals("subMini")) {
            idInv = Long.valueOf(3);
        } else if (tipoVehiculo.equals("subPlus")) {
            idInv = Long.valueOf(4);
        } else if (tipoVehiculo.equals("heavySub")) {
            idInv = Long.valueOf(5);
        }
        solicitud.setIdInventario(idInv);
        solicitud.setStatus(false);
        solicitudDao.crearSolicitud(jdbcTemplate, solicitud);
        List<Solicitud> solicitudesUser = solicitudDao.getSolicitudPendienteByIdUser(jdbcTemplate,
                (Long)usuario.getId(), idInv);
        if (solicitudes.isEmpty()) {
            printMsj(msj5);
        } else {
            if (montoPagado > solicitudes.get(0).getMonto()) {
                msj6 += "Estimado " + usuario.toString() + " El vehículo se le ha adjudicado; en los próximos " +
                        " días le estaremos dando la fecha para su retiro. Gracias";
                msj7 += "Estimado " + usuario.toString() + " El vehículo se le ha adjudicado. " +
                        "\nFavor pasar a retirar el vehículo";
                solicitud.setId(solicitudesUser.get(0).getId());
                solicitudDao.updateSolicitudById(jdbcTemplate, true, solicitud.getId());
            } else {
                Usuario user = getIdUsuarioById(solicitudes.get(0).getIdUsuario());
                msj6 += "Estimado " + user.toString() + "\nEl vehículo se le ha adjudicado, en los próximos " +
                        " días le estaremos dando la fecha para su retiro.\nGracias";
                msj7 += "Estimado " + user.toString() + " El vehículo se le ha adjudicado." +
                        "\nFavor pasar a retirar el vehículo";
                solicitud.setId(solicitudesUser.get(0).getId());
                solicitudDao.updateSolicitudById(jdbcTemplate, true, solicitudes.get(0).getId());
            }
            if (inventarios.get(0).getCantidad() == 0) {
                printMsj(msj6);
            } else {
                updateInventario(inventarios.get(0));
                printMsj(msj7);
            }
        }
    }

    /**
     * Método:              printMsj
     * Descripción:         Método que permite imprimir los mensajes
     * @param msj           variable de tipo string que representa el mensaje a imprimir
     */
    public void printMsj(String msj){
        System.out.println(msj);
    }

}
