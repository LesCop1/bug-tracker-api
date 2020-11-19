package org.bugtracker.api.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Bug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private Date creationDate;
    private Priority priority;
    private Progress progress;

    @ManyToOne(optional = true)
    private Developer assignee;

    public static enum Priority {LOW, NORMAL, HIGH};
    public static enum Progress {TODO, DOING, DONE};
}
