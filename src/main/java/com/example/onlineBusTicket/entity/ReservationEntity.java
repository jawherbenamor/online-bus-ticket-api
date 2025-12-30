package com.example.onlineBusTicket.entity;


import com.example.onlineBusTicket.reservationStatus.ReservationStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="client_id", nullable = false, foreignKey = @ForeignKey(name="fk_reservation_client"))
    private ClientEntity client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @OneToMany (mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationItemEntity> items = new ArrayList<>();

    public ReservationEntity() {
    }

    public ReservationEntity(Long id, ClientEntity client, ReservationStatus status, Instant createdAt, List<ReservationItemEntity> items) {
        this.id = id;
        this.client = client;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<ReservationItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ReservationItemEntity> items) {
        this.items = items;
    }
}
