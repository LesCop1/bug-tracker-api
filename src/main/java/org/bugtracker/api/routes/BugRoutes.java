package org.bugtracker.api.routes;

import java.util.Collection;

import org.bugtracker.api.exceptions.ResourceNotFoundException;
import org.bugtracker.api.models.Bug;
import org.bugtracker.api.models.Developer;
import org.bugtracker.api.payloads.BugRequest;
import org.bugtracker.api.repositories.BugRepository;
import org.bugtracker.api.repositories.DeveloperRepository;
import org.bugtracker.api.security.CurrentUser;
import org.bugtracker.api.security.UserPrincipal;
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

    @Autowired
    DeveloperRepository developerRepository;

    @GetMapping("/{id}")
    public Bug getBug(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return bugRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bug", "id", id));
    }

    @GetMapping()
    public Collection<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    @PostMapping()
    public Bug createBug(@Validated @RequestBody BugRequest bug, @CurrentUser UserPrincipal user) {
        Developer author = developerRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "id", user.getId()));

        return bugRepository.save(Bug.builder().title(bug.getTitle()).description(bug.getDescription()).author(author)
                .priority(bug.getPriority()).progress(bug.getProgress()).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBug(@PathVariable("id") Integer id, @Validated @RequestBody BugRequest updated)
            throws ResourceNotFoundException {
        return bugRepository.findById(id).map(bug -> {
            if (updated.getTitle() != null) {
                bug.setTitle(updated.getTitle());
            }

            if (updated.getDescription() != null) {
                bug.setDescription(updated.getDescription());
            }

            if (updated.getPriority() != null) {
                bug.setPriority(updated.getPriority());
            }

            if (updated.getProgress() != null) {
                bug.setProgress(updated.getProgress());
            }

            if (updated.getAssignee() != null) {
                bug.setAssignee(updated.getAssignee());
            }

            bugRepository.save(bug);
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
