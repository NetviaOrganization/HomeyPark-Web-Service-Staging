package com.homeypark.web_service.parkings.infrastructure.persistence.jpa.repositories;

import com.homeypark.web_service.parkings.domain.model.entities.Schedule;
import com.homeypark.web_service.parkings.domain.model.valueobjects.WeekDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(
            "SELECT COUNT(s.id) > 0 " +
                    "FROM Schedule s " +
                    "WHERE s.day = :day " +
                    "  AND s.startTime <= :startTime " +
                    "  AND s.endTime >= :endTime"
    )
    boolean existsScheduleEnclosingTimeRange(
            @Param("day") WeekDay day,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
