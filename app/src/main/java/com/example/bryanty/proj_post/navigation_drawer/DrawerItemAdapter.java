package com.example.bryanty.proj_post.navigation_drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bryanty.proj_post.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by BRYANTY on 24/05/2015.
 */
public class DrawerItemAdapter extends RecyclerView.Adapter<DrawerItemAdapter.DrawerItemViewHolder>{

    private LayoutInflater inflater;
    List<DrawerItem> item= Collections.emptyList();
    private Context context;

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

    public void delete(int position){
        item.remove(position);
        notifyItemRemoved(position);
    }

    //view holder
    class DrawerItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView drawerIcon;
        TextView drawerString;

        public DrawerItemViewHolder(View itemView) {
            super(itemView);
            drawerIcon= (ImageView)itemView.findViewById(R.id.drawerIcon);
            drawerString= (TextView)itemView.findViewById(R.id.drawerString);

            //initial drawer item set onClick listener
            drawerIcon.setOnClickListener(this);
            //drawerString.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Toast.makeText(context, "Item clicked at "+getPosition(), Toast.LENGTH_SHORT).show();
            delete(getPosition());
        }
    }
}
