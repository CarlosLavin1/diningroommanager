package com.example.diningroommanager.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statusId")
    private int id;
    private String value;
    private String display;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private List<ReservationRequest> reservationRequests;

    public Status(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public Status() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<ReservationRequest> getReservationRequests() {
        return reservationRequests;
    }

    public void setReservationRequests(List<ReservationRequest> reservationRequests) {
        this.reservationRequests = reservationRequests;
    }
}
