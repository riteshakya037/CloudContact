package com.cloudcontact.cloudcontact.Parse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cloudcontact.cloudcontact.ContactHandler.ContactSingleCard;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ritesh on 7/4/2015.
 */
public class ParseHandler {
    public static String applicationID = "Vae4j5Ev1cehvXThm3LaZyp38gtrjZeWU1NeqcrV";
    public static String client_id = "FEsgiSRzTevjX8FVI9LltRDGrE6qEzQJl4BKXJ6m";


    public ArrayList<ContactSingleCard> getObjects(final Context c, final RecyclerView.Adapter listDisplay, String sortField) {
        final ArrayList<ContactSingleCard> list = new ArrayList<>();
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
                                               ContactSingleCard note = new ContactSingleCard(
                                                       post.getString(String.valueOf(ContactTable.NAME.getFieldName())),
                                                       post.getString(String.valueOf(ContactTable.PHONE_NO.getFieldName())));
                                               list.add(note);
                                           }
                                           Collections.sort(list, new Comparator<ContactSingleCard>() {
                                               @Override
                                               public int compare(ContactSingleCard card1, ContactSingleCard card2) {
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
