package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.ReservationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Integer> {
    List<ReservationRequest> getReservationRequestsBySeating_Id(int id);
    List<ReservationRequest> getReservationRequestsBySeating_Event_Id(int id);
    List<ReservationRequest> getReservationRequestsByStatus_Value(String value);
    List<ReservationRequest> getReservationRequestsByStatus_ValueAndSeating_Event_Id(String value, int id);
}
