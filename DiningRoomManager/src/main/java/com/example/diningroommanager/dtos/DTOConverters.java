package com.example.diningroommanager.dtos;

import com.example.diningroommanager.entities.Event;
import com.example.diningroommanager.entities.Menu;

import java.util.ArrayList;
import java.util.List;

public class DTOConverters {
    public static EventDetailsDTO convertToEventDetailsDTO(Event event){
        return new EventDetailsDTO(event.getId(), event.getStartDate(), event.getEndDate(), event.getSeatingDuration(),
                event.getName(), event.getDescription(), event.getPrice(), event.getSeatings().stream().map(s -> new SeatingDTO(s.getId(), s.getStartDateAndTime())).toList(),
                convertToMenuDTO(event.getMenu()));
    }

    public static List<EventDetailsDTO> convertToEventDetailsDTOList(List<Event> events){
        var items = new ArrayList<EventDetailsDTO>();
        for (var item : events){
            items.add(convertToEventDetailsDTO(item));
        }
        return items;
    }

    public static MenuDTO convertToMenuDTO(Menu m){
        return new MenuDTO(m.getId(), m.getName(), m.getDescription(), m.getDateCreated(), m.getMenuItems().stream().map(menuItem -> new MenuItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getDescription())).toList());
    }
}
