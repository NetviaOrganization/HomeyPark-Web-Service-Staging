package com.homeypark.web_service.parkings.domain.services;

import com.homeypark.web_service.parkings.domain.model.entities.Schedule;
import com.homeypark.web_service.parkings.domain.model.queries.CheckScheduleEnclosingTimeRangeQuery;
import com.homeypark.web_service.parkings.domain.model.queries.GetAllScheduleQuery;

import java.util.List;

public interface ScheduleQueryService {
    List<Schedule> handle(GetAllScheduleQuery query);
    boolean handle(CheckScheduleEnclosingTimeRangeQuery query);
}
