package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@Getter
@Setter
public class Event {
    private String idEvent, name, description, city;
    private LocalDate date;
    private LocalTime hour;

    private Place thePlace;

    private EventType eventType;
    private EventStatus eventStatus;
    private EventPolicy eventPolicy;

    public Event(String idEvent, String name, String description, String city, LocalDate date, LocalTime hour, Place thePlace, EventType eventType, EventStatus eventStatus, EventPolicy eventPolicy) {
        this.idEvent = idEvent;
        this.name = name;
        this.description = description;
        this.city = city;
        this.date = date;
        this.hour = hour;
        this.thePlace = thePlace;
        this.eventType = eventType;
        this.eventStatus = eventStatus;
        this.eventPolicy = eventPolicy;
    }


}
