package com.cloudcontact.cloudcontact.Parse;

/**
 * Created by Ritesh on 7/4/2015.
 */
public enum ContactTable {
    CREATED_AT("createdAt"),
    CURRENT_ADDRESS("current_address"),
    EMAIL("email"),
    EMERGENCY_NO("emergency_no"),
    HOME_NO("home_no"),
    NAME("name"),
    OBJECT_ID("objectId"),
    PERMANENT_ADDRESS("permanent_address"),
    PERSONAL_EMAIL("personal_email"),
    PHONE_NO("phone_no"),
    UPDATED_AT("updatedAt"),;
    private String fieldName;

    ContactTable(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }



}
