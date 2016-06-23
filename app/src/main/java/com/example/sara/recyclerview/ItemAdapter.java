package com.example.sara.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import static com.example.sara.recyclerview.R.*;

/**
 * Created by Sara on 17/06/16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private ArrayList<Item> mItemList;
    private Context mContext;

    public ItemAdapter( ArrayList<Item> itemList, Context context) {
        this.mItemList=itemList;
        this.mContext= context;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(layout.item_card_view, parent, false);
        ItemViewHolder holder=new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) { //0 -> 0-1
        final Item item=mItemList.get(position);

        holder.tvDesc.setText(item.desc);

        Picasso.with(mContext)
                .load(item.image)
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.ivImage);

        holder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(mContext, SubActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("item", item);
                mContext.startActivity(in);

                //Toast.makeText(mContext,"desc: " + item.desc, Toast.LENGTH_SHORT).show();
                //Toast.makeText(mContext, "Position"+ position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {

        if (mItemList!=null){
            return mItemList.size();
        }

        return 0;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public CardView cvItem;
        public TextView tvDesc;
        public ImageView ivImage;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cvItem=(CardView)itemView.findViewById(id.cvItem);
            tvDesc=(TextView)itemView.findViewById(id.tvDesc);
            ivImage=(ImageView)itemView.findViewById(id.ivImage);
        }
    }

}
