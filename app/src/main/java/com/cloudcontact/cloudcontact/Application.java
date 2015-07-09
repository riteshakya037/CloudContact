package com.cloudcontact.cloudcontact;

import com.cloudcontact.cloudcontact.Parse.ParseHandler;
import com.parse.Parse;

/**
 * Created by Ritesh on 7/1/2015.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, ParseHandler.applicationID, ParseHandler.client_id);

    }
}
