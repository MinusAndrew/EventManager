package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@Getter
public class Event {
    private String idEvent, name, description, city;
    private LocalDate date;
    private LocalTime hour;

    private Place thePlace;

    private EventManager eventManager;
    private EventStatus eventStatus;
    private EventPolicy eventPolicy;

    public Event(String idEvent, String name, String description, String city, LocalDate date, LocalTime hour, Place thePlace, EventManager eventManager, EventStatus eventStatus, EventPolicy eventPolicy) {
        this.idEvent = idEvent;
        this.name = name;
        this.description = description;
        this.city = city;
        this.date = date;
        this.hour = hour;
        this.thePlace = thePlace;
        this.eventManager = eventManager;
        this.eventStatus = eventStatus;
        this.eventPolicy = eventPolicy;
    }


}
