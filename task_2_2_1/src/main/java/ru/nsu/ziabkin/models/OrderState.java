package ru.nsu.ziabkin.models;

/**
 * Enum represents order states.
 */
public enum OrderState {
    PENDING("In Queue"),
    COOKING("Cooking"),
    WAITING_FOR_WAREHOUSE("Waiting for warehouse space"),
    READY_FOR_DELIVERY("In warehouse"),
    DELIVERING("In delivery"),
    DELIVERED("Delivered"),;

    private final String description;
    OrderState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
