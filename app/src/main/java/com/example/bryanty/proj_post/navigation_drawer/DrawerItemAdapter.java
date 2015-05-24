package com.example.bryanty.proj_post.navigation_drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bryanty.proj_post.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by BRYANTY on 24/05/2015.
 */
public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemAdapter.DrawerItemViewHolder>{

    private LayoutInflater inflater;
    List<DrawerItem> item= Collections.emptyList();

    public DrawerItemAdapter(Context context, List<DrawerItem> item){
        inflater= LayoutInflater.from(context);
        this.item= item;
    }

    @Override
    public DrawerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.row_drawer_item, parent, false);
        DrawerItemViewHolder viewHolder= new DrawerItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerItemViewHolder holder, int position) {
        DrawerItem currentItem= item.get(position);

        holder.drawerIcon.setImageResource(currentItem.drawerIcon);
        holder.drawerString.setText(currentItem.drawerString);

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    //view holder
    class DrawerItemViewHolder extends RecyclerView.ViewHolder{

        ImageView drawerIcon;
        TextView drawerString;

        public DrawerItemViewHolder(View itemView) {
            super(itemView);
            drawerIcon= (ImageView)itemView.findViewById(R.id.drawerIcon);
            drawerString= (TextView)itemView.findViewById(R.id.drawerString);

        }
    }
}
