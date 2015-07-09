package com.cloudcontact.cloudcontact.ContactHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcontact.cloudcontact.ContactHandler.ContactTab.ContactTabFragment;
import com.cloudcontact.cloudcontact.ContactHandler.GroupTab.GroupTabFragment;
import com.cloudcontact.cloudcontact.R;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class ContactFragment extends Fragment implements MaterialTabListener {
    ViewPager tabPager = null;
    MaterialTabHost tabHost;
    TabAdaptor pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_frag, container, false);
        tabHost = (MaterialTabHost) v.findViewById(R.id.materialTabHost);
        tabPager = (ViewPager) v.findViewById(R.id.tabPager);
        pagerAdapter = new TabAdaptor(getChildFragmentManager());
        tabPager.setAdapter(pagerAdapter);
        tabPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
        return v;
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        tabPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }
}

class TabAdaptor extends FragmentStatePagerAdapter {

    public TabAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment tabFragment = null;
        switch (i) {
            case 0: {
                tabFragment = new ContactTabFragment();
                break;
            }
            case 1: {
                tabFragment = new GroupTabFragment();
                break;
            }
        }
        return tabFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String routineTitle = null;
        switch (position) {
            case 0: {
                routineTitle = "All Contacts";
                break;
            }
            case 1: {
                routineTitle = "Groups";
                break;
            }
        }
        return routineTitle;
    }
}