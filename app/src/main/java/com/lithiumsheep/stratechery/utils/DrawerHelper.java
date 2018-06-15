package com.lithiumsheep.stratechery.utils;

import android.app.Activity;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

public class DrawerHelper {

    public static Drawer attach(Activity activity) {
        return new DrawerBuilder().withActivity(activity)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(1).withName("Home"),
                        new DividerDrawerItem())
                .build();
    }
}
