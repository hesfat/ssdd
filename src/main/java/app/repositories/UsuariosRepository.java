package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.Usuario;


public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByNombreInAndPasswordHashIn(String nombre, String passwordHash);
	
	public Usuario findByNombre(String nombre);
	
}
