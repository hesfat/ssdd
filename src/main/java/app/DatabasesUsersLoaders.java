package app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entities.Amigo;
import app.entities.Usuario;
import app.repositories.UsuariosRepository;

@Component
public class DatabasesUsersLoaders {

	@Autowired
	private UsuariosRepository userRepository;
	
	@PostConstruct
	private void initDatabase(){	 	
		Usuario user = (new Usuario("user", "Apellido 1", "pass", new Date(), "USER"));
		ArrayList<Amigo> listAmigos = new ArrayList<Amigo>();
		listAmigos.add(new Amigo(1,1));
		listAmigos.add(new Amigo(1,2));
		user.setAmigos(listAmigos);
		userRepository.save(user);
		userRepository.save(new Usuario("admin", "Apellido 2", "pass", new Date(), "USER", "ADMIN"));
	}
}
