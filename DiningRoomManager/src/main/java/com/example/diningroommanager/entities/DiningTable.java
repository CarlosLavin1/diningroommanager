package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "dining_table")
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tableId")
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layoutId", foreignKey = @ForeignKey(name = "FK_Layout_Table"))
    private Layout layout;

    @NotNull
    private int numberOfSeats;

    public DiningTable() {
    }

    public DiningTable(Layout layout, int numberOfSeats) {
        this.layout = layout;
        this.numberOfSeats = numberOfSeats;
    }

    public DiningTable(int id, Layout layout, int numberOfSeats) {
        this.id = id;
        this.layout = layout;
        this.numberOfSeats = numberOfSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
