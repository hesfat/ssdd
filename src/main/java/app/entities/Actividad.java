package app.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.web.context.annotation.SessionScope;


@Entity
@SessionScope
public class Actividad implements Serializable  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long idCreador;
	private String nombre;
	private String descripcion;
	private Date fechaAlta;
	private Date fechaBaja;
	
	
	public Actividad(long idCreador, String nombre, String descripcion, Date fechaAlta, Date fechaBaja) {
		super();
		this.idCreador = idCreador;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
	}
	
	public Actividad() {
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdCreador() {
		return idCreador;
	}
	public void setIdCreador(long idCreador) {
		this.idCreador = idCreador;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	
	
}
