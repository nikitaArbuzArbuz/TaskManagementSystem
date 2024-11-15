package ru.effective.mobile.java.taskmanagementsystem.app.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments", schema = "tms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    @SequenceGenerator(name = "comments_seq", sequenceName = "comments_seq", schema = "tms")
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "task_id")
    private Task task;
}
