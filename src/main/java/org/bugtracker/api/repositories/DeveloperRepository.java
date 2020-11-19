package org.bugtracker.api.repositories;

import java.util.Optional;

import org.bugtracker.api.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Optional<Developer> findById(Integer id);
}
