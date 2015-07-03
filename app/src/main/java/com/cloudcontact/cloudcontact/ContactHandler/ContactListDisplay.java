package com.cloudcontact.cloudcontact.ContactHandler;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcontact.cloudcontact.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shwshrestha on 7/3/15.
 */
public class ContactListDisplay extends RecyclerView.Adapter<ContactListDisplay.ContactsViewHolder> {
    private final LayoutInflater inflator;
    ArrayList<singleCard> cardList;
    Context context;

    ContactListDisplay(final Context c) {
        this.context = c;
        inflator = LayoutInflater.from(c);
        cardList = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Contacts");
        query.orderByAscending("name");
        final ProgressDialog dlg = new ProgressDialog(c);
        dlg.setTitle("Please wait.");
        dlg.setMessage("Loading Data");
        dlg.show();
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> parseObjects, ParseException e) {
                                       if (e == null) {
                                           cardList.clear();
                                           for (ParseObject post : parseObjects) {
                                               singleCard note = new singleCard(post.getString("name"),post.getString("phone_no"));
                                               cardList.add(note);
                                           }
                                           notifyDataSetChanged();
                                           dlg.dismiss();
                                       } else {
                                           if (e.getCode() == 100) {
                                               Toast.makeText(c, "Unable To Connect to Internet", Toast.LENGTH_LONG).show();
                                           } else
                                               Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
                                       }
                                   }
                               }
        );
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.single_contact, parent, false);
        ContactsViewHolder holder = new ContactsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, int position) {
        holder.name.setText(cardList.get(position).getName());
        holder.ph_no.setText(cardList.get(position).getPh_no());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }



    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView name, ph_no;
        public ContactsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txtTitle);
            ph_no = (TextView) itemView.findViewById(R.id.ph_no);
        }
    }

}

class singleCard{
    String name;
    String ph_no;
    public String getName() {
        return name;
    }
    public String getPh_no() {
        return ph_no;
    }
    public singleCard(String name, String ph_no) {
        this.name = name;
        this.ph_no = ph_no;
    }
}
