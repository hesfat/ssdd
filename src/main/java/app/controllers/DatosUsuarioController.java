package app.controllers;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.entities.Actividad;
import app.entities.Comentarios;
import app.entities.Usuario;
import app.repositories.ActividadesRepository;
import app.repositories.ComentariosRepository;
import app.repositories.UsuariosRepository;

@Controller
public class DatosUsuarioController {
 
@Autowired
private UsuariosRepository repository;

@Autowired
private ActividadesRepository repositoryA;

@Autowired
private ComentariosRepository repositoryC;

private String nombreUsuario;

@PostConstruct
 public void init() {
	 	
 repository.save(new Usuario("Pepe", "Apellido 1","pass", new Date()));
 repository.save(new Usuario("Juan", "Apellido 2", "pass", new Date()));
 repositoryA.save(new Actividad(1, "Título de la actividad", "Descripción de la actividad", new Date(), new Date()));
 repositoryA.save(new Actividad(1, "C", "D", new Date(), new Date())); 
 repositoryC.save(new Comentarios(1,1,"Comentario 1", new Date(), null));
 repositoryC.save(new Comentarios(1,1,"Comentario 2", new Date(), null));
 repositoryC.save(new Comentarios(1,1,"Comentario 3", new Date(), null));

 
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

//region Usuarios

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
	
	//endregion
	
	//region Actividades
	
	@GetMapping("/actividades")
	public String listadoActividades(Model model) {
		 model.addAttribute("actividades", repositoryA.findAll());
	return "mostrar_actividades_template";
	} 
	

	 @GetMapping("/actividades/alta")
		public String altaActividad(Model model) {
		 return "alta_actividad";
	 }
	 

	 @PostMapping("/actividad/nuevo")
		public String nuevaActividad(Model model, Actividad actividad) {
		 
			repositoryA.save(actividad);

			return "actividad_guardada_template";

		}
	 
		@GetMapping("/actividad/{id}")
		public String verActividad(Model model, @PathVariable long id) {

			Actividad actividad = repositoryA.findOne(id);
			List<Comentarios> comentarios = repositoryC.findById(id);

			model.addAttribute("actividad", actividad);
			model.addAttribute("comentarios", comentarios);

			return "ver_actividad_template";
		}
	 
		 @PostMapping("/actividad/{id}")
			public String nuevoComentario(Model model, Comentarios comentario) {
			 
				repositoryC.save(comentario);

				return "actividad_guardada_template";

			}
		 
	
	
	//endregion
	
}