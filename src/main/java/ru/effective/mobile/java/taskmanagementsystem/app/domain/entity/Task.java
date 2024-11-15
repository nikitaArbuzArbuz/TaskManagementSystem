package ru.effective.mobile.java.taskmanagementsystem.app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tasks", schema = "tms")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_seq")
    @SequenceGenerator(name = "tasks_seq", sequenceName = "tasks_seq", schema = "tms")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "assignee_id")
    private User assignee;

    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
}
