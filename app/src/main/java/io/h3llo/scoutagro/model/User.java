package io.h3llo.scoutagro.model;

public class User {


    private String _id;
    private String usuario;
    private String primer_nombre;
    private String apellido_paterno;
    private String tipo_documento;
    private String numero_documento;
    private String email_1;
    private String numero_celular;
    private String role_id;
    private String role_name;
    private String img;

    public User(String _id, String usuario, String primer_nombre, String apellido_paterno, String tipo_documento, String numero_documento, String email_1, String numero_celular, String role_id, String role_name, String img) {
        this._id = _id;
        this.usuario = usuario;
        this.primer_nombre = primer_nombre;
        this.apellido_paterno = apellido_paterno;
        this.tipo_documento = tipo_documento;
        this.numero_documento = numero_documento;
        this.email_1 = email_1;
        this.numero_celular = numero_celular;
        this.role_id = role_id;
        this.role_name = role_name;
        this.img = img;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPrimer_nombre() {
        return primer_nombre;
    }

    public void setPrimer_nombre(String primer_nombre) {
        this.primer_nombre = primer_nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getNumero_documento() {
        return numero_documento;
    }

    public void setNumero_documento(String numero_documento) {
        this.numero_documento = numero_documento;
    }

    public String getEmail_1() {
        return email_1;
    }

    public void setEmail_1(String email_1) {
        this.email_1 = email_1;
    }

    public String getNumero_celular() {
        return numero_celular;
    }

    public void setNumero_celular(String numero_celular) {
        this.numero_celular = numero_celular;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
