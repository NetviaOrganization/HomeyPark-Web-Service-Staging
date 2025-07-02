package com.homeypark.web_service.parkings.application.acl;

import com.homeypark.web_service.parkings.domain.model.queries.CheckScheduleEnclosingTimeRangeQuery;
import com.homeypark.web_service.parkings.domain.model.valueobjects.WeekDay;
import com.homeypark.web_service.parkings.domain.services.ScheduleQueryService;
import com.homeypark.web_service.parkings.interfaces.acl.ScheduleContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ScheduleContextFacadeImpl implements ScheduleContextFacade {
    private final ScheduleQueryService scheduleQueryService;

    public ScheduleContextFacadeImpl(ScheduleQueryService scheduleQueryService) {
        this.scheduleQueryService = scheduleQueryService;
    }

    @Override
    public boolean doesScheduleEncloseTimeRange(String day, LocalTime startTime, LocalTime endTime) {
        WeekDay weekDay = WeekDay.valueOf(day.toUpperCase());
        var query = new CheckScheduleEnclosingTimeRangeQuery(weekDay, startTime, endTime);
        return scheduleQueryService.handle(query);

    }
}
