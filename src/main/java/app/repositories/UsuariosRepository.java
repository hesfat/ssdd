package app.repositories;

import java.util.List;

import javax.persistence.Cacheable;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.Usuario;


public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

	public Usuario findByNombreInAndPasswordHashIn(String nombre, String passwordHash);
	
	public Usuario findByNombre(String nombre);
	
	public Usuario findById(long id);
	
	public List<Usuario> findByIdIn(List<Long> ids);
	
}
