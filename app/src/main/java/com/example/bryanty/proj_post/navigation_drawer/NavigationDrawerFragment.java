package com.example.bryanty.proj_post.navigation_drawer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
//import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bryanty.proj_post.MainActivity;
import com.example.bryanty.proj_post.R;
import com.example.bryanty.proj_post.TestActivity;
import com.example.bryanty.proj_post.fragment.BookmarksFragment;
import com.example.bryanty.proj_post.fragment.HomeFragment;
import com.example.bryanty.proj_post.fragment.PostFragment;
import com.example.bryanty.proj_post.fragment.SubscriptionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
//public class NavigationDrawerFragment extends Fragment implements DrawerItemAdapter.DrawerItemClickListener{
public class NavigationDrawerFragment extends Fragment{

    //navigation drawer
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserFirstTimeDrawer;
    private boolean mFromSavedInstanceState;
    public static final String PREF_FILE_NAME= "user_pref";
    public static final String USER_FIRST_TIME_DRAWER= "user_first_time_drawer";
    private View fragmentNavDrawer;

    //drawer item
    private RecyclerView recyclerView_drawerItem;
    private DrawerItemAdapter adapter;

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
        View view= inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        //initial recycler view drawer item
        recyclerView_drawerItem= (RecyclerView)view.findViewById(R.id.drawerItem);
        adapter= new DrawerItemAdapter(getActivity(), getData());
        //onClick listener
//        adapter.setItemClickListener(this);
        recyclerView_drawerItem.setAdapter(adapter);
        recyclerView_drawerItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        //onItemTouch listener
        recyclerView_drawerItem.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recyclerView_drawerItem, new DrawerItemTouchListener() {
            @Override
            public void drawerItemOnClick(View view, int position) {
                //implement your item onClick listener here
                Toast.makeText(getActivity(), "onClick "+position,Toast.LENGTH_SHORT).show();
                getContent(position);
                mDrawerLayout.closeDrawers();

            }

            @Override
            public void drawerItemOnLongClick(View view, int position) {
                //implement your item onLongClick listener here
                Toast.makeText(getActivity(), "onLongClick "+position,Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
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

    //get all drawer item data
    // public static List<DrawerItem> getData(){
    public List<DrawerItem> getData(){
        List<DrawerItem> item= new ArrayList<>();
        int[] icons= {R.mipmap.ic_home, R.mipmap.ic_post, R.mipmap.ic_bookmarks, R.mipmap.ic_subscription, R.mipmap.ic_settings};
        //String[] titles= {"Home", "My Posts", "Bookmarks", "Subscriptions", "Settings"};
        String[] titles= getResources().getStringArray(R.array.navigation_drawer_item);

        for(int a=0; a< icons.length && a< titles.length; a++){
            DrawerItem currentItem= new DrawerItem();
            currentItem.drawerIcon= icons[a];
            currentItem.drawerString= titles[a];
            item.add(currentItem);
        }

        return item;
    }

    //determine which content should load
    public  void getContent(int position){
        Fragment fragment= null;
        FragmentManager fm= getActivity().getFragmentManager();

        switch (position){
            case 0:
                fragment= new HomeFragment();
                break;
            case 1:
                fragment= new PostFragment();
                break;
            case 2:
                fragment= new BookmarksFragment();
                break;
            case 3:
                fragment= new SubscriptionFragment();
                break;
        }

        fm.beginTransaction().replace(R.id.container, fragment).commit();
        //set action bar title based on the fragment selected
        ((MainActivity) getActivity()).setTitle(getResources().getStringArray(R.array.navigation_drawer_item)[position]);
    }


//    //drawer item onClick listener
//    @Override
//    public void drawerItemClick(View view, int position) {
//        Toast.makeText(getActivity(), "Item clicked at "+position, Toast.LENGTH_SHORT).show();
//        //start an Activity
//        startActivity(new Intent(getActivity(), TestActivity.class));
//    }

    //drawer item onItemTouch class
    class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private DrawerItemTouchListener drawerItemTouchListener;

        public RecyclerViewTouchListener(Context context, RecyclerView recyclerView, final DrawerItemTouchListener drawerItemTouchListener) {
            this.drawerItemTouchListener= drawerItemTouchListener;
            gestureDetector= new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                   // return super.onSingleTapUp(e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View childView= recyclerView_drawerItem.findChildViewUnder(e.getX(), e.getY());

                    if(childView!= null && drawerItemTouchListener!= null){

                        drawerItemTouchListener.drawerItemOnLongClick(childView, recyclerView_drawerItem.getChildPosition(childView));

                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View childView= rv.findChildViewUnder(e.getX(), e.getY());

            if(childView!= null && drawerItemTouchListener!= null && gestureDetector.onTouchEvent(e)){

                drawerItemTouchListener.drawerItemOnClick(childView, rv.getChildPosition(childView));

            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    }

    public interface DrawerItemTouchListener{
        public void drawerItemOnClick(View view, int position);
        public void drawerItemOnLongClick(View view, int position);
    }
}
