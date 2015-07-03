package com.cloudcontact.cloudcontact.ContactHandler;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcontact.cloudcontact.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        query.orderByDescending("name");
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
                                               singleCard note = new singleCard(post.getString("name"));
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
        holder.title.setText(cardList.get(position).getTitle());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }



    class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public ContactsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
        }
    }

}

class singleCard{
    String title;
    public String getTitle() {
        return title;
    }
    public singleCard(String title) {
        this.title = title;
    }
}
