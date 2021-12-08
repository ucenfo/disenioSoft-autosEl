package cr.ac.cenfotec.autosE.domian;

public class Inventario {
    private Long id;
    private String modelo;
    private int pasajeros;
    private int precio;
    private int cantidad;


    public Inventario(Long id, String modelo,
                      int pasajeros, int precio,
                      int cantidad) {
        this.id = id;
        this.modelo = modelo;
        this.pasajeros = pasajeros;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Inventario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(int pasajeros) {
        this.pasajeros = pasajeros;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    @Override
    public String toString() {
        return "Inventario{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", pasajeros=" + pasajeros +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
