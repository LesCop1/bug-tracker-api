package org.bugtracker.api.routes;

import java.util.Collection;

import org.bugtracker.api.exceptions.ResourceNotFoundException;
import org.bugtracker.api.models.Bug;
import org.bugtracker.api.repositories.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("bugs")
public class BugRoutes {
    @Autowired
    BugRepository bugRepository;

    @GetMapping("/{id}")
    public Bug getBug(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return bugRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bug", "id", id));
    }

    @GetMapping()
    public Collection<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    @PostMapping()
    public Bug createBug(@Validated @RequestBody Bug bug) {
        return bugRepository.save(bug);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBug(@PathVariable("id") Integer id, @Validated @RequestBody Bug updated)
            throws ResourceNotFoundException {
        return bugRepository.findById(id).map(bug -> {
            bugRepository.save(updated);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Bug", "id", id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBug(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return bugRepository.findById(id).map(bug -> {
            bugRepository.delete(bug);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Bug", "id", id));
    }
}
