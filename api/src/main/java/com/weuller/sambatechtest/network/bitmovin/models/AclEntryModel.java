package com.weuller.sambatechtest.network.bitmovin.models;

public class AclEntryModel {

    private String permission;

    public AclEntryModel(String permission) {
        this.permission = permission;
    }

    public AclEntryModel() {
        this.permission = "PUBLIC_READ";
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
