package com.example.onlineBusTicket.integration;

import com.example.onlineBusTicket.dto.bus.BusCreateRequest;
import com.example.onlineBusTicket.dto.client.ClientCreateRequest;
import com.example.onlineBusTicket.dto.reservation.PayPalDetails;
import com.example.onlineBusTicket.dto.reservation.PayReservationRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationCreateRequest;
import com.example.onlineBusTicket.dto.reservation.ReservationItemRequest;
import com.example.onlineBusTicket.reservationStatus.PaymentType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BusTicketApiIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void end_to_end_should_create_reservation_pay_and_list_bills_sorted() throws Exception {
        // 1) create client
        ClientCreateRequest clientReq = new ClientCreateRequest();
        clientReq.setName("Jawher");
        clientReq.setEmail("jawher.it@example.com");

        String clientJson = mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        Long clientId = objectMapper.readTree(clientJson).get("id").asLong();

        // 2) create bus #1 (80€)
        BusCreateRequest bus1 = new BusCreateRequest();
        bus1.setNumber("BUS-IT-001");
        bus1.setSeats(50);
        bus1.setDepartureTime(LocalTime.of(8, 30));
        bus1.setTripPrice(new java.math.BigDecimal("80.00"));

        String bus1Json = mockMvc.perform(post("/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bus1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();
        Long bus1Id = objectMapper.readTree(bus1Json).get("id").asLong();

        // 3) create bus #2 (120€ => discount)
        BusCreateRequest bus2 = new BusCreateRequest();
        bus2.setNumber("BUS-IT-002");
        bus2.setSeats(50);
        bus2.setDepartureTime(LocalTime.of(10, 0));
        bus2.setTripPrice(new java.math.BigDecimal("120.00"));

        String bus2Json = mockMvc.perform(post("/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bus2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();
        Long bus2Id = objectMapper.readTree(bus2Json).get("id").asLong();

        // 4) create reservation with 2 items
        ReservationItemRequest i1 = new ReservationItemRequest();
        i1.setBusId(bus1Id);
        i1.setTripDate(LocalDate.of(2025, 12, 30));

        ReservationItemRequest i2 = new ReservationItemRequest();
        i2.setBusId(bus2Id);
        i2.setTripDate(LocalDate.of(2025, 12, 31));

        ReservationCreateRequest resReq = new ReservationCreateRequest();
        resReq.setClientId(clientId);
        resReq.setItems(List.of(i1, i2));

        assertThat(bus1Id).isNotNull();
        assertThat(bus2Id).isNotNull();

        String payload = objectMapper.writeValueAsString(resReq);
        System.out.println(payload);
        String resJson = mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.totalAmount").exists())
                .andReturn().getResponse().getContentAsString();

        Long reservationId = objectMapper.readTree(resJson).get("id").asLong();

        // 5) pay reservation with PayPal
        PayReservationRequest payReq = new PayReservationRequest();
        payReq.setPaymentType(PaymentType.PAYPAL);
        PayPalDetails pp = new PayPalDetails();
        pp.setEmail("payer@example.com");
        payReq.setPaypal(pp);

        mockMvc.perform(post("/reservations/{id}/pay", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.reservationId").value(reservationId));

        // 6) list bills sorted by amount asc
        mockMvc.perform(get("/bills")
                        .param("sortBy", "amount")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").exists());
    }
}

