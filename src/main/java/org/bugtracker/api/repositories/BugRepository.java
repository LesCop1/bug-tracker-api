package org.bugtracker.api.repositories;

import java.util.Optional;

import org.bugtracker.api.models.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Integer> {
    Optional<Bug> findById(Integer id);
}
