package org.bugtracker.api.models;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference("author")
    @NotNull
    private Developer author;

    @CreationTimestamp
    private Date creationDate;

    @Builder.Default
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Priority priority = Priority.LOW;

    @Builder.Default
    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Progress progress = Progress.TODO;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference("assignee")
    private Developer assignee;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public static enum Priority {
        LOW, MEDIUM, HIGH
    };
    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public static enum Progress {
        TODO, DOING, DONE
    };
}
