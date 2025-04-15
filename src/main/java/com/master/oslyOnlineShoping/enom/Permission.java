package com.master.oslyOnlineShoping.enom;
public enum Permission {
    USER("user"),
    ADMIN("Admin"),
    MANAGER("Manager"),
    ASSOCIATE("Associate"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    ORDER_READ("order:read"),
    ORDER_WRITE("order:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

