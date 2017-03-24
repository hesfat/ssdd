package app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import app.entities.Usuario;
import app.repositories.UsuariosRepository;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {
	
 @Autowired
 private UsuariosRepository userRepository;
 
 @Override
 public Authentication authenticate(Authentication auth) throws AuthenticationException {
 Usuario user = userRepository.findByNombre(auth.getName());
 if (user == null) {
 throw new BadCredentialsException("User not found");
 }
 String password = (String) auth.getCredentials();
 if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
 throw new BadCredentialsException("Wrong password");
 }

 List<GrantedAuthority> roles = new ArrayList<>();
 for (String role : user.getRoles()) {
 roles.add(new SimpleGrantedAuthority(role));
 }
 return new UsernamePasswordAuthenticationToken(user.getNombre(), password, roles);
 }

 @Override
 public boolean supports(Class<?> authentication) {
     return authentication.equals(
       UsernamePasswordAuthenticationToken.class);
 }
}
