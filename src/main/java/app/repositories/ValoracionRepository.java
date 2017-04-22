package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Amigo;
import app.entities.Valoracion;


public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {
	
	public Valoracion findByIdCreadorInAndIdActividadIn(Long idCreador, Long idActividad);

}