package app.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.web.context.annotation.SessionScope;


@Entity
@SessionScope
@Table(name = "AMIGO")
public class Amigo implements Serializable  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
	private long idA;
	
	private long idUsuario;
	private long idAmigo;

	
	public Amigo(){
	}
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private Usuario usuario;
	
	public Amigo(long id, long idUsuario, long idAmigo) {
		this.idA = id;
		this.idUsuario = idUsuario;
		this.idAmigo = idAmigo;
	}
	
	public Amigo(long idUsuario, long idAmigo) {
		this.idUsuario = idUsuario;
		this.idAmigo = idAmigo;
	}
	
	public long getId() {
		return idA;
	}
	public void setId(long id) {
		this.idA = id;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public long getIdAmigo() {
		return idAmigo;
	}
	public void setIdAmigo(long idAmigo) {
		this.idAmigo = idAmigo;
	}	
}
