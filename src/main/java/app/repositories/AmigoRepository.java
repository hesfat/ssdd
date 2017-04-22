package app.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entities.Amigo;


public interface AmigoRepository extends JpaRepository<Amigo, Long> {
	
	public List<Amigo> findAllByIdUsuario(long id);
	
	public Amigo findByIdUsuarioInAndIdAmigoIn(long idUsuario, long idAmigo);
	
	@Transactional
	public void deleteByIdUsuarioAndIdAmigo(long idUsuario, long idAmigo);
	
}