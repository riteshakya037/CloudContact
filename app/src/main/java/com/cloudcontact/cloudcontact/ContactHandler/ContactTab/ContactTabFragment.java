package com.cloudcontact.cloudcontact.ContactHandler.ContactTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cloudcontact.cloudcontact.FastScroller.FastScroller;
import com.cloudcontact.cloudcontact.Parse.ContactTable;
import com.cloudcontact.cloudcontact.Parse.ParseHandler;
import com.cloudcontact.cloudcontact.Parse.ParseRow;
import com.cloudcontact.cloudcontact.R;

import java.util.ArrayList;


public class ContactTabFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    ContactListDisplay displayAdapter;
    FastScroller fastScroller;
    private String filter="";

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
        setHasOptionsMenu(true);
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onQueryTextChange(String query) {
        displayAdapter.getData(query);
        displayAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
