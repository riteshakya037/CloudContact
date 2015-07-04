package com.cloudcontact.cloudcontact.ContactHandler.GroupTab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudcontact.cloudcontact.ContactHandler.ContactSingleCard;
import com.cloudcontact.cloudcontact.Parse.ContactTable;
import com.cloudcontact.cloudcontact.Parse.ParseHandler;
import com.cloudcontact.cloudcontact.R;

import java.util.ArrayList;

/**
 * Created by shwshrestha on 7/3/15.
 */
public class GroupListDisplay extends RecyclerView.Adapter<GroupListDisplay.ContactsViewHolder> {
    private final LayoutInflater inflator;
    ArrayList<ContactSingleCard> cardList;
    Context context;

    GroupListDisplay(final Context context) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        cardList = new ArrayList<>();
        ParseHandler parseHandler = new ParseHandler();
        cardList = parseHandler.getObjects(context, this, ContactTable.NAME.getFieldName());
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

