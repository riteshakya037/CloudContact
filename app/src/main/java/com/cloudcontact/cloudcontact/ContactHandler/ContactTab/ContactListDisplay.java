package com.cloudcontact.cloudcontact.ContactHandler.ContactTab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudcontact.cloudcontact.BottomSheet.BottomSheetCallback;
import com.cloudcontact.cloudcontact.FastScroller.BubbleTextGetter;
import com.cloudcontact.cloudcontact.MainActivity;
import com.cloudcontact.cloudcontact.Parse.ContactTable;
import com.cloudcontact.cloudcontact.Parse.ParseHandler;
import com.cloudcontact.cloudcontact.Parse.ParseRow;
import com.cloudcontact.cloudcontact.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shwshrestha on 7/3/15.
 */
public class ContactListDisplay extends RecyclerView.Adapter<ContactListDisplay.ContactsViewHolder> implements BubbleTextGetter {
    private final LayoutInflater inflator;
    ArrayList<ParseRow> cardList;
    Context context;
    BottomSheetCallback callback;

    ContactListDisplay(final Context context, String filter) {
        this.context = context;
        this.callback = ((BottomSheetCallback) context);
        inflator = LayoutInflater.from(context);
        cardList = new ArrayList<>();
        ParseHandler parseHandler = new ParseHandler();
        cardList = parseHandler.getObjects(context, this, ContactTable.NAME.getFieldName(), filter);
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.single_contact, parent, false);
        ContactsViewHolder holder = new ContactsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {
        holder.name.setText(cardList.get(position).getName());
        holder.ph_no.setText(cardList.get(position).getPhone_no()[0]);
        if (position == 0 ||
                !cardList.get(position).getGroupLetter().equals(cardList.get(position - 1).getGroupLetter())) {
            holder.groupLetter.setText(cardList.get(position).getGroupLetter());
            holder.divider.setVisibility(View.VISIBLE);
        } else {
            holder.divider.setVisibility(View.GONE);
            holder.groupLetter.setText("");
        }
        holder.single_card.setOnClickListener(new View.OnClickListener() {
            @Override //Call method on mainActivity to show BottomSheet
            public void onClick(View view) {
                if (context instanceof MainActivity)
                    callback.showBottomSheet(cardList.get(position));
            }
        });
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
        RelativeLayout single_card;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txtTitle);
            ph_no = (TextView) itemView.findViewById(R.id.ph_no);
            groupLetter = (TextView) itemView.findViewById(R.id.groupLetter);
            divider = itemView.findViewById(R.id.divider);
            single_card = (RelativeLayout) itemView.findViewById(R.id.sinlgeContact);
        }
    }

}

