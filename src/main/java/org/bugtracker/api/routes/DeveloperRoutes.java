package org.bugtracker.api.routes;

import java.util.Collection;

import org.bugtracker.api.exceptions.ResourceNotFoundException;
import org.bugtracker.api.models.Developer;
import org.bugtracker.api.repositories.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeveloperRoutes {
    @Autowired
    DeveloperRepository developerRepository;

    @GetMapping("developers/{id}")
    public Developer getDeveloper(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return developerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Developer not found with id " + id));
    }

    @GetMapping("developers")
    public Collection<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    @PostMapping("developers")
    public Developer createDeveloper(@Validated @RequestBody Developer developer) {
        return developerRepository.save(
                Developer.builder().handle(developer.getHandle()).displayName(developer.getDisplayName()).build());
    }

    @DeleteMapping("developers/{id}")
    public ResponseEntity<?> deleteDeveloper(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return developerRepository.findById(id).map(developer -> {
            developerRepository.delete(developer);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Developer not found with id " + id));
    }
}
