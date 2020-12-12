package org.bugtracker.api.routes;

import java.util.Collection;

import org.bugtracker.api.exceptions.ResourceNotFoundException;
import org.bugtracker.api.models.Developer;
import org.bugtracker.api.payloads.DeveloperRequest;
import org.bugtracker.api.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("developers")
public class DeveloperRoutes {
    @Autowired
    DeveloperRepository developerRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/id/{id}")
    public Developer getDeveloperById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return developerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Developer", "id", id));
    }

    @GetMapping("/username/{username}")
    public Developer getDeveloperByUsername(@PathVariable("username") String username) throws ResourceNotFoundException {
        return developerRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Developer", "username", username));
    }

    @GetMapping
    public Collection<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    @PostMapping
    public Developer createDeveloper(@Validated @RequestBody Developer developer) {
        return developerRepository.save(
                Developer.builder().username(developer.getUsername()).name(developer.getName()).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBug(@PathVariable("id") Integer id, @Validated @RequestBody DeveloperRequest updated)
            throws ResourceNotFoundException {
        return developerRepository.findById(id).map(developer -> {
            if (updated.getName() != null) {
                developer.setName(updated.getName());
            }

            if (updated.getPassword() != null) {
                developer.setPassword(passwordEncoder.encode(updated.getPassword()));
            }

            developerRepository.save(developer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Bug", "id", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeveloper(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return developerRepository.findById(id).map(developer -> {
            developerRepository.delete(developer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Developer", "id", id));
    }
}
