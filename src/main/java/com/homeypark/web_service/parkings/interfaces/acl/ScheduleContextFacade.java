package com.homeypark.web_service.parkings.interfaces.acl;

import com.homeypark.web_service.parkings.domain.model.valueobjects.WeekDay;

import java.time.LocalTime;

public interface ScheduleContextFacade {
    boolean doesScheduleEncloseTimeRange(String day, LocalTime startTime, LocalTime endTime);
}
