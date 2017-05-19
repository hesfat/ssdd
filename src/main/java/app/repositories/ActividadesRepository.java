package app.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import app.entities.Actividad;

@Cacheable
public interface ActividadesRepository extends JpaRepository<Actividad, Long> {
	
}