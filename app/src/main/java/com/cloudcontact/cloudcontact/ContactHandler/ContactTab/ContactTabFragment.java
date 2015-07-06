package com.cloudcontact.cloudcontact.ContactHandler.ContactTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcontact.cloudcontact.ContactHandler.FastScroller;
import com.cloudcontact.cloudcontact.R;


public class ContactTabFragment extends Fragment {
    RecyclerView recyclerView;
    ContactListDisplay displayAdapter;
    FastScroller fastScroller;

    /**
     * This is a method for Fragment.
     * You can do the same in onCreate or onRestoreInstanceState
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.contact_tab, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.contactList);
        displayAdapter = new ContactListDisplay(getActivity());
        recyclerView.setAdapter(displayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        fastScroller = (FastScroller) layout.findViewById(R.id.fastscroller);
        fastScroller.setRecyclerView(recyclerView);
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }


}
