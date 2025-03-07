package com.tymex.homework.forecastsvc.repository;

import com.tymex.homework.forecastsvc.model.AuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogsRepository extends JpaRepository<AuditLogs, Long> {
}
