package app.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Usuario {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
private String nombre;
private String apellidos;
private Date fechaAlta;

public Usuario() {
}

public Usuario(String nombre, String apellidos, Date fechaAlta) {
	this.nombre = nombre;
	this.apellidos = apellidos;
	this.fechaAlta = fechaAlta;
}

public long getId() {
	return id;
}
public void setId(long Id) {
	this.id = Id;
}

public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getApellidos() {
	return apellidos;
}
public void setApellidos(String apellidos) {
	this.apellidos = apellidos;
}

public Date getFechaAlta() {
	return fechaAlta;
}
public void setFechaAlta(Date fechaAlta) {
	this.fechaAlta = fechaAlta;
}

}