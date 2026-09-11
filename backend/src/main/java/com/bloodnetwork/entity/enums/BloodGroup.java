package com.bloodnetwork.entity.enums;

public enum BloodGroup {
    A_POS("A+"),
    A_NEG("A-"),
    B_POS("B+"),
    B_NEG("B-"),
    AB_POS("AB+"),
    AB_NEG("AB-"),
    O_POS("O+"),
    O_NEG("O-");

    private final String displayValue;

    BloodGroup(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static BloodGroup fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        String clean = value.trim().toUpperCase();
        for (BloodGroup bg : BloodGroup.values()) {
            if (bg.displayValue.equalsIgnoreCase(clean) || bg.name().equalsIgnoreCase(clean)) {
                return bg;
            }
        }
        throw new IllegalArgumentException("Unknown blood group: " + value);
    }
}
