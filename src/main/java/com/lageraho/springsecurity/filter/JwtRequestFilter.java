package com.lageraho.springsecurity.filter;


import com.lageraho.springsecurity.service.UserDetailsServiceImpl;
import com.lageraho.springsecurity.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.header.key}")
    private String JWT_HEADER_KEY;

    @Autowired
    private UserDetailsServiceImpl userDetailService;
    @Autowired
    private JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        String authorizationHeader = httpServletRequest.getHeader(JWT_HEADER_KEY);
        String username = null;


        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader = authorizationHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(authorizationHeader);
            } catch (ExpiredJwtException e) {
                log.debug("{}", e.getMessage());
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT was expired");
                return;
            }

        }



        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            log.debug("Authenticating {}", username);
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            if (!userDetailService.loadUserByUsername(username).isEnabled()) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.getWriter().write("User was locked by administrator");
                return;
//                throw new ResponseStatusException(HttpStatus.LOCKED, "User was locked by administrator");
            }
            if (jwtUtil.validateToken(authorizationHeader, userDetails)) {
//                log.debug("Token is valid");

                try {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

//                    actionLogRepository.save(ActionLog.builder()
//                    		.timeStamp(System.currentTimeMillis())
//                            .username(username)
//                            .action(httpServletRequest.getRequestURI())
//                            .roleName(userDetailService.getRoleNameForUsername(username))
//                            .remarks("Successful")
//                            .build()
//                    );
                } catch (Exception e) {
//                    actionLogRepository.save(ActionLog.builder()
//                    		.timeStamp(System.currentTimeMillis())
//                            .username(username)
//                            .action(httpServletRequest.getRequestURI())
//                            .roleName(userDetailService.getRoleNameForUsername(username))
//                            .remarks("There was an error: " + e.getMessage())
//                            .build()
//                    );

                    e.printStackTrace();
                }
                httpServletResponse.addHeader("Expires", jwtUtil.extractExpiration(authorizationHeader) + "");
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
//        log.info("Completed jwt filter");
    }
}
