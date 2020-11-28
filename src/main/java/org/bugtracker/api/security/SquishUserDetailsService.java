package org.bugtracker.api.security;

import org.bugtracker.api.models.Developer;
import org.bugtracker.api.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SquishUserDetailsService implements UserDetailsService {

    @Autowired
    DeveloperRepository developerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Developer developer = developerRepository.findByHandle(username)
                .orElseThrow(() -> new UsernameNotFoundException("Developer not found with username : " + username));

        return UserPrincipal.builder().id(developer.getId()).displayName(developer.getDisplayName())
                .handle(developer.getHandle()).password(developer.getPassword()).build();
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Integer id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Developer not found with id : " + id));

        return UserPrincipal.builder().id(developer.getId()).displayName(developer.getDisplayName())
                .handle(developer.getHandle()).password(developer.getPassword()).build();
    }
}
