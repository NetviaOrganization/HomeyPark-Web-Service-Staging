package com.homeypark.web_service.reservations.application.acl;

import com.homeypark.web_service.reservations.domain.model.queries.GetSuccessfulReservationCountByUserIdQuery;
import com.homeypark.web_service.reservations.domain.services.ReservationQueryService;
import com.homeypark.web_service.reservations.interfaces.acl.ReservationContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ReservationContextFacadeImpl implements ReservationContextFacade {
    private final ReservationQueryService reservationQueryService;

    public ReservationContextFacadeImpl(ReservationQueryService reservationQueryService) {
        this.reservationQueryService = reservationQueryService;
    }

    @Override
    public Long getSuccessfulReservationCountByUserId(Long userId) {
        var query = new GetSuccessfulReservationCountByUserIdQuery(userId);
        return reservationQueryService.handle(query);
    }
}
