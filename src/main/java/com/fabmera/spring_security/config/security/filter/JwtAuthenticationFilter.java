package com.fabmera.spring_security.config.security.filter;

import com.fabmera.spring_security.entity.UserModel;
import com.fabmera.spring_security.repository.UserRepository;
import com.fabmera.spring_security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.-Obtener el header que contiene el jwt.
        String authHeader = request.getHeader("Authorization"); //Bearer espacio jwt
        //Si el header es nulo o no empieza con Bearer y espacio, continuo con la cadena de filtros
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //2.-Obtener jwt desde el header
        String jwt = authHeader.split(" ")[1];//Obtengo la posicion uno del array y lo separo por espacio
        //3.-Obtener el subject/username desde el jwt
        String username = jwtService. getSubject(jwt);
        if(username !=null){
            //4.-Setear un objeto de autenticacion en el contexto de spring security
            UserModel userModel = userRepository.findByUsername(username).get();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, userModel.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
