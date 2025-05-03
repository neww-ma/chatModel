package com.losstime.login.pojo;

/**
 * @author 马明
 */

public enum FriendsStatus {
    PENDING("pending"),
    ACCEPTED("accepted");

    private final String value;

    FriendsStatus(String value) {
            this.value = value;
    }

    public String getValue() {
            return value;
    }

    public static FriendsStatus fromValue(String value) {
        for (FriendsStatus status : FriendsStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + value);
    }
}
