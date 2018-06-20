package com.lithiumsheep.stratechery.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public abstract class MaterialDrawerActivity extends AppCompatActivity {

    private Drawer drawer;

    protected final void attachDrawer(Toolbar toolbar) {
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem(),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                )
                .withSelectedItem(-1)
                .build();
    }

    protected abstract boolean shouldMoveTaskToBackOnBackPressed();

    @Override
    public void onBackPressed() {
        if (drawer != null) {
            if (drawer.isDrawerOpen()) {
                drawer.closeDrawer();
            } else {
                if (shouldMoveTaskToBackOnBackPressed()) {
                    moveTaskToBack(true);
                } else {
                    super.onBackPressed();
                }
            }
        }
    }
}
