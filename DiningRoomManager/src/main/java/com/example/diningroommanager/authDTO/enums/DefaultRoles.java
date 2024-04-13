package com.example.diningroommanager.authDTO.enums;

import java.util.Arrays;
import java.util.List;

public enum DefaultRoles {

    USER("USER_ROLE", "User"),
    ADMIN("ROLE_ADMIN", "Admin");

    private final String name;

    private final String display;

    DefaultRoles(String name, String display) {
        this.name = name;
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public String getDisplay() {
        return display;
    }


    @Override
    public String toString() {
        return getName();
    }

    public static List<String> getNames(DefaultRoles ... defaultRoles) {
        return Arrays.stream(defaultRoles).map(DefaultRoles::getName).toList();
    }
}
