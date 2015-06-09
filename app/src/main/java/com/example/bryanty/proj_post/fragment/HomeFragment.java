package com.example.bryanty.proj_post.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bryanty.proj_post.R;
import com.example.bryanty.proj_post.adapter.InformationAdapter;
import com.example.bryanty.proj_post.object.Information;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private RecyclerView recyclerView_information;
    private InformationAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        //initial recycler view information item
        recyclerView_information= (RecyclerView)view.findViewById(R.id.recyclerViewHome);
        recyclerView_information.setHasFixedSize(true);
        adapter= new InformationAdapter(getActivity(), getData());
        recyclerView_information.setAdapter(adapter);

        recyclerView_information.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    //get all drawer item data
    public List<Information> getData(){
        List<Information> item= new ArrayList<>();
        int[] icons= {R.mipmap.ic_home, R.mipmap.ic_post, R.mipmap.ic_bookmarks, R.mipmap.ic_subscription, R.mipmap.ic_settings};
        String[] titles= getResources().getStringArray(R.array.navigation_drawer_item);
        String[] content= getResources().getStringArray(R.array.navigation_drawer_item);

        for(int a=0; a< icons.length && a< titles.length; a++){
            Information currentItem= new Information();
            currentItem.userAvatar= icons[a];
            currentItem.userName= titles[a];
            currentItem.content= content[a];
            item.add(currentItem);
        }

        return item;
    }

}
