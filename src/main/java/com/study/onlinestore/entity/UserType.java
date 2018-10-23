package com.study.onlinestore.entity;

public enum UserType {
    GUEST("GUEST"), USER("USER"), ADMIN("ADMIN");

    private final String name;

    UserType(String name) {
        this.name = name;
    }

    public static UserType getByName(String name) {
        UserType[] values = values();
        for (UserType userType : values) {
            if (userType.getName().equalsIgnoreCase(name)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("No user type for name: " + name);
    }

    public String getName() {
        return name;
    }
}
