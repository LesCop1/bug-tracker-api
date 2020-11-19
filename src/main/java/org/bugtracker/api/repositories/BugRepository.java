package org.bugtracker.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Object, Integer> { //TODO Create Bug model
    
}
