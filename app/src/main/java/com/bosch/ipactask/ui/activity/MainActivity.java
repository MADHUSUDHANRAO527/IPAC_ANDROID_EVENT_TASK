package com.bosch.ipactask.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bosch.ipactask.R;
import com.bosch.ipactask.db.database.AppDatabase;

import com.bosch.ipactask.ui.fragment.EventsListFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       replaceFragment(new EventsListFragment());
    }
    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
    // Replace current Fragment with the destination Fragment.
    public void replaceFragment(Fragment destFragment) {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.frame_layout, destFragment);
        fragmentTransaction.addToBackStack(destFragment.getClass().getSimpleName());
        // Commit the Fragment replace action.
        fragmentTransaction.commitAllowingStateLoss();
    }
    public void addFragment(Fragment destFragment,Bundle bundle) {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        destFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame_layout, destFragment);
        fragmentTransaction.addToBackStack(destFragment.getClass().getSimpleName());
        // Commit the Fragment replace action.
        fragmentTransaction.commitAllowingStateLoss();
    }
}
