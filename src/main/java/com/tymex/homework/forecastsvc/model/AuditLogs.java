package com.tymex.homework.forecastsvc.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit_logs")
public class AuditLogs {
    @Id
    private long id;

    @Column(name="created_at")
    @CreationTimestamp
    private Timestamp created_at;

    @Column(name="event_type")
    private String event_type;

    @Column(name="state")
    private String state;

    @Column(name="content", columnDefinition="LONGTEXT")
    private String content;
}
