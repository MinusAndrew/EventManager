package co.edu.uniquindio.eventmanager.model;

import co.edu.uniquindio.eventmanager.model.Enums.EventPolicy;
import co.edu.uniquindio.eventmanager.model.Enums.EventStatus;
import co.edu.uniquindio.eventmanager.model.Enums.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Getter
@Setter
public class Event {
    private String idEvent, name, description, city;
    private LocalDateTime date;

    private Place thePlace;

    private EventType eventType;
    private EventStatus eventStatus;
    private EventPolicy eventPolicy;

    public Event(String idEvent, String name, String description, String city, LocalDateTime date, Place thePlace, EventType eventType, EventPolicy eventPolicy) {
        this.idEvent = idEvent;
        this.name = name;
        this.description = description;
        this.city = city;
        this.date = date;
        this.thePlace = thePlace;
        this.eventType = eventType;
        this.eventStatus = EventStatus.PUBLISHED;
        this.eventPolicy = eventPolicy;
    }

    public String notification() {
        return "¡¡¡Nuevo evento!!! : " + name +
                ", Descripcion:" + description +
                ", En la ciudad de " + city +
                ", el dia " + date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG,FormatStyle.SHORT)) + " no faltes!!!";
    }

    @Override
    public String toString() {
        return
                        "   ID: " + idEvent + "\n" +
                        "   Name: " + name + "\n" +
                        "   Status: " + eventStatus + "\n"+"\n";
    }
}
