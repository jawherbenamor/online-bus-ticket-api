# Online Bus Ticket API

## Tech stack
- Java 21
- Spring Boot
- Spring Web / Data JPA
- H2 Database
- MapStruct
- JUnit 5 / Mockito
- MockMvc

## How to run
```bash
mvn clean spring-boot:run

## API Endpoints

### Clients
- `POST /clients`  
  Create a new client

- `GET /clients`  
  List all clients

- `GET /clients/{id}`  
  Get client by id

---

### Buses
- `POST /buses`  
  Create a new bus

- `GET /buses`  
  List all buses

- `GET /buses/{id}`  
  Get bus by id

---

### Reservations
- `POST /reservations`  
  Create a reservation (can contain multiple trips)

- `GET /reservations`  
  List all reservations

- `GET /reservations/{id}`  
  Get reservation by id

- `DELETE /reservations/{id}`  
  Delete a reservation

- `POST /reservations/{id}/pay`  
  Pay a reservation (payment simulated)

---

### Bills
- `GET /bills?sortBy=amount&direction=asc`  
  List bills with sorting
