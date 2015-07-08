package com.cloudcontact.cloudcontact.Parse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ritesh on 7/4/2015.
 * Handles all parse related activities
 */
public class ParseHandler {
    public static String applicationID = "Vae4j5Ev1cehvXThm3LaZyp38gtrjZeWU1NeqcrV";
    public static String client_id = "FEsgiSRzTevjX8FVI9LltRDGrE6qEzQJl4BKXJ6m";


    public ArrayList<ParseRow> getObjects(final Context c, final RecyclerView.Adapter listDisplay, String sortField) {
        final ArrayList<ParseRow> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
        query.orderByAscending(sortField);
        query.setLimit(1000);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> parseObjects, ParseException e) {
                                       if (e == null) {
                                           list.clear();
                                           for (ParseObject post : parseObjects) {
                                               ParseRow note = new ParseRow(
                                                       post.getString(String.valueOf(ContactTable.CREATED_AT.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.CURRENT_ADDRESS.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.EMAIL.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.EMERGENCY_NO.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.HOME_NO.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.NAME.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.OBJECT_ID.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.PERMANENT_ADDRESS.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.PERSONAL_EMAIL.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.PHONE_NO.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.UPDATED_AT.getFieldName())));
                                               list.add(note);
                                           }
                                           Collections.sort(list, new Comparator<ParseRow>() {
                                               @Override
                                               public int compare(ParseRow card1, ParseRow card2) {
                                                   return card1.getName().toUpperCase().compareTo(card2.getName().toUpperCase());
                                               }
                                           });
                                           listDisplay.notifyDataSetChanged();
                                       } else {
                                           if (e.getCode() == 100) {
                                               Toast.makeText(c, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                                           } else
                                               Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
                                       }
                                   }
                               }
        );
        return list;
    }

}
