package org.bugtracker.api.services;

import org.bugtracker.api.models.Developer;
import org.bugtracker.api.repositories.DeveloperRepository;
import org.bugtracker.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

        @Autowired
        DeveloperRepository developerRepository;

        @Override
        @Transactional
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Developer developer = developerRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));

                return UserPrincipal.builder()
                                        .id(developer.getId())
                                        .username(developer.getUsername())
                                        .password(developer.getPassword())
                                        .name(developer.getName())
                                        .build();
        }

        @Transactional
        public UserDetails loadUserById(Integer id) {
                Developer developer = developerRepository.findById(id)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

                return UserPrincipal.builder()
                                        .id(developer.getId())
                                        .username(developer.getUsername())
                                        .password(developer.getPassword())
                                        .name(developer.getName())
                                        .build();
        }
}
