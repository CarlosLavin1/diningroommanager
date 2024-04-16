package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
    @Positive(message = "Number of seats must be a positive number")
    @Min(value = 2, message = "Min number of seats per table is 2")
    @Max(value = 2, message = "Max number of seats per table is 12")
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

    @Override
    public String toString() {
        return "Table ID: " + this.id + ", Seats: " + this.numberOfSeats;
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
