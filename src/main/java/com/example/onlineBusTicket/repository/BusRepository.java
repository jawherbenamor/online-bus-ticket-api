package com.example.onlineBusTicket.repository;

import com.example.onlineBusTicket.entity.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepository extends JpaRepository<BusEntity,Long> {
    Optional<BusEntity> findByNumber(String number);
}
