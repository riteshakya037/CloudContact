package com.cloudcontact.cloudcontact.ContactHandler;

/**
 * Created by Ritesh on 7/4/2015.
 */
public class ContactSingleCard {
    String name;
    String ph_no;
    String groupLetter="";

    public String getName() {
        return name;
    }

    public String getPh_no() {
        return ph_no;
    }

    public ContactSingleCard(String name, String ph_no) {
        this.name = toTitleCase(name);
        this.ph_no = ph_no;
        if (name!=null)
        this.groupLetter = name.substring(0, 1).toUpperCase();
    }

    public String getGroupLetter() {
        return groupLetter;
    }

    private static String toTitleCase(String toBeCapped) {
        if (toBeCapped != null) {
            String[] tokens = toBeCapped.split("\\s");
            toBeCapped = "";

            for (int i = 0; i < tokens.length; i++) {
                char capLetter = Character.toUpperCase(tokens[i].charAt(0));
                toBeCapped += " " + capLetter + tokens[i].substring(1);
            }
            return toBeCapped.trim();
        }
        return "";
    }
}
