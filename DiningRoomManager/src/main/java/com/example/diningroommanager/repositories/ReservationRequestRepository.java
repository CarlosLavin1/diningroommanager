package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.DiningTable;
import com.example.diningroommanager.entities.ReservationRequest;
import com.example.diningroommanager.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Integer> {
    List<ReservationRequest> getReservationRequestsBySeating_Id(int id);
    List<ReservationRequest> getReservationRequestsBySeating_Event_Id(int id);
    List<ReservationRequest> findByDiningTableId(int id);
    List<ReservationRequest> getReservationRequestsByStatus_Value(String value);
    List<ReservationRequest> getReservationRequestsByStatus_ValueAndSeating_Event_Id(String value, int id);

    List<ReservationRequest> findBySeatingAndDiningTable(Seating seating, DiningTable diningTable);

    @Query("SELECT r FROM ReservationRequest r WHERE r.seating.event.id = :eventId AND r.diningTable.id = :tableId")
    List<ReservationRequest> getReservationRequestsBySeating_Event_IdAndDiningTable
            (@Param("eventId") int eventId, @Param("tableId") int tableId);
}
