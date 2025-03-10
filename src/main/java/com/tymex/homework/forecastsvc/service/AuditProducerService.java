package com.tymex.homework.forecastsvc.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tymex.homework.forecastsvc.model.AuditLogs;
import com.tymex.homework.forecastsvc.repository.AuditLogsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditProducerService {

    private final KafkaTemplate<String ,String> kafkaTemplate;
    private final AuditLogsRepository auditLogRepo;
    private final ObjectMapper objectMapper;


    public void logAndPublish(String event, String state, String cityName, Object content) throws JsonProcessingException {
        try {
            String contentToString = objectMapper.writeValueAsString(content);
                        AuditLogs msg = AuditLogs.builder()
                                .event_type(event)
                                .state(state)
                                .content(contentToString)
                                .created_at(Timestamp.from(Instant.now()))
                                .build();
            kafkaTemplate.send("forecastNotificationTopic", cityName, objectMapper.writeValueAsString(msg))
                    .whenComplete((record, exception) -> {
                        if (exception != null) {
                            log.error("Error sending message: {}", exception.getMessage());
                        }

                        auditLogRepo.save(msg);
                    })
            ;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
