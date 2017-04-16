package app.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.UserRepositoryAuthenticationProvider;
import app.entities.Actividad;
import app.entities.Comentarios;
import app.entities.Usuario;
import app.entities.Valoracion;
import app.repositories.ActividadesRepository;
import app.repositories.ComentariosRepository;
import app.repositories.UsuariosRepository;
import app.repositories.ValoracionRepository;

@Controller
public class DatosUsuarioController {
 
@Autowired
private UsuariosRepository repository;

@Autowired
private ActividadesRepository repositoryA;

@Autowired
private ComentariosRepository repositoryC;

@Autowired
private ValoracionRepository repositoryV;

@Autowired 
private HttpSession httpSession;



@PostConstruct
 public void init() {
	 	
	

  repositoryA.save(new Actividad(1, "Título de la actividad", "Descripción de la actividad", new Date(), new Date()));
 repositoryA.save(new Actividad(1, "C", "D", new Date(), new Date())); 
 repositoryC.save(new Comentarios(1,1,"Comentario 1", new Date(), null));
 repositoryC.save(new Comentarios(1,1,"Comentario 2", new Date(), null));
 repositoryC.save(new Comentarios(1,1,"Comentario 3", new Date(), null));
 repositoryV.save(new Valoracion(1,0,3, new Date()));
 repositoryV.save(new Valoracion(1,1,3, new Date()));
 
 }


@GetMapping("/") 
public String inicio(Model model) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	if (!(auth instanceof AnonymousAuthenticationToken)) {
		model.addAttribute("usuarioSesion",(Usuario)auth.getPrincipal());
	}
	//model.addAttribute("usuario",usuario);
	//model.addAttribute("usuarioSesion",(Usuario)httpSession.getAttribute("usuarioSesion"));
	 
	return "Inicio_template";
}

@GetMapping("/login") 
public String login(Model model) {
	return "login_template";
}

@GetMapping("/loginerror") 
public String loginerror(Model model) {
	return "login_error_template";
}

@GetMapping("/logout") 
public String logout(Model model) {
	return "logout_template";
}

//region Usuarios

@GetMapping("/usuarios")
 public String listadoUsuarios(Model model, HttpServletRequest request, HttpServletResponse response) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	 model.addAttribute("usuarios", repository.findAll());
	 model.addAttribute("admin", auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));
	 
return "mostrar_usuarios_template";
 }
 
 
 
 @GetMapping("usuarios/alta")
 	public String altaUsuario(Model model) {
	 return "alta_usuario_template";
 }
  
	
 @PostMapping("/usuarios/nuevo")
	public String nuevoUsuario(Model model, Usuario usuario) {
		BCryptPasswordEncoder Codificador = new  BCryptPasswordEncoder();
	 usuario.setPasswordHash(Codificador.encode(usuario.getPasswordHash()));
	 	List<String> roles = new ArrayList<String>();
	 	roles.add("USER");
		usuario.setRoles(roles);
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
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
			 repository.delete(id);
			 return "redirect:/";
		 }else{
			 return "/accionerror_template";
		 }
		
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
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = (Usuario) auth.getPrincipal();
			Actividad actividad = repositoryA.findOne(id);
			List<Comentarios> comentarios = repositoryC.findAllByIdActividad(id);
			Valoracion valoracion = repositoryV.findByIdCreadorInAndIdActividadIn(usuario.getId(), id);

			
			model.addAttribute("usuario", usuario);
			model.addAttribute("comentarios", comentarios);
			model.addAttribute("actividad", actividad);
			model.addAttribute("valoracion", valoracion);

			return "ver_actividad_template";
		}
	 
		 @PostMapping("/actividad/{id}")
			public String nuevoComentario(Model model, Comentarios comentario) {
			 comentario.setId(0);
			 comentario.setFechaAlta(new Date());
			 /*
			 Comentarios c = new Comentarios();
			 c.setIdActividad(comentario.getIdActividad());
			 c.setIdCreador(comentario.getIdCreador());
			 c.setTexto(comentario.getTexto());
			 c.setFechaAlta(new Date());
				repositoryC.save(c);
				*/
			 repositoryC.save(comentario);

				return "actividad_guardada_template";

			}
		 
		 @GetMapping("/actividad/{id}/{idUsuario}/{idValoracion}/{valor}")
			public String nuevoComentario1(Model model, @PathVariable long id, @PathVariable long idUsuario, @PathVariable long idValoracion, @PathVariable int valor) {
			  	
			 Valoracion valoracion = new Valoracion(idValoracion, idUsuario, id, valor, new Date());
			 
				repositoryV.save(valoracion);

				return "valoracion_guardada_template";

			}
		 
	
	
	//endregion
	
		 
			//for 403 access denied page
			@GetMapping("/403")
			public String accesssDenied(Model model, @PathVariable long id, @PathVariable long idUsuario, @PathVariable long idValoracion, @PathVariable int valor) {

			  //check if user is login
			  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			  if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
			  }

			 
			  return "usuario_no_logado";

			}
			
			
}