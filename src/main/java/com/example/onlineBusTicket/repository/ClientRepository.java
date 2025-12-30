package com.example.onlineBusTicket.repository;

import ch.qos.logback.core.net.server.Client;
import com.example.onlineBusTicket.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity,Long> {
    Optional<ClientEntity> findByEmail(String email);
}
