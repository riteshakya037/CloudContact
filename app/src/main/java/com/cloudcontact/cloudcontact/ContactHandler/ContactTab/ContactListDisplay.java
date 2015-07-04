package com.cloudcontact.cloudcontact.ContactHandler.ContactTab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudcontact.cloudcontact.ContactHandler.BubbleTextGetter;
import com.cloudcontact.cloudcontact.ContactHandler.ContactSingleCard;
import com.cloudcontact.cloudcontact.Parse.ContactTable;
import com.cloudcontact.cloudcontact.Parse.ParseHandler;
import com.cloudcontact.cloudcontact.R;

import java.util.ArrayList;

/**
 * Created by shwshrestha on 7/3/15.
 */
public class ContactListDisplay extends RecyclerView.Adapter<ContactListDisplay.ContactsViewHolder> implements BubbleTextGetter {
    private final LayoutInflater inflator;
    ArrayList<ContactSingleCard> cardList;
    Context context;
    String groupCheck = null;
    boolean once;

    ContactListDisplay(final Context context) {
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
        if (position == 0 ||
                !cardList.get(position).getGroupLetter().equals(cardList.get(position - 1).getGroupLetter())) {
            holder.groupLetter.setText(cardList.get(position).getGroupLetter());
            groupCheck = cardList.get(position).getGroupLetter();
            holder.divider.setVisibility(View.VISIBLE);
        } else {
            holder.divider.setVisibility(View.GONE);
            holder.groupLetter.setText("");
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    @Override
    public String getTextToShowInBubble(final int pos) {
        if (cardList.size() != 0)
            return Character.toString(cardList.get(pos).getName().toUpperCase().charAt(0));
        else return "";
    }


    class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView name, ph_no, groupLetter;
        View divider;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txtTitle);
            ph_no = (TextView) itemView.findViewById(R.id.ph_no);
            groupLetter = (TextView) itemView.findViewById(R.id.groupLetter);
            divider = itemView.findViewById(R.id.divider);
        }
    }

}

