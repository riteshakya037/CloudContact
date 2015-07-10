package com.cloudcontact.cloudcontact;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cloudcontact.cloudcontact.BottomSheet.BottomSheet;
import com.cloudcontact.cloudcontact.BottomSheet.BottomSheetCallback;
import com.cloudcontact.cloudcontact.ContactHandler.ContactFragment;
import com.cloudcontact.cloudcontact.Parse.ParseRow;

public class MainActivity extends ActionBarActivity implements BottomSheetCallback {
    Toolbar toolbar;
    private ContactFragment contactFragment;
    View fragContainer, bottomSheetView;
    BottomSheet bottomSheet;

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
        }

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
        }
    }


}
