package app.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.web.context.annotation.SessionScope;


@Entity
@SessionScope
public class Valoracion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long idCreador;
	private long idActividad;
	private int valoracion;
	private Date fechaAlta;
	
	public Valoracion(){
	}
	
	
	public Valoracion(long id, long idCreador, long idActividad, int valoracion, Date fechaAlta) {
		this.id = id;
		this.idCreador = idCreador;
		this.idActividad = idActividad;
		this.valoracion = valoracion;
		this.fechaAlta = fechaAlta;
	}
	
	public Valoracion(long idCreador, long idActividad, int valoracion, Date fechaAlta) {
		this.idCreador = idCreador;
		this.idActividad = idActividad;
		this.valoracion = valoracion;
		this.fechaAlta = fechaAlta;
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
	public int getValoracion() {
		return valoracion;
	}
	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	
	
	
}
