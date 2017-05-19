package app.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import app.entities.Amigo;


public interface AmigoRepository extends JpaRepository<Amigo, Long> {
	
	
	public List<Amigo> findAllByIdUsuario(long id);
	
	public Amigo findByIdUsuarioInAndIdAmigoIn(long idUsuario, long idAmigo);
	
	@Transactional
	@Modifying
	@Query(value="delete from Amigo a where a.idUsuario = ?1 and a.idAmigo = ?2")
	public void deleteByIdUsuarioAndIdAmigo(long idUsuario, long idAmigo);
	
}