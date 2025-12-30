package com.example.onlineBusTicket.repository;

import com.example.onlineBusTicket.entity.ReservationItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationItemRepository extends JpaRepository<ReservationItemEntity,Long> {
}
