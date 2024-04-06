package com.example.diningroommanager.dtos;

import com.example.diningroommanager.entities.Event;

import java.util.ArrayList;
import java.util.List;

public class DTOConverters {
    public static EventDetailsDTO convertToEventDetailsDTO(Event event){
        return new EventDetailsDTO(event.getId(), event.getStartDate(), event.getEndDate(), event.getSeatingDuration(),
                event.getName(), event.getDescription(), event.getPrice(), event.getSeatings().stream().map(s -> s.getId()).toList(),
                event.getLayout().getId(), event.getMenu().getId());
    }

    public static List<EventDetailsDTO> convertToEventDetailsDTOList(List<Event> events){
        var items = new ArrayList<EventDetailsDTO>();
        for (var item : events){
            items.add(convertToEventDetailsDTO(item));
        }
        return items;
    }
}
