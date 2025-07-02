package com.homeypark.web_service.parkings.application.internal.queryservices;

import com.homeypark.web_service.parkings.domain.model.entities.Schedule;
import com.homeypark.web_service.parkings.domain.model.queries.CheckScheduleEnclosingTimeRangeQuery;
import com.homeypark.web_service.parkings.domain.model.queries.GetAllScheduleQuery;
import com.homeypark.web_service.parkings.domain.services.ScheduleQueryService;
import com.homeypark.web_service.parkings.infrastructure.persistence.jpa.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleQueryServiceImpl implements ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleQueryServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<Schedule> handle(GetAllScheduleQuery query) {
        return scheduleRepository.findAll();
    }

    @Override
    public boolean handle(CheckScheduleEnclosingTimeRangeQuery query) {
        return scheduleRepository.existsScheduleEnclosingTimeRange(query.day(),query.startTime(),query.endTime());
    }
}
