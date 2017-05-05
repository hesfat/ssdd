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
public class Comentarios implements Serializable  {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long idCreador;
	private long idActividad;
	private String texto;
	private Date fechaAlta;
	private Date fechaBaja;
	
	
	public Comentarios(long idCreador, long idActividad, String texto, Date fechaAlta, Date fechaBaja) {
		
		this.idCreador = idCreador;
		this.idActividad = idActividad;
		this.texto = texto;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
	}
	
	public Comentarios(long id, long idCreador, long idActividad, String texto, Date fechaAlta, Date fechaBaja) {
		this.id = id;
		this.idCreador = idCreador;
		this.idActividad = idActividad;
		this.texto = texto;
		this.fechaAlta = fechaAlta;
		this.fechaBaja = fechaBaja;
	}
	
	public Comentarios() {
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
	public long getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(long idActividad) {
		this.idActividad = idActividad;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
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