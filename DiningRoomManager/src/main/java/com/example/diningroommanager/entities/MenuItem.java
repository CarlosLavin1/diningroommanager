package com.example.diningroommanager.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuItemId")
    private int id;

    @NotBlank(message = "Name is required")
    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId", foreignKey = @ForeignKey(name = "FK_MenuItem_Menu"))
    private Menu menu;

    public MenuItem() {
    }

    public MenuItem(int id, String name, String description, Menu menu) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menu = menu;
    }

    public MenuItem(String name, String description, Menu menu) {
        this.name = name;
        this.description = description;
        this.menu = menu;
    }

    @Override
    public String toString()
    {
        return "Menu ItemID: " + this.id + ", " +
                "Name: " + this.name + ", " +
                "Description: " + this.description;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
