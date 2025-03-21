package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Entity
public class Layout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "layoutId")
    private Integer id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToMany(mappedBy = "layout", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DiningTable> diningTables;

    @OneToMany(mappedBy = "layout", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> events;

    public List<DiningTable> getDiningTables() {
        return diningTables;
    }

    public void setDiningTables(List<DiningTable> diningTables) {
        this.diningTables = diningTables;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Layout() {
    }

    public Layout(String name, String description, Date createdDate, List<DiningTable> diningTables, List<Event> events) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.diningTables = diningTables;
        this.events = events;
    }

    public Layout(Integer id, String name, String description, Date createdDate, List<DiningTable> diningTables, List<Event> events) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.diningTables = diningTables;
        this.events = events;
    }

    // Automatically create the date
    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }

    // Automatically update the date
    @PreUpdate
    public void preUpdate() {
        this.createdDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<DiningTable> getTables() {
        return diningTables;
    }

    public void setTables(List<DiningTable> diningTables) {
        this.diningTables = diningTables;
    }
}
