package com.example.onlineBusTicket.repository;

import com.example.onlineBusTicket.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillRepository extends JpaRepository<BillEntity,Long> {
    Optional<BillEntity> findByReservationId(Long reservationId);
}
