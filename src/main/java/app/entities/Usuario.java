package app.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.annotation.SessionScope;


@Entity
@SessionScope
@Table(name = "USUARIO")
public class Usuario {
	
@Id
@Column(name = "IdUsuario")
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
private String nombre;
private String apellidos;
private String passwordHash;
private Date fechaAlta;

@ElementCollection(fetch = FetchType.EAGER)
private List<String> roles;

public Usuario() {
}

@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Amigo> amigos;

public List<Amigo> getAmigos() {
    return amigos;
}

public void setAmigos(List<Amigo> amigos) {
    this.amigos = amigos;
}

public Usuario(String nombre, String apellidos, String passwordHash, Date fechaAlta, String... roles) {
	BCryptPasswordEncoder Codificador = new  BCryptPasswordEncoder();
	this.nombre = nombre;
	this.apellidos = apellidos;
	this.passwordHash = Codificador.encode(passwordHash);
	this.fechaAlta = fechaAlta;
	this.roles = new ArrayList<>(Arrays.asList(roles));
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

public String getPasswordHash() {
	return passwordHash;
}

public void setPasswordHash(String password) {
	//BCryptPasswordEncoder Codificador = new  BCryptPasswordEncoder();
	//this.passwordHash = Codificador.encode(password);
	this.passwordHash = password;
}

}