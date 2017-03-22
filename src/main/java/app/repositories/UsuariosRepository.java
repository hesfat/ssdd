package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.Usuario;


public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByNombreInAndPasswordIn(String nombre, String password);
	
	public Usuario findByNombre(String nombre);
	
}
