package app.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Actividad;
import app.entities.Amigo;
import app.entities.Comentarios;
import app.entities.Usuario;
import app.entities.Valoracion;
import app.repositories.ActividadesRepository;
import app.repositories.AmigoRepository;
import app.repositories.ComentariosRepository;
import app.repositories.UsuariosRepository;
import app.repositories.ValoracionRepository;

@RestController
public class AppRestController {
 
@Autowired
private UsuariosRepository repository;

@Autowired
private ActividadesRepository repositoryA;

@Autowired
private ComentariosRepository repositoryC;

@Autowired
private ValoracionRepository repositoryV;

@Autowired
private AmigoRepository repositoryAmigo;

@Autowired 
private HttpSession httpSession;

//region Usuarios

@GetMapping("/inicio")
public Usuario inicio() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	if (!(auth instanceof AnonymousAuthenticationToken)) {
		return (Usuario)auth.getPrincipal();
	}
	return null;
}

@GetMapping("/usuariosRest")
 public Map<String,Object> listadoUsuarios() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	//	List<Usuario> listaAmigos = repositoryAmigo.findAllByIdUsuario();
	    Map<String,Object> map=new HashMap<>();
	    map.put("usuarios", repository.findAll());
	    map.put("usuario", auth.getPrincipal());
	    map.put("admin", auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));
	    return map;
 }
 
 @PostMapping("/usuarios/nuevo")
	public Usuario nuevoUsuario(Model model, Usuario usuario) {
		BCryptPasswordEncoder Codificador = new  BCryptPasswordEncoder();
	 usuario.setPasswordHash(Codificador.encode(usuario.getPasswordHash()));
	 	List<String> roles = new ArrayList<String>();
	 	roles.add("USER");
		usuario.setRoles(roles);
		return repository.save(usuario);
	}
 
 @PostMapping("/usuarios/editar")
	public Usuario editarUsuario(Model model, Usuario usuario) {

	 return repository.save(usuario);
	}
 
	@GetMapping("/usuarios/{id}")
	public Usuario verUsuario(Model model, @PathVariable long id) {

		Usuario usuario= repository.findOne(id);

		return usuario;
	}
	
	@GetMapping("/usuarios/{id}/editar")
	public Usuario editarUsuario(Model model, @PathVariable long id) {

		Usuario usuario= repository.findOne(id);

		return usuario;
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
		 
		 @GetMapping("/amigos/{idUsuario}/{idAmigo}")
			public String nuevoAmigo(Model model, @PathVariable long idUsuario, @PathVariable long idAmigo) {
			  	
			 if (repositoryAmigo.findByIdUsuarioInAndIdAmigoIn(idUsuario, idAmigo) == null)
			 {
			 
			 Amigo amigo = new Amigo(idUsuario, idAmigo);
			 
				repositoryAmigo.save(amigo);

				return "amigo_guardado_template";
			 }
			 	return "amigo_existe_template";
			}
		 
		 @GetMapping("/amigos/{idUsuario}")
			public String verAmigos(Model model, @PathVariable long idUsuario) {
				Usuario usuario= repository.findOne(idUsuario);
				List idsAmigos = new ArrayList<Long>();
				for (Amigo amigo : usuario.getAmigos())
				{
					idsAmigos.add(amigo.getIdAmigo());
				}
				model.addAttribute("amigos",repository.findByIdIn(idsAmigos));
				model.addAttribute("usuario", usuario);
				
				return "mostrar_amigos_template";

			}
	
		 @GetMapping("/amigos/eliminar/{idUsuario}/{idAmigo}")
			public String eliminarAmigos(Model model, @PathVariable long idUsuario, @PathVariable long idAmigo) {
				repositoryAmigo.deleteByIdUsuarioAndIdAmigo(idUsuario,idAmigo);
				return "redirect:/amigos/" + idUsuario;

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