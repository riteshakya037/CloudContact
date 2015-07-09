package com.cloudcontact.cloudcontact.Parse;

/**
 * Created by Ritesh on 7/8/2015.
 * Defines all the columns available in the parseObject
 */
public class ParseRow {
    private String groupLetter;
    private String createdAt;
    private String current_address;
    private String email;
    private String emergency_no;
    private String home_no;
    private String name;
    private String objectId;
    private String permanent_address;
    private String personal_email;
    private String phone_no;
    private String updatedAt;

    public ParseRow(String createdAt, String current_address, String email, String emergency_no, String home_no, String name, String objectId, String permanent_address, String personal_email, String phone_no, String updatedAt) {
        this.createdAt = nullCheck(createdAt);
        this.current_address = nullCheck(current_address);
        this.email = nullCheck(email);
        this.emergency_no = nullCheck(emergency_no).replaceAll("-", "");
        this.home_no = nullCheck(home_no).replaceAll("-", "");
        this.name = toTitleCase(nullCheck(name));
        this.objectId = nullCheck(objectId);
        this.permanent_address = nullCheck(permanent_address);
        this.personal_email = nullCheck(personal_email);
        this.phone_no = nullCheck(phone_no).replaceAll("-", "");
        this.updatedAt = nullCheck(updatedAt);
        this.groupLetter = name.substring(0, 1).toUpperCase();
    }

    private static String toTitleCase(String toBeCapped) {
        String[] tokens = toBeCapped.split("\\s");
        toBeCapped = "";

        for (int i = 0; i < tokens.length; i++) {
            char capLetter = Character.toUpperCase(tokens[i].charAt(0));
            toBeCapped += " " + capLetter + tokens[i].substring(1);
        }
        return toBeCapped.trim();
    }

    private String nullCheck(String str) {
        if (str == null)
            return "";
        else
            return str.trim();
    }

    public String getGroupLetter() {
        return groupLetter;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public String getEmail() {
        return email;
    }

    public String[] getEmergency_no() {
        return emergency_no.split("/");
    }

    public String[] getHome_no() {
        return home_no.split("/");
    }

    public String getName() {
        return name;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public String getPersonal_email() {
        return personal_email;
    }

    public String[] getPhone_no() {
        return phone_no.split("/");
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
