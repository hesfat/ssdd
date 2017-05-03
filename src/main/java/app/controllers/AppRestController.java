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

//region Usuarios

@GetMapping("/rest/inicio")
public Usuario inicio() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	if (!(auth instanceof AnonymousAuthenticationToken)) {
		return (Usuario)auth.getPrincipal();
	}
	return null;
}

@GetMapping("/rest/usuarios")
 public Map<String,Object> listadoUsuarios() {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	//	List<Usuario> listaAmigos = repositoryAmigo.findAllByIdUsuario();
	    Map<String,Object> map=new HashMap<>();
	    map.put("usuarios", repository.findAll());
	    map.put("usuario", auth.getPrincipal());
	    map.put("admin", auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));
	    return map;
 }
 
 @PostMapping("/rest/usuarios/nuevo")
	public Usuario nuevoUsuario(Usuario usuario) {
		return repository.save(usuario);
	}
 
 @PostMapping("/rest/usuarios/editar")
	public Usuario editarUsuario(Usuario usuario) {

	 return repository.save(usuario);
	}
 
	@GetMapping("/rest/usuarios/{id}")
	public Usuario verUsuario(@PathVariable long id) {

		Usuario usuario= repository.findOne(id);

		return usuario;
	}
	
	@GetMapping("/rest/usuarios/{id}/editar")
	public Usuario editarUsuario(Model model, @PathVariable long id) {

		Usuario usuario= repository.findOne(id);

		return usuario;
	}
	
	@GetMapping("/rest/usuarios/{id}/eliminar")
	public String eliminarUsuario( @PathVariable long id) {
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
	
	@GetMapping("/rest/actividades")
	public List<Actividad> listadoActividades() {
		return repositoryA.findAll();
	} 
	 

	 @PostMapping("/rest/actividad/nuevo")
		public Actividad nuevaActividad(Actividad actividad) {
		 
			return repositoryA.save(actividad);

		}
	 
		 @PostMapping("/rest/actividad/{id}")
			public String nuevoComentario(Comentarios comentario) {
			 repositoryC.save(comentario);
			 return "actividad_guardada_template";
			}
		 
		 @GetMapping("/rest/actividad/{id}/{idUsuario}/{idValoracion}/{valor}")
			public String nuevoComentario1(Valoracion valoracion) {
				repositoryV.save(valoracion);
				return "valoracion_guardada_template";
			}
		 
		 @GetMapping("/rest/amigos/{idUsuario}/{idAmigo}")
			public String nuevoAmigo(Model model, @PathVariable long idUsuario, @PathVariable long idAmigo) {
			  	
			 if (repositoryAmigo.findByIdUsuarioInAndIdAmigoIn(idUsuario, idAmigo) == null)
			 {
			 
			 Amigo amigo = new Amigo(idUsuario, idAmigo);
			 
				repositoryAmigo.save(amigo);

				return "amigo_guardado_template";
			 }
			 	return "amigo_existe_template";
			}
	
		 @GetMapping("/rest/amigos/eliminar/{idUsuario}/{idAmigo}")
			public void eliminarAmigos(@PathVariable long idUsuario, @PathVariable long idAmigo) {
				repositoryAmigo.deleteByIdUsuarioAndIdAmigo(idUsuario,idAmigo);
			}
		 
		 @GetMapping("/rest/amigos/{idUsuario}")
			public Usuario getUsuario(@PathVariable long idUsuario) {
				return repository.findOne(idUsuario);

			}
		 
		 @GetMapping("/rest/amigosID/{idUsuario}")
			public List findByIdIn(@PathVariable List idsAmigos) {
				return repository.findByIdIn(idsAmigos);

			}
		 
		 @GetMapping("/rest/amigosIDAmigo/{idUsuario}/{idAmigo}")
			public Amigo findByIdUsuarioInAndIdAmigoIn( @PathVariable long idUsuario, @PathVariable long idAmigo) {
				return repositoryAmigo.findByIdUsuarioInAndIdAmigoIn(idUsuario, idAmigo);
			}
		 
		 @GetMapping("/rest/amigosalta/{idUsuario}")
			public Amigo AltaAmigo(Amigo amigo) {
				return repositoryAmigo.save(amigo);
			}
		 
			@GetMapping("/rest/actividad/{id}")
			public Actividad getActividad(Long id) {
				return repositoryA.findOne(id);
			}
			@GetMapping("/rest/Comentarios/{id}")
			public List<Comentarios> getComentarios(Long id) {
				return repositoryC.findAllByIdActividad(id);
			}	
		 @GetMapping("/rest/valoracion/{idUsuario}/{id}")
			public Valoracion getValoracion(Long idUsuario, Long id) {
				return repositoryV.findByIdCreadorInAndIdActividadIn(idUsuario, id);
			}
		 
		 
		 
	//endregion
	
		 

			
			
}