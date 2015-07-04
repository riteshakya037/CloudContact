package com.cloudcontact.cloudcontact;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cloudcontact.cloudcontact.ContactHandler.ContactFragment;
import com.cloudcontact.cloudcontact.ContactHandler.ContactTab.ContactTabFragment;

public class MainActivity extends ActionBarActivity {
    Toolbar toolbar;
    private ContactFragment contactFragment;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing Toolbar
        toolbar = (Toolbar) findViewById(R.id.appBar);
        layout = (FrameLayout) findViewById(R.id.fragContainer);
        setSupportActionBar(toolbar);
        contactFragment = new ContactFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer, contactFragment, "Contact").setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.search, menu);

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

    public void hideToolBar(int dy) {
        if (toolbar.getTranslationY() < toolbar.getHeight() && toolbar.getTranslationY() > 0) {
            toolbar.animate().translationY(-dy).setInterpolator(new AccelerateInterpolator(2));
            layout.animate().translationY(-dy).setInterpolator(new AccelerateInterpolator(2));
        }
    }

    public void showToolBar(int dy) {
        if (toolbar.getTranslationY() > toolbar.getHeight() && toolbar.getTranslationY() < 0) {
            toolbar.animate().translationY(dy).setInterpolator(new DecelerateInterpolator(2));
            layout.animate().translationY(dy).setInterpolator(new DecelerateInterpolator(2));
        }
    }
}
