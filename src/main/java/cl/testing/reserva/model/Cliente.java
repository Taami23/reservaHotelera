package cl.testing.reserva.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Cliente {

    @Id
    @GeneratedValue
    private Integer idCliente;
    private String nombre;
    private String rut;
    private Date fechaNacimiento;
    private String telefono;
    private String correoElectrinico;
    private String contrasena;

    public Cliente() {
    }

    public Cliente(String nombre, String rut, Date fechaNacimiento, String telefono, String correoElectrinico, String contrasena) {
        this.nombre = nombre;
        this.rut = rut;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correoElectrinico = correoElectrinico;
        this.contrasena = contrasena;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectrinico() {
        return correoElectrinico;
    }

    public void setCorreoElectrinico(String correoElectrinico) {
        this.correoElectrinico = correoElectrinico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(idCliente, cliente.idCliente) &&
                Objects.equals(nombre, cliente.nombre) &&
                Objects.equals(rut, cliente.rut) &&
                Objects.equals(fechaNacimiento, cliente.fechaNacimiento) &&
                Objects.equals(telefono, cliente.telefono) &&
                Objects.equals(correoElectrinico, cliente.correoElectrinico) &&
                Objects.equals(contrasena, cliente.contrasena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCliente, nombre, rut, fechaNacimiento, telefono, correoElectrinico, contrasena);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", rut='" + rut + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", telefono='" + telefono + '\'' +
                ", correoElectrinico='" + correoElectrinico + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }


}
