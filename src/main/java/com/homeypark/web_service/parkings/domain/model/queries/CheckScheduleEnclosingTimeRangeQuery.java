package com.homeypark.web_service.parkings.domain.model.queries;


import com.homeypark.web_service.parkings.domain.model.valueobjects.WeekDay;

import java.time.LocalTime;

public record CheckScheduleEnclosingTimeRangeQuery(
        WeekDay day,
        LocalTime startTime,
        LocalTime endTime
) {
}
