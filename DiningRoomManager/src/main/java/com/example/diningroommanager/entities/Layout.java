package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
public class Layout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "layoutId")
    private int id;

    @NotBlank(message = "Name is required")
    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToMany(mappedBy = "layout", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DiningTable> diningTables;

    public Layout() {
    }

    public Layout(String name, String description, Date createdDate, List<DiningTable> diningTables) {
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.diningTables = diningTables;
    }

    public Layout(int id, String name, String description, Date createdDate, List<DiningTable> diningTables) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
        this.diningTables = diningTables;
    }

    // Automatically create the date
    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
