package cr.ac.cenfotec.autosE.domian;

public class Solicitud {
    private Long id;
    private Long idUsuario;
    private Long idInventario;
    private float monto;
    private boolean status;

    public Solicitud() {
    }

    public Solicitud(Long idUsuario, Long idInventario, float monto, boolean status) {
        this.idUsuario = idUsuario;
        this.idInventario = idInventario;
        this.monto = monto;
        this.status = status;
    }

    public Solicitud(Long id, Long idUsuario, Long idInventario, float monto, boolean status) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idInventario = idInventario;
        this.monto = monto;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Long idInventario) {
        this.idInventario = idInventario;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idInventario=" + idInventario +
                ", monto=" + monto +
                ", status=" + status +
                '}';
    }
}
