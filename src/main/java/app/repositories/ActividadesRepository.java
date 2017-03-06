package app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.Actividad;


public interface ActividadesRepository extends JpaRepository<Actividad, Long> {
	
}