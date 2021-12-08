package cr.ac.cenfotec.autosE.domian;


public class Usuario {
    private Long id;
    private String cedula;
    private String name;
    private String lastName1;
    private String lastName2;
    private String phone;
    private String mail;



    public Usuario(Long id, String cedula, String name,
                   String lastName1, String lastName2,
                   String phone, String mail) {
        this.id = id;
        this.cedula = cedula;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.phone = phone;
        this.mail = mail;
    }
    public Usuario(String cedula, String name,
                   String lastName1, String lastName2,
                   String phone, String mail) {
        this.id = id;
        this.cedula = cedula;
        this.id = id;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.phone = phone;
        this.mail = mail;
    }
    public Usuario() {
    }
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return name + " " + lastName1 + " " + lastName2 + ", cédula: " + cedula;
    }
}
