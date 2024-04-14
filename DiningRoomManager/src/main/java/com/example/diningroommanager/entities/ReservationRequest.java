package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class ReservationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationRequestId")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seating_id", foreignKey = @ForeignKey(name = "FK_ReservationRequest_Seating"))
    private Seating seating;

    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    private String email;
    @NotNull(message = "Group size is required")
    @Min(value = 2, message = "Minimum group size is 2 people")
    @Max(value = 200, message = "The maximum group size is 200 members")
    private Integer groupSize;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", foreignKey = @ForeignKey(name = "FK_RESERVATIONREQUEST_STATUS"))
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tableId", foreignKey = @ForeignKey(name = "FK_ReservationRequest_Table"))
    private DiningTable diningTable;

    public ReservationRequest(int id, Seating seating, String firstName, String lastName, String email, Integer groupSize, Status status, DiningTable diningTable) {
        this.id = id;
        this.seating = seating;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groupSize = groupSize;
        this.status = status;
        this.diningTable = diningTable;
    }

    public ReservationRequest(Seating seating, String firstName, String lastName, String email, Integer groupSize, Status status, DiningTable diningTable) {
        this.seating = seating;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groupSize = groupSize;
        this.status = status;
        this.diningTable = diningTable;
    }

    public ReservationRequest(int id, Seating seating, String firstName, String lastName, String email, Integer groupSize, Status status) {
        this.id = id;
        this.seating = seating;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groupSize = groupSize;
        this.status = status;
    }

    public ReservationRequest(Seating seating, String firstName, String lastName, String email, Integer groupSize, Status status) {
        this.seating = seating;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groupSize = groupSize;
        this.status = status;
    }

    public ReservationRequest(String firstName, String lastName, String email, Integer groupSize, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groupSize = groupSize;
        this.status = status;
    }

    public ReservationRequest(String firstName, String lastName, String email, Integer groupSize) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groupSize = groupSize;
    }

    public ReservationRequest(int id, String firstName, String lastName, String email, Integer groupSize) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groupSize = groupSize;
    }

    public ReservationRequest() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Seating getSeating() {
        return seating;
    }

    public void setSeating(Seating seating) {
        this.seating = seating;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Transient // not saved to the database
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
    }

    public DiningTable getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }

}
