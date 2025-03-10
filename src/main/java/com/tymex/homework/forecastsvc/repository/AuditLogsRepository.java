package com.tymex.homework.forecastsvc.repository;

import com.tymex.homework.forecastsvc.model.AuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogsRepository extends JpaRepository<AuditLogs, Long> {
}
