package org.bugtracker.api.payloads;

import javax.validation.constraints.Size;

import org.bugtracker.api.models.Developer;
import org.bugtracker.api.models.Bug.Priority;
import org.bugtracker.api.models.Bug.Progress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BugRequest {
    @Size(min = 2, max = 20)
    private String title;

    @Size(min = 20, max = 200)
    private String description;

    private Priority priority;

    private Progress progress;

    private Developer assignee;
}
