package com.homeypark.web_service.reservations.infrastructure.persistence.jpa.repositories;

import com.homeypark.web_service.reservations.domain.model.aggregates.Reservation;
import com.homeypark.web_service.reservations.domain.model.valueobject.GuestId;
import com.homeypark.web_service.reservations.domain.model.valueobject.HostId;
import com.homeypark.web_service.reservations.domain.model.valueobject.ParkingId;
import com.homeypark.web_service.reservations.domain.model.valueobject.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStatus(Status status);
    List<Reservation> findByStatusIn(List<Status> statusList);
    List<Reservation> findByHostId(HostId hostId);
    List<Reservation> findByGuestId(GuestId guestId);

    @Query(
            "SELECT COUNT(r.id) > 0 " +
                    "FROM Reservation r " +
                    "WHERE r.parkingId = :parkingId " +
                    "AND r.reservationDate = :reservationDate " +
                    "AND r.status IN :statusList " +
                    "AND (" +
                    "  (r.startTime <= :startTime AND r.endTime > :startTime) OR " +
                    "  (r.startTime < :endTime AND r.endTime >= :endTime) OR " +
                    "  (r.startTime >= :startTime AND r.endTime <= :endTime) OR " +
                    "  (r.startTime = :startTime AND r.endTime = :endTime)" +
                    ")"
    )
    boolean existsByOverlapWithStatus(
            @Param("parkingId") ParkingId parkingId,
            @Param("reservationDate") LocalDate reservationDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("statusList") List<Status> statusList
    );
}
