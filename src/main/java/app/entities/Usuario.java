package app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.web.context.annotation.SessionScope;


@Entity
@SessionScope
public class Usuario {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
private String nombre;
private String apellidos;
private String password;
private Date fechaAlta;

@ElementCollection(fetch = FetchType.EAGER)
private List<String> roles;

public Usuario() {
}

public Usuario(String nombre, String apellidos, String password, Date fechaAlta) {
	this.nombre = nombre;
	this.apellidos = apellidos;
	this.fechaAlta = fechaAlta;
	this.password = password;
}

public List<String> getRoles() {
	return roles;
}

public void setRoles(List<String> roles) {
	this.roles = roles;
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

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

}