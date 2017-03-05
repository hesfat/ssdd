package app.controllers;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.entities.Usuario;
import app.repositories.UsuariosRepository;

@Controller
public class DatosUsuarioController {
 
@Autowired
private UsuariosRepository repository;

private String nombreUsuario;

@PostConstruct
 public void init() {
	 	
 repository.save(new Usuario("Pepe", "Apellido 1","pass", new Date()));
 repository.save(new Usuario("Juan", "Apellido 2", "pass", new Date()));
 }
 
@GetMapping("/") 
public String inicio(Model model) {
	 return "Inicio.html";
}

@PostMapping("/login") 
public String login(Model model, Usuario usuario) {
	usuario = repository.findByNombreInAndPasswordIn(usuario.getNombre(), usuario.getPassword());
	nombreUsuario = usuario.getNombre();
	
	return "usuario_logado_template";
}


@GetMapping("/usuarios")
 public String listadoUsuarios(Model model) {
	 model.addAttribute("usuarios", repository.findAll());
return "mostrar_usuarios_template";
 }
 
 
 
 @GetMapping("usuarios/alta")
 	public String altaUsuario(Model model) {
	 return "alta_usuario_template";
 }
  
	
 @PostMapping("/usuarios/nuevo")
	public String nuevoUsuario(Model model, Usuario usuario) {
	 
		repository.save(usuario);

		return "usuario_guardado_template";

	}
 
 @PostMapping("/usuarios/editar")
	public String editarUsuario(Model model, Usuario usuario) {

		repository.save(usuario);

		return "usuario_guardado_template";

	}
 
 
	@GetMapping("/usuarios/{id}")
	public String verUsuario(Model model, @PathVariable long id) {

		Usuario usuario= repository.findOne(id);

		model.addAttribute("usuario", usuario);

		return "ver_usuario_template";
	}
	
	@GetMapping("/usuarios/{id}/editar")
	public String editarUsuario(Model model, @PathVariable long id) {

		Usuario usuario= repository.findOne(id);

		model.addAttribute("usuario", usuario);

		return "editar_usuario_template";
	}
	
	@GetMapping("/usuarios/{id}/eliminar")
	public String eliminarUsuario(Model model, @PathVariable long id) {
		repository.delete(id);
		return "redirect:/";
	}
	
	
	
}