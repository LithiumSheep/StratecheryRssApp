package com.lithiumsheep.stratechery.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.lithiumsheep.stratechery.R;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public abstract class MaterialDrawerActivity extends AppCompatActivity implements
        Drawer.OnDrawerItemClickListener,
        AccountHeader.OnAccountHeaderProfileImageListener {

    private Drawer drawer;

    protected final void attachDrawer(Toolbar toolbar) {
        AccountHeader header =
                new AccountHeaderBuilder()
                        .withActivity(this)
                        .withHeaderBackground(R.drawable.stratechery_banner_large)
                        .withHeaderBackgroundScaleType(ImageView.ScaleType.FIT_CENTER)
                        .withOnAccountHeaderProfileImageListener(this)
                        .build();

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        new SectionDrawerItem().withName("Explore").withDivider(false),
                        new PrimaryDrawerItem().withName("Concepts").withIcon(CommunityMaterial.Icon.cmd_lightbulb_on),
                        new PrimaryDrawerItem().withName("Companies").withIcon(GoogleMaterial.Icon.gmd_business),
                        new PrimaryDrawerItem().withName("Topics").withIcon(CommunityMaterial.Icon.cmd_dictionary), // library, books, clipboard
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("About").withIcon(GoogleMaterial.Icon.gmd_info),
                        new SecondaryDrawerItem().withName("Settings").withIcon(GoogleMaterial.Icon.gmd_settings),
                        new SecondaryDrawerItem().withName("Support Development").withIcon(CommunityMaterial.Icon.cmd_heart),
                        new SecondaryDrawerItem().withName("Open Source").withIcon(CommunityMaterial.Icon.cmd_github_circle)
                )
                .withOnDrawerItemClickListener(this)
                .withSelectedItem(-1)
                .build();
    }

    @Override
    public final boolean onProfileImageClick(View view, IProfile profile, boolean current) {
        return false;
    }

    @Override
    public final boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
        return false;
    }

    @Override
    public final boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch (position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
        drawer.deselect();
        return false;
    }

    protected final Drawer getDrawer() {
        return drawer;
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
