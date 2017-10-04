package com.example.meoryou.myapplication.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.meoryou.myapplication.DataBase.Item;
import com.example.meoryou.myapplication.R;
import com.example.meoryou.myapplication.ToDoList.ToDo;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by me or you on 9/26/2017.
 */

public class ItemAdapter  extends RecyclerView.Adapter<ItemHolder>{
    // source
    Context context;
    ArrayList<Item> items;

    // constructor
    public ItemAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items= items;
    }

    //inflate holder method
    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item,parent,false);
        // false because we are not attaching this view to some other view group
        return new ItemHolder(v);
    }


    // bind method
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        // gets data
        final String name = items.get(position).getName();
        final String desc = items.get(position).getDescription();
        final String date = items.get(position).getDateFormat();
        // bind information
        holder.nameText.setText(name);
        holder.desText.setText(desc);
        holder.dateTxt.setText(date);


        // when click item
        holder.setItemOnClickListener(new ItemClickListener(){
            public void onItemClick(int pos){
                Intent intent =  new Intent(context,ToDo.class); // goes to different activity
                intent.putExtra("DESC",desc); // send the description at the particular position
                intent.putExtra("POS",pos);// send the position of the item
                intent.putExtra("NAM", name); // send the name og the item
                context.startActivity(intent);// activate
            }
        });
    }

    // number item
    @Override
    public int getItemCount() {
        return items.size();
    }
}
