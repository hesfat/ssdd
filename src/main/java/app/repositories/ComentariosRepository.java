package app.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.Comentarios;


public interface ComentariosRepository extends JpaRepository<Comentarios, Long> {
	public List<Comentarios> findById(Long id);
	
}