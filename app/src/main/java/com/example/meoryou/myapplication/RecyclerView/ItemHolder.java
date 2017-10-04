package com.example.meoryou.myapplication.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.meoryou.myapplication.R;

/**
 * Created by me or you on 9/26/2017.
 */

public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // these can be access in the same package
    TextView nameText;
    TextView desText;
    TextView dateTxt;
    ItemClickListener itemClickListener;
    // constructor
    public ItemHolder(View itemView) {
        super(itemView);
        // item in cardView
        nameText= (TextView) itemView.findViewById(R.id.name_text);
        desText=(TextView) itemView.findViewById(R.id.desc_text);
        dateTxt=(TextView)itemView.findViewById(R.id.date);
        // for item click
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(getLayoutPosition()); // position of item
            }

    public void setItemOnClickListener(ItemClickListener itemClickListener){
    this.itemClickListener = itemClickListener;
    }
}
