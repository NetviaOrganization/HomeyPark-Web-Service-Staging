package com.homeypark.web_service.reservations.domain.model.commands;

import com.homeypark.web_service.reservations.domain.model.valueobject.Status;

public record UpdateStatusCommand(Long reservationId, Status status) {
}
