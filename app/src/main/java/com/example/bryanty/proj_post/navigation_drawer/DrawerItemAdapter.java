package com.example.bryanty.proj_post.navigation_drawer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bryanty.proj_post.R;
import com.example.bryanty.proj_post.TestActivity;

import java.util.Collections;
import java.util.List;

/**
 * Created by BRYANTY on 24/05/2015.
 */
public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemAdapter.DrawerItemViewHolder>{

    private LayoutInflater inflater;
    List<DrawerItem> item= Collections.emptyList();
    private Context context;

//    private DrawerItemClickListener drawerItemClickListener;

    public DrawerItemAdapter(Context context, List<DrawerItem> item){
        inflater= LayoutInflater.from(context);
        this.item= item;
        this.context= context;
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

    //delete drawer item from RecyclerView
    public void delete(int position){
        item.remove(position);
        notifyItemRemoved(position);
    }

//    //set drawer item onClick listener
//    public void setItemClickListener(DrawerItemClickListener drawerItemClickListener){
//        this.drawerItemClickListener= drawerItemClickListener;
//    }

    //view holder
//    class DrawerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    class DrawerItemViewHolder extends RecyclerView.ViewHolder{
        ImageView drawerIcon;
        TextView drawerString;

        public DrawerItemViewHolder(View itemView) {
            super(itemView);
            drawerIcon= (ImageView)itemView.findViewById(R.id.drawerIcon);
            drawerString= (TextView)itemView.findViewById(R.id.drawerString);

            //initial drawer item set onClick listener
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View v) {
//            //start an Activity
//            //context.startActivity(new Intent(context, TestActivity.class));
//
//            /*
//            * there are not best pratice direct use adapter to do onClick stuff
//            * we should implement an interface to do our onClick stuff
//            * */
//            if(drawerItemClickListener!= null){
//                drawerItemClickListener.drawerItemClick(v, getPosition());
//            }
//
//        }
    }

//    //drawer item click interface
//    public interface DrawerItemClickListener{
//        public void drawerItemClick(View view, int position);
//    }
}
