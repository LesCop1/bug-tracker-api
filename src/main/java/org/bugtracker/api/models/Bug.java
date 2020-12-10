package org.bugtracker.api.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;

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
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @CreationTimestamp
    private Date creationDate;

    @Builder.Default
    @NotNull
    private Priority priority = Priority.LOW;

    @Builder.Default
    @NotNull
    private Progress progress = Progress.TODO;

    @ManyToOne(optional = true)
    @JsonBackReference
    private Developer assignee;

    public static enum Priority {LOW, MEDIUM, HIGH};
    public static enum Progress {TODO, DOING, DONE};
}
