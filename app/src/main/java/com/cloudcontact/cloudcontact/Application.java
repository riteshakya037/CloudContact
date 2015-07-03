package com.cloudcontact.cloudcontact;

import com.parse.Parse;

/**
 * Created by Ritesh on 7/1/2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "Vae4j5Ev1cehvXThm3LaZyp38gtrjZeWU1NeqcrV", "FEsgiSRzTevjX8FVI9LltRDGrE6qEzQJl4BKXJ6m");

    }
}
