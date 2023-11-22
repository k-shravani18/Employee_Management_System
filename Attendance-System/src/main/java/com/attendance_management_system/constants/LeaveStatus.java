package com.attendance_management_system.constants;

public enum LeaveStatus {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DENIED("DENIED");

    private final String value;

    LeaveStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
