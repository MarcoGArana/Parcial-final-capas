package com.uca.parcialfinalncapas.security;

import com.uca.parcialfinalncapas.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository; // Repository to access employee data

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Fetches an employee by username or email, throws an exception if not found
        com.uca.parcialfinalncapas.entities.User user = userRepository.findByCorreo(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail));

        // Maps the employee's roles to granted authorities
        Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Converts each role to a granted authority
                .collect(Collectors.toSet());

        // Returns a Spring Security User object with the employee's username, password, and authorities
        return new User(user.getNombre(), user.getPassword(), grantedAuthorities);
    }
}