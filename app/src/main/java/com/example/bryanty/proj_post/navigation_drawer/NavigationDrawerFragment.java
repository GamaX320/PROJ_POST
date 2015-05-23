package com.example.bryanty.proj_post.navigation_drawer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bryanty.proj_post.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserFirstTimeDrawer;
    private boolean mFromSavedInstanceState;
    public static final String PREF_FILE_NAME= "user_pref";
    public static final String USER_FIRST_TIME_DRAWER= "user_first_time_drawer";
    private View fragmentNavDrawer;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //read value from preference
        mUserFirstTimeDrawer= Boolean.getBoolean(readPreference(getActivity(), USER_FIRST_TIME_DRAWER, "false"));

        if(savedInstanceState!= null){
            mFromSavedInstanceState= true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    //setup all component inside navigation drawer- call this method to setup navigation drawer
    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar, int fragmentId){

        fragmentNavDrawer= getActivity().findViewById(fragmentId);
        mDrawerLayout= drawerLayout;

        //ActionBarDrawerToggle(activity,drawer layout, toolbar, string open, string close)
        mDrawerToggle= new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserFirstTimeDrawer){
                    mUserFirstTimeDrawer= true;
                    savePreference(getActivity(), USER_FIRST_TIME_DRAWER, mUserFirstTimeDrawer+"");
                }
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }
        };

        //check the user is first time visit or not
        if(!mUserFirstTimeDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(fragmentNavDrawer);
        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //to add navigation indicator icon with sync effect
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    //to saved first time user preference
    public static void savePreference(Context context, String name, String value){

        SharedPreferences sharedPreferences= context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply(); //editor.commit() apply() faster than commit() but apply() doesn't the file save success or not
    }

    //to read user preference
    public static String readPreference(Context context, String name, String value){
        SharedPreferences sharedPreferences= context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, value);
    }

}
