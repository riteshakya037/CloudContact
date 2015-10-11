package com.cloudcontact.cloudcontact;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cloudcontact.cloudcontact.BottomSheet.BottomSheet;
import com.cloudcontact.cloudcontact.BottomSheet.BottomSheetCallback;
import com.cloudcontact.cloudcontact.ContactHandler.ContactFragment;
import com.cloudcontact.cloudcontact.Parse.ParseRow;

public class MainActivity extends AppCompatActivity implements BottomSheetCallback, SearchView.OnQueryTextListener {
    Toolbar toolbar;
    private ContactFragment contactFragment;
    View fragContainer;
    BottomSheet bottomSheet;
    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing Toolbar
        toolbar = (Toolbar) findViewById(R.id.appBar);
        fragContainer = findViewById(R.id.fragContainer);
        setSupportActionBar(toolbar);

        //initialize first fragment
        contactFragment = new ContactFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer, contactFragment, "Contact").setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement our filter logic
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
//
//    public void hideToolBar(int dy) {
//        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
//        fragContainer.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2));
//    }
//
//    public void showToolBar(int dy) {
//        fragContainer.animate().translationY(toolbar.getHeight()).setInterpolator(new DecelerateInterpolator(2));
//        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
//    }

    @Override
    public void showBottomSheet(ParseRow parseRow) {
        if (bottomSheet == null || !bottomSheet.isShowing()) {
            bottomSheet = new BottomSheet(this, parseRow);
            bottomSheet.show();
            bottomSheet.layoutFiller.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheet.dismiss();
                }
            });
        }
    }


}
