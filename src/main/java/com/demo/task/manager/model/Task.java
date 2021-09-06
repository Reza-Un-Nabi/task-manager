package com.demo.task.manager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Lob
    private String description;

    @Column(nullable = false)
    private String status;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name="project")
    private Project project;

    @ManyToOne
    @JoinColumn(name="user")
    private User user;

    @UpdateTimestamp
    private Timestamp lastUpdate;
    @CreationTimestamp
    private Timestamp createdStamp;
}
