package com.homeypark.web_service.reservations.application.internal.outboundservices.acl;

import com.homeypark.web_service.parkings.domain.model.valueobjects.WeekDay;
import com.homeypark.web_service.parkings.interfaces.acl.ScheduleContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ExternalScheduleService {
    private final ScheduleContextFacade scheduleContextFacade;

    public ExternalScheduleService(ScheduleContextFacade scheduleContextFacade) {
        this.scheduleContextFacade = scheduleContextFacade;
    }

    public boolean doesScheduleEncloseTimeRange(String weekDay, LocalTime startTime, LocalTime endTime) {
        return scheduleContextFacade.doesScheduleEncloseTimeRange(weekDay, startTime, endTime);
    }
}
